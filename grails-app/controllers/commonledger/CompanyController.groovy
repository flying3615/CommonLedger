package commonledger

class CompanyController {

    CompanyService companyService

    def index() {}

    def list() {
        def queryResult = companyService.listQuery("Select * From Account", session.realmId, session.access_token)
        render view: 'list', model: [queryResp: queryResult?.Account]
    }
}
