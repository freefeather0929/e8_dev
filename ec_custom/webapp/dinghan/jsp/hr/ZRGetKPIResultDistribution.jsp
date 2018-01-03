<%@page import="dinghan.zrac.hr.util.kpiutil.ZRKPIUitl"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：用于获取中车轨道某年某季度某个部门的kpi考核结果分布
	 * 编写人：张肖宇
	 * 编写时间：2018-01-01
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
		
		ZRKPIUitl util = new ZRKPIUitl();
		
		out.print(util.accountLevelDistribution(deptId, year, season));
		out.flush();
		out.close();
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>