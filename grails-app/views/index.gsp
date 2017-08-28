<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>

<div class="svg" role="presentation">
    <div class="grails-logo-container">
        <asset:image src="grails-cupsonly-logo-white.svg" class="grails-logo"/>
    </div>
</div>

<div id="content" role="main" style="position: relative">
    <section class="row colset-2-its" style="position: absolute; left: 15%;">
        <h1>Welcome to Grails</h1>

        <p>
            Congratulations, you have successfully started your first Grails application! At the moment
            this is the default page, feel free to modify it to either redirect to a controller or display
            whatever content you may choose. Below is a list of controllers that are currently deployed in
            this application.
        </p>

        <div class="col-md-offset-5 col-md-2">
            <a href="/company/index" role="button" class="btn btn-primary">Accounts Management</a>
            <br>
            <div id="controllers" role="navigation">
                <h2>Available Controllers:</h2>
                <ul>
                    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName }}">
                        <li class="controller">
                            ${c.fullName}
                        </li>
                    </g:each>
                </ul>
            </div>
        </div>

    </section>
</div>

</body>
</html>
