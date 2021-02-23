package com.jquery.handler.notice;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jquery.dto.NoticeAttachVO;
import com.jquery.dto.NoticeVO;
import com.jquery.handler.CommandHandler;
import com.jquery.service.NoticeService;
import com.jquery.service.NoticeServiceImpl;
import com.jquery.utils.GetUploadPath;
import com.jquery.utils.MakeFileName;
import com.jquery.utils.ServletFileUploadBuilder;

public class NoticeModifyHandler implements CommandHandler {

	private NoticeService noticeService;
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equals("GET")) {
			String url = request.getContextPath() + "/notice/modify.jsp";
			return url;
		}else if(request.getMethod().equals("POST")) {
			
			NoticeVO notice = null;
			try {
				notice = modifyFile(request, response);
				noticeService.modify(notice);
				
				response.getWriter().print(HttpServletResponse.SC_OK);
				
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}finally {
				response.getWriter().print(notice.getNno());
			}
		}
		
		
		return null;
	}

	
	// 업로드 파일 환경 설정
	private static final int MEMORY_THRESHOLD = 1024*1024*3;	// 3MB
	private static final int MAX_FILE_SIZE = 1024*1024*40;	// 40MB
	private static final int MAX_REQUEST_SIZE = 1024*1024*200;	// 200MB
	
	private NoticeVO modifyFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ServletFileUpload upload = ServletFileUploadBuilder.build(request, MEMORY_THRESHOLD, MAX_FILE_SIZE, MAX_REQUEST_SIZE);
		
		// 실제 저장 경로를 설정
		String uploadPath = GetUploadPath.getUploadPath("noticeAttach.upload");
		
		File file = new File(uploadPath);
		if(!file.mkdirs()) {
			System.out.println(uploadPath + "가 이미 존재하거나 생성을 실패했습니다.");
		}
	
		// request로부터 받는 파일을 추출해서 저장
		NoticeVO notice = new NoticeVO();
		List<NoticeAttachVO> attachList = new ArrayList<NoticeAttachVO>();
		
		List<FileItem> formItems = upload.parseRequest(request);
		if(formItems != null && formItems.size() > 0) {
			List<String> deleteFiles = new ArrayList<String>();
			for(FileItem item : formItems) {
				if(!item.isFormField()) {	// board: attachList
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
					
					attachList.add(noticeAttach);
					
					System.out.println("upload file : " + noticeAttach);
				} else {	// notice: nno, title, writer, content, deleteFile
					// 파라미터 구분(파라미터 이름)
					switch(item.getFieldName()) {
					case "title":
						notice.setTitle(item.getString("utf-8"));
						break;
					case "writer":
						notice.setWriter(item.getString("utf-8"));
						break;
					case "content":
						notice.setContent(item.getString("utf-8"));
						break;
					case "nno":
						notice.setNno(Integer.parseInt(item.getString("utf-8")));
						break;
					case "deleteFile":
						int nano = Integer.parseInt(item.getString("utf-8"));
						
						NoticeAttachVO noticeAttach = noticeService.getNoticeAttachVO(nano);
						
						noticeService.removeNoticeAttachNano(nano);	// DB 삭제
						
						String filePath = noticeAttach.getUploadpath() + File.separator + noticeAttach.getUuid()+"$$"+ noticeAttach.getFilename();					
						File targetFile = new File(filePath);
						
						if(targetFile.exists()) {	
							targetFile.delete();	// 파일 삭제
						}
						
						deleteFiles.add(item.getString("utf-8"));
						
						break;
					}
				}
			}
			
			notice.setNoticeAttachList(attachList);
			
			System.out.println("receive noard : \n" + notice);
			System.out.println("deleteFile : \n" + deleteFiles);
		}
		
		return notice;
	}

}
