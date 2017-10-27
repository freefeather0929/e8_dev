<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings,weaver.file.Prop,weaver.rtx.RTXConfig"%>
<%@ page import="java.lang.reflect.*"%>
<%@ page import="weaver.file.FileUpload"%>
<%@ page import="java.util.*"%>
<%@ page import="weaver.conn.RecordSetDataSource"%>
<%@page import="net.sf.json.*"%>
<%@ page import="javax.servlet.*"%>
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
	
	int userId = user.getUID();
	
	String roleId = request.getParameter("roleid");
	String sql = "select r.rolesmark,m.roleid from HrmRoles r,HrmRoleMembers m where r.id = m.roleid and m.resourceid ="+userId;
	recordSet.executeSql(sql);

	StringBuffer json = new StringBuffer();
	json.append("{");
	json.append("\"userroles\":[");
	int num = 0;
	while(recordSet.next()){
		if(num>0) { json.append(","); } 
		json.append("{");
		json.append("'roleid':'"+recordSet.getInt("roleid")+"',");
		json.append("'rolemark':'"+recordSet.getString("rolesmark")+"'");
		json.append("}");
		num++;
	}
	json.append("]}");
	out.println(json.toString());
	out.close();
%>