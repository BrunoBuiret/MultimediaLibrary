<%@include file="/WEB-INF/bootstrap.jsp" %>
<c:set var="_page_current" value="home" />
<%@include file="/WEB-INF/views/fragments/header.jspf" %>
    <div class="jumbotron">
        <div class="pull-left">
            <figure>
                <c:url value="/assets/img/logo-polytech.png" var="_url" />
                <img src="${fn:escapeXml(_url)}" alt="Logo Polytech" style="margin-right: 20px;" />
            </figure>
        </div>
        <h1>Médiathèque Polytech Lyon</h1>
        <p>
            Bienvenue sur le site web de gestion de la médiathèque de Polytech Lyon.
        </p>
        <div class="clearfix"></div>
    </div>
<%@include file="/WEB-INF/views/fragments/footer.jspf" %>