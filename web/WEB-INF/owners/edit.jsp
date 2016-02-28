<%@include file="/WEB-INF/_inc/bootstrap.jsp" %>
<c:set var="_page_title" value="Édition d'un propriétaire » ${fn:escapeXml(owner.firstName)} ${fn:escapeXml(owner.lastName)}" />
<c:set var="_page_current" value="owners_edit" />
<%@include file="/WEB-INF/_inc/header.jsp" %>
    <h1>
        Édition d'un propriétaire
        <small>${fn:escapeXml(owner.firstName)} ${fn:escapeXml(owner.lastName)}</small>
    </h1>
    <form class="form-horizontal" method="post" action="owners.jsp?action=edit&id=${owner.id}">
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
                            value="${fn:escapeXml(owner.firstName)}"
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
                            value="${fn:escapeXml(owner.lastName)}"
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
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-success">
                    Sauvegarder
                </button>
                <button type="reset" class="btn btn-danger">
                    Réinitialiser
                </button>
                <a href="owners.jsp?action=list" class="btn btn-default">
                    Retour à la liste
                </a>
            </div>
        </div>
        <input type="hidden" name="id" value="${owner.id}" />
    </form>
<%@include file="/WEB-INF/_inc/footer.jsp" %>