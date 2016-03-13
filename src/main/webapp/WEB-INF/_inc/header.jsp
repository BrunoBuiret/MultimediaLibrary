<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta name="author" content="Bruno Buiret" />
        <title>
            Médiathèque Polytech
            <c:if test="${not empty _page_title}">
                » ${_page_title}
            </c:if>
        </title>
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" media="screen" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous" />
        <c:url value="/css/general.css" var="_url" />
        <link rel="stylesheet" type="text/css" href="${fn:escapeXml(_url)}" />
        <c:if test="${not empty _page_stylesheets}">
            ${_page_stylesheets}
        </c:if>
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>
        <nav class="navbar navbar-default navbar-static-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <c:url value="/index.jsp" var="_url" />
                    <a class="navbar-brand" href="${fn:escapeXml(_url)}">Médiathèque Polytech</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li<c:if test="${not empty _page_current && _page_current == 'home'}"> class="active"</c:if>>
                            <c:url value="/index.jsp" var="_url" />
                            <a href="${fn:escapeXml(_url)}">
                                Accueil
                            </a>
                        </li>
                        <li class="dropdown<c:if test="${not empty _page_current && fn:startsWith(_page_current, 'works_')}"> active</c:if>">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                Oeuvres <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li class="dropdown-header">
                                    Prêts
                                </li>
                                <li<c:if test="${not empty _page_current && _page_current == 'works_loans_list'}"> class="active"</c:if>>
                                    <c:url value="/loanableWorks.jsp?action=list" var="_url" />
                                    <a href="${fn:escapeXml(_url)}">
                                        Catalogue
                                    </a>
                                </li>
                                <li<c:if test="${not empty _page_current && _page_current == 'works_loans_add'}"> class="active"</c:if>>
                                    <c:url value="/loanableWorks.jsp?action=add" var="_url" />
                                    <a href="${fn:escapeXml(_url)}">
                                        Ajouter une oeuvre
                                    </a>
                                </li>
                                <li role="separator" class="divider">
                                </li>
                                <li class="dropdown-header">
                                    Ventes
                                </li>
                                <li<c:if test="${not empty _page_current && _page_current == 'works_sales_list'}"> class="active"</c:if>>
                                    <c:url value="/sellableWorks.jsp?action=list" var="_url" />
                                    <a href="${fn:escapeXml(_url)}">
                                        Catalogue
                                    </a>
                                </li>
                                <li<c:if test="${not empty _page_current && _page_current == 'works_sales_add'}"> class="active"</c:if>>
                                    <c:url value="/sellableWorks.jsp?action=add" var="_url" />
                                    <a href="${fn:escapeXml(_url)}">
                                        Ajouter une oeuvre
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown<c:if test="${not empty _page_current && fn:startsWith(_page_current, 'adherents')}"> active</c:if>">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                Adhérents <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li<c:if test="${not empty _page_current && _page_current == 'adherents_list'}"> class="active"</c:if>>
                                    <c:url value="/adherents.jsp?action=list" var="_url" />
                                    <a href="${fn:escapeXml(_url)}">
                                        Liste des adhérents
                                    </a>
                                </li>
                                <li<c:if test="${not empty _page_current && _page_current == 'adherents_add'}"> class="active"</c:if>>
                                    <c:url value="/adherents.jsp?action=add" var="_url" />
                                    <a href="${fn:escapeXml(_url)}">
                                        Ajout d'un adhérent
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown<c:if test="${not empty _page_current && fn:startsWith(_page_current, 'owners')}"> active</c:if>">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                Propriétaires <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li<c:if test="${not empty _page_current && _page_current == 'owners_list'}"> class="active"</c:if>>
                                    <c:url value="/owners.jsp?action=list" var="_url" />
                                    <a href="${fn:escapeXml(_url)}">
                                        Liste des propriétaires
                                    </a>
                                </li>
                                <li<c:if test="${not empty _page_current && _page_current == 'owners_add'}"> class="active"</c:if>>
                                    <c:url value="/owners.jsp?action=add" var="_url" />
                                    <a href="${fn:escapeXml(_url)}">
                                        Ajout d'un propriétaire
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li<c:if test="${not empty _page_current && _page_current == 'contact'}"> class="active"</c:if>>
                            <c:url value="/contact.jsp" var="_url" />
                            <a href="${fn:escapeXml(_url)}">
                                Contact
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
