<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  
  <title>Board | home</title>

  <!-- Font Awesome Icons -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap/plugins/fontawesome-free/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap/dist/css/adminlte.min.css">
  <!-- Google Font: Source Sans Pro -->
  <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700" rel="stylesheet">
</head>
<body class="hold-transition sidebar-mini">



  <!-- Content Wrapper. Contains page content -->
  <div style="width:100%;">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">Board Detail</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">상세보기</a></li>
              <li class="breadcrumb-item active">Free Board</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <div class="content">
    	<div class="row">
			<div class="col-md-12">
				<div class="card card-outline card-info">
					<div class="card-header">
						<h3 class="card-title">상세보기</h3>
						<div class="card-tools">
							<button type="button" id="modifyBtn" class="btn btn-warning" onclick="location.href='modifyForm.do?bno=${board.bno}';">MODIFY</button>						
						    <button type="button" id="removeBtn" class="btn btn-danger" onclick="remove_go();">REMOVE</button>					    
						    <button type="button" id="listBtn" class="btn btn-primary" onclick="CloseWindow();">CLOSE</button>
						</div>
					</div>
					<div class="card-body">
						<div class="form-group col-sm-12">
							<label for="title">제 목</label>
							<input type="text" class="form-control" id="title" value="${board.title }" readonly />							
						</div>
						<div class="row">	
							<div class="form-group col-sm-4" >
								<label for="writer">작성자</label>
								<input type="text" class="form-control" id="writer" value="${board.writer }" readonly />
							</div>		
							
							<div class="form-group col-sm-4" >
								<label for="regDate">작성일</label>
								<input type="text" class="form-control" id="regDate" value="${board.regDate }" readonly />
							
							</div>
							<div class="form-group col-sm-4" >
								<label for="viewcnt">조회수</label>
								<input type="text" class="form-control" id="viewcnt" value="${board.viewcnt }" readonly />
							</div>
						</div>		
						<div class="form-group col-sm-12">
							<label for="content">내 용</label>
							<div id="content"></div>	
						</div>						
					</div>
					<div class="card-footer" style="padding:0;">
						<div class="card card-warning">
							<div class="card-header" style="padding-top:0;padding-bottom:0;">
								<i class="nav-icon fas fa-book"></i>&nbsp;<span style="font-weight:bold;">Attaches</span>
							</div>
							<div class="card-body">
								<div class="row attachList">
								
									<c:forEach items="${board.attachList }" var="attach">
									<div class="col-md-4 col-sm-4 col-xs-12 attach"  style="cursor:pointer;"
										 onclick="location.href='/board/attach/getFile.do?bno=${attach.bno }&ano=${attach.ano }';">
										<div class="info-box">	
										 	<span class="info-box-icon bg-yellow">
												<i class="fa fa-copy"></i>
											</span>
											<div class="info-box-content">
												<span class ="info-box-text">${attach.regDate }</span>
												<span class ="info-box-number">${attach.fileName }</span>
											</div>
										</div>
									</div>
									</c:forEach>
									
								</div> 
							</div>
						</div>
					</div>
												
				</div><!-- end card -->				
			</div><!-- end col-md-12 -->
		</div><!-- end row  -->
		
		<!-- reply component start --> 
		<div class="row">
			<div class="col-md-12">
				<div class="card card-info">					
					<div class="card-body">
						<!-- The time line -->
						<div class="timeline" id="repliesDiv">
							<!-- timeline time label -->
							<div class="time-label" >
								<span class="bg-green">Replies List </span>							
							</div>
							
						</div>
						<div class='text-center'>
							<ul id="pagination" class="pagination justify-content-center m-0">
								
							</ul>
						</div>
					</div>
					<div class="card-footer">
						<label for="newReplyWriter">Writer</label>
						<input class="form-control" type="text" placeholder="USER ID"	 id="newReplyWriter" value="${loginUser.id }" readonly="readonly"> 
						<label for="newReplyText">Reply Text</label>
						<input class="form-control" type="text"	placeholder="REPLY TEXT" id="newReplyText">
						<br/>
						<button type="button" class="btn btn-primary" onclick="replyRegist_go();" id="replyAddBtn">ADD REPLY</button>
					</div>				
				</div>			
				
			</div><!-- end col-md-12 -->
		</div><!-- end row -->
		
		
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->



<!-- Modal -->
<div id="modifyModal" class="modal modal-default fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" style="display:none;"></h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>        
      </div>
      <div class="modal-body" data-rno>
      	<form role="frm">
	      	<input type="hidden" name="rno" id="rno">
	      	<input type="hidden" name="replyer" id="replyer">
	        <p><input type="text" name="replytext" id="replytext" class="form-control"></p>
      	</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-info" id="replyModBtn">Modify</button>
        <button type="button" class="btn btn-danger" id="replyDelBtn">DELETE</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- jQuery -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/dist/js/adminlte.min.js"></script>
