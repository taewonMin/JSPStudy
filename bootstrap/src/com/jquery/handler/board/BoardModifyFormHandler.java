package com.jquery.handler.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jquery.dto.BoardVO;
import com.jquery.handler.CommandHandler;
import com.jquery.service.BoardService;

public class BoardModifyFormHandler implements CommandHandler {

	private BoardService boardService;
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url="board/modifyForm";
		
		BoardVO board = boardService.getBoard(Integer.parseInt(request.getParameter("bno")));
		
		request.setAttribute("board", board);
		
		return url;
	}

}
