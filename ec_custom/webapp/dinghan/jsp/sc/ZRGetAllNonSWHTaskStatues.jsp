<%@page import="dinghan.zrac.sc.util.nonswhutil.NonSWHTaskAppUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：获取所有非标工时通知单子流程-任务执行流程的状态
	 * 编写人：张肖宇
	 * 编写时间：2017-11-12
	 * 
	 */
	try{
		
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		
		//int userid = Integer.valueOf(request.getParameter("userid"));
		 
		NonSWHTaskAppUtil util = new NonSWHTaskAppUtil();
		
		int mainwfid = Integer.valueOf(request.getParameter("mainwfid")) ;
		
		String json = util.queryAllTaskStatus(mainwfid);
		out.print(json);
		out.flush();
		out.close();
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>