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
		Search Result for ${search}
	</title>
</head>

<body>

<%
    String result = (String) request.getAttribute("result");
%>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">
        var value = "<%=result%>";
        if(value != "success" && value != "null"){
        	swal("Unable to Search", "Please type something", "error");
        }
</script>
	<div class="col-xl-8 main-section">
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
			<form action="SearchInDB" method="post">
				<div class="row mb-4">
					<div class="form-group col-md-9">
						<input placeholder="What're you searching for?"
							class="form-control form-control-underlined" type="search"
							id="Categories_Search" name="search">
					</div>
					<div class="form-group col-md-3">
						<button type="submit" class="btn" value="Search"
							style="width: 100%;">Search</button>
					</div>
				</div>


				</form>
				<table class="table table-dark table-hover">
					<thead>
						<tr>
							<th scope="col">Title</th>
							<th scope="col">Description</th>
							<th scope="col">Posted Date</th>
							<th scope="col">Price</th>
							<th scope="col">Item listed in Categories</th>
						</tr>
					</thead>
					<tbody>
			<c:forEach items="${listItem}" var="post">
				<tr>
					<td>${post.getTitle()}</td>
					<td>${post.getDescription()}</td>
					<td>${post.getPost_date()}</td>
					<td>$ ${post.getPrice()}</td>
					<td>${post.getCategoryList()}</td>
					<td>
						<form action = "ItemReview" method="post">
												<div class="form-group col-xs-6">
						<button type="submit" name= "itemID" class="btn" value="${post.getIditem()}"
							style="width: 100%;">Review</button>
					</div>
							
						</form>
					</td>
				</tr>
			</c:forEach>
					</tbody>
				</table>
		
		</div>
	</div>
</body>

</html>