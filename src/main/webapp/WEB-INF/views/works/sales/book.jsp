<%@include file="/WEB-INF/bootstrap.jsp" %>
<c:set var="_page_title" value="Réservation de &quot;${fn:escapeXml(bookingForm.oeuvrevente.titreOeuvrevente)}&quot;" />
<c:set var="_page_current" value="works_sales_book" />
<c:set var="_page_stylesheets">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/css/select2.min.css" media="screen" />
</c:set>
<c:set var="_page_scripts">
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/select2.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/i18n/fr.js"></script>
    <script type="text/javascript">
        $(function() {
            // Select2
            var $adherentsList = $("#adherents");
            var $options = $adherentsList.find("option");
            var firstItemId = $options.length > 0 ? $options.eq(0).val() : -1;

            $adherentsList.select2({
                language: "fr"
            });

            $("form").on("reset", function(e) {
                <c:choose>
                    <c:when test="${not empty bookingForm.adherent}">
                        $adherentsList.val(${bookingForm.adherent.idAdherent}).trigger("change");
                    </c:when>
                    <c:otherwise>
                        $adherentsList.val(firstItemId).trigger("change");
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
    <c:url value="/works/sellable/book" var="_url" />
    <form:form cssClass="form-horizontal" method="post" action="${_url}" modelAttribute="bookingForm">
        <form:input path="statut" type="hidden" value="?" />
        <div class="form-group">
            <label class="control-label col-sm-2">
                Nom de l'oeuvre
            </label>
            <div class="col-sm-10">
                <p class="form-control-static">
                    ${fn:escapeXml(bookingForm.oeuvrevente.titreOeuvrevente)}
                </p>
                <form:input path="oeuvrevente" type="hidden" value="${bookingForm.oeuvrevente.idOeuvrevente}" />
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2">
                Date de la réservation
            </label>
            <div class="col-sm-10">
                <fmt:formatDate value="${today}" pattern="dd/MM/yyyy" var="dateToday"/>
                <p class="form-control-static">
                    <c:out value="${dateToday}" />
                </p>
                <form:input path="dateReservation" type="hidden" value="${dateToday}" />
            </div>
        </div>
        <spring:bind path="adherent">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="adherent" for="adherent" cssClass="control-label col-sm-2">
                    Adhérent*
                </form:label>
                <div class="col-sm-10">
                    <c:choose>
                        <c:when test="${not empty adherentsList && fn:length(adherentsList) gt 0}">
                            <form:select
                                cssClass="form-control"
                                path="adherent"
                                id="adherents"
                            >
                                <form:options items="${adherentsList}" itemValue="idAdherent" itemLabel="fullName"></form:options>
                            </form:select>
                            <form:errors cssClass="help-block" path="adherent" />
                        </c:when>
                        <c:otherwise>
                            <p class="form-control-static">
                                Il n'existe aucun adhérent pour le moment.
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </spring:bind>
        <div class="col-sm-10 col-sm-offset-2 btn-group">
            <button class="btn btn-success" type="submit"<c:if test="${empty adherentsList || fn:length(adherentsList) eq 0}"> disabled="disabled"</c:if>>
                Réserver
            </button>
            <button class="btn btn-danger" type="reset">
                Réinitialiser
            </button>
            <c:url value="/works/sellable" var="_url" />
            <a class="btn btn-default" href="${fn:escapeXml(_url)}">
                Retour à la liste
            </a>
        </div>
    </form:form>
<%@include file="/WEB-INF/views/fragments/footer.jspf" %>