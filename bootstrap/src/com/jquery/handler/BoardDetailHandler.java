package com.jquery.handler;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jquery.dto.AttachVO;
import com.jquery.dto.BoardVO;
import com.jquery.service.BoardService;
import com.jquery.service.BoardServiceImpl;
import com.jquery.utils.JsonResolver;
import com.jquery.utils.MakeFileName;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

public class BoardDetailHandler implements CommandHandler {

	private BoardService boardService = BoardServiceImpl.getInstance();
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@Override
	public boolean isRedirect(HttpServletRequest req) {
		return false;
	}

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//1. parameter 수용
		int bno = Integer.parseInt(req.getParameter("bno"));
		String from = req.getParameter("from");
		
		try {
			//2. 처리(boardService);
			BoardVO board = null;
			if(from!=null && from.equals("modify")) {
				board = boardService.getBoardForModify(bno);
			}else {
				board = boardService.getBoard(bno);
			}
			
			List<AttachVO> renamedAttachList = MakeFileName.parseFileNameFromAttaches(board.getAttachList(), "\\$\\$");
			board.setAttachList(renamedAttachList);
			
			//3. 결과(JsonResolver : json)
			JsonResolver.view(res, board);
			
		}catch(SQLException e) {
			res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
			throw e;
		}
		
		return null;
	}

}
