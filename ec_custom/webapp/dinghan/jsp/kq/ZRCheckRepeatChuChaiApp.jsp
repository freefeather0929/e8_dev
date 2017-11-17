<%@page import="dinghan.workflow.kq.appdata.entity.ZRChuChaiAppData"%>
<%@page import="dinghan.workflow.kq.appdata.util.ZRChuChaiCheckRepeatUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：检测重复的中车轨道-出差申请
	 * 编写人：张肖宇
	 * 编写时间：2017-10-28
	 * 
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
		
		ZRChuChaiCheckRepeatUtil util = new ZRChuChaiCheckRepeatUtil();
		
		//ZRWaiChuCheckRepeatUtil util = new ZRWaiChuCheckRepeatUtil();
		try{
			ZRChuChaiAppData zrChuChaiAppData = util.executeCheck(userid, startDate, endDate, starttime, endtime, appno);
			if(zrChuChaiAppData!=null){
				out.print("{'repeat':1,'appno':'"+zrChuChaiAppData.getAppNo()+"'}");
			}else{
				out.print("{'repeat':0}");
			}
		}catch(Exception e){
			out.print(e.getStackTrace());
			e.printStackTrace();
		}
		out.flush();
		out.close();
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>