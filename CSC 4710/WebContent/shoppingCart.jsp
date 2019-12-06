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
			
						<table class="table table-dark table-hover">
					<thead>
						<tr>
							<th scope="col">Title</th>
							<th scope="col">Description</th>
							<th scope="col">Price</th>
							<th scope="col">Remove</th>
						</tr>
					</thead>
					<tbody>
			<c:forEach items="${listItem}" var="post">
				<tr>
					<td>${post.getTitle()}</td>
					<td>${post.getDescription()}</td>
					<td>$ ${post.getPrice()}</td>
								<td>
						<form action = "removeItemCart" method="post">
						<div class="form-group col-xs-6">
						<button type="submit" name= "itemID" class="btn" value="${post.getIditem()}"
						style="width: 100%;">Remove</button>
					</div>
						</form>
					</td>
		
				</tr>
			</c:forEach>
					</tbody>
				</table>
	
			
			</form>
			<div class="col-12 text-center" style="margin-bottom: 20px;">
				<a href="login.jsp">Log Out</a> &nbsp;&nbsp;&nbsp;
			</div>
		</div>
	</div>
</body>
</html>