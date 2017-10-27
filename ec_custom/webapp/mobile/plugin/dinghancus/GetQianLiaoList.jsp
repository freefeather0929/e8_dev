<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings,weaver.file.Prop,weaver.rtx.RTXConfig" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="weaver.file.FileUpload" %>
<%@ page import="java.util.*" %>
<%@ page import="weaver.conn.RecordSetDataSource" %>
<%@page import="net.sf.json.*"%>
<%@ page import="javax.servlet.*" %>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>
<%@page import="weaver.formmode.setup.ModeRightInfo"%>
<jsp:useBean id="ModeShareManager" class="weaver.formmode.view.ModeShareManager" scope="page" />
<jsp:useBean id="recordSet" class="weaver.conn.RecordSet" scope="page" />

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	
	String wfid = request.getParameter("wfid");
	String formid = request.getParameter("formid");
	if(wfid != ""){
		StringBuffer json = new StringBuffer();
		String sql = "select id,isown from formtable_main_"+ Math.abs(Integer.parseInt(formid, 10)) +"_dt1 where isown = '1' and ownsoluted <> '1' and mainid = "+wfid;
		recordSet.executeSql(sql);
		json.append("{'count':'"+recordSet.getCounts()+"','ids':[");
		int i = 0;
		while(recordSet.next()){
			if(i>0){ json.append(","); }
			json.append("'"+recordSet.getInt("id")+"'");
			i++;
		}
		json.append("]}");
		
		out.print(json.toString());
		out.flush();
		out.close();	
	}
	
	out.close();
%>