<nav class="navbar navbar-default">
    <div class="container">
        <ul class="nav nav-tabs">
            <li class="nav-item">
                <a class="nav-link <c:out value="${requestScope.listClientsActive}"/>" href="<c:out value="${pageContext.request.contextPath}"/>/clients?action=list">
                    List Clients
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:out value="${requestScope.addClientsActive}"/>" href="<c:out value="${pageContext.request.contextPath}"/>/clients?action=add">
                    Add Client
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:out value="${requestScope.listBanksActive}"/>" href="<c:out value="${pageContext.request.contextPath}"/>/banks?action=list">
                    List Banks
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link <c:out value="${requestScope.addBanksActive}"/>" href="<c:out value="${pageContext.request.contextPath}"/>/banks?action=add">
                    Add Bank
                </a>
            </li>
        </ul>
    </div>
</nav>
