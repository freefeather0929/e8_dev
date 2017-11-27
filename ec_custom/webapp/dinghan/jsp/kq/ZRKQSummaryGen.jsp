<%@page import="net.sf.json.JSONObject"%>
<%@page import="dinghan.workflow.kq.summary.handle.ZRKQSummaryDataHandler"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：考勤汇总生成
	 * 编写人：张肖宇
	 * 编写时间：2017-11-25
	 * 
	 */
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	int num = 0;
	JSONObject resJson = new JSONObject();
	ZRKQSummaryDataHandler handler = new ZRKQSummaryDataHandler(user.getUID());		
	String year = Util.null2String(request.getParameter("year"));
	String month = Util.null2String(request.getParameter("month"));
	//out.println(request.getParameter("userids"));
	//String[] userIds = request.getParameter("userids").split(",");
	
	if(!"".equals(year) && !"".equals(month)){
		if(!"".equals(Util.null2String(request.getParameter("accountid")))){
			int accountId = Integer.valueOf(request.getParameter("accountid"));
			num = handler.updateKQSummaryDataForAccount(accountId, year, month);
		}else{
			if(!"".equals(Util.null2String(request.getParameter("userids")))){
				
				String[] userIds = request.getParameter("userids").split(",");
				//out.println(userIds[0]);
				//out.println(userIds[1]);
				Integer[] userIDArray = new Integer[userIds.length];
				for(int i=0;i<userIds.length;i++){
					//out.println(userIds[i]);
					//out.println(userIds[1]);
					userIDArray[i] = new Integer(userIds[i]);
				}
				num = handler.updateKQSummaryDataForUserArray(userIDArray, year, month);
			}
		}
	}
	resJson.put("num", num);
	resJson.put("result",handler.getUvJAarray());
	out.println(resJson.toString());
	out.flush();
	out.close();
	
%>