<!doctype html>
<html>
<head>
    <title>Page Not Found</title>
    <meta name="layout" content="main">
</head>

<body>
<div class="container">
    <a href="/company/accountForm" class="btn btn-info">Add</a>

    <table>
        <th>
            <tr>
                <td>ID</td>
                <td>Name</td>
                <td>Type</td>
                <td>Balance</td>
                <td>SubType</td>
                <td>Active</td>
            </tr>
        </th>
        <g:each in="${queryResp}" status="i" var="account">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>${account?.Id}</td>
                <td>${account?.FullyQualifiedName}</td>
                <td>${account?.AccountType}</td>
                <td>${account?.CurrentBalance}</td>
                <td>${account?.AccountSubType}</td>
                <td>${account?.Active}</td>
                <td><a href="/company/accountForm/${account?.Id}" class="btn btn-warning">Update</a></td>
            </tr>
        </g:each>
    </table>
</div>

</body>
</html>