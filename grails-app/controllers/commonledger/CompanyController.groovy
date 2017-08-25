package commonledger

class CompanyController {

    CompanyService companyService

    def index() {}

    def list() {
        def queryResult = companyService.listQuery("Select * From Account", session.realmId, session.access_token)
        if(!queryResult.error){
            render view: 'list', model: [queryResp: queryResult.message?.Account]
        }else{
            render view: 'error', model: [message:queryResult.message]
        }
    }
}
