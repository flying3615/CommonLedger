package commonledger

import grails.transaction.Transactional
import groovyx.net.http.RESTClient

@Transactional
class CompanyService {

    final RESTClient CLIENT = new RESTClient()
    def oAuth2Configuration


    def listQuery(String query, String realmId, String accessToken) {

        def companyInfoEndpoint = String.format("%s/v3/company/%s/query?query=%s", oAuth2Configuration.accountingAPI, realmId, URLEncoder.encode(query))
        def response = CLIENT.get(
                uri: companyInfoEndpoint,
                headers: [
                        Accept         : "application/json",
                        "Authorization": "Bearer ${accessToken}"
                ]
        )

        if (response.status == 200) {
            return response.data.QueryResponse
        } else {
            println("failed getting companyInfo")
        }
    }
}
