<%@include file="/WEB-INF/bootstrap.jsp" %>
<c:set var="_page_title" value="Emprunt de &quot;${fn:escapeXml(borrowingForm.oeuvrepret.titreOeuvrepret)}&quot;" />
<c:set var="_page_current" value="works_loans_book" />
<c:set var="_page_stylesheets">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/css/select2.min.css" media="screen" />
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.4.5/jquery.datetimepicker.min.css" media="screen" />
</c:set>
<c:set var="_page_scripts">
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/select2.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/i18n/fr.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.4.5/jquery.datetimepicker.min.js"></script>
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
                    <c:when test="${not empty borrowingForm.adherent}">
                        $adherentsList.val(${borrowingForm.adherent.idAdherent}).trigger("change");
                    </c:when>
                    <c:otherwise>
                        $adherentsList.val(firstItemId).trigger("change");
                    </c:otherwise>
                </c:choose>
            });
            
            // Datetime picker
            var disabledDates = [
                <%--
                <c:forEach items="${loanDates}" var="date" varStatus="_loop">
                    "<fmt:formatDate value="${date}" pattern="yyyy/MM/dd" />"<c:if test="${!loop.last}">,</c:if>
                </c:forEach>
                --%>
            ];
            
            $("#dateStart, #dateEnd").datetimepicker({
                lang: "fr",
                timepicker: false,
                format: "d/m/Y",
                dayOfWeekStart: 1,
                minDate: new Date(),
                disabledDates: disabledDates,
                highlightedDates: disabledDates
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
    <c:url value="/works/loanable/book" var="_url" />
    <form:form cssClass="form-horizontal" method="post" action="${_url}" modelAttribute="borrowingForm">
        <div class="form-group">
            <label class="control-label col-sm-2">
                Nom de l'oeuvre
            </label>
            <div class="col-sm-10">
                <p class="form-control-static">
                    ${fn:escapeXml(borrowingForm.oeuvrepret.titreOeuvrepret)}
                </p>
                <form:input path="oeuvrepret" type="hidden" value="${borrowingForm.oeuvrepret.idOeuvrepret}" />
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
        <spring:bind path="dateDebut">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="dateDebut" for="dateStart" cssClass="control-label col-sm-2">
                    Date de début*
                </form:label>
                <div class="col-sm-10">
                    <form:input
                        cssClass="form-control"
                        path="dateDebut"
                        type="text"
                        id="dateStart"
                        placeholder="Par exemple, 02/05/2016"
                    />
                    <form:errors cssClass="help-block" path="dateDebut" />
                </div>
            </div>
        </spring:bind>
        <spring:bind path="dateFin">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:label path="dateFin" for="dateEnd" cssClass="control-label col-sm-2">
                    Date de fin*
                </form:label>
                <div class="col-sm-10">
                    <form:input
                        cssClass="form-control"
                        path="dateFin"
                        type="text"
                        id="dateEnd"
                        placeholder="Par exemple, 02/05/2016"
                    />
                    <form:errors cssClass="help-block" path="dateFin" />
                </div>
            </div>
        </spring:bind>
        <div class="col-sm-10 col-sm-offset-2 btn-group">
            <button class="btn btn-success" type="submit"<c:if test="${empty adherentsList || fn:length(adherentsList) eq 0}"> disabled="disabled"</c:if>>
                Emprunter
            </button>
            <button class="btn btn-danger" type="reset">
                Réinitialiser
            </button>
            <c:url value="/works/loanable" var="_url" />
            <a class="btn btn-default" href="${fn:escapeXml(_url)}">
                Retour à la liste
            </a>
        </div>
    </form:form>
<%@include file="/WEB-INF/views/fragments/footer.jspf" %>