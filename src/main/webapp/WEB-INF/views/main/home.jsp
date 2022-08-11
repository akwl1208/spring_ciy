<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="container">
	<button class="btn btn-outline-primary btn-ajax1">ajax 테스트</button>
	<div class="box"></div>
</div>

<script>
 $(function(){
	$('.btn-ajax1').click(function(){
		let board = {
			bd_num : 1,
			bd_title : '제목',
			bd_content : '내용'
		}
		$.ajax({
			//비동기 : 작업이 끝날 때까지 기다리지 않음 -> ajax가 동작 중인지 끝났는지 상관없이 다음 작업
			//동기 : 작업이 끝날 때까지 기다림 -> ajax가 끝날 때까지 기다린 후, 다음 작업
	        async:true, //비동기 여부 : true(비동기), false(동기)
	        type:'POST',
	        data: JSON.stringify(board),
	        url: '<%=request.getContextPath()%>/test',
	        dataType:"json", //서버에서 보내준 데이터 타입
	        contentType:"application/json; charset=UTF-8", //화면에서 ajax로 보내줄 데이터 타입 -> data의 타입
	        success : function(data){
	        	console.log(data);
	            console.log(data.bd_title);
	        }
	        ,error : function(a,b,c){
	        }
    	});
		$.ajax({
	        async:false, 
	        type:'POST',
	        data: JSON.stringify(board),
	        url: '<%=request.getContextPath()%>/test2',
	        dataType:"json",
	        contentType:"application/json; charset=UTF-8",
	        success : function(data){
	        	console.log(data);
	            console.log(data[0].bd_title); //보내준 데이터(배열)에 따라 다르게 받음
	        }
	        ,error : function(a,b,c){
	        }
    	});
		$.ajax({
	        async:false, 
	        type:'POST',
	        data: JSON.stringify(board),
	        url: '<%=request.getContextPath()%>/test3',
	        dataType:"json",
	        contentType:"application/json; charset=UTF-8",
	        success : function(data){
	        	console.log(data);
	            console.log(data.board.bd_title); //객체 
	        }
	        ,error : function(a,b,c){
	        }
    	});
	})
 })
</script>


