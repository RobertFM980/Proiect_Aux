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
        <div class="col-md-4">
            <form class="form-horizontal" method="post">
            <div class="input-group">
                <div class="form-outline" data-mdb-input-init>
                    <input type="search" id="title" name="title" class="form-control" />
                    <label class="form-label" for="title">Search</label>
                </div>
                <button type="submit" class="btn btn-primary" data-mdb-ripple-init>
                    <i class="glyphicon glyphicon-search"></i>
                </button>
            </div>
            </form>
        </div>
        <div class="col-md-4">
            <c:out value="${requestScope.personInfo.name}"/> might have
            <c:out value="${requestScope.personInfo.age}"/> years
        </div>
        <div class="col-md-4">
            Info3
        </div>
    </div>
</div>

<%@ include file="../shared/footer.jsp" %>
