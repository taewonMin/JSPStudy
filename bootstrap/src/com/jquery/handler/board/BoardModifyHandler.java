package com.jquery.handler.board;

import java.io.File;
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

public class BoardModifyHandler implements CommandHandler {

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
		
		String url= null;
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		MultipartHttpServletRequestParser multi = null;
		try {
			multi = new MultipartHttpServletRequestParser(request, MEMORY_THRESHOLD, MAX_FILE_SIZE, MAX_REQUEST_SIZE);
			
			// 파일 삭제
			String[] deleteFilesAno = multi.getParameterValues("deleteFile");
			
			if(deleteFilesAno != null) for(String deletefileAno : deleteFilesAno) {
				
				int ano = Integer.parseInt(deletefileAno);
				
				AttachVO attach = boardService.getAttachVO(ano);
				
				boardService.removeAttachAno(ano);	// DB 삭제
				
				String filePath = attach.getUploadPath() + File.separator + attach.getFileName();					
				File targetFile = new File(filePath);
				
				if(targetFile.exists()) {	
					targetFile.delete();	// 파일 삭제
				}
				
			}
			
			// 파일 저장 경로 설정
			String uploadPath = GetUploadPath.getUploadPath("attach.upload");
			
			// 파일 저장
			List<AttachVO> attachList = FileUploadResolver.fileUpload(multi.getFileItems("uploadFile"), uploadPath);
			
			int bno = Integer.parseInt(multi.getParameter("bno"));
			String writer = multi.getParameter("writer");
			String title = multi.getParameter("title");
			String content = multi.getParameter("content");
			
			BoardVO board = new BoardVO();
			board.setBno(bno);
			board.setWriter(writer);
			board.setContent(content);
			board.setTitle(title);
			board.setAttachList(attachList);
			
			boardService.modify(board);
			
			// 결과
			out.println("<script>");
			out.println("window.location.href='"+request.getContextPath()+"/board/detail.do?from=modify&bno="+bno+"';");
			out.println("</script>");
		}catch(Exception e) {
			e.printStackTrace();
			out.println("<script>");
			out.println("alert('글수정이 실패했습니다.');");
			out.println("</script>");
		}
		
		return url;
	}

}
