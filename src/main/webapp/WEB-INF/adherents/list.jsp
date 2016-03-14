<%@include file="/WEB-INF/_inc/bootstrap.jsp" %>
<c:set var="_page_title" value="Liste des adhérents" />
<c:set var="_page_current" value="adherents_list" />
<%@include file="/WEB-INF/_inc/header.jsp" %>
    <div class="page-header">
        <h1>
            Liste des adhérents
        </h1>
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
        <c:url value="/adherents.jsp?action=add" var="_url" />
        <a class="btn btn-default" href="${fn:escapeXml(_url)}" role="button">
            <span class="glyphicon glyphicon-plus"></span>
            Ajout
        </a>
    </p>
    <div class="table-responsive">
        <table class="table table-hover table-striped">
            <thead>
                <tr>
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
                    <c:when test="${fn:length(adherents) gt 0}">
                        <c:forEach items="${adherents}" var="adherent">
                            <tr>
                                <td>
                                    ${fn:escapeXml(adherent.firstName)}
                                </td>
                                <td>
                                    ${fn:escapeXml(adherent.lastName)}
                                </td>
                                <td>
                                    ${fn:escapeXml(adherent.town)}
                                </td>
                                <td class="text-center">
                                    <c:url value="/adherents.jsp?action=edit&id=${adherent.id}" var="_url" />
                                    <a href="${fn:escapeXml(_url)}"><!--
                                        --><span class="glyphicon glyphicon-pencil"></span><!--
                                    --></a>
                                    <c:url value="/adherents.jsp?action=delete&id=${adherent.id}" var="_url" />
                                    <a href="${fn:escapeXml(_url)}" class="color-danger"><!--
                                        --><span class="glyphicon glyphicon-trash"></span><!--
                                    --></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="4">
                                Il n'y a aucun adhérent pour le moment.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
<%@include file="/WEB-INF/_inc/footer.jsp" %>