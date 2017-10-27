<%@page import="weaver.workflow.webservices.WorkflowRequestInfo"%>
<%@page import="weaver.hrm.User"%>
<%@page import="weaver.workflow.request.RequestManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="weaver.conn.RecordSet"%>
<jsp:useBean id="res" class="weaver.conn.RecordSet" scope="page"></jsp:useBean>
<jsp:useBean id="user" class="weaver.hrm.User" scope="page"></jsp:useBean>
<jsp:useBean id="requstMgr" class="weaver.workflow.request.RequestManager" scope="page"></jsp:useBean>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	String reqId = request.getParameter("requestid");
	String noteId = request.getParameter("nodeid");
	String userid = request.getParameter("f_bel_userid");
    
    user = new User(Integer.parseInt(userid));
    
    String loginId = user.getLoginid();
    
    String json = "{'requestid':'"+reqId+"','nodeid':'"+noteId+"','uid':'"+userid+"','loginid':'"+loginId+"','test':'1'}";
	
	
		response.setContentType("application/json; charset=UTF-8");
		out.print(json);
		out.flush();
		out.close();
	
	
	
	
	
 %>
