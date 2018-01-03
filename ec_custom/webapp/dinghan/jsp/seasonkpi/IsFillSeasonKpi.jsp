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
	 * 功能：用于判断当前用户当前年份和季度的季度绩效考核流程是否已经填写过了
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
		
		out.print(seasonKPIService.isFilled(appUser));
		out.flush();
		out.close();
	
	} catch(Exception e){
		e.printStackTrace();
	}
%>