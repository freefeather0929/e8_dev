<%@page import="dinghan.workflow.kq.kqdt.check.util.ZRWaiChuKQDTCheckUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：核定明细表 - 中车轨道-外出公干申请
	 * 编写人：张肖宇
	 * 编写时间：2017-10-24
	 * 
	 */
	try{
		
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		
		ZRWaiChuKQDTCheckUtil waichuCheckUtil = new ZRWaiChuKQDTCheckUtil();
		
		//int userid = Integer.valueOf(request.getParameter("userid"));
		
		waichuCheckUtil.executeCheckAll(28);
		
		out.print(1);
		out.flush();
		out.close();
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>