import com.fasterxml.jackson.databind.ObjectMapper
import grails.converters.JSON
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.apache.tomcat.util.codec.binary.Base64
import org.grails.web.json.JSONObject

class HttpHelper {

    OAuth2Configuration oAuth2Configuration

    def addHeader(post){
        def base64ClientIdSec = Base64.encodeBase64String(
                (oAuth2Configuration.clientID
                        + ":" +
                        oAuth2Configuration.clientSecret).getBytes())
        post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
        post.setHeader("Authorization", "Basic " + base64ClientIdSec)
        post.setHeader("Accept", "application/json")
        return post
    }


    def makeUrl(String scope,String csrfToken){
        try {
            def discoveryDocumentReq = new URL(oAuth2Configuration.discoveryAPI)

            JSONObject respJSON = JSON.parse(discoveryDocumentReq.newReader())
            oAuth2Configuration.authEndPoint = respJSON.authorization_endpoint
            oAuth2Configuration.tokenEndPoint = respJSON.token_endpoint

            def url = "${oAuth2Configuration.authEndPoint}?" +
                    "client_id=${oAuth2Configuration.clientID}" +
                    "&response_type=code&scope=${URLEncoder.encode(scope, "UTF-8")}" +
                    "&redirect_uri=${URLEncoder.encode(oAuth2Configuration.redirectUri, "UTF-8")}" +
                    "&state=$csrfToken"

            return url
        } catch (UnsupportedEncodingException e) {
            println("Exception while preparing url for redirect ", e)
        }
        return null
    }

    def getCSRFToken(){
        def csrfToken = UUID.randomUUID().toString()
        return csrfToken
    }


    def retrieveBearerTokens(String auth_code) {
        def post = new HttpPost(oAuth2Configuration.tokenEndPoint)
        List<NameValuePair> urlParameters = new ArrayList<>()
        urlParameters.add(new BasicNameValuePair("code", auth_code))
        urlParameters.add(new BasicNameValuePair("redirect_uri", oAuth2Configuration.redirectUri))
        urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"))
        post = this.addHeader(post)
        post.setEntity(new UrlEncodedFormEntity(urlParameters))
        HttpResponse response = HttpClientBuilder.create().build().execute(post)

        if (response.getStatusLine().getStatusCode() != 200) {
            println("failed getting access token")
            return null
        }
        JSONObject jsonObject = convertResponseToJSON(response)
        println("Response Json $jsonObject")

        String access_token = jsonObject.getString("access_token")
        String refresh_token = jsonObject.getString("refresh_token")

        return [access_token,refresh_token]
    }


    def convertResponseToJSON(HttpResponse response){
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
        StringBuffer result = new StringBuffer()
        String line
        while ((line = rd.readLine()) != null) {
            result.append(line)
        }
        return new JSONObject(result.toString())
    }

}
