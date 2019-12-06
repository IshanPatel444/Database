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
		Favorite Seller
	</title>
</head>
<body>

<div class="modal-dialog text-center">
	
		<div class="col-sm-12 main-section">
			<div class="modal-content">
			<h1 style="align-self: center; color: #c2fbfe;">Welcome</h1>

				<table class="table table-dark table-hover">
					<thead>
						<tr>
							<th scope="col">Name</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${userList}" var="favUser">
							<tr>
								<td>${favUser.getFullName()}</td>								
							</tr>
						</c:forEach>
					</tbody>
				</table>
						<form action="part_4" method="post" class="col-12">
					<input
						type="submit" class="btn" value="submit">
				</form>
		
				
				<br>
			</div>
		</div>
	</div>
</body>
</html>