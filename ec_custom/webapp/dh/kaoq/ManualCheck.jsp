<%@page import="dinghan.workflow.beans.QingJia"%>
<%@page import="dinghan.workflow.beans.JiaBan1"%>
<%@page import="weaver.dh.interfaces.AutoTask"%>
<%@page import="org.json.JSONObject"%>
<%@page import="weaver.dh.interfaces.toU9"%>
<%@page import="weaver.email.EmailWorkRunnable"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	
	AutoTask auto = new AutoTask();
	auto.execute();
	out.print(1);
	out.close();
%>