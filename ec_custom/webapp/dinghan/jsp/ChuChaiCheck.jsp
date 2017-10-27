<%@page contentType="text/html; charset=UTF-8" language="java"  %>
<%@page import="weaver.conn.WeaverConnection"%>
<%@page import="weaver.conn.RecordSet"%>
<%@page import="dinghan.workflow.beans.*"%>
<%@page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings,weaver.file.Prop,weaver.rtx.RTXConfig"%>
<%@page import="weaver.file.FileUpload"%>
<%@page import="java.util.*"%>
<%@page import="weaver.conn.RecordSetDataSource"%>
<%@page import="net.sf.json.*"%>
<%@page import="javax.servlet.*"%>
<%@page import="weaver.hrm.User"%>
<%@page import="weaver.hrm.HrmUserVarify"%>
<%@page import="java.text.*"%>
<%@page import="org.apache.commons.logging.*"%>
<jsp:useBean id="chuChai" class="dinghan.workflow.beans.ChuChai" scope="page" />
   <%
   	JSONObject returnData = new JSONObject();
    returnData.put("info", "检测通过");
   	returnData.put("flag", "true");
       try { 
	        String cc = request.getParameter("chuchai");//得到json字符串（明细表2信息）
	   		JSONObject jsonobj  = JSONObject.fromObject(cc);//将明细表2转化成json对象
	   		
	   		String userid=String.valueOf(jsonobj.get("userid"));  //获取json中userid  根据userid查重
	      	String ksrq=String.valueOf(jsonobj.get("ksrq"));  //获取json中开始日期（字符串）
	   		String jsrq=String.valueOf(jsonobj.get("jsrq"));//  获取json中结束日期（字符串）
	   		String sstime=String.valueOf(jsonobj.get("kssj"));//获取json中开始时间（字符串）
	   		String setime=String.valueOf(jsonobj.get("jssj"));//结束时间
	   		String requestid=String.valueOf(jsonobj.get("rqid"));//requestid
	   		
	   		ArrayList<ChuChai> ccList = ChuChai.queryList(userid,ksrq,jsrq,requestid);//通过userid和rq查询明细表二数据
	   		for(int j=0;j<ccList.size();j++){
	   			ChuChai cc_main=(ChuChai)ccList.get(j);
	   			String sDate=cc_main.getYjccsj();//明细开始日期
	   			String eDate=cc_main.getCcsj2();//明细结束日期
	   			String mxKssj = cc_main.getCcsj1();//明细开始时间
	   			String mxjssj = cc_main.getCcsj3();//明细结束时间
	   			
	   			if(cc_main.getSjkssj().equals("") || cc_main.getSjjssj().equals("") || cc_main.getKssj1().equals("") || cc_main.getKssj3().equals("")){
	   			}else{
	   				sDate=cc_main.getSjkssj();//明细开始日期
	   				eDate=cc_main.getSjjssj();//明细结束日期
	   				mxKssj = cc_main.getKssj1();//明细开始时间
	   				mxjssj = cc_main.getKssj3();//明细结束时间
	   			}
	   			
	   			if(sDate.compareTo(jsrq)==0){
	   				if(mxKssj.compareTo(setime)<0){
	   					returnData.put("info", "检测到重复明细");
	   					returnData.put("flag", "false");
	   					return;
	   				}
	   			}else if(eDate.compareTo(ksrq)==0){
	   				if(mxjssj.compareTo(sstime)>0){
	   					returnData.put("info", "检测到重复明细");
	   					returnData.put("flag", "false");
	   					return;
	   				}
	   			}else if(eDate.compareTo(ksrq)<0 || sDate.compareTo(jsrq)>0){
   						returnData.put("info", "检测通过");
  						returnData.put("flag", "true");
	   					return;
	   			}else{
	   				returnData.put("info", "检测到重复明细");
	   					returnData.put("flag", "false");
	   					return;
	   			}
	   		}
	   	} catch (Exception e) {
	   		// TODO Auto-generated catch block
	   		e.printStackTrace();
	   		returnData.put("info", "程序异常，请与信息部联系");
	   		returnData.put("flag", "false");
	   	} finally {
	   		out.print(returnData.toString());
	   		out.flush();
	   		out.close();
	   	}
   %>