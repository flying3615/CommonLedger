package commonledger

class OAuth2Controller {


    def httpHelper
    def oAuth2Configuration

    def index() {
        println "redirect to connect"
//        redirect(uri:'connect')
    }

    def connect() {
        session.csrfToken = httpHelper.getCSRFToken()
        redirect(uri: httpHelper.makeUrl(oAuth2Configuration.c2qbScope, session.csrfToken))
    }

    def disconnect() {
        session.realmId = null
        session.auth_code = null
        session.access_token = null
        session.refresh_token = null
        redirect (uri: '/')
    }


    def oauth2redirect() {
        //set session auth code and realmID from callback parameters
        if (session.csrfToken == params.state) {
            session.realmId = params.realmId
            session.auth_code = params.code

            Tuple2 tokens = httpHelper.retrieveBearerTokens(params.code)
            if(tokens==null)render view:'error',model:[message:'Cannot get token']

            def (String access_token,String refresh_token) = tokens

            session.access_token = access_token
            session.refresh_token = refresh_token
        }else{
            render view:'error',model:[message:'CSRF Wrong']
        }

        redirect(controller:'company',action:'index')
    }

}
