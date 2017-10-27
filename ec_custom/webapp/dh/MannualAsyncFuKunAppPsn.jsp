<%@page import="weaver.conn.RecordSet"%>
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
	String sqlAsync = "";
	String sqlAppPsn = "select id,apppsn from formtable_main_126 where apppsn > 0";
	
	RecordSet rs = new RecordSet();
	RecordSet rs1 = new RecordSet();
	
	rs.executeSql(sqlAppPsn);
	
	while(rs.next()){
		sqlAsync = "update formtable_main_126 set apppsn_1 = " + rs.getInt("apppsn") + " where id = " + rs.getInt("id");
		rs1.executeSql(sqlAsync);
	}
	
	out.print(HttpServletResponse.SC_OK);
	out.close();
%>