<%@ include file="../shared/header.jsp" %>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav navbar-right">
            <li>
                <a href="<c:out value="${pageContext.request.contextPath}"/>/logout">Logout</a>
            </li>
        </ul>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h1>Not Authorized!</h1>
        </div>
    </div>
</div>

<%@ include file="../shared/footer.jsp" %>

