<%@page import="dinghan.workflow.service.AsyncDepartmentInfoService"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.lang.reflect.*"%>
<%@ page import="java.util.*"%>
<%@ page import="weaver.conn.RecordSetDataSource"%>
<%@page import="net.sf.json.*"%>
<%@ page import="javax.servlet.*"%>
<jsp:useBean id="recordSet" class="weaver.conn.RecordSet" scope="page" />
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>
<%@page import="weaver.formmode.setup.ModeRightInfo"%>

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	
	/* 用于同步人员信息
		可以按人员、部门、分部（一级分部）进行同步
	 */
	String subcompanyID = Util.null2String(request.getParameter("subcomid"));
	String deptID = Util.null2String(request.getParameter("deptid"));
	String userIDStr = Util.null2String(request.getParameter("userids"));
	String sql = "";
	List<Integer> userIDList = new ArrayList<Integer>();

	if(userIDStr != ""){
		String[] userIDs = userIDStr.split(",");
		for(String userID : userIDs){
			userIDList.add(Integer.parseInt(userID));
		}
	}else if(deptID != ""){
		sql = "select id from HrmResource where departmentid = " + deptID;
		
		recordSet.executeSql(sql);
		
		while(recordSet.next()){
			userIDList.add(recordSet.getInt("id"));
		}
	}else if(subcompanyID != ""){
		sql = "select id from HrmResource where subcompanyid1 = " + subcompanyID;
		
		recordSet.executeSql(sql);
		
		while(recordSet.next()){
			userIDList.add(recordSet.getInt("id"));
		}
	}

	

	if(userIDList.size() > 0){
		AsyncDepartmentInfoService asyncDIS = new AsyncDepartmentInfoService();
		for(int i =0; i<userIDList.size(); i++){
			out.println(userIDList.get(i));
			asyncDIS.asyncDepartmentInfo(userIDList.get(i));
		}
	}

%>