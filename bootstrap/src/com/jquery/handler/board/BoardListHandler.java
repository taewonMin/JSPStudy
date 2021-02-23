package com.jquery.handler.board;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jquery.command.SearchCriteria;
import com.jquery.handler.CommandHandler;
import com.jquery.service.BoardService;
import com.jquery.service.BoardServiceImpl;
import com.jquery.utils.JsonResolver;

public class BoardListHandler implements CommandHandler {

	private BoardService boardService;
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String url = "board/list";
		
		// 입력
		SearchCriteria cri = new SearchCriteria();
		
		String page = request.getParameter("page");
		String perPageNum = request.getParameter("perPageNum");
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		
		cri.setPage(page);
		cri.setPerPageNum(perPageNum);
		cri.setSearchType(searchType);
		cri.setKeyword(keyword);
		
		// 처리
		Map<String, Object> dataMap = boardService.getBoardList(cri);
		
		request.setAttribute("boardList", dataMap.get("boardList"));
		request.setAttribute("pageMaker", dataMap.get("pageMaker"));
		
		return url;
	}

}
