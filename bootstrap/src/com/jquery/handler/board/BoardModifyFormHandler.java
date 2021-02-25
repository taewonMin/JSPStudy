package com.jquery.handler.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jquery.dto.AttachVO;
import com.jquery.dto.BoardVO;
import com.jquery.handler.CommandHandler;
import com.jquery.service.BoardService;
import com.jquery.utils.MakeFileName;

public class BoardModifyFormHandler implements CommandHandler {

	private BoardService boardService;
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url="board/modify";
		
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		BoardVO board = boardService.getBoard(bno);
		List<AttachVO> renamedAttachList = MakeFileName.parseFileNameFromAttaches(board.getAttachList(), "\\$\\$");
		board.setAttachList(renamedAttachList);
		
		request.setAttribute("board", board);
		
		return url;
	}

}
