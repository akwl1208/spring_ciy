<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�α���</title>
</head>
<body>
	<div class="container">
		<form action="/spring/login" class="mt-3" method="post">
			<h1>�α���</h1>
			<div class="form-group">
	 			<input type="text" class="form-control" name="me_id" placeholder="���̵�">
			</div>
			<div class="form-group">
		 		<input type="password" class="form-control" name="me_pw" placeholder="��й�ȣ">
			</div>
			<button class="btn btn-outline-success mb-3 col-12">�α���</button>
		</form>
		<a href="<c:url value="/find?type=id"></c:url>">���̵� ã��</a>/
		<a href="<c:url value="/find?type=pw"></c:url>">��� ã��</a>
	</div>
</body>
</html>