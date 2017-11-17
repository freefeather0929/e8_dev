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
	 * 功能：根据用户ID获取是否允许加班
	 * 编写人：张肖宇
	 * 编写时间：2017-10-31
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
				json.append("'error':'0',");
				json.append("'info':'");
				if(userInfo.getAllowovertime() <1){
					json.append("申请人： "+userInfo.getNameCN()+"（"+workcode+"） 的人员信息中的标记为不能提交加班，或者此项未配置，如有疑问，请与考勤管理员联系！");
				}
				json.append("',");
				json.append("'username':'"+userInfo.getNameCN()+"',");
				json.append("'allow':'"+userInfo.getAllowovertime()+"'");
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
				json.append("'info':'当前申请人的工号为空，请联系系统管理员检查并添加！'");
				json.append("}");
		}
		
		out.print(json.toString());
		out.flush();
		out.close();
		
%>