package com.jquery.handler.board;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jquery.command.SearchCriteria;
import com.jquery.handler.CommandHandler;
import com.jquery.service.BoardService;

public class BoardListHandler implements CommandHandler {

	private BoardService boardService;
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String url = "board/list";
		
		// 입력
		String page = request.getParameter("page");
		String perPageNum = request.getParameter("perPageNum");
		String searchType = request.getParameter("searchType");
		String keyword = request.getParameter("keyword");
		
		SearchCriteria cri = new SearchCriteria(page,perPageNum,searchType,keyword);
		
		try {
			Map<String, Object> dataMap = boardService.getBoardList(cri);
			
			request.setAttribute("dataMap", dataMap);
		}catch(Exception e) {
			url = null;
			e.printStackTrace();
		}
		
		return url;
	}

}
