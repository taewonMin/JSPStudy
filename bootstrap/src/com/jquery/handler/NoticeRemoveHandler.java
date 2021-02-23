package com.jquery.handler;

import java.io.File;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jquery.dto.NoticeAttachVO;
import com.jquery.dto.NoticeVO;
import com.jquery.service.NoticeService;
import com.jquery.service.NoticeServiceImpl;

public class NoticeRemoveHandler implements CommandHandler {
	
	private NoticeService noticeService;
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int nno = Integer.parseInt(request.getParameter("nno"));

		try {
			
			NoticeVO notice = noticeService.getNotice(nno);
			List<NoticeAttachVO> attachList = notice.getNoticeAttachList();
			
			if(attachList != null) {
				for(NoticeAttachVO attach : attachList) {
					File deleteTarget = new File(attach.getUploadpath()+File.separator+attach.getUuid()+"$$"+attach.getFilename());
					
					if(deleteTarget.exists()) {
						deleteTarget.delete();
					}
				}
			}
			
			noticeService.remove(nno);

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('삭제되었습니다');");
			out.println("window.opener.location.reload(true);window.close();");
			out.println("</script>");
			out.close();

		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return null;
	}

}
