<%@ include file="../shared/header.jsp" %>
<%@ include file="../shared/nav.jsp" %>

<div class="container">
    <a href="<c:out value="${pageContext.request.contextPath}"/>/clients?action=download">
        <button type="button" class="btn btn-info">Download</button>
    </a>
<table class="table table-striped">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>City</th>
        <th>Birth Date</th>
        <th></th>
    </tr>

    <c:forEach items="${requestScope.clients}" var="client">
    <tr>
        <td><c:out value="${client.id}"/></td>
        <td><c:out value="${client.name}"/></td>
        <td><c:out value="${client.city}"/></td>
        <td><c:out value="${client.formattedBirthDate}"/></td>
        <td>
            <a href="<c:out value="${pageContext.request.contextPath}"/>/clients?action=edit&clientId=<c:out value="${client.id}"/>">
                Edit <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
            </a> |
            <a href="<c:out value="${pageContext.request.contextPath}"/>/clients?action=delete&clientId=<c:out value="${client.id}"/>">
                Delete <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </a>
        </td>
    </tr>
    </c:forEach>
</table>

</div>

<%@ include file="../shared/footer.jsp" %>
