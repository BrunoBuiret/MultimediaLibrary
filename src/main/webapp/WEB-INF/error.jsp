<%@include file="/WEB-INF/_inc/bootstrap.jsp" %>
<%@include file="/WEB-INF/_inc/header.jsp" %>
<div class="callout callout-danger">
    <c:if test="${not empty _error_title}">
        <h4>
            ${_error_title}
        </h4>
    </c:if>
    <p>
        ${_error_message}
    </p>
    <c:if test="${not empty _error_exception}">
        <p class="text-right">
            <a
                href="#error-details"
                class="btn btn-danger"
                role="button"
                data-toggle="collapse"
                aria-expanded="false"
                aria-controls="error-details"
            >
                Voir les détails
            </a>
        </p>
        <div class="collapse" id="error-details">
            <c:if test="${not empty _error_exception.message}">
                <blockquote>
                    <p>${_error_exception.message}</p>
                </blockquote>
            </c:if>
            <c:if test="${not empty _error_exception.stackTrace}">
                <ol>
                    <c:forEach items="${_error_exception.stackTrace}" var="item">
                        <li>
                            <strong>${item.fileName}</strong> à la ligne <strong>${item.lineNumber}</strong><br/>
                            <code>${item.className}.${item.methodName}()</code>
                        </li>
                    </c:forEach>
                </ol>
            </c:if>
        </div>
    </c:if>
</div>
<%@include file="/WEB-INF/_inc/footer.jsp" %>