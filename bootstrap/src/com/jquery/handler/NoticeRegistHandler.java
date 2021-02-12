package com.jquery.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jquery.dto.AttachVO;
import com.jquery.dto.NoticeAttachVO;
import com.jquery.dto.NoticeVO;
import com.jquery.service.NoticeService;
import com.jquery.service.NoticeServiceImpl;
import com.jquery.utils.GetUploadPath;
import com.jquery.utils.MakeFileName;
import com.jquery.utils.ServletFileUploadBuilder;

public class NoticeRegistHandler implements CommandHandler {

	private NoticeService noticeService = NoticeServiceImpl.getInstance();
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	@Override
	public boolean isRedirect(HttpServletRequest req) {
		if(req.getMethod().equals("GET")) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equals("GET")) {
			
			String url =request.getContextPath()+"/notice/regist.jsp";
			return url;
		}else if(request.getMethod().equals("POST")) {
			try {
				NoticeVO notice = fileUpload(request);
				noticeService.regist(notice);
			} catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}
		}
		
		return null;
	}

	// 업로드 파일 환경 설정
	private static final int MEMORY_THRESHOLD = 1024*1024*3;	// 3MB
	private static final int MAX_FILE_SIZE = 1024*1024*40;	// 40MB
	private static final int MAX_REQUEST_SIZE = 1024*1024*200;	// 200MB
	
	private NoticeVO fileUpload(HttpServletRequest request) throws Exception {
		NoticeVO notice = new NoticeVO();
		List<NoticeAttachVO> noticeAttachList = new ArrayList<NoticeAttachVO>();
		
		ServletFileUpload upload = ServletFileUploadBuilder.build(request, MEMORY_THRESHOLD, MAX_FILE_SIZE, MAX_REQUEST_SIZE);
		
		// 실제 저장 경로를 설정
		String uploadPath = GetUploadPath.getUploadPath("noticeAttach.upload");
		
		File file = new File(uploadPath);
		if(!file.mkdirs()) {
			System.out.println(uploadPath + "가 이미 존재하거나 생성을 실패했습니다.");
		}
		
		try {
			List<FileItem> formItems = upload.parseRequest(request);
			
			String writer = null;	// request.getParameter("writer")
			String title = null;	// request.getParameter("title")
			String content = null;	// request.getParameter("content")
			int nno = -1;	// Integer.parseInt(request.getParameter("bno"))
			
			for(FileItem item : formItems) {
				// 1.1 필드
				if(item.isFormField()) {
					// 파라미터 구분(파라미터 이름)
					switch(item.getFieldName()) {
					case "title":
						title = item.getString("utf-8");
						break;
					case "writer":
						writer = item.getString("utf-8");
						break;
					case "content":
						content = item.getString("utf-8");
						break;
					}
					
				}else {	// 1.2 파일
					// summernote의 files를 제외함
					if(!item.getFieldName().equals("uploadFile")) 
						continue;
					
					String fileName = new File(item.getName()).getName();
					
					String UUID = MakeFileName.toUUIDFileName(fileName, "$$");
					String filePath = uploadPath + File.separator + UUID;
					File storeFile = new File(filePath);
					
					// local HDD에 저장
					try {
						item.write(storeFile);
					}catch(Exception e) {
						e.printStackTrace();
						throw e;
					}
					
					// DB에 저장할 attach에 file 내용 추가
					NoticeAttachVO noticeAttach = new NoticeAttachVO();
					noticeAttach.setFilename(fileName);
					noticeAttach.setUploadpath(uploadPath);
					noticeAttach.setFiletype(fileName.substring(fileName.lastIndexOf(".") + 1));
					noticeAttach.setUuid(UUID.split("\\$\\$")[0]);
					
					noticeAttachList.add(noticeAttach);
					
					System.out.println("upload file : " + noticeAttach);
				}
			}
			
			notice.setNoticeAttachList(noticeAttachList);
			notice.setWriter(writer);
			notice.setContent(content);
			notice.setTitle(title);
			
		} catch(FileUploadException e) {
			e.printStackTrace();
			throw e;
		}
		
		return notice;
	}
}
