<%@page import="dinghan.workflow.kq.userinfo.offdays.AnnualDaysHaddle"%>
<%@page import="dinghan.common.util.CalendarUtil"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：调休假检测
	 * 编写人：张肖宇
	 * 编写时间：2017-11-23
	 * 
	 */
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	//int num = 0;
	JSONObject resJson = new JSONObject();
	AnnualDaysHaddle haddle = new AnnualDaysHaddle();
	String curDate = CalendarUtil.getCurDate().substring(0, 7);
	
	String userid = Util.null2String(request.getParameter("userid"),"-1") ;
	String appNo = Util.null2String(request.getParameter("appno"),"");
	String apphour = Util.null2String(request.getParameter("apphour"),"0");
	
	int userID = Integer.parseInt(userid);
	double appHour = Double.parseDouble(apphour);
	double used = haddle.queryUsedHourCurMonth(userID, curDate, appNo);
	//double added = haddle.queryAddHourbyOverTime(userID, "2017-11");
	double rest = haddle.queryCurrentLeftHour(userID, curDate, appNo);
	
	//resJson.put("added", added);
	resJson.put("used", used);
	resJson.put("rest", rest);
	resJson.put("apphour",appHour);
	resJson.put("reduced", (rest - appHour));
	
	out.println(resJson.toString());
	out.flush();
	out.close();
	
%>