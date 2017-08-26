<!doctype html>
<html>
<body>
<div class="container">
    <g:if test="${error}">
        <div class="alert alert-danger" role="alert">
            <strong>Oh snap!</strong> ${error}
        </div>
    </g:if>


    <g:form name="accountForm" url="[controller: 'company', action: 'saveOrUpdateAccount']">
    <g:hiddenField name="Id" value="${account?.id}"/>

    <div class="form-group">
        <label for="accName">Account Name</label>
        <g:textField id="accName" class="form-control" placeholder="Account Name" name="name" value="${account?.name}"/>
    </div>

    <div class="form-group">
        <label for="accType">Account Type</label>
        <g:select id="accType" name="accType" value="${account?.accountType?.value()}" from="${accountTpyeList}"/>
    </div>

    <div class="form-group">
        <label for="currentBalance">Account Balance</label>
        <g:textField id="currentBalance" class="form-control" name="currentBalance" value="${account?.currentBalance}"/>
    </div>

    <div class="form-group">
        <label for="accountSubType">Account SubType</label>
        <g:textField id="accountSubType" class="form-control" name="accountSubType" value="${account?.accountSubType}"/>
    </div>

    <div class="form-group">
        <label for="active">Active</label>
        <g:select id="active" name="active" value="${account?.active}" from="${activeList}"/>
    </div>

    <div class="form-group">
        <div class="col-md-offset-6 col-md-6" style="margin-top: 20px">
            <g:submitButton name="${account?.id!=null?"Update":"Add"} Account" class="btn btn-info"/>
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