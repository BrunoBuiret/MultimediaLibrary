<%@include file="/WEB-INF/_inc/bootstrap.jsp" %>
<c:set var="_page_title">
    Emprunt
    » ${fn:escapeXml(work.name)}
</c:set>
<c:set var="_page_current" value="works_loans_borrow" />
<c:set var="_page_stylesheets">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.4.5/jquery.datetimepicker.min.css" media="screen" />
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/css/select2.min.css" media="screen" />
</c:set>
<c:set var="_page_scripts">
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-datetimepicker/2.4.5/jquery.datetimepicker.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/select2.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.2/js/i18n/fr.js"></script>
    <script type="text/javascript">
        $(function() {
            // Datetime picker
            var disabledDates = [
                <c:forEach items="${loanDates}" var="date" varStatus="_loop">
                    "<fmt:formatDate value="${date}" pattern="yyyy/MM/dd" />"<c:if test="${!loop.last}">,</c:if>
                </c:forEach>
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
            
            // Select 2
            var $adherentsList = $("#adherentId");
            var $options = $adherentsList.find("option");
            var firstItemId = $options.length > 0 ? $options.eq(0).val() : -1;
            
            $adherentsList.select2({
                language: "fr"
            });
            
            $("form").on("reset", function(e) {
                $adherentsList.trigger("change");
                <c:choose>
                    <c:when test="${_last_adherent_id != null}">
                        $adherentsList.val(${_last_adherent_id}).trigger("change");
                    </c:when>
                    <c:otherwise>
                        $adherentsList.val(firstItemId).trigger("change");
                    </c:otherwise>
                </c:choose>
            });
        });
    </script>
</c:set>
<%@include file="/WEB-INF/_inc/header.jsp" %>
    <div class="page-header">
        <h1>
            Emprunter une oeuvre
            <small><cite>${fn:escapeXml(work.name)}</cite></small>
        </h1>
    </div>
    <c:url value="/loanableWorks.jsp?action=borrow&id=${work.id}" var="_url" />
    <form class="form-horizontal" method="post" action="${fn:escapeXml(_url)}">
        <div class="form-group">
            <label class="control-label col-sm-2">
                Nom de l'oeuvre
            </label>
            <div class="col-sm-10">
                <p class="form-control-static">
                    ${fn:escapeXml(work.name)}
                </p>
                <input type="hidden" name="id" value="${work.id}" />
            </div>
        </div>
        <div class="form-group<c:if test="${not empty _error_adherent_id}"> has-error</c:if>">
            <label for="adherentId" class="control-label col-sm-2">
                Adhérent*
            </label>
            <div class="col-sm-10">
                <select class="form-control" id="adherentId" name="adherentId">
                    <c:forEach items="${adherents}" var="adherent">
                        <option
                            value="${adherent.id}"
                            <c:if test="${not empty _last_adherent_id and _last_adherent_id == adherent.id}">
                                selected="selected"
                            </c:if>
                        >
                            <c:out value="${adherent.firstName} ${adherent.lastName}" />
                        </option>
                    </c:forEach>
                </select>
                <c:if test="${not empty _error_adherent_id}">
                    <span class="help-block">
                        ${_error_adherent_id}
                    </span>
                </c:if>
            </div>
        </div>
        <div class="form-group<c:if test="${not empty _error_date_start}"> has-error</c:if>">
            <label for="dateStart" class="control-label col-sm-2">
                Date de début*
            </label>
            <div class="col-sm-10">
                <input
                    type="text"
                    class="form-control"
                    id="dateStart"
                    name="dateStart"
                    placeholder="Par exemple, 20/01/2016"
                    <c:if test="${not empty _last_date_start}">
                        value="${fn:escapeXml(_last_date_start)}"
                    </c:if>
                />
                <c:if test="${not empty _error_date_start}">
                    <span class="help-block">
                        ${_error_date_start}
                    </span>
                </c:if>
            </div>
        </div>
        <div class="form-group<c:if test="${not empty _error_date_end}"> has-error</c:if>">
            <label for="dateEnd" class="control-label col-sm-2">
                Date de fin*
            </label>
            <div class="col-sm-10">
                <input
                    type="text"
                    class="form-control"
                    id="dateEnd"
                    name="dateEnd"
                    placeholder="Par exemple, 21/01/2016"
                    <c:if test="${not empty _last_date_end}">
                        value="${fn:escapeXml(_last_date_end)}"
                    </c:if>
                />
                <c:if test="${not empty _error_date_end}">
                    <span class="help-block">
                        ${_error_date_end}
                    </span>
                </c:if>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-success">
                    Emprunter
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
                <c:url value="/loanableWorks.jsp?action=list" var="_url" />
                <a href="${fn:escapeXml(_url)}" class="btn btn-default">
                    Retour à la liste
                </a>
            </div>
        </div>
    </form>
<%@include file="/WEB-INF/_inc/footer.jsp" %>