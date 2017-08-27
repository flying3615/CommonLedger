package commonledger

import com.intuit.ipp.data.Account
import com.intuit.ipp.data.AccountSubTypeEnum
import com.intuit.ipp.data.AccountTypeEnum
import org.apache.commons.lang.StringUtils

class CompanyController {

    CompanyService companyService

    def index() {}

    def list() {

        def queryStr = params.query ?: "select * from account"
        def queryResult = companyService.query(queryStr, session.realmId, session.access_token)
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
        render view: 'accountForm', model: [account: account, accountTypeList: AccountTypeEnum.values().collect {
            it.value()
        },accountSubTypeList: AccountSubTypeEnum.values().collect {
            it.value()
        }, activeList: [true, false], error: params.error]
    }

    def saveOrUpdateAccount() {
        Account account = new Account()
        account.id = params.id
        account.accountType = AccountTypeEnum.fromValue(params.accType)
        account.accountSubType = params.accountSubType
        account.active = params.active
        account.name = params.name

        String error_message = ""
        try{
            //check balance format
            account.currentBalance = new BigDecimal(params.currentBalance)
        }catch (e){
            error_message = "Balance is no number ${e}"
        }

        if (StringUtils.isNotEmpty(error_message)) {
            render(view: 'accountForm', model: [account: account, accountTpyeList: AccountTypeEnum.values().collect {
                it.value()
            }, activeList: [true, false], error: error_message])
        } else {
            def (success,message) = companyService.saveOrUpdateAccount(account, session.realmId, session.access_token)
            println("after save update...$success  $message")
            if(success){
                redirect(action: 'list')
            }else{
                render view: 'accountForm', model: [account: account, accountTypeList: AccountTypeEnum.values().collect {
                    it.value()
                },accountSubTypeList: AccountSubTypeEnum.values().collect {
                    it.value()
                }, activeList: [true, false], error: message]
            }
        }
    }

}
