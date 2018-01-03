<%@page import="net.sf.json.JSONObject"%>
<%@page import="dinghan.zrac.hr.util.kpiutil.ZRKPIUitl"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：获取重复季度绩效流程requestId
	 * 编写人：张肖宇
	 * 编写时间：2017-12-11
	 * 
	 */
	 String json = null;
	try{
		
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		
		//String userId = request.getParameter("userid");
		String year = Util.null2String(request.getParameter("year"))==""?"-1":request.getParameter("year");
		String season = request.getParameter("season");
		String dept1Id = Util.null2String(request.getParameter("dept1id"));
		String dept2Id = Util.null2String(request.getParameter("dept2id"));
		String dept3Id = Util.null2String(request.getParameter("dept3id"));
		
		ZRKPIUitl util = new ZRKPIUitl();
		
		json = util.getDeptKPI(Integer.parseInt(year), season, Integer.parseInt(dept1Id), Integer.parseInt(dept2Id), Integer.parseInt(dept3Id));
		
		out.print(json);
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>