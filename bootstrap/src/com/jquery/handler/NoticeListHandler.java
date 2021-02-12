package com.jquery.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jquery.command.SearchCriteria;
import com.jquery.service.NoticeService;
import com.jquery.service.NoticeServiceImpl;
import com.jquery.utils.JsonResolver;

public class NoticeListHandler implements CommandHandler {

	private NoticeService noticeService = NoticeServiceImpl.getInstance();
	
	@Override
	public boolean isRedirect(HttpServletRequest requset) {
		return false;
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equals("GET")) {
			
			String url = "/notice/list.jsp";

			return url;
		}else if(request.getMethod().equals("POST")) {
			
			ObjectMapper mapper = new ObjectMapper();
			
			SearchCriteria cri = mapper.readValue(request.getReader(), SearchCriteria.class);
			
			try {
				Map<String, Object> dataMap = noticeService.getNoticeList(cri);
				
				JsonResolver.view(response, dataMap);
				
			}catch(Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}
		}
		return null;
	}

}
