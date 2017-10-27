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
	String deptId = Util.null2String(request.getParameter("deptid"));
	
	if(deptId != ""){
		
		String sql = "select top 1 id,departmentmark,departmentname,subcompanyid1,supdepid,tlevel from HrmDepartment where id ="+ deptId;
		
		recordSet.executeSql(sql);
		
		StringBuffer json = new StringBuffer();
		
		if(recordSet.first()){
			json.append("{'flag':'1',");
			json.append("'id':'"+recordSet.getInt("id")+"',");
			json.append("'departmentmark':'"+recordSet.getString("departmentmark")+"',");
			json.append("'departmentname':'"+recordSet.getString("departmentname")+"',");
			json.append("'subcompanyid1':'"+recordSet.getInt("subcompanyid1")+"',");
			json.append("'supdepid':'"+recordSet.getInt("supdepid")+"',");
			json.append("'tlevel':'"+recordSet.getInt("tlevel")+"'");
			json.append("}");
			out.print(json.toString());
			out.flush();
			out.close();
		}
	}else{
		out.close();
		return;
	}

%>