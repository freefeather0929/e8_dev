<%@page import="dinghan.workflow.kq.appdata.entity.ZRWaiChuAppData"%>
<%@page import="dinghan.workflow.kq.appdata.util.ZRWaiChuCheckRepeatUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：检测重复的中车轨道-外出公干申请
	 * 编写人：张肖宇
	 * 编写时间：2017-10-22
	 * 目前仅用于移动端
	 */
	try{
		
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		
		int userid = Integer.valueOf(request.getParameter("userid"));
		
		String startDate = request.getParameter("startdate");
		String endDate = request.getParameter("enddate");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		String appno = request.getParameter("appno");
		
		ZRWaiChuCheckRepeatUtil util = new ZRWaiChuCheckRepeatUtil();
		
		ZRWaiChuAppData zrWCAppData = util.executeCheck(userid, startDate, endDate, starttime, endtime, appno);
		
		if(zrWCAppData!=null){
			out.print("{'repeat':1,'appno':'"+zrWCAppData.getAppno()+"'}");
		}else{
			out.print("{'repeat':0}");
		}
		
		out.flush();
		out.close();
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>