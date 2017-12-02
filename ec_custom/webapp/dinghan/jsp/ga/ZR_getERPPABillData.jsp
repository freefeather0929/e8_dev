<%@page import="dinghan.zrac.ga.wfbuild.erp2oa.ZRPAAppWFBuilder"%>
<%@page import="dinghan.zrac.ga.wfbuild.erp2oa.ZRPAAppBill"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>


 
<%
	/*
	 * 功能：获取中车ERP差旅费用报销单数据
	 * 编写人：hsf
	 * 编写时间：2017-11-19
	 */
	try{
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		StringBuilder json = new StringBuilder(); 
		
		Map<String,String> parameters = new HashMap<String,String>();
		
		String docNo = Util.null2String(request.getParameter("DocNo"));    
		
		ZRPAAppWFBuilder zRPAAppWFBuilder = new ZRPAAppWFBuilder();
		
		if(zRPAAppWFBuilder.hasCreated(docNo)){
			json.append("{'error':'此单号已经创建过申请单，请检查你填写的单号！'}");
		}else{
			ZRPAAppBill zRPAAppBill = new ZRPAAppBill();   
			if("".equals(docNo) == false){ 
				//out.println("docNo == " + docNo);
				//out.println(zRPAAppBill.getUrl());
				//parameters.put("DocNo", docNo);   
		        // json = HttpUtils.sendGet(zRPAAppBill.getUrl()+"/ReiBillSvl", parameters);
				  
				  
				json.append(zRPAAppBill.queryPABillInfo(docNo));    
			}
		}
		
		out.println(json);
		out.flush();
		out.close();  
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>