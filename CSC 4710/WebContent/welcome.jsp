<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/solid.css">
  <script src="https://use.fontawesome.com/releases/v5.0.7/js/all.js"></script>
  <style>  <%@include file="/WEB-INF/css/style.css"%></style>
	<title>
		Welcome
	</title>
</head>

<body>
	<div class="col-xl-5 main-section">
		<div class="modal-content">
			<h1 style="align-self: center; color: #c2fbfe;">Welcome</h1>
			<div class="form-inline">
			
			<form>
					<a href="search.jsp">Search Item</a>&nbsp;&nbsp;&nbsp;
				</form>
			<form >
					<a href="add_item.jsp">Add Item</a>&nbsp;&nbsp;&nbsp;
				</form>
			
				<form action="UserItemInDB" method="post">
					<button type="submit" class="btn btn-link" style="width: 100%;">
						Item by User
					</button>
				</form>
				
				<form action="FavUser" method="post">
					<button type="submit" class="btn btn-link" style="width: 100%;">
						Fav User
					</button>
				</form>
				
			</div>
			<form class="col-5" style="align-self: center;">
				<div class="form-group form-inline">
					<label class="form-check-label" for="first_name"
						style="color: #c2fbfe;">First Name</label> <input type="text"
						class="form-control" placeholder=${first_name } name="UserID"
						readonly="">

				</div>

				<div class="form-group form-inline">
					<label class="form-check-label" for="last_name"
						style="color: #c2fbfe;">Last Name </label> <input type="text"
						class="form-control" placeholder=${last_name } name="UserID"
						readonly="">

				</div>

				<div class="form-group form-inline">
					<label class="form-check-label" for="email" style="color: #c2fbfe;">Email</label>
					<input type="text" class="form-control" placeholder=${email
						}
						name="UserID" readonly="">
				</div>

				<div class="form-group form-inline">
					<label class="form-check-label" for="gender"
						style="color: #c2fbfe;">Gender</label> <input type="text"
						class="form-control" placeholder=${gender } name="UserID" readonly>
				</div>

			</form>
			<div class="col-12 text-center" style="margin-bottom: 20px;">
				<a href="login.jsp">Log Out</a> &nbsp;&nbsp;&nbsp;
			</div>
		</div>
	</div>
</body>
</html>