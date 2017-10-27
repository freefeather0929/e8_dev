<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="weaver.conn.WeaverConnection"%>
<%@page import="weaver.conn.RecordSet"%>
<%@ page import="weaver.general.Util"%>
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
	int usrId = user.getUID();
	String appno = request.getParameter("appno");
	String formId = Util.null2String(request.getParameter("formid"));
	
	int num = 0;
 
	String sql = "select count(id) as num from formtable_main_"+ Math.abs(Integer.parseInt(formId)) +" where apppsn = "+usrId ;
	if(appno.equals("")){
		sql += " and appno <>''";
	}else{
		sql += " and appno not like '%"+appno+"%' and appno <> ''";
	}
	sql += " and verified <> '1'";
	
	recordSet.executeSql(sql);
	
	while(recordSet.next()){
		num = recordSet.getInt("num");
	}
	
	StringBuffer json = new StringBuffer();
	json.append("{'num':'"+num+"'}");
	out.print(json.toString());
	out.flush();
	out.close();
%>