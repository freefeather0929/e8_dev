<%@page import="dinghan.workflow.kq.appdata.entity.ZRLouDaKaAppData"%>
<%@page import="dinghan.workflow.kq.appdata.util.ZRLouDaKaCheckRepeatUtil"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：检测重复的中车轨道-漏刷卡申请
	 * 编写人：张肖宇
	 * 编写时间：2017-10-25
	 * 
	 */
	try{
		
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		
		int userid = Integer.valueOf(request.getParameter("userid"));
		
		String billCardDate = request.getParameter("fillcarddate");
		String appNo = request.getParameter("appno");
		String fillCardType = request.getParameter("fillcardtype");
		//out.print("userid :: "+ userid);
		//out.print("fillCardDate :: "+billCardDate);
		//out.print("appNo :: " +appNo);
		//out.print("fillcardtype :: " + fillCardType);
		StringBuffer json = new StringBuffer();
		ZRLouDaKaCheckRepeatUtil util = new ZRLouDaKaCheckRepeatUtil();
		
		ZRLouDaKaAppData zrLDKAppData = util.executeCheckSameDate(userid,billCardDate, appNo);
		//out.print("zrLDKAppData for samedate == null ? :: " + (zrLDKAppData == null));
		if(zrLDKAppData!=null){
			json.append("{");
			json.append("'repeat':1,");
			json.append("'appno':'"+zrLDKAppData.getAppno()+"',");
			json.append("'info':'存在与当前填写补填日期相同的申请单，单号为："+zrLDKAppData.getAppno()+"，请检查！'");
			json.append("}");
		}else{
			if("1".equals(fillCardType)){
				zrLDKAppData = util.executeCheck(userid, billCardDate, appNo);
				//out.print("zrLDKAppData for in month == null ? :: " + (zrLDKAppData == null));
				if(zrLDKAppData!=null){
					json.append("{");
					json.append("'repeat':1,");
					json.append("'appno':'"+zrLDKAppData.getAppno()+"',");
					json.append("'info':'检测到本月已经填写了单号为：" + zrLDKAppData.getAppno() + "的补漏打卡申，请每月仅能填写一次“忘带卡”类型的补漏打卡申请单，请检查！'");
					json.append("}");
				}
			}else{
				json.append("{'repeat':0}");
			}
		}
		
		out.print(json.toString());
		out.flush();
		out.close();
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>