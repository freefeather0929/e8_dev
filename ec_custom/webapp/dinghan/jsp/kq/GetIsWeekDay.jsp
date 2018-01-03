<%@page import="dinghan.workflow.com.selectitem.SelectItemInfoImpl"%>
<%@page import="dinghan.workflow.kq.holiday.dao.impl.HolidaySelectDaoImpl"%>
<%@page import="dinghan.workflow.kq.holiday.dao.HolidaySelectDao"%>
<%@page import="dinghan.workflow.kq.holiday.entity.HolidayConfig"%>
<%@page import="dinghan.common.util.CalendarUtil"%>
<%@page import="dinghan.workflow.kq.holiday.dao.impl.HolidayConfigDaoImpl"%>
<%@page import="dinghan.workflow.kq.holiday.dao.HolidayConfigDao"%>
<%@page import="weaver.conn.RecordSet"%>
<%@page import="dinghan.workflow.kq.userinfo.entity.UserInfo"%>
<%@page import="dinghan.workflow.kq.userinfo.UserInfoDaoImpl"%>
<%@page import="dinghan.workflow.kq.userinfo.UserInfoDao"%>
<%@page import="dinghan.workflow.kq.util.SignRelationUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：根据用户ID和日期判定该日期是否为工作日
	 * 编写人：张肖宇
	 * 编写时间：2018-01-03
	 */
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	HolidaySelectDao hdaySelectDao = new HolidaySelectDaoImpl(new SelectItemInfoImpl());
	String apppsnid = Util.null2String(request.getParameter("apppsnid"));
	String appDate = Util.null2String(request.getParameter("appdate"));
	StringBuffer json = new StringBuffer();
	String workcode = null;
	
	//out.print(workcode);
	//UserInfoDao userDao = new UserInfoDaoImpl();
	int isWeekDay = 1;
	//String weekDayName = "";
	String weekDay = "";
	if(apppsnid!="" && appDate!=""){
		HolidayConfigDao holidayDao = new HolidayConfigDaoImpl();
		HolidayConfig holidayCfg = holidayDao.query(appDate, Integer.parseInt(apppsnid));
		weekDay = CalendarUtil.judgeWeekDay(appDate);
		
		if("星期六".equals(weekDay) || "星期日".equals(weekDay)){
			isWeekDay = 0;
		}
		//UserInfo userInfo = userDao.queryByCode(workcode);
		if(holidayCfg!=null){
			weekDay = hdaySelectDao.queryHolidaySelectItemInfo(holidayCfg.getJrmc()).getSelectName();
			if(holidayCfg.getJrmc() != 8){
				isWeekDay = 1;
			}
		}
	}
	json.append("{'isweekday':'"+isWeekDay+"','weekdayname':'"+weekDay+"'}");
	out.print(json.toString());
	out.flush();
	out.close();
		
%>