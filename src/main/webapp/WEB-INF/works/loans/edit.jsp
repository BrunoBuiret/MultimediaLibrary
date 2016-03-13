<%@include file="/WEB-INF/_inc/bootstrap.jsp" %>
<c:set var="_page_title">
    Édition d'une oeuvre à prêter
    » ${fn:escapeXml(work.name)}
</c:set>
<c:set var="_page_current" value="works_loans_edit" />
<c:set var="_page_stylesheets">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/css/select2.min.css" media="screen" />
</c:set>
<c:set var="_page_scripts">
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/select2.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/i18n/fr.js"></script>
    <script type="text/javascript">
        $(function() {
            // Initialize vars
            var $a = $("#ownerId");
            
            //
            $a.select2({
                language: "fr"
            });
            
            
            $("form").on("reset", function(e) {
                <c:choose>
                    <c:when test="${_last_owner_id != null}">
                        $a.val(${_last_owner_id}).trigger("change");
                    </c:when>
                    <c:otherwise>
                        $a.val(${work.owner.id}).trigger("change");
                    </c:otherwise>
                </c:choose>
            });
        });
    </script>
</c:set>
<%@include file="/WEB-INF/_inc/header.jsp" %>
    <div class="page-header">
        <h1>
            Édition d'une oeuvre à prêter
            <small>${fn:escapeXml(work.name)}</small>
        </h1>
    </div>
    <c:url value="/loanableWorks.jsp?action=edit&id=${work.id}" var="_url" />
    <form class="form-horizontal" method="post" action="${fn:escapeXml(_url)}">
        <div class="form-group<c:if test="${not empty _error_name}"> has-error</c:if>">
            <label for="name" class="control-label col-sm-2">
                Nom de l'oeuvre*
            </label>
            <div class="col-sm-10">
                <input
                    type="text"
                    class="form-control"
                    id="name"
                    name="name"
                    <c:choose>
                        <c:when test="${_last_name != null}">
                            value="${fn:escapeXml(_last_name)}"
                        </c:when>
                        <c:otherwise>
                            value="${fn:escapeXml(work.name)}"
                        </c:otherwise>
                    </c:choose>
                />
                <c:if test="${not empty _error_name}">
                    <span class="help-block">
                        ${_error_name}
                    </span>
                </c:if>
            </div>
        </div>
        <div class="form-group<c:if test="${not empty _error_owner_id}"> has-error</c:if>">
            <label for="ownerId" class="control-label col-sm-2">
                Propriétaire*
            </label>
            <div class="col-sm-10">
                <select class="form-control" id="ownerId" name="ownerId">
                <c:forEach items="${owners}" var="owner">
                    <option
                        value="${owner.id}"
                        <c:choose>
                            <c:when test="${_last_owner_id != null}">
                                <c:if test="${_last_owner_id == owner.id}">
                                    selected="selected"
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${work.owner.id == owner.id}">
                                    selected="selected"
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    >
                        <c:out value="${owner.firstName} ${owner.lastName}" />
                    </option>
                </c:forEach>
                </select>
                <c:if test="${not empty _error_owner_id}">
                    <span class="help-block">
                        ${_error_owner_id}
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
                <c:url value="/loanableWorks.jsp?action=list" var="_url" />
                <a href="${fn:escapeXml(_url)}" class="btn btn-default">
                    Retour à la liste
                </a>
            </div>
        </div>
    </form>
<%@include file="/WEB-INF/_inc/footer.jsp" %>