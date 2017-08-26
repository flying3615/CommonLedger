package commonledger

import com.intuit.ipp.data.Account
import com.intuit.ipp.data.AccountClassificationEnum
import com.intuit.ipp.data.AccountTypeEnum
import org.apache.commons.lang.RandomStringUtils

class CompanyController {

    CompanyService companyService

    def index() {}

    def list() {
        def queryResult = companyService.query("Select * From Account", session.realmId, session.access_token)
        if (!queryResult.error) {
            render view: 'list', model: [queryResp: queryResult.message?.Account]
        } else {
            render view: 'error', model: [message: queryResult.message]
        }
    }

    def accountForm() {
        Account account
        if (params.id) {
            account = companyService.getAccount(params.id, session.realmId, session.access_token)

        } else {
            account = new Account()
        }

        render view: 'accountForm', model: [account: account]
    }

    def saveOrUpdateAccount() {
        println params
        //http client save or update
        redirect(action: 'list')
    }

}
