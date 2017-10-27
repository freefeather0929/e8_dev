<%@page import="weaver.dh.interfaces.AutoCountSYNianXiuandTiaoXiu"%>
<%@page import="dinghan.workflow.service.QingJiaService"%>
<%@ page import="dinghan.workflow.beans.Jbtemp"%>
<%@ page import="dinghan.workflow.beans.JiaBan1"%>
<%@ page import="weaver.dh.interfaces.AutoTask"%>
<%@ page import="org.json.JSONObject"%>
<%@ page import="weaver.dh.interfaces.toU9"%>
<%@ page import="weaver.email.EmailWorkRunnable"%>
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
	
	//Integer[] wf_id_arry = {916,1586,1588,1592,1599,1652,1701,1728,1743,1858,1896,2016,2021};
	
		//QingJiaService qjService = new QingJiaService();
		
		//qjService.countLastTiaoXiuorNianXiuHour(63, QingJiaService.TiaoXiu, "", 7);
		
		AutoCountSYNianXiuandTiaoXiu autoCountSY = new AutoCountSYNianXiuandTiaoXiu();
		
		autoCountSY.execute();
	
	
	out.print(1);
	out.close();
%>