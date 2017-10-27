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
	 * 功能：用于获取当前流程申请人的考核关系
	 * 编写人：张肖宇
	 * 编写时间：2016-12-14
	 */
	try{
		
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		int appPsnID = Integer.valueOf(request.getParameter("apppsnid"));
		
		User appUser = new User(appPsnID);
		
		SeasonKPIService seasonKPIService = new SeasonKPIService();
		
		out.print(seasonKPIService.getExamRelationShip(appUser));
		out.flush();
		out.close();
	
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>