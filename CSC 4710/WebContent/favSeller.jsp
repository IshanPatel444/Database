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

<%
    String result = (String) request.getAttribute("result");
%>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">
        var value = "<%=result%>";
        if(value != "error" && value != "null"){
        	swal("List has been updated");
        }
</script>

<div class="modal-dialog text-center">
	
		<div class="col-sm-12 main-section">
			<div class="modal-content">
			<h1 style="align-self: center; color: #c2fbfe;">Welcome</h1>
			<div class="form-inline">
			<form>
					<a href="search.jsp">Search Item</a>&nbsp;&nbsp;&nbsp;
				</form>
			<form>
					<a href="add_item.jsp">Add Item</a>&nbsp;&nbsp;&nbsp;
				</form>
			
				<form action="UserItemInDB" method="post">
					<button type="submit" class="btn btn-link" style="width: 100%;">
						Item by User
					</button>
				</form>
			</div>

				<table class="table table-dark table-hover">
					<thead>
						<tr>
							<th scope="col">Favorite</th>
							<th scope="col">Name</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${AllUserID}" var="favUser">
							<tr>
								<td><input class="form-check-input" type="checkbox"
									name="ck_fav" id=${favUser.getUserID() }
									value=${favUser.getUserID() } /> <script
										type="text/javascript">
										var message = '<c:out value="${favUser.isChecked()}"/>';
										if(message=='true')
											document.getElementById(${favUser.getUserID()}).checked = true;
									</script></td>
								<td>${favUser.getFullName()}</td>
								<td>
									<form action = "UserItemInDB" method="post">
												<div class="form-group col-xs-6">
						<button type="submit" name= "sellerID" class="btn" value="${favUser.getUserID()}"
							style="width: 100%;">Visit</button>
							</div>
						</form>
								</td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
<script type="text/javascript">
	
	function getCK(){
		var usersCK = document.getElementsByName('ck_fav');
		var selectedUser = [];
		
		for (var i = 0; i < usersCK.length; i++) {
			if(usersCK[i].checked)
				selectedUser.push(usersCK[i].value);
		}

		document.getElementById("hiddenField").value = selectedUser;
	}
	</script>
				<form action="FavUser" method="post" class="col-12">
					<input type="hidden" id="hiddenField" name="hiddenField" /> <input
						type="submit" class="btn" value="submit" onclick="getCK()">
				</form>
				<br>

				<div class="col-12 new-user">
					<a href="login.jsp">Return to Login</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>