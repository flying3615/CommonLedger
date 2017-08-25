package commonledger

import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.grails.web.json.JSONObject

class CompanyController {

    def oAuth2Configuration
    def httpHelper
    CompanyService companyService
    HttpClient CLIENT = HttpClientBuilder.create().build();

    def index() {}

    def list() {
//        String companyInfoEndpoint = String.format("%s/v3/company/%s/companyinfo/%s", oAuth2Configuration.accountingAPI, session.realmId, session.realmId)
        String companyInfoEndpoint = String.format("%s/v3/company/%s/query?query=%s", oAuth2Configuration.accountingAPI, session.realmId, URLEncoder.encode("Select * From Account"))
        println companyInfoEndpoint
        HttpGet companyInfoReq = new HttpGet(companyInfoEndpoint)

        companyInfoReq.setHeader("Accept", "application/json")
        String accessToken = (String) session.getAttribute("access_token")
        companyInfoReq.setHeader("Authorization", "Bearer " + accessToken)
        HttpResponse response = CLIENT.execute(companyInfoReq)

        println("Response Code : ${response.getStatusLine().getStatusCode()}")

        if (response.getStatusLine().getStatusCode() != 200){
            println("failed getting companyInfo")
            return new JSONObject().put("response","Failed").toString()
        }

        render httpHelper.convertResponseToJSON(response)
    }
}
