package com.jquery.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jquery.command.SearchCriteria;
import com.jquery.service.NoticeService;
import com.jquery.service.NoticeServiceImpl;
import com.jquery.utils.JsonResolver;

public class NoticeListHandler implements CommandHandler {

	private NoticeService noticeService = NoticeServiceImpl.getInstance();
	
	@Override
	public boolean isRedirect(HttpServletRequest requset) {
		return false;
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "/notice/list.jsp";

		SearchCriteria cri = new SearchCriteria();
		
		String page = request.getParameter("page");
		String perPageNum = request.getParameter("perPageNum");
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		
		System.out.println(page);
		System.out.println(perPageNum);
		System.out.println(searchType);
		System.out.println(keyword);
		
		cri.setPage(page);
		cri.setPerPageNum(perPageNum);
		cri.setSearchType(searchType);
		cri.setKeyword(keyword);
		
		Map<String, Object> dataMap = noticeService.getNoticeList(cri);
		
		request.setAttribute("noticeList", dataMap.get("noticeList"));
		request.setAttribute("pageMaker", dataMap.get("pageMaker"));
		request.setAttribute("cri", cri);
		
		return url;
	}

}