<!-- Summernote -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/summernote/summernote-bs4.min.js"></script>
<!-- jquery cookie -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<!-- handlebars -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.6/handlebars.min.js" ></script>
<!-- common -->
<script src="<%=request.getContextPath() %>/resources/js/common.js?v=2" ></script>

<script type="text/javascript">

$('#content').html('${board.content}');

function remove_go(){
	if(confirm("정말 삭제하시겠습니까?")){
		location.href="remove.do?bno=${board.bno}";
	}
}
</script>

<script type="text/x-handlebars-template"  id="reply-list-template" >
{{#each .}}
<div class="replyLi" >
	<i class="fas fa-comments bg-yellow"></i>
 	<div class="timeline-item" >
  		<span class="time">
    		<i class="fa fa-clock"></i>{{prettifyDate regdate}}
	 		<a class="btn btn-primary btn-xs" id="modifyReplyBtn" data-rno={{rno}}
	    		data-replyer={{replyer}} data-toggle="modal" data-target="#modifyModal">Modify</a>
  		</span>
	
  		<h3 class="timeline-header"><strong style="display:none;">{{rno}}</strong>{{replyer}}</h3>
  		<div class="timeline-body" id="{{rno}}-replytext">{{replytext}} </div>
	</div>
</div>
{{/each}}	
</script>

<script type="text/javascript">	// 댓글 리스트
var replyPage=$.cookie('reply_page') ? $.cookie('reply_page') : 1;

searchList_go(replyPage);

//reply list
function getPage(pageInfo){
	$.getJSON(pageInfo, function(data){
		printData(data.replyList,$('#repliesDiv'),$('#reply-list-template'),'.replyLi');
		printPaging(data.pageMaker,$('.pagination'));
	});
}

function searchList_go(page){
	
	replyPage = page;
	setPageParams(replyPage,10,'','');
	
	var pageParamsKeys = Object.keys(pageParams);
	for(var key of pageParamsKeys){
		$.cookie("reply_"+key,pageParams[key],{path:"/"});
	}
	getPage("/board/replies/list.do?bno=${board.bno}&page="+replyPage);
	
	return false;
	
}

function replyRegist_go(){
	var replyer = $('#newReplyWriter').val();
	var replytext = $('#newReplyText').val();
	
	if(!(replyer && replytext)){
		alert("작성자 혹은 내용은 필수입니다.");
		return;
	}
	
	var data={
			"bno": "${board.bno}",
			"replyer": replyer,
			"replytext": replytext
	}
	
	$.ajax({
		url:"/board/replies/regist.do",
		type:"post",
		data: JSON.stringify(data),
		success: function (data){
			var result=data.split(",");
			if(result[0]=="SUCCESS"){
				alert("댓글이 등록되었습니다.")
				replyPage=result[1];	// 페이지 이동
				$.cookie('reply_page',result[1],{path:"/"});	// 상태유지
				getPage("/board/replies/list.do?bno=${board.bno}&page="+result[1]);	// 리스트 출력
				$('#newReplyWriter').val("");
				$('#newReplyText').val("");
			}else{
				alert("댓글이 등록을 실패했습니다.")
			}
		}
	});
	
}

//reply modify 권한체크
$('div.timeline').on('click','#modifyReplyBtn',function(event){
// 	alert("modify reply btn click");
	var rno = $(this).attr("data-rno");
	var replyer = $(this).attr("data-replyer");
	var replytext = $('#'+rno+'-replytext').text();
	
	$('#modifyModal input#replytext').val(replytext);
	$('#modifyModal input#rno').val(rno);
	$('#modifyModal input#replyer').val(replyer);
});

$('#replyModBtn').on('click',function(event){
	var form = $('#modifyModal form[role="frm"]');
// 	alert(form.serialize());
	
	$.ajax({
		url: "/board/replies/modify.do",
		type:"post",
		data:form.serialize(),
		success:function(result){
			alert("수정되었습니다.");
			getPage("/board/replies/list.do?bno=${board.bno}&page="+replyPage);
		},
		error:function(error){
			alert("수정 실패했습니다.");
		},
		complete:function(){
			$('#modifyModal').modal('hide');
		}
	});
	
});

$('#replyDelBtn').on('click', function(event){
// 	alert("reply delete btn click");
	var rno = $('#modifyModal input#rno').val();
// 	alert(rno);

	$.ajax({
		url:"/board/replies/remove.do",
		type:"post",
		data:JSON.stringify({rno:rno,bno:${board.bno}}),
		success:function(page){
			alert("삭제되었습니다.");
			$.cookie('reply_page',page,{path:"/"});
			getPage("/board/replies/list.do?bno=${board.bno}&page="+page);
		},
		error:function(error){
			alert("삭제 실패했습니다.");
		},
		complete:function(){
			$('#modifyModal').modal('hide');
		}
	});
});

</script>

</body>
</html>