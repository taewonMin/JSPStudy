package com.jquery.handler.board;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jquery.dto.AttachVO;
import com.jquery.dto.BoardVO;
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
		String url = null;
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		MultipartHttpServletRequestParser multi = null;
		try {
			
			multi = new MultipartHttpServletRequestParser(request, MEMORY_THRESHOLD, MAX_FILE_SIZE, MAX_REQUEST_SIZE);
			
			// 파일 저장 경로 설정
			String uploadPath = GetUploadPath.getUploadPath("attach.upload");
			
			// 파일 저장
			List<AttachVO> attachList = FileUploadResolver.fileUpload(multi.getFileItems("uploadFile"), uploadPath);
			
			// 게시글 저장
			String writer = multi.getParameter("writer");
			String title = multi.getParameter("title");
			String content = multi.getParameter("content");
			
			BoardVO board = new BoardVO();
			board.setAttachList(attachList);
			board.setWriter(writer);
			board.setContent(content);
			board.setTitle(title);
				
			boardService.regist(board);
			
			// 결과
			out.println("<script>");
			out.println("window.opener.location.href='"+request.getContextPath()+"/board/list.do';");
			out.println("window.close();");
			out.println("</script>");
		} catch(Exception e) {
			e.printStackTrace();
			out.println("<script>");
			out.println("alert('글등록이 실패했습니다.');");
			out.println("window.close();");
			out.println("</script>");
		}
		
		return url;
		
	}

}
