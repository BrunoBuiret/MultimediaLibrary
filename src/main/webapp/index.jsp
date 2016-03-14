<%@include file="/WEB-INF/_inc/bootstrap.jsp" %>
<c:set var="_page_current" value="home" />
<%@include file="/WEB-INF/_inc/header.jsp" %>
    <div class="jumbotron">
        <h1>Médiathèque Polytech</h1>
        <p>
            Bienvenue sur le site web de gestion de la médiathèque de Polytech Lyon.
        </p>
        <p class="pull-right" style="margin-bottom: 0;">
            <c:url value="/loanableWorks.jsp?action=list" var="_url" />
            <a class="btn btn-default" href="${fn:escapeXml(_url)}" role="button">
                <span class="glyphicon glyphicon-transfer"></span>
                Catalogue des prêts
            </a>
            <c:url value="/sellableWorks.jsp?action=list" var="_url" />
            <a class="btn btn-default" href="${fn:escapeXml(_url)}" role="button">
                <span class="glyphicon glyphicon-euro"></span>
                Catalogue des ventes
            </a>
        </p>
        <div class="clearfix"></div>
    </div>
<%@include file="/WEB-INF/_inc/footer.jsp" %>