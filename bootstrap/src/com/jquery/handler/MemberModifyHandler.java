package com.jquery.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jquery.service.MemberService;
import com.jquery.service.MemberServiceImpl;

public class MemberModifyHandler implements CommandHandler {

	private MemberService memberService = new MemberServiceImpl();
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@Override
	public boolean isRedirect(HttpServletRequest requset) {
		return false;
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return null;
	}

}
