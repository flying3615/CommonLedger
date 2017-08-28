<!doctype html>
<html>
<head>
    <title>Account Form</title>
    <meta name="layout" content="main">
</head>

<body>
<div class="container">
    <g:if test="${error}">
        <div class="alert alert-danger" role="alert">
            <strong>Oh snap!</strong> ${error}
        </div>
    </g:if>


    <g:form name="accountForm" url="[controller: 'company', action: 'saveOrUpdateAccount']" style="position: absolute; left: 35%">
        <g:hiddenField name="id" value="${account?.id}"/>

        <div class="form-group row">
            <label for="accName" class="col-md-2 col-form-label">Account Name</label>

            <div class="col-md-10">
                <g:textField id="accName" class="form-control" placeholder="Account Name" name="name"
                             value="${account?.name}"/>
            </div>
        </div>

        <div class="form-group row">
            <label for="accType" class="col-md-2 col-form-label">Account Type</label>

            <div class="col-md-10">
                <g:select id="accType" name="accType" value="${account?.accountType?.value()}"
                          from="${accountTypeList}"/>
            </div>
        </div>

        <div class="form-group row">
            <label for="currentBalance" class="col-md-2 col-form-label">Account Balance</label>

            <div class="col-md-10">
                <g:textField id="currentBalance" class="form-control" name="currentBalance"
                             value="${account?.currentBalance}"/>
            </div>
        </div>

        <div class="form-group row">
            <label for="accountSubType" class="col-md-2 col-form-label">Account SubType</label>

            <div class="col-md-10">
                <g:select id="accountSubType" name="accountSubType" value="${account?.accountSubType}"
                          from="${accountSubTypeList}"/>
            </div>

        </div>

        <div class="form-group row">
            <label for="active" class="col-md-2 col-form-label">Active</label>

            <div class="col-md-10">
                <g:select id="active" name="active" value="${account?.active}" from="${activeList}"/>
            </div>
        </div>

        <div class="form-group row">
            <div class="col-md-2 col-md-offset-5">
                <g:submitButton name="${account?.id != null ? "Update" : "Add"} Account" class="btn btn-info"/>
            </div>
        </div>
    </g:form>

</div>

</body>

</html>