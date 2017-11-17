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
	 * 功能：根据用户ID获取是否已经创建了其考勤人员信息
	 * 编写人：张肖宇
	 * 编写时间：2017-11-13
	 */
	
		
		User user = HrmUserVarify.getUser(request, response);
		if(user == null){
			response.sendRedirect("/login/Login.jsp");
			return;
		}
		
		String apppsnid = Util.null2String(request.getParameter("apppsnid"));
		StringBuffer json = new StringBuffer();
		String workcode = null;
		String sql = "select workcode from HrmResource where id = " + apppsnid;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			workcode = Util.null2String(rs.getString("workcode"));
		}
		//out.print(workcode);
		UserInfoDao userDao = new UserInfoDaoImpl();
		if(workcode!=""){
			UserInfo userInfo = userDao.queryByCode(workcode);
			if(userInfo!=null){
				json.append("{");
				json.append("'error':'0'");
				json.append("}");
			}else{
				json.append("{");
				json.append("'error':'1',");
				json.append("'info':'当前申请人的人员信息未在考勤平台查询到，请联系当地的考勤员为你检查、添加人员信息！'");
				json.append("}");
			}
		}else{
			json.append("{");
			json.append("'error':'2',");
			json.append("'info':'当前申请人的工号为空，请联系系统管理员检查并为其添加！'");
			json.append("}");
		}
		
		out.print(json.toString());
		out.flush();
		out.close();
		
%>