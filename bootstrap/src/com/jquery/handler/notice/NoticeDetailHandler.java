package com.jquery.handler.notice;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jquery.dto.NoticeAttachVO;
import com.jquery.dto.NoticeVO;
import com.jquery.handler.CommandHandler;
import com.jquery.service.NoticeService;
import com.jquery.service.NoticeServiceImpl;
import com.jquery.utils.JsonResolver;
import com.jquery.utils.MakeFileName;

public class NoticeDetailHandler implements CommandHandler {

	private NoticeService noticeService;
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equals("GET")) {
			
			String url = req.getContextPath() + "/notice/detail.jsp";
			
			return url;
		}else if(req.getMethod().equals("POST")) {
			
			//1. parameter 수용
			int nno = Integer.parseInt(req.getParameter("nno"));
			String from = req.getParameter("from");
			
			try {
				//2. 처리(noticeService);
				NoticeVO notice = null;
				if(from!=null && from.equals("modify")) {
					notice = noticeService.getNoticeForModify(nno);
				}else {
					notice = noticeService.getNotice(nno);
				}
				
				List<NoticeAttachVO> renamedNoticeAttachList = MakeFileName.parseFileNameFromNoticeAttaches(notice.getNoticeAttachList(), "\\$\\$");
				notice.setNoticeAttachList(renamedNoticeAttachList);
				
				JsonResolver.view(res, notice);
				
			}catch(SQLException e) {
				res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
				throw e;
			}
		}
		return null;
	}

}
