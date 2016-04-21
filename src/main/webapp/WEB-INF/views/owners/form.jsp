<%@include file="/WEB-INF/bootstrap.jsp" %>
<%@include file="/WEB-INF/views/fragments/header.jspf" %>
    <c:if test="${not empty _page_title}">
        <div class="page-header">
            <h1><c:out value="${_page_title}" /></h1>
        </div>
    </c:if>
    <c:url value="/owners/submit" var="_url" />
    <form:form cssClass="form-horizontal" method="post" action="${_url}" modelAttribute="ownerForm">
        <c:if test="${empty ownerForm.idProprietaire}">
            <input type="hidden" name="_is_new" value="1" />
        </c:if>
        <form:input
            type="hidden"
            path="idProprietaire"
        />
        <spring:bind path="prenomProprietaire">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="prenomProprietaire" for="firstName" cssClass="control-label col-sm-2">
                    Prénom*
                </form:label>
                <div class="col-sm-10">
                    <form:input
                        type="text"
                        cssClass="form-control"
                        path="prenomProprietaire"
                        id="firstName"
                    />
                    <form:errors cssClass="help-block" path="prenomProprietaire" />
                </div>
            </div>
        </spring:bind>
        <spring:bind path="nomProprietaire">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="nomProprietaire" for="lastName" cssClass="control-label col-sm-2">
                    Nom*
                </form:label>
                <div class="col-sm-10">
                    <form:input
                        type="text"
                        cssClass="form-control"
                        path="nomProprietaire"
                        id="lastName"
                    />
                    <form:errors cssClass="help-block" path="nomProprietaire" />
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
                <c:url value="/owners" var="_url" />
                <a class="btn btn-default" href="${fn:escapeXml(_url)}">
                    Retour à la liste
                </a>
            </div>
        </div>
    </form:form>
<%@include file="/WEB-INF/views/fragments/footer.jspf" %>