<%@page import="dinghan.workflow.com.selectitem.SelectItemInfoImpl"%>
<%@page import="dinghan.workflow.kq.holiday.dao.impl.HolidaySelectDaoImpl"%>
<%@page import="dinghan.workflow.kq.holiday.dao.HolidaySelectDao"%>
<%@page import="dinghan.workflow.kq.holiday.entity.HolidayConfig"%>
<%@page import="dinghan.workflow.kq.holiday.dao.impl.HolidayConfigDaoImpl"%>
<%@page import="dinghan.workflow.kq.holiday.dao.HolidayConfigDao"%>
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
	
	HolidaySelectDao holidaySelectDao = new HolidaySelectDaoImpl(new SelectItemInfoImpl());
	
	HolidayConfigDao holidayConfigDao = new HolidayConfigDaoImpl();
	HolidayConfig hc = holidayConfigDao.query("2017-10-01", 4);
	out.println("Id :: " + hc.getId());
	out.println("<br/>");
	out.println("Day :: " + hc.getDay());
	out.println("<br/>");
	out.println("Jrmc :: " + hc.getJrmc());
	out.println("<br/>");
	out.println("JrmcCN :: " + holidaySelectDao.queryHolidaySelectItemInfo(hc.getJrmc()).getSelectName());
	out.println("<br/>");
	out.println("Person :: " + hc.getPerson());
	out.println("<br/>");
	out.println("Kssj :: " + hc.getKssj());
	out.println("<br/>");
	out.println("Jssj :: " + hc.getJssj());
	out.println("<br/>");
	out.println("Overnum :: " + hc.getOvernum());
	out.println("<br/>");
	out.println(1);
	out.close();
%>