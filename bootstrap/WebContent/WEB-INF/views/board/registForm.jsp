<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  
  <title>Board | Regist</title>

  <!-- Font Awesome Icons -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap/plugins/fontawesome-free/css/all.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap/dist/css/adminlte.min.css">
  <!-- include summernote css/js -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/bootstrap/plugins/summernote/summernote.min.css" rel="stylesheet">
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
            <h1 class="m-0 text-dark">Free Board</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">목 록</a></li>
              <li class="breadcrumb-item active">Free Board</li>
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
                  <h3 class="card-title p-1">글등록</h3>
                  <div class ="card-tools">
                     <button type="button" class="btn btn-primary" id="registBtn" onclick="submit_go();">등 록</button>
                     &nbsp;&nbsp;&nbsp;&nbsp;
                     <button type="button" class="btn btn-warning" id="cancelBtn" onclick="CloseWindow();">취 소</button>
                  </div>
               </div><!--end card-header  -->
               <div class="card-body pad">
                  <form role="form" method="post" action="regist.do" name="registForm" enctype="multipart/form-data">
                     <div class="form-group">
                        <label for="title">제 목</label> 
                        <input type="text" id="title"
                           name='title' class="form-control" placeholder="제목을 쓰세요">
                     </div>                     
                     <div class="form-group">
                        <label for="writer">작성자</label> 
                        <input type="text" id="writer" name="writer" class="form-control" value="${loginUser.id }" readonly="readonly">
                     </div>
                     <div class="form-group">
                        <label for="content">내 용</label>
                        <textarea class="textarea" name="content" id="content" rows="20"
                           placeholder="1000자 내외로 작성하세요." style="display: none;"></textarea>
                     </div>
                     <div class="form-group">                        
                     <div class="card card-outline card-success">
                        <div class="card-header">
                           <h5 style="display:inline;line-height:40px;">첨부파일 : </h5>
                           &nbsp;&nbsp;<button class="btn btn-xs btn-primary" 
                           type="button" id="addFileBtn" onclick="addBtnClick_go();">Add File</button>
                        </div>                           
                        <div class="card-footer fileInput">
                        </div>
                     </div>
                  </div>
                  </form>
               </div><!--end card-body  -->
               <div class="card-footer" style="display:none">
                  
               </div><!--end card-footer  -->
            </div><!-- end card -->            
         </div><!-- end col-md-12 -->
      </div><!-- end row -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

<!-- jQuery -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/dist/js/adminlte.min.js"></script>
<!-- Summernote -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/summernote/summernote-bs4.min.js"></script>
<!-- handlebars -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.6/handlebars.min.js" ></script>
<!-- common -->
<script src="<%=request.getContextPath() %>/resources/js/common.js?v=1" ></script>

<script>

summernote_start('#content');

function submit_go(){
	
	var form = document.registForm;
	if(form.title.value==""){
		form.title.focus();
		alert("제목은 필수입니다.");
		return;
	}
	
	var files = $('input[name="uploadFile"]');
	for(var file of files){
		console.log(file.name + " : " + file.value);
		if(file.value==""){
			alert("파일을 선택하세요.");
			file.focus();
			file.click();
			return;
		}
	}
	
	form.submit();
}
</script>

<!-- 파일 업로드 -->
<script type="text/javascript">
var fileIndex = 0;

function addBtnClick_go(){
// 	alert("chk");
	if($('input[name="uploadFile"]').length >= 5){
		alert("파일추가는 5개까지만 가능합니다.");
		return;
	}
	
	var input=$('<input>').attr({"type":"file","name":"uploadFile"}).css("display","inline");
	var div=$('<div>').addClass("inputRow");
	div.append(input).append("<button style='border:0;outline:0;' class='badge bg-red btn"+fileIndex+"' onclick='deleteFile_go("+fileIndex+");' type='button'>X</button>");
	div.appendTo('.fileInput');
	
	fileIndex++;
}

function deleteFile_go(number){
// 	$(data).closest("div").remove();
	$('div.inputRow button.btn'+number).parent('div.inputRow').remove();
}

$('.fileInput').on('change','input[type="file"]',function(event){
	if(this.files[0].size>1024*1024*40){
		alert("파일 용량이 40MB를 초과하였습니다.");
		this.value="";
		$(this).focus();
		return false;
	}
});

</script>

</body>
</html>