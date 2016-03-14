<%@include file="/WEB-INF/_inc/bootstrap.jsp" %>
<c:set var="_page_title">
    Édition d'un adhérent 
    » ${fn:escapeXml(adherent.firstName)} ${fn:escapeXml(adherent.lastName)}
</c:set>
<c:set var="_page_current" value="adherents_edit" />
<%@include file="/WEB-INF/_inc/header.jsp" %>
    <div class="page-header">
        <h1>
            Édition d'un adhérent
            <small>${fn:escapeXml(adherent.firstName)} ${fn:escapeXml(adherent.lastName)}</small>
        </h1>
    </div>
    <c:url value="/adherents.jsp?action=edit&id=${adherent.id}" var="_url" />
    <form class="form-horizontal" method="post" action="${fn:escapeXml(_url)}">
        <div class="form-group<c:if test="${not empty _error_first_name}"> has-error</c:if>">
            <label for="firstName" class="control-label col-sm-2">
                Prénom*
            </label>
            <div class="col-sm-10">
                <input
                    type="text"
                    class="form-control"
                    id="firstName"
                    name="firstName"
                    <c:choose>
                        <c:when test="${_last_first_name != null}">
                            value="${fn:escapeXml(_last_first_name)}"
                        </c:when>
                        <c:otherwise>
                            value="${fn:escapeXml(adherent.firstName)}"
                        </c:otherwise>
                    </c:choose>
                />
                <c:if test="${not empty _error_first_name}">
                    <span class="help-block">
                        ${_error_first_name}
                    </span>
                </c:if>
            </div>
        </div>
        <div class="form-group<c:if test="${not empty _error_last_name}"> has-error</c:if>">
            <label for="lastName" class="control-label col-sm-2">
                Nom*
            </label>
            <div class="col-sm-10">
                <input
                    type="text"
                    class="form-control"
                    id="lastName"
                    name="lastName"
                    <c:choose>
                        <c:when test="${_last_last_name != null}">
                            value="${fn:escapeXml(_last_last_name)}"
                        </c:when>
                        <c:otherwise>
                            value="${fn:escapeXml(adherent.lastName)}"
                        </c:otherwise>
                    </c:choose>
                />
                <c:if test="${not empty _error_last_name}">
                    <span class="help-block">
                        ${_error_last_name}
                    </span>
                </c:if>
            </div>
        </div>
        <div class="form-group<c:if test="${not empty _error_town}"> has-error</c:if>">
            <label for="town" class="control-label col-sm-2">
                Ville*
            </label>
            <div class="col-sm-10">
                <input
                    type="text"
                    class="form-control"
                    id="town"
                    name="town"
                    <c:choose>
                        <c:when test="${_last_town != null}">
                            value="${fn:escapeXml(_last_town)}"
                        </c:when>
                        <c:otherwise>
                            value="${fn:escapeXml(adherent.town)}"
                        </c:otherwise>
                    </c:choose>
                />
                <c:if test="${not empty _error_town}">
                    <span class="help-block">
                        ${_error_town}
                    </span>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-success">
                    Sauvegarder
                </button>
                <button type="reset" class="btn btn-danger">
                    Réinitialiser
                </button>
                <c:url value="/adherents.jsp?action=list" var="_url" />
                <a href="${fn:escapeXml(_url)}" class="btn btn-default">
                    Retour à la liste
                </a>
            </div>
        </div>
        <input type="hidden" name="id" value="${adherent.id}" />
    </form>
<%@include file="/WEB-INF/_inc/footer.jsp" %>