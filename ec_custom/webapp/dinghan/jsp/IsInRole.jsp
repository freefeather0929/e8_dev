<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings,weaver.file.Prop,weaver.rtx.RTXConfig" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="weaver.file.FileUpload" %>
<%@ page import="java.util.*" %>
<%@ page import="weaver.conn.RecordSetDataSource" %>
<%@page import="net.sf.json.*"%>
<%@ page import="javax.servlet.*" %>
<jsp:useBean id="recordSet" class="weaver.conn.RecordSet" scope="page" />
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>
<%@page import="weaver.formmode.setup.ModeRightInfo"%>
<jsp:useBean id="ModeShareManager" class="weaver.formmode.view.ModeShareManager" scope="page" />

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	
	String loginid = Util.getCookie(request , "loginidweaver");
	
	String roleId = request.getParameter("roleid");

	System.out.println("select * from HrmRoleMembers where roleid="+roleId+" and resourceid ="+loginid);
	recordSet.executeSql("select * from HrmRoleMembers where roleid="+roleId+" and resourceid ="+loginid);
	
	while(recordSet.next()){
		System.out.println(recordSet.getInt("resourceid"));
	}
	
	System.out.println(recordSet.getCounts());
	StringBuffer json = new StringBuffer();
	json.append("{");
	json.append("\"inrole\":");
	if(recordSet.getCounts() >0 ){
		json.append("\"1\"");
	}else{
		json.append("\"0\"");
	}
	json.append("}");

	out.println(json.toString());
	out.close();
	//out.close();
%>