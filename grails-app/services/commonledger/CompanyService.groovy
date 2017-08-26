package commonledger

import com.intuit.ipp.data.Account
import com.intuit.ipp.data.AccountTypeEnum
import grails.transaction.Transactional
import groovyx.net.http.RESTClient
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder

@Transactional
class CompanyService {

    final RESTClient CLIENT = new RESTClient()
    def oAuth2Configuration


    def query(String query, String realmId, String accessToken) {

        try {
            def companyInfoEndpoint = String.format("%s/v3/company/%s/query?query=%s", oAuth2Configuration.accountingAPI, realmId, URLEncoder.encode(query))
            def response = CLIENT.get(
                    uri: companyInfoEndpoint,
                    headers: [
                            Authorization: "Bearer ${accessToken}"
                    ]
            )

            if (response.status == 200) {
                return [error: false, message: response.data.QueryResponse]
            } else if (response.status == 401) {
                //todo refresh token
                println "call for new token"
            } else {
                return [error: true, message: "http response code is not 200"]
            }
        } catch (ex) {
            return [error: true, message: ex.toString()]
        }

    }


    def getResult(HttpResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
        StringBuffer result = new StringBuffer()
        String line = ""
        while ((line = rd.readLine()) != null) {
            result.append(line)
        }
        return result
    }

    /**
     * Account Body
     *{"Name": "Accounts Payable (A/P)",
     "SubAccount": false,
     "FullyQualifiedName": "Accounts Payable (A/P)",
     "Active": true,
     "Classification": "Liability",
     "Description": "Description added during update.",
     "AccountType": "Accounts Payable",
     "AccountSubType": "AccountsPayable",
     "CurrentBalance": -1091.23,
     "CurrentBalanceWithSubAccounts": -1091.23,
     "domain": "QBO",
     "sparse": false,
     "Id": "33",
     "SyncToken": "0",
     "MetaData": {"CreateTime": "2014-09-12T10:12:02-07:00",
     "LastUpdatedTime": "2015-06-30T15:09:07-07:00"}}
     * @param account
     * @param realmId
     * @param accessToken
     * @return
     */

    def saveOrUpdateAccount(Account account, String realmId, String accessToken) {

        def id = account.id
        def name = account.name
        def accountSubType = account.accountSubType
        def currentBalance = account.currentBalance
        def accType = account.accountType.value()
        def active = account.active

        println("$id, $name, $accountSubType, $currentBalance, $accType, $active ")
        HttpClient CLIENT = HttpClientBuilder.create().build()
        def response
        if (account.id) {
            String json = '{"Name": "'+name+'","Active": "'+active+'","AccountSubType": "'+accountSubType+'","sparse": true,"Id": "'+id+'","SyncToken": "0","AccountType": "'+accType+'","CurrentBalance": '+currentBalance+'}'
            StringEntity entity = new StringEntity(json)
            def updateEndpoint = "${oAuth2Configuration.accountingAPI}/v3/company/${realmId}/account?operation=update&minorversion=4"
            HttpPost httpPost = new HttpPost(updateEndpoint)
            httpPost.setEntity(entity)
            httpPost.setHeader("Accept", "application/json")
            httpPost.setHeader("Content-type", "application/json")
            httpPost.setHeader("Authorization", "Bearer ${accessToken}")
            response = CLIENT.execute(httpPost)
            println getResult(response)

        } else {
            //save
            String json = '{"Name": "'+name+'","Active": "'+active+'","AccountSubType": "'+accountSubType+'","AccountType": "'+accType+'","CurrentBalance": '+currentBalance+'}'
            StringEntity entity = new StringEntity(json)
            def saveEndpoint = "${oAuth2Configuration.accountingAPI}/v3/company/${realmId}/account"
            HttpPost httpPost = new HttpPost(saveEndpoint)
            httpPost.setEntity(entity)
            httpPost.setHeader("Accept", "application/json")
            httpPost.setHeader("Content-type", "application/json")
            httpPost.setHeader("Authorization", "Bearer ${accessToken}")
            response = CLIENT.execute(httpPost)
            println getResult(response)

            println "after save"
        }
        if (response.getStatusLine().getStatusCode() == 200) {
            println "update save success"
            return true
        } else {
            println "update save failed"
            return false
        }

    }

    def getAccount(String accountID, String realmId, String accessToken) {
        Account account = new Account()
        def accountEndPoint = "${oAuth2Configuration.accountingAPI}/v3/company/${realmId}/account/${accountID}"
        def response = CLIENT.get(
                uri: accountEndPoint,
                headers: [
                        Accept         : "application/json",
                        "Authorization": "Bearer ${accessToken}"
                ]
        )
        def accountJSON = response.data.Account
        account.id = accountJSON.Id
        account.name = accountJSON.Name
        account.active = accountJSON.Active
        account.accountType = AccountTypeEnum.fromValue(accountJSON.AccountType)
        account.currentBalance = accountJSON.CurrentBalance
        account.accountSubType = accountJSON.AccountSubType
        return account

    }


}
