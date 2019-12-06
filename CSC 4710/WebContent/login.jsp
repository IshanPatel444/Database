<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>

  <title>Login</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/solid.css">
  <script src="https://use.fontawesome.com/releases/v5.0.7/js/all.js"></script>
  <style>  <%@include file="/WEB-INF/css/style.css"%></style>
  
  
</head>

<body>

<%
    String result = (String) request.getAttribute("result");
%>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">
        var value = "<%=result%>";
        if(value != "success" && value != "null"){
        	swal("Unable to login", "Please check your ID and Password", "error");
        }
</script>

<div class="modal-dialog text-center">
  <div class="col-sm-8 main-section">
<div class="modal-content">
			<h1 style="align-self: center; color: #c2fbfe;">Welcome</h1>
			<div class="form-inline" style="align-self: center;">
			<form>
					<a href="search.jsp">Search Item</a>&nbsp;&nbsp;&nbsp;
				</form>
			</div>
      <form  action="LogInDB" method="post" class="col-12">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Enter Username" name="UserID"> 
        </div>
        <div class="form-group">
            <input type="text" class="form-control" placeholder="Enter Password" name="password">
          </div>

        <button type="submit" class="btn btn-primary" value="submit">
          <i class="fas fa-sign-in-alt"></i>
          Login
        </button>
      </form>

<div class="col-12 new-user" style="margin-top: 20px;">
        <a href="register.jsp"> New User</a>
      </div>
    </div> <!-- End of Modal Content -->
  </div>
</div>
</body>
</html>