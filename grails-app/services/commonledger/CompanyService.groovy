package commonledger

import grails.converters.JSON
import grails.transaction.Transactional
import groovyx.net.http.RESTClient

@Transactional
class CompanyService {

    final RESTClient CLIENT = new RESTClient()
    def oAuth2Configuration


    def listQuery(String query, String realmId, String accessToken) {

        try {
            def companyInfoEndpoint = String.format("%s/v3/company/%s/query?query=%s", oAuth2Configuration.accountingAPI, realmId, URLEncoder.encode(query))
            def response = CLIENT.get(
                    uri: companyInfoEndpoint,
                    headers: [
                            Accept         : "application/json",
                            "Authorization": "Bearer ${accessToken}"
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
}
