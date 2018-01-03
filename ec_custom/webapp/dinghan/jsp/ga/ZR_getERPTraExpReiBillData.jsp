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
		String apppronum =Util.null2String(request.getParameter("AppproNum"));   		
		ZRTraExpReiAppWFBuilder zRTraExpReiAppWFBuilder = new ZRTraExpReiAppWFBuilder();
		if(zRTraExpReiAppWFBuilder.hasCreated(docNo,apppronum)){  
			json.append("{'error':'此单号已经创建过申请单，请检查你填写的单号！'}");
		}else{
			ZRTraExpReiAppBill zRTraExpReiAppBill = new ZRTraExpReiAppBill();   
			if("".equals(docNo) == false){ 
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