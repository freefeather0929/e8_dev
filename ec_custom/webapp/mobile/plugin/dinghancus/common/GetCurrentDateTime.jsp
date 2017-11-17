<%@page import="dinghan.common.util.CalendarUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="weaver.conn.RecordSetDataSource"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	
		out.print("{'curdate':'"+CalendarUtil.getCurDate()+"'}");
		out.flush();
		out.close();
	
		out.flush();
		out.close();
	
%>