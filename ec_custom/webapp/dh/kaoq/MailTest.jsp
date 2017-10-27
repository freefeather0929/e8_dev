<%@page import="dinghan.workflow.service.kqservice.KQMailServiceBySys"%>
<%@page import="dinghan.workflow.service.kqservice.KQMailService"%>
<%@page import="weaver.email.EmailWorkRunnable"%>
<%@page import="dinghan.workflow.test.MailTest"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings,weaver.file.Prop,weaver.rtx.RTXConfig"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<jsp:useBean id="recordSet" class="weaver.conn.RecordSet" scope="page" />
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	
/* 	String fromAddress = request.getParameter("fromaddress");
	String fromName = request.getParameter("fromName");
	String fromWpd = request.getParameter("pwd");*/
	
	String tipStr = request.getParameter("tip"); 
	
	String userIds = Util.null2String(request.getParameter("userids"));
	String accountId = Util.null2String(request.getParameter("account"));
	String month = Util.null2String(request.getParameter("month"));
	
	String userCode="";
	
	String sql = "";
	
	int mailed = 0;
	
	//选判断人员，后判断zhangtao
	if(!"".equals(userIds)){	
		//按人员发送
		String[] arrUserId = userIds.split(",");
		
		for(int i=0; i<arrUserId.length; i++){
			sql = "select Code from uf_hr_userinfo1 where Name = " + arrUserId[i] ;
			recordSet.executeSql(sql);
			
			while(recordSet.next()){
				userCode = Util.null2String(recordSet.getString("Code"));
			}
			
			if(!"".equals(userCode) && userCode != null){
				
				/* KQMailService mailService = new KQMailService();
				mailService.setFormAdress(fromAddress);
				mailService.setFormName(fromName);
				mailService.setFromPwd(fromWpd);
				mailService.setTip(tipStr);
				mailService.setUserCode(userCode);
				mailService.setMonthStr(month); */
				KQMailServiceBySys mailService = new KQMailServiceBySys();
				
				mailService.setTip(tipStr);
				mailService.setUserCode(userCode);
				mailService.setMonthStr(month);
				
				mailService.executeMail();	//邮件发送
				mailed ++;
			}
		}
	}else if(!"".equals(accountId)){	
		//按帐套发送
		
		sql = "select Code from uf_hr_userinfo1 where Company = " + accountId;
		
		ArrayList<String> codeList = new ArrayList<String>();
		
		recordSet.executeSql(sql);
	
		while(recordSet.next()){
			userCode = Util.null2String(recordSet.getString("Code"));
			if(!"".equals(userCode)){
				codeList.add(userCode);
			}
		}
		
		for(String code : codeList){
			KQMailServiceBySys mailService = new KQMailServiceBySys();
			
			/* mailService.setFormAdress(fromAddress);
			mailService.setFormName(fromName);
			mailService.setFromPwd(fromWpd);
			mailService.setTip(tipStr);
			mailService.setUserCode(code);
			mailService.setMonthStr(month); */
			
			mailService.setTip(tipStr);
			mailService.setUserCode(code);
			mailService.setMonthStr(month);
			
			mailService.executeMail();	//邮件发送
			
			mailed ++;
		}
	}
	String info = "邮件发送成功！ 共 [ "+mailed+" ]封,考勤月份："+month+"。";
	StringBuffer json = new StringBuffer();
	json.append("{'info':'"+info+"'}");
	
	out.print(json.toString());
	out.close();
%>