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

<jsp:useBean id="jiaban" class="dinghan.workflow.beans.JiaBan1" scope="page" />
   <%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	JSONObject returnData = new JSONObject();
    returnData.put("info", "检测通过");
	returnData.put("flag", "true");
    try { 
        String jiaban1 = request.getParameter("jiaban");//得到json字符串
		JSONObject jsonobj = JSONObject.fromObject(jiaban1);
		JSONArray jsonarray = jsonobj.getJSONArray("jiaban"); 
		
  		for(int i=0;i<jsonarray.size();i++){
    		JSONObject jsonObject = jsonarray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
			
			ArrayList<JiaBan1> jbList= JiaBan1.queryList(String.valueOf(jsonObject.get("userid")),String.valueOf(jsonObject.get("jbrq")),String.valueOf(jsonObject.get("rqid")));//加班明细表1数组
			
			for(int j=0;j<jbList.size();j++){
				jiaban=jbList.get(j);
				if(jiaban.getYjkssj().compareTo(String.valueOf(jsonObject.get("yjjssj")))>=0 || jiaban.getYjjssj().compareTo(String.valueOf(jsonObject.get("yjkssj")))<=0){
				}else{
					returnData.put("info", "检测到重复明细");
					returnData.put("flag", "false");
					return;
				}
			}
		}
     } catch (Exception e) {
		// TODO Auto-generated catch block
		returnData.put("info", "程序异常，请与信息部联系");
		returnData.put("flag", "false");
	}finally{
		out.write(returnData.toString());
		out.flush();
		out.close();
	}
        
    	
   
    %>