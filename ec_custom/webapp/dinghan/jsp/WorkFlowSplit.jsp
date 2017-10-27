<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="weaver.workflow.webservices.WorkflowRequestTableField"%>
<%@page import="weaver.workflow.webservices.WorkflowBaseInfo"%>
<%@page import="weaver.conn.RecordSet"%>
<%@page import="weaver.workflow.webservices.WorkflowMainTableInfo"%>
<%@page import="weaver.workflow.webservices.WorkflowRequestTableRecord"%>
<%@page import="weaver.workflow.webservices.WorkflowDetailTableInfo"%>
<%@page import="weaver.workflow.webservices.WorkflowRequestInfo"%>
<%@page import="weaver.workflow.webservices.WorkflowServiceImpl"%>
<%@page import="weaver.workflow.webservices.WorkflowService"%>
<%@page import="weaver.workflow.workflow.WorkflowManage"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings,weaver.file.Prop,weaver.rtx.RTXConfig" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="weaver.file.FileUpload" %>
<%@ page import="java.util.*" %>
<%@ page import="weaver.conn.RecordSetDataSource" %>
<%@page import="net.sf.json.*"%>
<%@ page import="javax.servlet.*" %>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>
<%@page import="weaver.formmode.setup.ModeRightInfo"%>
<jsp:useBean id="rs" class="weaver.conn.RecordSet"></jsp:useBean>
<jsp:useBean id="rs1" class="weaver.conn.RecordSet"></jsp:useBean>
<jsp:useBean id="rs2" class="weaver.conn.RecordSet"></jsp:useBean>

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
	
	int uId = user.getUID();	//获取当前操作人ID
   
    String str = request.getParameter("fenliurows");
    
	String[] fenliuRows = str.split("#");	//要分流的明细记录ID
	
	int requestId = Util.getIntValue(request.getParameter("requestid"));	//当前流程的请求ID
	
	String formName = "formtable_main_" + Math.abs(Util.getIntValue(request.getParameter("formid")));	//当前流程对应的表名
	
	String sql = "select top 1 * from "+ formName + " where requestId = " + requestId;
	
	String appNo = "";
	
	int mainId = 0;
	
	rs.executeSql(sql);
	
	if(rs.first()){
		appNo = rs.getString("appno");
		mainId = rs.getInt("id");
	}else{
		out.print("{'flag':'0','info':'没有找到要分流的流程，请与开发人员联系。'}");
		out.clearBuffer();
		out.close();
		return;
	}
	
	WorkflowService wfs = new WorkflowServiceImpl();
	//获取当前流程请求信息
	WorkflowRequestInfo curWFRInfo = wfs.getWorkflowRequest(requestId, uId, requestId);

	//获取流程基本信息,用于给分流流程赋值
	WorkflowBaseInfo curWFBaseInfo = curWFRInfo.getWorkflowBaseInfo();
	
	//创建分流流程请求信息
	WorkflowRequestInfo fenliuWFInfo = new WorkflowRequestInfo();
	
	fenliuWFInfo.setWorkflowBaseInfo(curWFBaseInfo);	//流程基础信息
	//fenliuWFInfo.setWorkflowMainTableInfo(fenTblInfo);		//流程主表信息
	fenliuWFInfo.setCreatorId(curWFRInfo.getCreatorId());
	fenliuWFInfo.setCreatorName(curWFRInfo.getCreatorName());
	fenliuWFInfo.setCurrentNodeId(curWFRInfo.getCurrentNodeId());
	fenliuWFInfo.setCurrentNodeName(curWFRInfo.getCurrentNodeName());

	fenliuWFInfo.setCreateTime(dateFm.format(new Date()));
	fenliuWFInfo.setRequestName("非生产物采购申请流程-"+curWFRInfo.getCreatorName()+"-"+dateFm.format(new Date()));
	fenliuWFInfo.setWorkflowRequestLogs(curWFRInfo.getWorkflowRequestLogs());
	fenliuWFInfo.setStatus(curWFRInfo.getStatus());
	
	String fenliurid = wfs.doCreateWorkflowRequest(fenliuWFInfo, uId);
	
	if(Util.getIntValue(fenliurid) > 0){
		//改写当前流程明细中mianid值为分流流程的主表记录id值
		//获取分流流程对应的主表记录id
		String sql1 = "select top 1 id from "+ formName + " where requestid="+fenliurid;
		rs.executeSql(sql1);
		int fenliuMainId = 0;
		if(rs.first()){
			fenliuMainId = rs.getInt("id");
			for(String rowId:fenliuRows){
				sql1 = "update " +formName + "_dt1 set mainid="+fenliuMainId+" where id="+ rowId;
				rs.executeSql(sql1);
			}
		}
		//主表赋值
		String sql3 = "select top 1 * from "+formName+" where requestId = '"+requestId+"'";
		rs.executeSql(sql3);
		
		String sql2 = "update formtable_main_8 set ";
		
		while(rs.next()){
			sql2 += "lxfs = '" + rs.getString("lxfs") + "',";
			if(rs.getInt("cgzxdy") != -1){
				sql2 += "cgzxdy = '" + rs.getInt("cgzxdy") + "',";
			}
			//sql2 += "sgwpjehj = '" + rs.getDouble("sgwpjehj") + "',";
			sql2 += "sqyy = '" + Util.null2String(rs.getString("sqyy")) + "',";
			sql2 += "dxsm = '" + rs.getString("dxsm") + "',";
			if(rs.getDouble("sgwpjehj")!=-1){
				sql2 += "sgwpjehj = '" + rs.getDouble("sgwpjehj") + "',";
			}
			if(rs.getInt("sffhndzcys") != -1){
				sql2 += "sffhndzcys = '" + rs.getInt("sffhndzcys") + "',";
			}
			if(rs.getInt("zclx") != -1){
				sql2 += "zclx = '" + rs.getInt("zclx") + "',";
			}
			if(rs.getInt("bhlx") != -1){
				sql2 += "bhlx = '" + rs.getInt("bhlx") + "',";
			}
			sql2 += "bhhrzpzh = '" + rs.getString("bhhrzpzh") + "',";
			sql2 += "cljg = '" + rs.getString("cljg") + "',";
			if(rs.getInt("shyj01") != -1){
				sql2 += "f.shyj01 = '" + rs.getInt("shyj01") + "',";
			}
			sql2 += "apppsn = '" + rs.getInt("apppsn") + "',";
			sql2 += "appdate = '" + rs.getString("appdate") + "',";
			sql2 += "company = '" + rs.getInt("company") + "',";
			sql2 += "appdept1 = '" + rs.getInt("appdept1") + "',";
			sql2 += "appdept2 = '" + rs.getInt("appdept2") + "',";
			sql2 += "usrdept1 = '" + rs.getInt("usrdept1") + "',";
			sql2 += "isfenliubill = '1'";
		}
		
		sql2 += " where requestId = "+fenliurid;
		
		rs2.executeSql(sql2);
		out.println("{'flag':'1','requestid':'"+fenliurid+"'}");
		out.flush();
		out.close();
	}else{
		out.print(fenliurid);
		out.flush();
		out.close();
	}
%>