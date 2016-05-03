<%@include file="/WEB-INF/bootstrap.jsp" %>
<c:set var="_page_title" value="Liste des adhérents" />
<c:set var="_page_current" value="adherents_list" />
<c:set var="_page_scripts">
    <script type="text/javascript">
        $(function() {
            // Initialize vars
            var $multiDeleteButton = $("#multi-delete-button");
            var $toggleAllCheckBox = $("#toggle-all");
            var $checkBoxes = $("input[type='checkbox'][name='ids[]']");

            // Register event handlers
            $toggleAllCheckBox.click(function(e) {
                var isChecked = $toggleAllCheckBox.prop("checked");

                // Enable or disable the multi delete button
                $multiDeleteButton.prop("disabled", !isChecked);

                // Check or uncheck every check boxes
                $checkBoxes.prop("checked", isChecked);
            });

            $checkBoxes.click(function(e) {
                var checkedNumber = $checkBoxes.filter(":checked").length;

                // Enable or disable the multi delete button
                $multiDeleteButton.prop("disabled", checkedNumber === 0);

                // Check or uncheck the toggle all check box
                $toggleAllCheckBox.prop("checked", $checkBoxes.length === checkedNumber);
            });

            // Enable tooltips
            $("[data-toggle='tooltip']").tooltip();
        });
    </script>
</c:set>
<%@include file="/WEB-INF/views/fragments/header.jspf" %>
    <div class="page-header">
        <h1><c:out value="${_page_title}" /></h1>
    </div>
    <%@include file="/WEB-INF/views/fragments/flashes.jspf" %>
    <c:url value="/adherents/delete" var="_url" />
    <form method="post" action="${fn:escapeXml(_url)}">
        <div class="btn-group" role="group">
            <c:url value="/adherents/add" var="_url" />
            <a class="btn btn-default" role="button" href="${fn:escapeXml(_url)}">
                <span class="glyphicon glyphicon-plus"></span>
                Ajouter
            </a>
            <button
                class="btn btn-default"
                id="multi-delete-button"
                type="submit"
                disabled="disabled"
            >
                <span class="glyphicon glyphicon-trash"></span>
                Supprimer
            </button>
        </div>
        <div class="spacer spacer-sm"></div>
        <div class="table-responsive">
            <table class="table<c:if test="${not empty adherents && fn:length(adherents) gt 0}"> table-hover table-striped</c:if>">
                <thead>
                    <tr>
                        <th style="width: 30px;">
                            <input
                                type="checkbox"
                                id="toggle-all"
                                <c:if test="${empty adherents || fn:length(adherents) eq 0}">
                                    disabled="disabled"
                                </c:if>
                            />
                        </th>
                        <th>
                            Prénom
                        </th>
                        <th>
                            Nom
                        </th>
                        <th>
                            Ville
                        </th>
                        <th style="width: 50px;">
                        </th>
                    </tr>
                </thead>
                <tfoot>
                </tfoot>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty adherents && fn:length(adherents) gt 0}">
                            <c:forEach items="${adherents}" var="adherent">
                                <tr>
                                    <td>
                                        <input
                                            type="checkbox"
                                            name="ids[]"
                                            id="adherent_${adherent.idAdherent}"
                                            value="${adherent.idAdherent}"
                                        />
                                    </td>
                                    <td>
                                        <label class="table-label" for="adherent_${adherent.idAdherent}">
                                            ${fn:escapeXml(adherent.prenomAdherent)}
                                        </label>
                                    </td>
                                    <td>
                                        <label class="table-label" for="adherent_${adherent.idAdherent}">
                                            ${fn:escapeXml(adherent.nomAdherent)}
                                        </label>
                                    </td>
                                    <td>
                                        <label class="table-label" for="adherent_${adherent.idAdherent}">
                                            ${fn:escapeXml(adherent.villeAdherent)}
                                        </label>
                                    </td>
                                    <td>
                                        <c:url value="/adherents/edit/${adherent.idAdherent}" var="_url" />
                                        <a
                                            href="${fn:escapeXml(_url)}"
                                            data-toggle="tooltip"
                                            data-placement="left"
                                            title="Éditer cet adhérent"
                                        ><!--
                                            --><span class="glyphicon glyphicon-pencil"></span><!--
                                        --></a>
                                        <c:url value="/adherents/delete/${adherent.idAdherent}" var="_url" />
                                        <a
                                            href="${fn:escapeXml(_url)}"
                                            class="color-danger"
                                            data-toggle="tooltip"
                                            data-placement="left"
                                            title="Supprimer cet adhérent"
                                        ><!--
                                            --><span class="glyphicon glyphicon-trash"></span><!--
                                        --></a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5">
                                    Il n'y a aucun adhérent pour le moment.
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </form>
<%@include file="/WEB-INF/views/fragments/footer.jspf" %>