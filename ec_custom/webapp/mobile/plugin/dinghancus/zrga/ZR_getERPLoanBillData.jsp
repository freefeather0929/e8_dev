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
		StringBuilder json = new StringBuilder();
		
		Map<String,String> parameters = new HashMap<String,String>();
		
		String docNo = Util.null2String(request.getParameter("docno"));
		
		ZRLoanAppWFBuilder zrLoanAppWFBuilder = new ZRLoanAppWFBuilder();
		
		if(zrLoanAppWFBuilder.hasCreated(docNo)){
			json.append("{'error':'此单号已经创建过申请单，请检查你填写的单号！'}");
		}else{
			ZRLoanAppBill zrLoanAppBill = new ZRLoanAppBill();
			if("".equals(docNo) == false){
				//out.println("docNo == " + docNo);
				//out.println(zrLoanAppBill.getUrl());
				//parameters.put("DocNo", docNo);
				//json = HttpUtils.sendGet(zrLoanAppBill.getUrl()+"/LoanBillSvl", parameters);
				json.append(zrLoanAppBill.queryBillInfo(docNo));
			}
		}
		
		out.println(json);
		out.flush();
		out.close();
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>