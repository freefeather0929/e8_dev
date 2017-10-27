<%@page import="weaver.formmode.setup.ModeRightInfo"%>
<%@page import="dinghan.workflow.beans.ReceiptList"%>
<%@page import="dinghan.workflow.beans.ReceiptAccount"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	Calendar calendar = new GregorianCalendar();
	String curDate = "";
	curDate += calendar.get(Calendar.YEAR);
	curDate += "-";
	if(calendar.get(Calendar.MONTH+1)<10){
		curDate += "0";
	}
	curDate += calendar.get(Calendar.MONTH+1);
	curDate += "-";
	if(calendar.get(Calendar.DATE)<10){
		curDate += "0";
	}
	curDate += calendar.get(Calendar.DATE);
	String curTime = "";
	if(calendar.get(Calendar.HOUR_OF_DAY) < 10){
		curTime += "0";
	}
	curTime += calendar.get(Calendar.HOUR);
	curTime += "-";
	if(calendar.get(Calendar.MINUTE) < 10){
		curTime += "0";
	}
	curTime +=  calendar.get(Calendar.MINUTE);
	curTime += "-";
	if(calendar.get(Calendar.SECOND) < 10){
		curTime += "0";
	}
	curTime += calendar.get(Calendar.SECOND);
	
	int userId = user.getUID();
	String requestId = request.getParameter("requestid");
	String formId = request.getParameter("formid");
	String sql = "select id,appno,apppsn,hkdate,pjtype,kppsn,kpdate,contratype,unitname,apptotal from formtable_main_" + Math.abs(Integer.parseInt(formId)) + " where requestId = "+requestId;
	rs.executeSql(sql);
	ReceiptAccount ra = new ReceiptAccount();
	int wfMainId = -1; 
	//获取当前流程中要向发票模块写入的主表信息
	while(rs.next()){
		wfMainId = rs.getInt("id");
		ra.setAppno(rs.getString("appno"));
		ra.setApppsn(rs.getInt("apppsn"));
		ra.setContraType(rs.getInt("contratype"));
		ra.setHkDate(rs.getString("hkdate"));
		ra.setKpDate(curDate);
		ra.setKppsn(rs.getInt("kppsn"));
		ra.setKptotal(rs.getDouble("apptotal"));
		ra.setKpUnit(rs.getString("unitname"));
		ra.setPjType(rs.getInt("pjtype"));
		ra.setRequestId(Integer.parseInt(requestId));
	}
	//获取流程中的票据明细
	sql = "select * from formtable_main_" + Math.abs(Integer.parseInt(formId)) + "_dt2 where mainid = " +wfMainId;
	rs.executeSql(sql);
	ArrayList<ReceiptList> rList = new ArrayList<ReceiptList>();
	while(rs.next()){
		ReceiptList r = new ReceiptList();
		r.setMainId(wfMainId);
		r.setCode(rs.getString("d_code"));
		r.setContraNo(rs.getString("d_contracode"));
		r.setContraMoney(rs.getDouble("d_contramoney"));
		r.setMoney(rs.getDouble("d_kpmoney"));
		r.setStatue(rs.getInt("d_kpstatus"));
		r.setMark(rs.getString("d_mark"));
		rList.add(r);
	}
	ra.setReceiptList(rList);
	String receiptFormName = "uf_receiptaccount";
	sql = "select id from "+receiptFormName+" where requestId = " + requestId;
	rs.executeSql(sql);
	int raId = -1;
	if(rs.getCounts() > 0){
		if(rs.next()){
			raId = rs.getInt("id");
		}
		sql = "delete from "+receiptFormName+" where requestId = " + requestId;
		rs.executeSql(sql);
		sql = "delete from "+receiptFormName+"_dt1 where mainid = " + raId;
		rs.executeSql(sql);
	}
	//插入新的开票台账信息
	sql = "insert into "+receiptFormName+" (requestId, appno, apppsn, kpdate, pjtype, kppsn, hkdate, contratype, kpunit, kptotal, formmodeid, modedatacreater, modedatacreatertype, modedatacreatedate, modedatacreatetime)" +
			" values('"+requestId+"','"+ra.getAppno()+"','"+ra.getApppsn()+"','"+ra.getKpDate()+"','"+ra.getPjType()+"','"+userId+"','"+ra.getHkDate()+"','"+ra.getContraType()+"','"+ra.getKpUnit()+"','"+ra.getKptotal()+"','32','"+userId+"','0','"+curDate+"','"+curTime+"')";
	rs.executeSql(sql);
	sql = "select id from "+receiptFormName+" where requestId ="+requestId;
	rs.executeSql(sql);
	int receiptAccountId = -1;
	while(rs.next()){
		receiptAccountId = rs.getInt("id");
	}
	for(int i=0;i<ra.getReceiptList().size();i++){
		sql = "insert into "+receiptFormName+"_dt1 (mainid, d_contracode, d_contramoney, d_code, d_kpmoney, d_kpstatus, d_mark)" +
				" values ('"+receiptAccountId+"','"+ra.getReceiptList().get(i).getContraNo()+"','"+ra.getReceiptList().get(i).getContraMoney()+"','"+ra.getReceiptList().get(i).getCode()+"','"+ra.getReceiptList().get(i).getMoney()+"','"+ra.getReceiptList().get(i).getStatue()+"','"+ra.getReceiptList().get(i).getMark()+"')"; 
		rs.executeSql(sql);
	}
	
	ModeRightInfo modeRightInfo = new ModeRightInfo();
	modeRightInfo.rebuildModeDataShareByEdit(userId, 32, receiptAccountId);
	
	out.print("{'modeid':32,'accountid':'"+receiptAccountId+"'}");
	out.flush();
	out.close();
%>