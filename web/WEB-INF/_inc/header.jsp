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
        <link rel="stylesheet" type="text/css" href="css/general.css" />
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
                    <a class="navbar-brand" href="index.jsp">Médiathèque Polytech</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li<c:if test="${not empty _page_current && _page_current == 'home'}"> class="active"</c:if>>
                            <a href="index.jsp">
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
                                    <a href="loanableWorks.jsp?action=list">
                                        Catalogue
                                    </a>
                                </li>
                                <li<c:if test="${not empty _page_current && _page_current == 'works_loans_add'}"> class="active"</c:if>>
                                    <a href="loanableWorks.jsp?action=add">
                                        Ajouter une oeuvre
                                    </a>
                                </li>
                                <li role="separator" class="divider">
                                </li>
                                <li class="dropdown-header">
                                    Ventes
                                </li>
                                <li<c:if test="${not empty _page_current && _page_current == 'works_sales_list'}"> class="active"</c:if>>
                                    <a href="sellableWorks.jsp?action=list">
                                        Catalogue
                                    </a>
                                </li>
                                <li<c:if test="${not empty _page_current && _page_current == 'works_sales_add'}"> class="active"</c:if>>
                                    <a href="sellableWorks.jsp?action=add">
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
                                    <a href="adherents.jsp?action=list">
                                        Liste des adhérents
                                    </a>
                                </li>
                                <li<c:if test="${not empty _page_current && _page_current == 'adherents_add'}"> class="active"</c:if>>
                                    <a href="adherents.jsp?action=add">
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
                                    <a href="owners.jsp?action=list">
                                        Liste des propriétaires
                                    </a>
                                </li>
                                <li<c:if test="${not empty _page_current && _page_current == 'owners_add'}"> class="active"</c:if>>
                                    <a href="owners.jsp?action=add">
                                        Ajout d'un propriétaire
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li<c:if test="${not empty _page_current && _page_current == 'contact'}"> class="active"</c:if>>
                            <a href="contact.jsp">
                                Contact
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container">
