<!doctype html>
<html>
<head>
    <title>Page Not Found</title>
    <meta name="layout" content="main">
</head>
<body>

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
            <tr class="${(i%2)==0?'even':'odd'}">
                <td>${account?.Id}</td>
                <td>${account?.FullyQualifiedName}</td>
                <td>${account?.AccountType}</td>
                <td>${account?.CurrentBalance}</td>
                <td>${account?.AccountSubType}</td>
                <td>${account?.Active}</td>
            </tr>
        </g:each>
    </table>


</body>
</html>