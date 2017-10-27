<%@page import="dinghan.workflow.kq.util.DepartmentInfoUtil"%>
<%@page import="dinghan.workflow.service.seasonkpiservice.SeasonKPIService"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：根据用户ID获取其部门结构信息
	 * 编写人：张肖宇
	 * 编写时间：2017-06-19
	 */
	try{
		
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		
		int userid = Integer.valueOf(request.getParameter("userid"));
		DepartmentInfoUtil util = new DepartmentInfoUtil();

		out.print(util.getDepartmentContruction(userid));
		out.flush();
		out.close();
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>