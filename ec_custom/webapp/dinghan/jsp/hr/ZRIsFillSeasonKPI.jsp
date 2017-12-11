<%@page import="dinghan.zrac.hr.entity.kpientity.ZRSeasonKPI"%>
<%@page import="dinghan.zrac.hr.service.kpiservice.impl.ZRKPIServiceImpl"%>
<%@page import="dinghan.zrac.hr.service.kpiservice.ZRKPIService"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：获取重复季度绩效流程
	 * 编写人：张肖宇
	 * 编写时间：2017-12-11
	 * 
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
		
		ZRKPIService zrKPIService = new  ZRKPIServiceImpl();
		ZRSeasonKPI zrSeasonKPI = zrKPIService.queryByUserIdAndSeason(Integer.parseInt(userId), Integer.parseInt(year), season);
		
		if(zrSeasonKPI != null){
			
		}else{
			
		}
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>