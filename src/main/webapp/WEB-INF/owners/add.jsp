<%@include file="/WEB-INF/_inc/bootstrap.jsp" %>
<c:set var="_page_title" value="Ajout d'un propriétaire" />
<c:set var="_page_current" value="owners_add" />
<%@include file="/WEB-INF/_inc/header.jsp" %>
    <div class="page-header">
        <h1>
            Ajout d'un nouveau propriétaire
        </h1>
    </div>
    <form class="form-horizontal" method="post" action="owners.jsp?action=add">
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
                    <c:if test="${not empty _last_first_name}">
                        value="${fn:escapeXml(_last_first_name)}"
                    </c:if>
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
                    <c:if test="${not empty _last_last_name}">
                        value="${fn:escapeXml(_last_last_name)}"
                    </c:if>
                />
                <c:if test="${not empty _error_last_name}">
                    <span class="help-block">
                        ${_error_last_name}
                    </span>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-success">
                    Ajouter
                </button>
                <button type="reset" class="btn btn-danger">
                    <c:choose>
                        <c:when test="${_method == _method_post}">
                            Réinitialiser
                        </c:when>
                        <c:otherwise>
                            Tout effacer
                        </c:otherwise>
                    </c:choose>
                </button>
                <a href="owners.jsp?action=list" class="btn btn-default">
                    Retour à la liste
                </a>
            </div>
        </div>
    </form>
<%@include file="/WEB-INF/_inc/footer.jsp" %>