<!doctype html>
<html>
<head>
    <title>Page Not Found</title>
    <meta name="layout" content="main">
</head>

<body>
<div class="row">
    <div class="col-md-4 col-md-offset-3" style="position: absolute;top: 100px;">
        <g:form name="listAccForm" url="[controller: 'company', action: 'list']">
            <div class="form-group">
                <label for="compid">Company ID</label>
                <g:textField id="compid" class="form-control" name="compid" value="${session.realmId}"/>
            </div>

            <div class="form-group">
                <label for="query">Query String</label>
                <g:textField id="query" class="form-control" placeholder="query string" name="query"
                             value="select * from account"/>
            </div>

            <div class="col-md-offset-5 col-md-2">
                <g:submitButton name="Query" class="btn btn-info"/>
            </div>

        </g:form>

    </div>
</div>
</body>
</html>