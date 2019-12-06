<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<title>Register</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.8/css/solid.css">
<script src="https://use.fontawesome.com/releases/v5.0.7/js/all.js"></script>
<style> 
	<%@	include file ="/WEB-INF/css/style.css"%>
</style>
</head>

<body>
	<div class="modal-dialog text-center">
		<div class="col-sm-8 main-section">
			<div class="modal-content">
				<form action="RegisterDB" method="post" class="col-12">

					<div class="form-group">
						<input type="text" class="form-control"
							placeholder="Enter Username" name="UserID">
					</div>

					<div class="form-group">
						<input type="text" class="form-control"
							placeholder="Enter Password" name="password">
					</div>

					<div class="form-group">
						<input type="text" class="form-control"
							placeholder="Confirm Password" name="confirm_password">
					</div>

					<div class="form-group">
						<input type="text" class="form-control"
							placeholder="Enter First Name" name="first_name">
					</div>

					<div class="form-group">
						<input type="text" class="form-control"
							placeholder="Enter Last Name" name="last_name">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="Enter Email"
							name="email">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="Enter Age"
							name="age">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="Enter Gender"
							name="gender">
					</div>

					<button type="submit" class="btn" value="submit">Sign Up</button>
				</form>

				<div class="col-12 new-user">
					<a href="login.jsp">Return to Login</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>