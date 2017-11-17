<%@page import="dinghan.workflow.kq.appdata.entity.ZRQingJiaAppData"%>
<%@page import="dinghan.workflow.kq.appdata.util.ZRQingJiaCheckRepeatUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	/*
	 * 功能：检测重复的中车轨道-请假申请
	 * 编写人：张肖宇
	 * 编写时间：2017-11-16
	 * 
	 */
	try{
		
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		ZRQingJiaCheckRepeatUtil util = new ZRQingJiaCheckRepeatUtil();
		String json = request.getParameter("json");
		JSONObject jsonObj = JSONObject.fromObject(json); 
		
		int userid = Integer.valueOf(jsonObj.getInt("psnid"));
		String appno = jsonObj.getString("appno");
		JSONArray jarray = jsonObj.getJSONArray("rows");
		
		String rowIndex;
		String preStartDate;
		String preEndDate;
		String preStartTime;
		String preEndTime;
		
		List<ZRQingJiaAppData> repeatList =  null;
		
		JSONObject jsonTmp = null;
		JSONArray jsonArrTmp = null;
		JSONArray jsonArray_Res = new JSONArray();
		
		JSONObject row = null;
		for(int k = 0; k< jarray.size();k++){
			jsonTmp = new JSONObject();
			jsonArrTmp = new JSONArray();
			row = jarray.getJSONObject(k);
			rowIndex = row.getString("rowindex");
			preStartDate = row.getString("prestartdate");
			preEndDate = row.getString("preenddate");
			preStartTime = row.getString("prestarttime");
			preEndTime = row.getString("preendtime");
			repeatList = util.executeCheck(userid, preStartDate, preEndDate, preStartTime, preEndTime, appno);
			if(repeatList != null){
				for(int i = 0;i< repeatList.size();i++){
					jsonArrTmp.add(repeatList.get(i).getOddnum());
				}
			}
			jsonTmp.put("rowindex", rowIndex);
			jsonTmp.put("repeatno",jsonArrTmp);
			jsonArray_Res.add(jsonTmp);
		}
		
		//out.print("<p>");
		
		//util.executeCheck(userid, overTimeDate, preStartTime, preEndTime, appno)
		//out.print(jsonObj.toString());
		out.print(jsonArray_Res.toString());
		out.flush();
		out.close();
		
	} catch(Exception e){
		e.printStackTrace();
	}
	
%>