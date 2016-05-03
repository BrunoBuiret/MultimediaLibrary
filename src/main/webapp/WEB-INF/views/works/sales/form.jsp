<%@include file="/WEB-INF/bootstrap.jsp" %>
<c:set var="_page_stylesheets">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/css/select2.min.css" media="screen" />
</c:set>
<c:set var="_page_scripts">
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/select2.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/i18n/fr.js"></script>
    <script type="text/javascript">
        $(function() {
            // Select2
            var $ownersList = $("#owner");
            var $options = $ownersList.find("option");
            var firstItemId = $options.length > 0 ? $options.eq(0).val() : -1;

            $ownersList.select2({
                language: "fr"
            });

            $("form").on("reset", function(e) {
                <c:choose>
                    <c:when test="${not empty workForm.idProprietaire}">
                        $ownersList.val(${workForm.idProprietaire.idProprietaire}).trigger("change");
                    </c:when>
                    <c:otherwise>
                        $ownersList.val(firstItemId).trigger("change");
                    </c:otherwise>
                </c:choose>
            });
        });
    </script>
</c:set>
<%@include file="/WEB-INF/views/fragments/header.jspf" %>
    <c:if test="${not empty _page_title}">
        <div class="page-header">
            <h1><c:out value="${_page_title}" /></h1>
        </div>
    </c:if>
    <c:url value="/works/sellable/submit" var="_url" />
    <form:form cssClass="form-horizontal" method="post" action="${_url}" modelAttribute="workForm">
        <c:if test="${empty workForm.idOeuvrevente}">
            <input type="hidden" name="_is_new" value="1" />
        </c:if>
        <form:input
            type="hidden"
            path="idOeuvrevente"
        />
        <form:input
            type="hidden"
            path="etatOeuvrevente"
            value="L"
        />
        <spring:bind path="titreOeuvrevente">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="titreOeuvrevente" for="title" cssClass="control-label col-sm-2">
                    Titre*
                </form:label>
                <div class="col-sm-10">
                    <form:input
                        type="text"
                        cssClass="form-control"
                        path="titreOeuvrevente"
                        id="title"
                    />
                    <form:errors cssClass="help-block" path="titreOeuvrevente" />
                </div>
            </div>
        </spring:bind>
        <spring:bind path="prixOeuvrevente">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="prixOeuvrevente" for="price" cssClass="control-label col-sm-2">
                    Prix*
                </form:label>
                <div class="col-sm-10">
                    <div class="input-group">
                        <form:input
                            type="text"
                            cssClass="form-control"
                            path="prixOeuvrevente"
                            id="price"
                            placeholder="Par exemple, 19.99"
                        />
                        <span class="input-group-addon">&euro;</span>
                    </div>
                    <form:errors cssClass="help-block" path="prixOeuvrevente" />
                </div>
            </div>
        </spring:bind>
        <spring:bind path="idProprietaire">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="idProprietaire" for="owner" cssClass="control-label col-sm-2">
                    Propriétaire*
                </form:label>
                <div class="col-sm-10">
                    <c:choose>
                        <c:when test="${not empty ownersList && fn:length(ownersList) gt 0}">
                            <form:select
                                cssClass="form-control"
                                path="idProprietaire"
                                id="owner"
                            >
                                <form:options items="${ownersList}" itemValue="idProprietaire" itemLabel="fullName"></form:options>
                            </form:select>
                            <form:errors cssClass="help-block" path="idProprietaire" />
                        </c:when>
                        <c:otherwise>
                            <p class="form-control-static">
                                Il n'existe aucun propriétaire pour le moment.
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </spring:bind>
        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2 btn-group">
                <button class="btn btn-success" type="submit"<c:if test="${empty ownersList || fn:length(ownersList) eq 0}"> disabled="disabled"</c:if>>
                    Enregistrer
                </button>
                <button class="btn btn-danger" type="reset">
                    Réinitialiser
                </button>
                <c:url value="/works/sellable" var="_url" />
                <a class="btn btn-default" href="${fn:escapeXml(_url)}">
                    Retour à la liste
                </a>
            </div>
        </div>
    </form:form>
<%@include file="/WEB-INF/views/fragments/footer.jspf" %>