<%@page import="dinghan.zrac.hr.util.kpiutil.ZRKPIUitl"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：获取KPI考核关系
	 * 编写人：张肖宇
	 * 编写时间：2016-12-14
	 */
	try{
		
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		
		String userId = request.getParameter("userid");
		String year = request.getParameter("year");
		String season = request.getParameter("season");
		
		ZRKPIUitl util = new ZRKPIUitl();
		
		out.print(util.getKPIRelationShip(Integer.parseInt(userId), Integer.parseInt(year), season));
		out.flush();
		out.close();
	
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>