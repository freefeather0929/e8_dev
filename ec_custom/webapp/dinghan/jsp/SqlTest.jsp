<%@page import="dinghan.zrac.ga.LoanApplication"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings,weaver.file.Prop,weaver.rtx.RTXConfig" %>
<%@ page import="java.util.*" %>
<%@ page import="weaver.conn.RecordSetDataSource" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	
	LoanApplication l = new LoanApplication();
	
	l.execute();
	
	//out.close();
%>
<!DOCTYPE html>
<html>
	<body>
		
	</body>
</html>