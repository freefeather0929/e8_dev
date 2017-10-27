<%@page contentType="text/html; charset=UTF-8" language="java" %>
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

<jsp:useBean id="abnormal" class="dinghan.workflow.beans.Abnormal" scope="page" />
   <%
	JSONObject returnData = new JSONObject();
    returnData.put("info", "检测通过");
	returnData.put("flag", "true");
    try { 
        int userid = Integer.parseInt(request.getParameter("userid"));//得到json字符串
		String date= request.getParameter("date");
		
		abnormal=new Abnormal(userid,date,-1);//得到考勤异常对象
		
		if(abnormal.getSqrq()!=null){
			returnData.put("info", ""+date+"日你已填写过考勤异常申请，不可重复申请");
			returnData.put("flag", "false");
		}
     } catch (Exception e) {
		// TODO Auto-generated catch block
		returnData.put("info", "程序异常，请与信息部联系");
		returnData.put("flag", "false");
	}finally{
		out.print(returnData.toString());
		out.flush();
		out.close();
	}

    %>