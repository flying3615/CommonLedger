import grails.core.GrailsApplication
import org.springframework.beans.factory.annotation.Value

class OAuth2Configuration {


    String authEndPoint
    String tokenEndPoint

    @Value('${intuit.c2qbScope}')
    String c2qbScope

    @Value('${intuit.OAuth2AppRedirectUri}')
    String redirectUri

    @Value('${intuit.OAuth2AppClientId}')
    String clientID

    @Value('${intuit.OAuth2AppClientSecret}')
    String clientSecret

    @Value('${intuit.discoveryAPI}')
    String discoveryAPI

    @Value('${intuit.IntuitAccountingAPIHost}')
    String accountingAPI
}
