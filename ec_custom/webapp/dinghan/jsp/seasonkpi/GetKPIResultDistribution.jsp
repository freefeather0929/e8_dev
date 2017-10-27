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
	 * 功能：用于获取某年某季度某个部门的kpi考核结果分布
	 * 编写人：张肖宇
	 * 编写时间：2017-06-19
	 */
	try{
		
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		
		int year = Integer.valueOf(request.getParameter("year"));
		String season = request.getParameter("season");
		int deptId = Integer.valueOf(request.getParameter("deptid"));
		
		SeasonKPIService seasonKPIService = new SeasonKPIService();
		
		out.print(seasonKPIService.accountLevelDistribution(deptId,year,season));
		out.flush();
		out.close();
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>