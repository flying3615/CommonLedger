// Place your Spring DSL code here
beans = {
    oAuth2Configuration(OAuth2Configuration)
    httpHelper(HttpHelper){
        oAuth2Configuration = oAuth2Configuration
    }
}
