package com.jquery.listener;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jquery.handler.ApplicationContext;

//@WebListener
public class InitListener implements ServletContextListener {

	
	public void contextInitialized(ServletContextEvent ctxEvent)  { 
		ServletContext ctx = ctxEvent.getServletContext();

		String beanConfigXml = ctx.getInitParameter("contextConfigLocation");
		beanConfigXml = ctx.getRealPath("/")
				+ beanConfigXml.replace("classpath:", "/WEB-INF/classes/").replace("/", File.separator);

		if (beanConfigXml == null)
			return;

		// XML문서를 Document객체로 변환
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = factory.newDocumentBuilder();
			Document document = documentBuilder.parse(beanConfigXml);
			Element root = document.getDocumentElement();
			NodeList beans = root.getElementsByTagName("bean"); // <beans> <bean id="identify" class="class Type"
																// ></bean> </beans>
			
			Map<String, Object> applicationContext = ApplicationContext.getApplicationContext(); // application context
																									// map

			for (int i = 0; i < beans.getLength(); i++) {

//				System.out.println(beans.item(i).getNodeName());

				Node bean = beans.item(i);
				if (bean.getNodeType() == Node.ELEMENT_NODE) {
					Element ele = (Element) bean;
					String id = ele.getAttribute("id");
					String classType = ele.getAttribute("class");
					
					// map instance put
					Class<?> cls = Class.forName(classType);

					// 클래스 생성자 접근(싱글톤 파괴됨)
					Constructor constructor = cls.getDeclaredConstructors()[0];
					constructor.setAccessible(true);
					Object targetObj = constructor.newInstance();
					constructor.setAccessible(false);
					
//					Object targetObj = cls.newInstance();.
					
					applicationContext.put(id, targetObj);
					
					System.out.println("id : " + id + ", class : " + classType);
				}
			}

			for (int i = 0; i < beans.getLength(); i++) {
				Node bean = beans.item(i);
				
				if (bean.getNodeType() == Node.ELEMENT_NODE) {
					Element eleBean = (Element)bean;
					
					NodeList properties = bean.getChildNodes();

					for (int j = 0; j < properties.getLength(); j++) {
						Node property = properties.item(j);
						if (property.getNodeType() == Node.ELEMENT_NODE) {
							Element ele = (Element) property;
							String name = ele.getAttribute("name");
							String ref = ele.getAttribute("ref-value");

							String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
							Method[] methods;

							methods = Class.forName(eleBean.getAttribute("class")).getMethods();
							
							for (Method method : methods) {
								// 의존성 여부 확인
								if (method.getName().equals(setMethodName)) {
									System.out.println(setMethodName+" : "+method.getName());
									
									// invoke(메서드를 호출할 객체, 전달할 파라미터 값)
									method.invoke(applicationContext.get(eleBean.getAttribute("id")),applicationContext.get(ref));
									
									System.out.println("[invoke]"+applicationContext.get(eleBean.getAttribute("id"))+":"+applicationContext.get(ref));
									
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
   
    public void contextDestroyed(ServletContextEvent ctxEvent)  { 
    }

	
}
