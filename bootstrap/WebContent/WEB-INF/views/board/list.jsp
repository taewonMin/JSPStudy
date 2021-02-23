<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="cri" value="${pageMaker.cri }" />

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
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
    <div class="content">
     		<div class="card">
			<div class="card-header with-border">
				<button type="button" class="btn btn-primary" id="registBtn" onclick="OpenWindow('registForm.do','글등록',800,700);">글등록</button>				
				<div id="keyword" class="card-tools" style="width:550px;">
					<div class="input-group row">	
						 <!-- sort num -->
					  	<select class="form-control col-md-3" name="perPageNum" id="perPageNum" onchange="searchList_go(1);">
					  		<option value="10" >정렬개수</option>
					  		<option value="20" ${cri.perPageNum == 20 ? 'selected':''}>20개씩</option>
					  		<option value="30" ${cri.perPageNum == 30 ? 'selected':''}>30개씩</option>
					  		<option value="50" ${cri.perPageNum == 50 ? 'selected':''}>50개씩</option>
					  	</select>					
						<select class="form-control col-md-3" name="searchType" id="searchType">
							<option value="tcw" ${cri.searchType eq 'tcw' ? 'selected':''}>전 체</option>
							<option value="t" ${cri.searchType eq 't' ? 'selected':''}>제 목</option>
							<option value="w" ${cri.searchType eq 'w' ? 'selected':''}>작성자</option>
							<option value="c" ${cri.searchType eq 'c' ? 'selected':''}>내 용</option>
							<option value="tc" ${cri.searchType eq 'tc' ? 'selected':''}>제목+내용</option>
							<option value="cw" ${cri.searchType eq 'cw' ? 'selected':''}>작성자+내용</option>
						</select>					
						<input  class="form-control col-md-5" type="text" name="keyword" placeholder="검색어를 입력하세요." value="${cri.keyword }"/>
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
				<table class="table table-bordered text-center boardList" >					
					<tr style="font-size:0.95em;">
						<th style="width:10%;">번 호</th>
						<th style="width:50%;">제 목</th>
						<th style="width:15%;">작성자</th>
						<th>등록일</th>
						<th style="width:10%;">조회수</th>
					</tr>
					
					<c:forEach items="${boardList }" var="board">			
						<tr style='font-size:0.85em;'>
							<td>${board.bno }</td>
							<td style="text-align:left;max-width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
								<a href="javascript:OpenWindow('detail.do?bno=${board.bno }','상세보기',800,700);">
									<span class="col-sm-12 ">${board.title }
										<span class="nav-item" style="display:${board.replycnt > 0 ? 'visible' : 'none'};">
											&nbsp;&nbsp;<i class="fa fa-comment"></i>
											<span class="badge badge-warning navbar-badge">${board.replycnt}</span>
										</span>
									</span>
								</a>
							</td>
							<td>${board.writer }</td>
							<td>${board.regDate }</td>
							<td><span class="badge bg-red">${board.viewcnt }</span></td>
						</tr>
					</c:forEach>
					
				</table>				
			</div>
			<div class="card-footer">
				<ul id="pagination" class="pagination justify-content-center m-0">
					<li class="page-item">
						<a class="page-link" href="javascript:searchList_go(1);">
						<i class="fas fa-angle-double-left"></i>
						</a>
					</li>
					<li class="page-item">
						<a class="page-link" href="javascript:searchList_go(
						${pageMaker.prev ? pageMaker.startPage-1 : 1}				
						);"><i class="fas fa-angle-left"></i></a>
					</li>
					
					<c:forEach var="pageNum" begin="${pageMaker.startPage }" end="${pageMaker.endPage }">
						<li class="page-item ${pageMaker.cri.page == pageNum?'active':''}">
							<a class="page-link" href="javascript:searchList_go(${pageNum});" >${pageNum }
							</a>
						</li>
					</c:forEach>
					
					<li class="page-item">
						<a class="page-link" href="javascript:searchList_go(
						${pageMaker.next ? pageMaker.endPage+1 : pageMaker.cri.page}			
						);"><i class="fas fa-angle-right" ></i></a>
					</li>
					<li class="page-item">
						<a class="page-link" href="javascript:searchList_go(
							${pageMaker.realEndPage} );">
							<i class="fas fa-angle-double-right"></i></a>
					</li>		
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

<!-- common -->
<script src="<%=request.getContextPath() %>/resources/js/common.js?v=1"></script>

<form id="boardForm">
	<input type="hidden" name="page" value="${cri.page}">
	<input type="hidden" name="perPageNum" value="${cri.perPageNum}">
	<input type="hidden" name="searchType" value="${cri.searchType}">
	<input type="hidden" name="keyword" value="${cri.keyword}">
</form>

<script type="text/javascript">

function searchList_go(page,url){
   var boardForm = $('#boardForm');
   boardForm.find("[name='page']").val(page);
   boardForm.find("[name='perPageNum']").val($('select[name="perPageNum"]').val());
   boardForm.find("[name='searchType']").val($('select[name="searchType"]').val());
   boardForm.find("[name='keyword']").val($('div.input-group>input[name="keyword"]').val());
   boardForm.attr("method","post");
   if(url){
	   boardForm.attr("action",url)
   }else{
	   boardForm.attr("action","list.do")
   }
   
   boardForm.submit();
}
</script>

</body>
</html>