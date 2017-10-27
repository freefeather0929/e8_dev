<%@page import="dinghan.workflow.beans.UserInfo"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="weaver.conn.WeaverConnection"%>
<%@page import="weaver.conn.RecordSet"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings"%>
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
	String accountId = Util.null2String(request.getParameter("accountid"));
	String userIdStr = Util.null2String(request.getParameter("userids"));
	
	String[] userList = userIdStr.split(",");
	
	UserInfo userInfo = null;
	
	StringBuffer json = new StringBuffer();
	
	json.append("");
	
	if(!"".equals(userIdStr)){
		json.append("{\"num\":\""+userList.length+"\",");
		json.append("\"info\":[");
		
		for(int i =0;i<userList.length;i++ ){
			if(i>0) 
				json.append(",");
			userInfo = new UserInfo(Integer.parseInt(userIdStr));
			json.append("{\"code"\":"\" +userInfo.getCode()+ "\",");
			json.append("\"name\":\""+userInfo.getNameCN()+"\",");
			json.append("\"id\":\""+userInfo.getName()+"\"}");
		}
		json.append("]}");
	}else{
		
		if(!"".equals(accountId)){
			String sql = "select Name,Code,NameCN from uf_hr_userinfo1 where Company = " +accountId;
			recordSet.executeSql(sql);
			json.append("{\"num\":\""+recordSet.getCounts()+"\",");
			json.append("\"info\":[");
			int i = 0;
			while(recordSet.next()){
				if (i>0)
					json.append(",");
				json.append("{\"code\":\""+"\""+recordSet.getString("Code")+"\",");
				json.append("\"name\":\""+"\""+recordSet.getString("NameCN")+"\",");
				json.append("\"id\":\""+"\""+recordSet.getString("Name")+"\"");
				json.append("}");
				i++;
			}
		}
	}
	
	out.print(json.toString());
	out.flush();
	out.close();

%>