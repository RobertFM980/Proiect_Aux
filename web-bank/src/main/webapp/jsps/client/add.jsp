<%@ include file="../shared/header.jsp" %>
<%@ include file="../shared/nav.jsp" %>
<div class="container">
    <form class="form-horizontal" method="post">
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" name="name">
            </div>
        </div>
        <div class="form-group">
            <label for="city" class="col-sm-2 control-label">City</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="city" name="city">
            </div>
        </div>
        <div class="form-group">
            <label for="birthDate" class="col-sm-2 control-label">Birth Date</label>
            <div class="col-sm-10">
                <input type="date" class="form-control" id="birthDate" name="birthDate">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Add</button>
            </div>
        </div>
    </form>

</div>

<%@ include file="../shared/footer.jsp" %>
