<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  
  <title>Notice | Modify</title>

  <!-- Font Awesome Icons -->
  <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/bootstrap/plugins/fontawesome-free/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/bootstrap/dist/css/adminlte.min.css">
  <!-- include summernote css/js -->
  <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/bootstrap/plugins/summernote/summernote.min.css" rel="stylesheet">
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
            <h1 class="m-0 text-dark">Notice Board</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">목 록</a></li>
              <li class="breadcrumb-item active">Notice Board</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content container-fluid">
		<div class="row justify-content-center">
			<div class="col-md-9" style="max-width:960px;">
				<div class="card card-outline card-info">
					<div class="card-header">
						<h3 class="card-title p-1">글수정</h3>
						<div class ="card-tools">
							<button type="button" class="btn btn-warning" id="modifyBtn">수 정</button>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button type="button" class="btn btn-danger" id="cancelBtn" onclick="history.go(-1);">취 소</button>
						</div>
					</div><!--end card-header  -->
					<div class="card-body pad">
						<form role="form" method="post" action="regist.html" name="modifyForm">
							<div class="form-group">
								<label for="title">제 목</label> 
								<input type="text" id="title"
									name='title' class="form-control" placeholder="제목을 쓰세요">
							</div>							
							<div class="form-group">
								<label for="writer">작성자</label> 
								<input type="text" id="writer" name="writer" class="form-control" value="">
							</div>
							<div class="form-group">
								<label for="content">내 용</label>
								<textarea class="textarea" name="content" id="content" rows="20"
									placeholder="1000자 내외로 작성하세요." style="display: none;"></textarea>
							</div>
							<div class="form-group">
								<div class="card card-warning">
								<div class="card-header" style="padding-top:0;padding-bottom:0;">
									<i class="nav-icon fas fa-book"></i>&nbsp;<span style="font-weight:bold;">Attaches</span>
								</div>
								<div class="card-body attachList" style="padding:5px;">
									<ul class="mailbox-attachments d-flex align-items-stretch clearfix">
										<!-- attach list -->
									</ul>
								</div>
								</div>
							</div>
							<div class="form-group">
								<div class="card card-outline card-success">
									<div class="card-header">
										<h5 style="display:inline;line-height:40px;">첨부파일 : </h5>
										&nbsp;&nbsp;<button class="btn btn-xs btn-primary" 
										type="button" id="addFileBtn">Add File</button>
									</div>									
									<div class="card-footer fileInput">
									</div>
								</div>
							</div>
						</form>
					</div><!--end card-body  -->
					<div class="card-footer" >
						
					</div><!--end card-footer  -->
				</div><!-- end card -->				
			</div><!-- end col-md-12 -->
		</div><!-- end row -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

<!-- jQuery -->
<script src="<%= request.getContextPath() %>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="<%= request.getContextPath() %>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="<%= request.getContextPath() %>/resources/bootstrap/dist/js/adminlte.min.js"></script>
<!-- summernote Editor -->
<script src="<%= request.getContextPath() %>/resources/bootstrap/plugins/summernote/summernote.min.js"></script>
<!-- jquery cookie -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<!-- handlebars -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.6/handlebars.min.js" ></script>
<!-- common -->
<script src="<%= request.getContextPath() %>/resources/js/common.js" ></script>

<script type="text/javascript">
var nno = getParameterValueFromUrl('nno');

// board 본문 가져오기
$.ajax({
	url:"/notice/detail.do?from=modify&nno="+nno,
	type:'post',
	dataType: 'json',
	success:function(notice){
		$('#title').val(notice.title);
		$('#writer').val(notice.writer);
		$('#viewcnt').val(notice.viewcnt);
		$('#regDate').val(prettifyDate(notice.regdate));
		$('#content').html(notice.content);
		
		summernote_start($("#content"));
		printData(notice.noticeAttachList,$('div.attachList > ul'),$('#attach-div-template'),'.attach-item');
	},
	error:function(xhr){
		alert('서버 장애로 데이터를 불러올 수 없습니다.');
	}
})

$('#modifyBtn').on('click',function(e){
// 	alert("modify btn click");

	if($("input[name='title']").val()==""){
		alert(input.name+"은 필수입니다.");
		$("input[name='title']").focus();
		return;
	}
	
	var files = $('input[name="uploadFile"]');
	for(var file of files){
		console.log(file.name + " : " + file.value);
		if(file.value==""){
			alert("파일을 선택하세요.");
			file.focus();
			file.click();
			return false;
		}
	}
	
	var formData = new FormData(document.modifyForm);
	formData.append("nno",nno);
	
	$.ajax({
		url: '/notice/modify.do',
		data:formData,
		type:'post',
		processData:false,
		contentType:false,
		success:function(data){
			window.location.href="/notice/detail.do?from=modify&nno="+nno;
		},
		error : function(error){
			alert("시스템 장애로 인해 글등록이 불가합니다.");
		}
	});
	
});

</script>

<script  type="text/x-handlebars-template"  id="attach-div-template">
{{#each .}}
<li class="attach-item">																			
	<div class="mailbox-attachment-info float-left ">
		<a class="mailbox-attachment-name row" name="attachedFile" attach-fileName="{{filename }}" attach-no="{{nano }}"
			 href="/notice/attach/getFile.do?nno={{nno }}&nano={{nano }}"  >													
			<span><i class="fas fa-paperclip"></i>{{filename }}&nbsp;&nbsp;</span>
			<button type="button" style="border:0;outline:0;" class="badge badge-danger">X</button>
		</a>																			
	</div>
</li>	
{{/each}}
</script>
<script type="text/javascript">
$('div.attachList').on('click','a[name="attachedFile"] > button', function(event){
// 	alert("x btn click");
	var parent = $(this).parent('a[name="attachedFile"]');
	alert(parent.attr("attach-fileName")+"파일을 삭제합니다.");
	
	var nano = parent.attr("attach-no");
	
	$(this).parents('li.attach-item').remove();
	
	var input=$('<input>').attr({
		"type":"hidden",
		"name":"deleteFile",
		"value":nano
	});
	$('form[role="form"]').prepend(input);
	
	return false;
});

$('#addFileBtn').on('click',function(data){
// 	alert("add file btn click");
	var attachedFile=$('a[name="attachedFile"]').length;	// 기존 첨부파일
	var inputFile=$('input[name="uploadFile"]').length;		// 추가된 첨부파일
	var attachCount = attachedFile+inputFile;
	
	if(attachCount >= 5){
		alert("파일추가는 5개까지만 가능합니다.");
		return;
	}
	
	var input = $('<input>').attr({"type":"file","name":"uploadFile"}).css("display","inline");
	
	var div=$('<div>').addClass("inputRow");
	div.append(input).append("<button style='border:0;outline:0;' class='badge bg-red' type='button'>X</button>");
	div.appendTo('.fileInput');
});

$('div.fileInput').on('click','div.inputRow > button',function(event){
	$(this).parent('div.inputRow').remove();
});
$('.fileInput').on('change','input[type="file"]',function(event){
	if(this.files[0].size > 1024*1024*40){
		alert("파일 용량이 40MB를 초과하였습니다.");
		this.value="";
		$(this).focus();
		return false;
	}
});
</script>

</body>
</html>