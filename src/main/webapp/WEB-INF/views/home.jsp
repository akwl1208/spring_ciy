<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>
 
<P>  안녕하세요. 제 이름은 ${name} 입니다. </P>

<!-- 화면에서 서버로 데이터 전송하는 법
	1. a태그
	2. form 태그
	 - input/select/textarea 태그에 name을 반드시 지정
 -->
<a href="/spring?age=20">나이는 20살입니다</a>

<form action="/spring" method="get">
	<input type="text" name="hobby"> <br>
	<input type="text" name="id"> <br>
	<input type="password" name="pw"> <br>
	<button>전송</button> <br>
</form>
<a href="/spring/login">로그인 페이지로</a>
</body>
</html>
