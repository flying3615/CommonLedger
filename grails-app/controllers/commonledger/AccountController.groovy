package commonledger

class AccountController {


    def httpHelper
    def oAuth2Configuration

    def index() {
        println("index view!!!")
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

            def (String access_token,String refresh_token) = httpHelper.retrieveBearerTokens(params.code)

            session.access_token = access_token
            session.refresh_token = refresh_token
        }
        redirect(controller:'company',action:'index')
    }

}
