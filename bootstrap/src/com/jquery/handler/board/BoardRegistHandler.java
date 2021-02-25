package com.jquery.handler.board;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jquery.dto.AttachVO;
import com.jquery.dto.BoardVO;
import com.jquery.exception.NotMultipartFormDataException;
import com.jquery.handler.CommandHandler;
import com.jquery.service.BoardService;
import com.jquery.utils.FileUploadResolver;
import com.jquery.utils.GetUploadPath;
import com.jquery.utils.MultipartHttpServletRequestParser;

public class BoardRegistHandler implements CommandHandler {

	private BoardService boardService;
	public void setBoardService(BoardService boardService) {
		this.boardService = boardService;
	}
	
	// 업로드 파일 환경 설정
	private static final int MEMORY_THRESHOLD = 1024*1024*3;	// 3MB
	private static final int MAX_FILE_SIZE = 1024*1024*40;	// 40MB
	private static final int MAX_REQUEST_SIZE = 1024*1024*200;	// 200MB
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "board/regist_success";
		
		MultipartHttpServletRequestParser multi = null;
		List<AttachVO> attachList = null;
		
		try {
			
			multi = new MultipartHttpServletRequestParser(request, MEMORY_THRESHOLD, MAX_FILE_SIZE, MAX_REQUEST_SIZE);
			
			// 파일 저장 경로 설정
			String uploadPath = GetUploadPath.getUploadPath("attach.upload");
			
			// 파일 저장
			attachList = FileUploadResolver.fileUpload(multi.getFileItems("uploadFile"), uploadPath);
			
		} catch(NotMultipartFormDataException e) {
			e.printStackTrace();
			url="board/regist_fail";
		} catch(Exception e) {
			e.printStackTrace();
			url="board/regist_fail";
		}
		
		// 게시글 저장
		BoardVO board = new BoardVO();
		board.setRegDate(new Date());
		board.setAttachList(attachList);
		board.setTitle(multi.getParameter("title"));
		board.setContent(multi.getParameter("content"));
		board.setWriter(multi.getParameter("writer"));
		
		try {
			boardService.regist(board);
			
		}catch(SQLException e) {
			e.printStackTrace();
			url="board/regist_fail";
		}
		
		return url;
		
	}
}
