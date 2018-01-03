<%@page import="dinghan.zrac.ga.wfbuild.erp2oa.corn.ZRLoanAppWFCorn"%>
<%@page import="dinghan.zrac.ga.wfbuild.erp2oa.ZRLoanAppWFBuilder"%>
<%@page import="dinghan.zrac.ga.wfbuild.erp2oa.ZRLoanAppBill"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：获取中车ERP借款单数据
	 * 编写人：张肖宇
	 * 编写时间：2017-10-25
	 */
	try{
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		
		ZRLoanAppWFCorn corn = new ZRLoanAppWFCorn();
		//corn.setStartDate("2017-07-01");
		//corn.setStartDate("2017-07-31");
		corn.execute();
		out.print(1);
		out.flush();
		out.close();
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>