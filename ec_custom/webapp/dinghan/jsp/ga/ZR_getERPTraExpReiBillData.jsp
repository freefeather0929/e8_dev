<%@page import="dinghan.zrac.ga.wfbuild.erp2oa.ZRTraExpReiAppWFBuilder"%>
<%@page import="dinghan.zrac.ga.wfbuild.erp2oa.ZRTraExpReiAppBill"%>
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
		
		ZRTraExpReiAppWFBuilder zRTraExpReiAppWFBuilder = new ZRTraExpReiAppWFBuilder();
		
		if(zRTraExpReiAppWFBuilder.hasCreated(docNo)){ 
			json.append("{'error':'此单号已经创建过申请单，请检查你填写的单号！'}");
		}else{
			ZRTraExpReiAppBill zRTraExpReiAppBill = new ZRTraExpReiAppBill();   
			if("".equals(docNo) == false){ 
				//out.println("docNo == " + docNo);
				//out.println(zRTraExpReiAppBill.getUrl());
				//parameters.put("DocNo", docNo);   
		        // json = HttpUtils.sendGet(zRTraExpReiAppBill.getUrl()+"/ReiBillSvl", parameters);
				json.append(zRTraExpReiAppBill.queryTraExpReiBillInfo(docNo));    
			}      
		}
		
		out.println(json);
		out.flush();
		out.close();  
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>