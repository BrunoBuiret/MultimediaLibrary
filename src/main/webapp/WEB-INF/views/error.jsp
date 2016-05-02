<%@include file="/WEB-INF/bootstrap.jsp" %>
<%@include file="/WEB-INF/views/fragments/header.jspf" %>
    <div class="page-header">
        <h1>
            <c:choose>
                <c:when test="${not empty customTitle}">
                    <c:out value="${customTitle}" />
                </c:when>
                <c:otherwise>
                    Une erreur est survenue
                </c:otherwise>
            </c:choose>
        </h1>
    </div>
    <div>
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active">
                <a href="#overview" aria-controls="overview" role="tab" data-toggle="tab">
                    Aperçu
                </a>
            </li>
            <li role="presentation">
                <a href="#details" aria-controls="details" role="tab" data-toggle="tab">
                    Détails
                </a>
            </li>
        </ul>
        <div class="tab-content tab-bordered">
            <div role="tabpanel" class="tab-pane active" id="overview">
                <p class="text-justify">
                    <c:out value="${customMessage}" />
                </p>
            </div>
            <div role="tabpanel" class="tab-pane" id="details">
                <blockquote>
                    <p class="text-justify">
                        <c:out value="${errorMessage}" />
                    </p>
                </blockquote>
                <c:if test="${not empty stackTrace && fn:length(stackTrace) gt 0}">
                    <ol>
                        <c:forEach items="${stackTrace}" var="item">
                            <li>
                                <strong>${item.fileName}</strong> à la ligne <strong>${item.lineNumber}</strong><br/>
                                <code>${item.className}.${item.methodName}()</code>
                            </li>
                        </c:forEach>
                    </ol>
                </c:if>
            </div>
        </div>
    </div>
<%@include file="/WEB-INF/views/fragments/footer.jspf" %>