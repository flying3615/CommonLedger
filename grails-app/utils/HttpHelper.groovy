import groovyx.net.http.RESTClient
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.tomcat.util.codec.binary.Base64
import org.grails.web.json.JSONObject

import java.lang.reflect.Method

import static groovyx.net.http.ContentType.URLENC

class HttpHelper {

    OAuth2Configuration oAuth2Configuration

    final RESTClient CLIENT = new RESTClient()

    def makeUrl(String scope, String csrfToken) {

        def response = CLIENT.get(uri: oAuth2Configuration.discoveryAPI)
        if (response.status == 200) {
            oAuth2Configuration.authEndPoint = response.data.authorization_endpoint
            oAuth2Configuration.tokenEndPoint = response.data.token_endpoint

            def url = "${oAuth2Configuration.authEndPoint}?" +
                    "client_id=${oAuth2Configuration.clientID}" +
                    "&response_type=code&scope=${URLEncoder.encode(scope, "UTF-8")}" +
                    "&redirect_uri=${URLEncoder.encode(oAuth2Configuration.redirectUri, "UTF-8")}" +
                    "&state=$csrfToken"

            return url
        } else {
            println "Cannot get discovery api"
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
     * use HttpClient to post raw json string
     * @param ur
     * @param json
     * @param realmId
     * @param accessToken
     * @return
     */
    def postRawJson(String url,String json, String accessToken) {
        HttpClient CLIENT = HttpClientBuilder.create().build()
        StringEntity entity = new StringEntity(json)
        HttpPost httpPost = new HttpPost(url)
        httpPost.setEntity(entity)
        httpPost.setHeader("Accept", "application/json")
        httpPost.setHeader("Content-type", "application/json")
        httpPost.setHeader("Authorization", "Bearer ${accessToken}")
        return CLIENT.execute(httpPost)
    }

    def getCSRFToken() {
        def csrfToken = UUID.randomUUID().toString()
        return csrfToken
    }


    def retrieveBearerTokens(String auth_code) {
        def base64ClientIdSec = Base64.encodeBase64String((oAuth2Configuration.clientID + ":" + oAuth2Configuration.clientSecret).getBytes())
        def response = CLIENT.post(
                uri: oAuth2Configuration.tokenEndPoint,
                body: [
                        code        : auth_code,
                        redirect_uri: oAuth2Configuration.redirectUri,
                        grant_type  : "authorization_code"
                ],
                headers: [
                        "Authorization": "Basic ${base64ClientIdSec}",
                        "Accept"       : "application/json"
                ],
                requestContentType: URLENC

        )


        if (response.status == 200) {
            String access_token = response.data.access_token
            String refresh_token = response.data.refresh_token
            return [access_token, refresh_token]
        }
        return null


    }


}
