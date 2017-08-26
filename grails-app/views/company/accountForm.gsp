<!doctype html>
<html>
<body>
<div class="container">

<g:form name="accountForm" url="[controller: 'company', action: 'saveOrUpdateAccount']">
    <g:hiddenField name="id" value="${account?.id}"/>

    <div class="form-group row">
        <label for="accName">Account Name</label>
        <g:textField id="accName" class="form-control" placeholder="Account Name" name="name" value="${account?.name}"/>
    </div>

    <div class="form-group row">
        <label for="type">Account Type</label>
        <g:textField id="accType" class="form-control" placeholder="Account Type" name="type"
                     value="${account?.accountType}"/>
    </div>

    <div class="form-group row">
        <label for="currentBalance">Account Balance</label>
        <g:textField id="currentBalance" class="form-control" name="currentBalance" value="${account?.currentBalance}"/>
    </div>

    <div class="form-group row">
        <label for="accountSubType">Account SubType</label>
        <g:textField id="accountSubType" class="form-control" name="acctNum" value="${account?.accountSubType}"/>
    </div>

    <div class="form-group row">
        <label for="active">Active</label>
        <g:textField id="active" class="form-control" name="active" value="${account?.active}"/>
    </div>

    <div class="form-group row">
        <div class="col-md-offset-6 col-md-6" style="margin-top: 20px">
            <g:submitButton name="Add Account" class="btn btn-info"/>
        </div>
    </div>
</g:form>
</div>

</body>

<head>
    <title>Page Not Found</title>
    <meta name="layout" content="main">
</head>
</html>