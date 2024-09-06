<%@ include file="../shared/header.jsp" %>

<div class="container">
    <c:if test="${requestScope.error != null}">
        <div class="alert alert-danger"><c:out value="${requestScope.error}"/></div>
    </c:if>

    <form class="form-signin col-md-4 col-md-offset-4" method="post">
        <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
        <div class="form-group">
            <label for="inputUsername" class="sr-only">Username</label>
            <input type="text" id="inputUsername" name="username" class="form-control" placeholder="Username" required autofocus>
        </div>
        <div class="form-group">
            <label for="inputPassword" class="sr-only">Password</label>
            <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
        </div>
        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" value="remember-me"> Remember me
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
</div>

<%@ include file="../shared/footer.jsp" %>
