<%@page import="dinghan.zrac.ga.wfbuild.erp2oa.CheckInfoFromOA"%>
<%@page import="dinghan.zrac.ga.ConstantUtils"%>
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
 		String isNeedCEO="";
 		Map<String,String> parameters = new HashMap<String,String>();
 		String apppsnid = Util.null2String(request.getParameter("apppsnid"));     
 		CheckInfoFromOA checkInfoFromOA = new CheckInfoFromOA();
 		ConstantUtils constantUtils = new ConstantUtils();
 		isNeedCEO =checkInfoFromOA.checkIsOrNotNeedAppByCEOByAppID(apppsnid); 
 		if (constantUtils.isNeedCEO_3.equals(isNeedCEO)) {
 			json.append("{'isNeedCEO':'3'}");
 		} else if (constantUtils.isNeedCEO_2.equals(isNeedCEO)) {  
 			json.append("{'isNeedCEO':'2'}");  
 		}else if (constantUtils.isNotNeedCEO_0.equals(isNeedCEO)) {
 			json.append("{'isNeedCEO':'0'}");  
 		}
 		out.println(json);
 		out.flush();
 		out.close();

 	} catch (Exception e) {
 		e.printStackTrace();
 	}
 %>