<%@ include file="../shared/header.jsp" %>
<%@ include file="../shared/nav.jsp" %>
<div class="container">
<table class="table table-striped">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th></th>
    </tr>

    <c:forEach items="${requestScope.banks}" var="bank">
    <tr>
        <td><c:out value="${bank.id}"/></td>
        <td><c:out value="${bank.name}"/></td>
        <td>
            <a href="<c:out value="${pageContext.request.contextPath}"/>/banks?action=edit&bankId=<c:out value="${bank.id}"/>">
                Edit <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
            </a> |
            <a href="<c:out value="${pageContext.request.contextPath}"/>/banks?action=delete&bankId=<c:out value="${bank.id}"/>">
                Delete <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </a>
        </td>
    </tr>
    </c:forEach>
</table>

</div>

<%@ include file="../shared/footer.jsp" %>
