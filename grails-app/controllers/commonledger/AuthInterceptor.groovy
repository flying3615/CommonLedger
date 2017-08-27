package commonledger


class AuthInterceptor {

    AuthInterceptor() {
        match(controller:"company")
    }


    boolean before() {
        if(!session.access_token){
            redirect(controller:'OAuth2',action: 'connect')
        }
        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
