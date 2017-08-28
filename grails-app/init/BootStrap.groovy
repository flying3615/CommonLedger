import grails.converters.JSON
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder

class BootStrap {

    def init = { servletContext ->
        environments {
            production {
                servletContext.setAttribute("env", "prod")
            }
            development {
                servletContext.setAttribute("env", "dev")
            }
        }
    }
    def destroy = {
    }


}
