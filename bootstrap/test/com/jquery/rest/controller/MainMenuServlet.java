package com.jquery.rest.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jquery.dto.MenuVO;
import com.jquery.service.MenuService;
import com.jquery.service.MenuServiceImpl;
import com.jquery.utils.JsonResolver;

@WebServlet("/common/mainMenu")
public class MainMenuServlet extends HttpServlet {
    
	private MenuService menuService = new MenuServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mCode = request.getParameter("mCode");
		
		if(mCode==null) mCode="M000000";
		
		try {
			List<MenuVO> menuList = menuService.getMainMenuList();
			MenuVO menu = menuService.getMenuByMcode(mCode);
			
			Map<String, Object> jsonDataMap = new HashMap<>();
			jsonDataMap.put("menuList", menuList);
			jsonDataMap.put("menu", menu);
			
			JsonResolver.view(response, jsonDataMap);
		} catch(Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
