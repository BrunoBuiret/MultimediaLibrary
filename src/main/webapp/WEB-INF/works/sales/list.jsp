<%@include file="/WEB-INF/_inc/bootstrap.jsp" %>
<c:set var="_page_title" value="Catalogue des ventes" />
<c:set var="_page_current" value="works_sales_list" />
<c:set var="_page_scripts">
    <script type="text/javascript">
        $(function () {
            $("[data-toggle='tooltip']").tooltip();
        });
    </script>
</c:set>
<%@include file="/WEB-INF/_inc/header.jsp" %>
    <div class="page-header">
        <h1>Catalogue des ventes</h1>
    </div>
    <c:if test="${not empty _flash}">
        <c:forEach items="${_flash}" var="item">
            <div class="alert alert-${item.type}" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Fermer">
                    <span aria-hidden="true">&times;</span>
                </button>
                ${item.contents}
            </div>
        </c:forEach>
    </c:if>
    <p>
        <a class="btn btn-default" href="sellableWorks.jsp?action=add" role="button">
            <span class="glyphicon glyphicon-plus"></span>
            Ajout
        </a>
    </p>
    <div class="table-responsive">
        <table class="table table-hover table-striped">
            <thead>
                <tr>
                    <th>
                        Titre
                    </th>
                    <th>
                        Prix
                    </th>
                    <th>
                        Propriétaire
                    </th>
                    <th style="width: 70px;">
                    </th>
                </tr>
            </thead>
            <tfoot>
            </tfoot>
            <tbody>
                <c:choose>
                    <c:when test="${fn:length(works) gt 0}">
                        <c:forEach items="${works}" var="work">
                            <tr>
                                <td>
                                    ${fn:escapeXml(work.name)}
                                </td>
                                <td>
                                    <fmt:formatNumber value="${work.price}" type="currency" />
                                </td>
                                <td>
                                    ${fn:escapeXml(work.owner.firstName)}
                                    ${fn:escapeXml(work.owner.lastName)}
                                </td>
                                <td class="text-center">
                                    <a
                                        href="sellableWorks.jsp?action=book&amp;id=${work.id}"
                                        class="color-success"
                                        data-toggle="tooltip"
                                        data-placement="left"
                                        title="Réserver cette oeuvre pour l'acheter"
                                    ><!--
                                        --><span class="glyphicon glyphicon-tag"></span><!--
                                    --></a>
                                    <a
                                        href="sellableWorks.jsp?action=edit&amp;id=${work.id}"
                                        data-toggle="tooltip"
                                        data-placement="left"
                                        title="Éditer cette oeuvre"
                                    ><!--
                                        --><span class="glyphicon glyphicon-pencil"></span><!--
                                    --></a>
                                    <a
                                        href="sellableWorks.jsp?action=delete&amp;id=${work.id}"
                                        class="color-danger"
                                        data-toggle="tooltip"
                                        data-placement="left"
                                        title="Supprimer cette oeuvre"
                                    ><!--
                                        --><span class="glyphicon glyphicon-remove"></span><!--
                                    --></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="5">
                                Il n'y a aucune vente pour le moment.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
<%@include file="/WEB-INF/_inc/footer.jsp" %>