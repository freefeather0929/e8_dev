<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="weaver.conn.WeaverConnection"%>
<%@page import="weaver.conn.RecordSet"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings,weaver.file.Prop,weaver.rtx.RTXConfig"%>
<%@ page import="weaver.file.FileUpload"%>
<%@ page import="java.util.*"%>
<%@ page import="weaver.conn.RecordSetDataSource"%>
<%@page import="net.sf.json.*"%>
<%@ page import="javax.servlet.*"%>
<jsp:useBean id="recordSet" class="weaver.conn.RecordSet" scope="page" />
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	String deptId = Util.null2String(request.getParameter("deptid"));
	String userIdStr = Util.null2String(request.getParameter("userids"));
	String sql = "select tlevel from HrmDepartment where id = "+ deptId;
	recordSet.executeSql(sql);
	int deptL = 0;
	if(recordSet.first()){
	   deptL = recordSet.getInt("tlevel");
	}
	if(userIdStr != ""){
		String[] userIds = userIdStr.split(",");
		StringBuffer json = new StringBuffer();
		int deptid_query = 0;
		int num = 0;
		int userid = 0;
		json.append("{'resinfo':[");
		String deptQuerySql = "";
		if(deptL==3){
			deptQuerySql = "select field0,id from cus_fielddata where id in ("+userIdStr+") and scope = 'HrmCustomFieldByInfoType'";
		}else{
			deptQuerySql = "select field1,id from cus_fielddata where id in ("+userIdStr+") and scope = 'HrmCustomFieldByInfoType'";
		}
		RecordSet rs = new RecordSet();
		rs.executeSql(deptQuerySql);
		while(rs.next()){
			deptid_query = rs.getInt(1);
			userid = rs.getInt(2);
			User iUser = new User(userid);
			/*
			out.print("sql:"+deptQuerySql);
			out.println("deptLevel : "+deptL);
			out.print(" -- deptId : "+ deptId);
			out.print(" -- deptId_query : "+deptid_query);
			out.println(" --  userName : "+iUser.getLastname());
			*/
			if(deptid_query != Util.getIntValue(deptId)){
			    if(num>0){json.append(",");}
			    json.append("{'lastname':'"+iUser.getLastname()+"'}");
			    num++;
			}
		}
		json.append("]}");
		out.print(json.toString());
		out.flush();
		out.close();
	}else{
		out.flush();
		out.close();
	}
%>