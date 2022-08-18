<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<body>
	<div class=container>
		<form method="post" class="mt-5" action="<c:url value='/board/insert'></c:url>">
			<h1>게시글 등록</h1>
			<input name="bd_ori_num" value="${bd_ori_num}" type="hidden">
			<input name="bd_depth" value="${bd_depth}" type="hidden">
			<div class="form-group">
			  <input type="text" class="form-control" name="bd_title" placeholder="제목">
			</div>
			<div class="form-group">
			  <textarea class="form-control" rows="10" name="bd_content" placeholder="내용"></textarea>
			</div>
			<button class="btn btn-outline-primary col-12 mb-3">게시글 등록</button>
		</form>
	</div>
</body>
</html>