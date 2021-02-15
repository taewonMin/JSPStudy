<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  
  <title>Notice | home</title>

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
            <h1 class="m-0 text-dark">Notice Board</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="javascript:initPageParam();searchList_go(1);">목 록</a></li>
              <li class="breadcrumb-item active">Notice Board</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <div class="content">
   		<div class="card">
			<div class="card-header with-border">
				<button type="button" class="btn btn-primary" id="registBtn" onclick="OpenWindow('regist.do','글등록',800,700);">글등록</button>				
				<div id="keyword" class="card-tools" style="width:550px;">
					<div class="input-group row">	
						 <!-- sort num -->
					  	<select class="form-control col-md-3" name="perPageNum" id="perPageNum">
					  		<option value="10" >정렬개수</option>
					  		<option value="20">20개씩</option>
					  		<option value="30">30개씩</option>
					  		<option value="50">50개씩</option>
					  		
					  	</select>					
						<select class="form-control col-md-3" name="searchType" id="searchType">
							<option value="tcw" >전 체</option>
							<option value="t">제 목</option>
							<option value="w" >작성자</option>
							<option value="c">내 용</option>
							<option value="tc">제목+내용</option>
							<option value="cw">작성자+내용</option>							
						</select>					
						<input  class="form-control col-md-5" type="text" name="keyword" placeholder="검색어를 입력하세요." value=""/>
						<span class="input-group-append col-me-1">
							<button class="btn btn-primary" type="button" onclick="searchList_go(1);" 
							data-card-widget="search">
								<i class="fa fa-fw fa-search"></i>
							</button>
						</span>
					</div>
				</div>						
			</div>
			<div class="card-body">
				<table class="table table-bordered text-center noticeList" >					
					<tr style="font-size:0.95em;">
						<th style="width:10%;">번 호</th>
						<th style="width:50%;">제 목</th>
						<th style="width:15%;">작성자</th>
						<th>등록일</th>
						<th style="width:10%;">조회수</th>
					</tr>				
					
				</table>				
			</div>
			<div class="card-footer">
				<ul id="pagination" class="pagination justify-content-center m-0">
								
				</ul>		
			</div>
		</div>
		
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

<!-- REQUIRED SCRIPTS -->

<!-- jQuery -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/dist/js/adminlte.min.js"></script>
<!-- handlebars -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.6/handlebars.min.js" ></script>

<!-- jquery cookie -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>


<!-- common -->
<script src="<%=request.getContextPath() %>/resources/js/common.js?v=1"></script>

<script  type="text/x-handlebars-template"  id="notice-td-template">
{{#each .}}
<tr style='font-size:0.85em;' class="notice">
	<td>{{nno }}</td>
	<td style="text-align:left;max-width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
		<a href="javascript:initPageParam();OpenWindow('detail.do?nno={{nno }}','상세보기',800,700);">
			<span class="col-sm-12 ">{{title }}
			</span>
		</a>
	</td>
	<td>{{writer }}</td>
	<td>{{prettifyDate regdate }}</td>
	<td><span class="badge bg-red">{{viewcnt }}</span></td>
</tr>
{{/each}}
</script>
<script type="text/javascript">

setPageParams($.cookie('page'), $.cookie('perPageNum'),$.cookie('searchType'),$.cookie('keyword'));

showList();

function searchList_go(page){
	//$.ajax data 생성
	setPageParams(page, $('select[name="perPageNum"]').val(), $('select[name="searchType"]').val(),$('input[name="keyword"]').val());
	
	var pageParamsKeys = Object.keys(pageParams);
	for(var key of pageParamsKeys){

		$.cookie(key,pageParams[key],{path:"/"});
	}
	
	showList();
}

function showList(){
	
	if($.cookie('perPageNum'))
		$('select[name="perPageNum"]').val($.cookie("perPageNum"));
	if($.cookie('searchType'))
		$('select[name="searchType"]').val($.cookie("searchType"));
	if($.cookie('keword'))
		$('input[name="keyword"]').val($.cookie("keyword"));
	
	$.ajax({
		url: '/notice/list.do',
		type: "post", 	// 반드시 'post' 보낼 것.
		data: JSON.stringify(pageParams),
		success: function(data){
			printData(data.noticeList,$('.noticeList'),$('#notice-td-template'),'tr.notice');
			printPaging(data.pageMaker,$('ul.pagination'));
		},
		error:function(){
			alert("시스템장애로 현재 서비스가 불가합니다.");
		}
	});
}
</script>

</body>
</html>