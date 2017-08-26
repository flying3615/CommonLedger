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
        if(!queryResult.error){
            render view: 'list', model: [queryResp: queryResult.message?.Account]
        }else{
            render view: 'error', model: [message:queryResult.message]
        }
    }

    def accountForm(){

        Account account = new Account()
        account.setName("Ba" + RandomStringUtils.randomAlphanumeric(7))
        account.setSubAccount(false)
        account.setFullyQualifiedName(account.getName())
        account.setActive(true)
        account.setClassification(AccountClassificationEnum.ASSET)
        account.setAccountType(AccountTypeEnum.BANK)
        account.setCurrentBalance(new BigDecimal("0"))
        account.setCurrentBalanceWithSubAccounts(new BigDecimal("0"))
        account.setTxnLocationType("FranceOverseas")
        account.setAcctNum("B" + RandomStringUtils.randomAlphanumeric(6))

        render view:'accountForm', model: [account:account]
    }

}
