<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<title>Item Description</title>
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
<%
    String result = (String) request.getAttribute("result");
%>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">
        var value = "<%=result%>";
        if(value != "success" && value != "null"){
        	swal("Unable to add Item", "limit excited for the day", "error");
        }
</script>

	<div class="modal-dialog text-center">
	
		<div class="col-sm-9 main-section">
			<div class="modal-content">
			<h1 style="align-self: center; color: #c2fbfe;">Welcome</h1>
			<div class="form-inline">
			<form>
					<a href="search.jsp">Search Item</a>&nbsp;&nbsp;&nbsp;
				</form>
			<form action="FavUser" method="post">
					<button type="submit" class="btn btn-link" style="width: 100%;">
						Fav User
					</button>
				</form>
			
				<form action="UserItemInDB" method="post">
					<button type="submit" class="btn btn-link" style="width: 100%;">
						Item by User
					</button>
				</form>
			</div>
				<form action="AddItemDB" method="post" class="col-12">

					<div class="form-group">
						<input type="text" class="form-control" placeholder="Enter Price"
							name="price">
					</div>

					<div class="form-group">
						<input type="text" class="form-control"
							placeholder="Enter Description" name="description">
					</div>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="Enter Title"
							name="title">
					</div>

					<ul class="list-group list-group-flush">

						<li class="list-group-item"><input class="form-check-input"
							type="checkbox" name="ck_category" value="electronic">
							<label class="form-check-label"
							for="electronic">ELECTRONIC </label></li>

						<li class="list-group-item"><input class="form-check-input"
							type="checkbox" name="ck_category" value="consoles" id="consoles">
							<label class="form-check-label" for="consoles">GAME
								CONSOLES </label></li>
						
						<li class="list-group-item"><input class="form-check-input"
							type="checkbox" name="ck_category" value="desktop" id="desktop">
							<label class="form-check-label" for="desktop">DESKTOP </label></li>
				
						<li class="list-group-item"><input class="form-check-input"
							type="checkbox" name="ck_category" value="laptop" id="laptop">
							<label class="form-check-label" for="laptop">LAPTOP </label></li>
			
						<li class="list-group-item"><input class="form-check-input"
							type="checkbox" name="ck_category" value="phone" id="phone">
							<label class="form-check-label" for="phone">CELL-PHONE </label></li>
					</ul>
					<br>
					<button type="submit" class="btn" value="submit">Add Item
					</button>
				</form>

				<div class="col-12 new-user">
					<a href="login.jsp">Return to Login</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>