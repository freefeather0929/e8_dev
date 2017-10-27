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
	
	int mainTblID = 3801;
	
	QingJia.delete(mainTblID, 0);
	
	List<QingJia> qingjia_3_List = QingJia.generateFromQingJia_1_List(mainTblID);
	
	for(QingJia q : qingjia_3_List){
		q.insert();
	}
	
	QingJia.checkList(mainTblID);
	
	out.print(1);
	out.close();
%>