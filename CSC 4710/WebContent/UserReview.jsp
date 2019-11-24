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
<title>Review</title>

</head>
<body>

<%
    String result = (String) request.getAttribute("result");
%>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">
        var value = "<%=result%>";
        if(value != "success" && value != "null"){
        	swal("Unable to Add Review", "Please type Again", "error");
        }
</script>
	<div class="col-xl-8 main-section">
		<div class="modal-content table table-dark table-hover"
			style="padding: 25px;">
			<c:forEach items="${listReview}" var="post">
				<div
					class="list-group list-group-item list-group-item-action flex-column align-items-start">
					<div class="d-flex w-100 justify-content-between">
						<h5 class="mb-1">${post.getReview_rating()}</h5>
						<small>${post.getPost_date()}</small>
					</div>
					<p class="mb-1">${post.getReview_description()}</p>
					<small>by ${post.getFNAME()} ${post.getLNAME()}</small>
				</div>
			</c:forEach>
			
			<h1 style="align-self: center; color: #c2fbfe; margin-top:20px;">Add Review</h1>

			<form action="AddItemReview" method="post">
				<div class="form-group">
					<div class="container">
						<div class="row">
							<div class="input-group mb-3 ">
								<div class="input-group-prepend">
									<label class="input-group-text">Rating</label>
								</div>
								<select class="custom-select" name="rating">
									<option selected>Choose...</option>
									<option value="Excellent">Excellent</option>
									<option value="Fair">Fair</option>
									<option value="Good">Good</option>
									<option value="Poor">Poor</option>
								</select>
							</div>
						</div>
					</div>
					<label >Description</label> <input type="text"
						class="form-control" name="description" placeholder="">
				</div>
				<button type="submit" class="btn btn-primary" value="submit">
        Submit</button>
			</form>
		</div>
	</div>

</body>
</html>