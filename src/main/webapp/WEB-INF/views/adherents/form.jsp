<%@include file="/WEB-INF/bootstrap.jsp" %>
<%@include file="/WEB-INF/views/fragments/header.jspf" %>
    <c:if test="${not empty _page_title}">
        <div class="page-header">
            <h1><c:out value="${_page_title}" /></h1>
        </div>
    </c:if>
    <c:url value="/adherents/submit" var="_url" />
    <form:form cssClass="form-horizontal" method="post" action="${_url}" modelAttribute="adherentForm">
        <form:input
            type="hidden"
            path="idAdherent"
        />
        <spring:bind path="prenomAdherent">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="prenomAdherent" for="firstName" cssClass="control-label col-sm-2">
                    Prénom*
                </form:label>
                <div class="col-sm-10">
                    <form:input
                        type="text"
                        cssClass="form-control"
                        path="prenomAdherent"
                        id="firstName"
                    />
                    <form:errors cssClass="help-block" path="prenomAdherent" />
                </div>
            </div>
        </spring:bind>
        <spring:bind path="nomAdherent">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="nomAdherent" for="lastName" cssClass="control-label col-sm-2">
                    Nom*
                </form:label>
                <div class="col-sm-10">
                    <form:input
                        type="text"
                        cssClass="form-control"
                        path="nomAdherent"
                        id="lastName"
                    />
                    <form:errors cssClass="help-block" path="nomAdherent" />
                </div>
            </div>
        </spring:bind>
        <spring:bind path="villeAdherent">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="villeAdherent" for="town" cssClass="control-label col-sm-2">
                    Ville*
                </form:label>
                <div class="col-sm-10">
                    <form:input
                        type="text"
                        cssClass="form-control"
                        path="villeAdherent"
                        id="town"
                    />
                    <form:errors cssClass="help-block" path="villeAdherent" />
                </div>
            </div>
        </spring:bind>
        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2 btn-group">
                <button class="btn btn-success" type="submit">
                    Enregistrer
                </button>
                <button class="btn btn-danger" type="reset">
                    Réinitialiser
                </button>
                <c:url value="/adherents" var="_url" />
                <a class="btn btn-default" href="${fn:escapeXml(_url)}">
                    Retour à la liste
                </a>
            </div>
        </div>
    </form:form>
<%@include file="/WEB-INF/views/fragments/footer.jspf" %>