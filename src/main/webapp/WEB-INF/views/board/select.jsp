<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세</title>
</head>
<body>
	<div class=container>
		<!-- board는 controller에서 보내준 것임 -->
		<c:if test="${board != null && 'N'.charAt(0) == board.bd_del}">
			<h1>게시글 상세</h1>
			<div class="form-group">
			  <input type="text" class="form-control" name="bd_title" value="${board.bd_title}" readonly>
			</div>
			<div class="form-group">
			  <input type="text" class="form-control" name="bd_me_id" value="${board.bd_me_id}" readonly>
			</div>
			<div class="form-group">
			  <input type="text" class="form-control" name="bd_reg_date" value="${board.bd_reg_date_time_str}" readonly>
			</div>
			<div class="form-group">
			  <input type="text" class="form-control" name="bd_views" value="${board.bd_views}" readonly>
			</div>
			<div class="from-group mb-3">
				<button type="button" class="btn btn<c:if test="${likes.li_state != 1 }">-outline</c:if>-info up btn-likes">추천</button>
				<button type="button" class="btn btn<c:if test="${likes.li_state != -1 }">-outline</c:if>-danger down btn-likes">비추천</button>
			</div>
			<div class="form-group">
			  <textarea class="form-control" rows="10" name="bd_content" readonly>${board.bd_content}</textarea>
			</div>
			<c:if test="${user != null && user.me_id == board.bd_me_id}">
				<a href="<%=request.getContextPath()%>/board/update/${board.bd_num}" class="btn btn-outline-danger">수정</a>
				<a href="<%=request.getContextPath()%>/board/delete/${board.bd_num}" class="btn btn-outline-danger">삭제</a>
			</c:if>
			<div class="form-group">
				<textarea class="form-control" name="co_content"></textarea>
				<button class="btn btn-outline-info col-12 btn-comment-insert">댓글등록</button>
			</div>
			<div class="list-comment"> </div>
			<ul class="pagination justify-content-center"></ul>
		</c:if>
		<c:if test="${board != null && 'A'.charAt(0) == board.bd_del}">
			<h1>관리자에 의해 삭제된 게시글입니다</h1>
		</c:if>
		<c:if test="${board != null && 'Y'.charAt(0) == board.bd_del}">
			<h1>작성자에 의해 삭제된 게시글입니다</h1>
		</c:if>
		<c:if test="${board == null}">
			<h1>잘못된 경로로 접근했습니다</h1>
		</c:if>
	</div>
	<script>
		let criteria = {
			page : 1,
			perPageNum : 5
		}
		let bd_num = '${board.bd_num}';
		$(function(){
			$('.btn-likes').click(function(){
				let li_state = $(this).hasClass('up') ? 1 : -1 ;
				let obj = {
						li_bd_num : '${board.bd_num}',
						li_state : li_state,
						li_me_id : '${user.me_id}'
				}
				//로그인 안함
				if(obj.li_me_id == ''){
					if(confirm('추천/비추천은 로그인해야 합니다. 로그인하겠습니까?')){
						location.href='<%=request.getContextPath()%>/login'
					}
				}
				$.ajax({
	        async:true,
	        type:'POST',
	        data: JSON.stringify(obj),
	        url: '<%=request.getContextPath()%>/board/likes',
	        contentType:"application/json; charset=UTF-8",
	        success : function(data){
	        	$('.btn-likes.up').removeClass('btn-info').addClass('btn-outline-info');
	        	$('.btn-likes.down').removeClass('btn-danger').addClass('btn-outline-danger');
						if(data == '1'){
							alert('해당 게시글을 추천했습니다')
							$('.btn-likes.up').addClass('btn-info').removeClass('btn-outline-info');
						}
						else if(data == '-1'){
							alert('해당 게시글을 비추천했습니다')
							$('.btn-likes.down').addClass('btn-danger').removeClass('btn-outline-danger');
						}else if(data == '10'){
							alert('해당 게시글 추천을 취소했습니다')
						}else if(data == '-10'){
							alert('해당 게시글 비추천을 취소했습니다')
						}else
							alert('잘못된 접근입니다')
	        }
				});
			})
		})
		//댓글 등록
		$(function(){
			$('.btn-comment-insert').click(function(){
				let co_content = $('[name=co_content]').val();
				let co_bd_num = '${board.bd_num}';
				
				if('${user.me_id}' == ''){
					if(confirm('로그인한 회원만 댓글 작성이 가능합니다. 로그인하겠습니까?')){
						location.href = '<%=request.getContextPath()%>/login'
						return;
					}
				}
				
				let obj = {
					co_content : co_content,
					co_bd_num : co_bd_num
				}
				console.log(obj);
				$.ajax({
			    async: true,
			    type:'POST',
			    data: JSON.stringify(obj),
			    url: '<%=request.getContextPath()%>/ajax/comment/insert',
			    dataType:"json", 
			    contentType:"application/json; charset=UTF-8",
			    success : function(data){
			    	alert(data.res);
			    	getCommentList(criteria, bd_num);
			    }
				});
			})
		})
		//댓글 삭제
		$(function(){
			$(document).on('click','.btn-comment-delete',function () {
				let co_num = $(this).siblings('[name=co_num]').val();
				let obj={
					co_num
				};
				$.ajax({
			    async: true,
			    type:'POST',
			    data: JSON.stringify(obj),
			    url: '<%=request.getContextPath()%>/ajax/comment/delete',
			    dataType:"json", 
			    contentType:"application/json; charset=UTF-8",
			    success : function(data){
			    	if(data.res){
			    		alert("댓글이 삭제되었습니다")
			    		getCommentList(criteria, bd_num);
			    	}else{
			    		alert("삭제에 실패했습니다")
			    	}
			    }
				})
			})
		})
		//댓글 수정
		$(function(){
			$(document).on('click','.btn-comment-update',function(){
				$('.btn-comment-update-cancel').click();
				//기존 댓글 내용이 입력창으로 바뀜
				let co_content = $(this).siblings('.co_content').text();
				let str ='<textarea class="co_content2">'+co_content+'</textarea>';
				$(this).siblings('.co_content').after(str);
				$(this).siblings('.co_content').hide();
				$(this).hide();
				$(this).siblings('.btn-comment-delete').hide();
				
				str = '<button class="btn-comment-update-complete">수정완료</button>';
				str += '<button class="btn-comment-update-cancel">수정취소</button>';
				$(this).parent().append(str);
			})
			//수정완료 버튼 클릭
			$(document).on('click','.btn-comment-update-complete',function(){
				let co_num = $(this).siblings('[name=co_num]').val();
				let co_content = $(this).siblings('.co_content2').val();
				let obj={
					co_num,
					co_content
				};
				console.log(obj)
				$.ajax({
			    async: true,
			    type:'POST',
			    data: JSON.stringify(obj),
			    url: '<%=request.getContextPath()%>/ajax/comment/update',
			    dataType:"json", 
			    contentType:"application/json; charset=UTF-8",
			    success : function(data){
			    	if(data.res){
			    		alert("댓글이 수정되었습니다")
			    		getCommentList(criteria, bd_num);
			    	}else{
			    		alert("수정에 실패했습니다")
			    	}
			    }
				})
			})
			//수정취소 버튼 클릭
			$(document).on('click','.btn-comment-update-cancel',function(){
				$(this).siblings('.co_content').show();
				$(this).siblings('.co_content2').remove();
				$(this).siblings('.btn-comment-update').show();
				$(this).siblings('.btn-comment-delete').show();
				$('.btn-comment-update-cancel').remove();
				$('.btn-comment-update-complete').remove();
			})
		})
		function getCommentList(cri,bd_num){
			$.ajax({
		    async: true,
		    type:'POST',
		    data: JSON.stringify(cri),
		    url: '<%=request.getContextPath()%>/ajax/comment/list/'+bd_num,
		    dataType:"json", 
		    contentType:"application/json; charset=UTF-8",
		    success : function(data){
		    	let str = '';
		    	for(co of data.list){
			    	str +=
						'<div class="item-comment">'+
							'<div class="co_me_id"><b>'+co.co_me_id+'</b></div>'+
							'<div class="co_content">'+co.co_content+'</div>'+
							'<div class="co_reg_date">'+co.co_reg_date_time_str+'</div>'+
							'<input value="'+co.co_num+'" name="co_num" type="hidden">';
							if(co.co_me_id == '${user.me_id}'){
								str +=
								'<button class="btn-comment-delete">삭제</button>'+
								'<button class="btn-comment-update">수정</button>'
							}
						str +=	
						'</div>'
		    	}
		    	$('.list-comment').html(str);
		    	//페이지 네이션
		    	let pm = data.pm;
		    	let pmStr = '';
					if(pm.prev){
						pmStr +=
						'<li class="page-item">'+
							'<a class="page-link" href="javascript:0;" onclick="criteria.page = '+(pm.startPage-1)+';getCommentList(criteria,bd_num)">이전</a>'+
						'</li>';
					}
					for(let i = pm.startPage; i<=pm.endPage; i++){
						let active = pm.cri.page == i ? 'active' : '';
						pmStr +=
						'<li class="page-item '+ active + '">'+
		  	  		'<a class="page-link" href="javascript:0;" onclick="criteria.page = '+(i)+';getCommentList(criteria, bd_num)">' + i + '</a>'+
		  	  	'</li>';	
					}	    			
					if(pm.next){
						pmStr +=
						'<li class="page-item">'+
							'<a class="page-link" href="javascript:0;" onclick="criteria.page = '+(pm.endPage+1)+'getCommentList(criteria, bd_num)">다음</a>'+
						'</li>';
					}
					$('.pagination').html(pmStr);
		    }
			});
		}
		getCommentList(criteria, bd_num);
	</script>
</body>
</html>