<%@page import="com.jquery.command.SearchCriteria"%>
<%@page import="com.jquery.command.PageMaker"%>
<%@page import="java.util.List"%>
<%@page import="com.jquery.dto.NoticeVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	SearchCriteria cri = ((PageMaker)request.getAttribute("pageMaker")).getCri();
	pageContext.setAttribute("cri", cri);
%>
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
					  	<select class="form-control col-md-3" name="perPageNum" id="perPageNum" onChange="searchList_go(1);">
					  		<option value="10">정렬개수</option>
					  		<option value="20" ${cri.perPageNum eq 20 ? 'selected':'' }>20개씩</option>
					  		<option value="30" ${cri.perPageNum eq 30 ? 'selected':'' }>30개씩</option>
					  		<option value="50" ${cri.perPageNum eq 50 ? 'selected':'' }>50개씩</option>
					  	</select>					
						<select class="form-control col-md-3" name="searchType" id="searchType">
							<option value="tcw" ${cri.searchType eq '' ? 'selected':'' }>전 체</option>
							<option value="t" ${cri.searchType eq 't' ? 'selected':'' }>제 목</option>
							<option value="w" ${cri.searchType eq 'w' ? 'selected':'' }>작성자</option>
							<option value="c" ${cri.searchType eq 'c' ? 'selected':'' }>내 용</option>
							<option value="tc" ${cri.searchType eq 'tc' ? 'selected':'' }>제목+내용</option>
							<option value="cw" ${cri.searchType eq 'cw' ? 'selected':'' }>작성자+내용</option>							
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
				<table class="table table-bordered text-center noticeList" >					
					<tr style="font-size:0.95em;">
						<th style="width:10%;">번 호</th>
						<th style="width:50%;">제 목</th>
						<th style="width:15%;">작성자</th>
						<th>등록일</th>
						<th style="width:10%;">조회수</th>
					</tr>				
					
					<% List<NoticeVO> noticeList = (List<NoticeVO>) request.getAttribute("noticeList");
						for(NoticeVO notice : noticeList){
							pageContext.setAttribute("notice", notice);
					%>
					
					<tr style='font-size:0.85em;'>
						<td>${notice.nno }</td>
						<td style="text-align:left;max-width: 100px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
							<a href="javascript:initPageParam();OpenWindow('detail.do?nno=${notice.nno }','상세보기',800,700);">
								<span class="col-sm-12 ">${notice.title }
								</span>
							</a>
						</td>
						<td>${notice.writer }</td>
						<td>${notice.regdate }</td>
						<td><span class="badge bg-red">${notice.viewcnt }</span></td>
					</tr>
					
					<%
						}
					%>
										
				</table>				
			</div>
			<div class="card-footer">
				<nav aria-label="member list Navigation">
				 	<ul class="pagination justify-content-center m-0">
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
						
						<% 	PageMaker pageMaker = (PageMaker) request.getAttribute("pageMaker");
							for(int i=pageMaker.getStartPage(); i<pageMaker.getEndPage()+1; i++){
								pageContext.setAttribute("pageNum", i);
						%>
						
						<li class="page-item ${pageMaker.cri.page == pageNum?'active':''}">
							<a class="page-link" href="javascript:searchList_go(${pageNum});" >${pageNum }
							</a>
						</li>
						
						<%	
							}
						%>
						
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
				 </nav>
			</div>
		</div>
		
    </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

<form id="pageForm">
	<input type="hidden" name="page" value="${cri.page }">
	<input type="hidden" name="perPageNum" value="${cri.perPageNum }">
	<input type="hidden" name="searchType" value="${cri.searchType }">
	<input type="hidden" name="keyword" value="${cri.keyword}">
</form>

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

<script type="text/javascript">
function searchList_go(page, url){
	
	var form = $('#pageForm');
	
	form.find('[name="page"]').val(page);
	form.find('[name="perPageNum"]').val($('select[name="perPageNum"]').val());
	form.find('[name="searchType"]').val($('select[name="searchType"]').val());
	form.find('[name="keyword"]').val($('div.input-group>input[name="keyword"]').val());
	
	form.attr("method","post");
	if(url){
		form.attr("action",url);
	}else{
		form.attr("action","list.do");
	}
    form.submit();
}

</script>

</body>
</html>