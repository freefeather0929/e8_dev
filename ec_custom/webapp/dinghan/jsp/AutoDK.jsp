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
<jsp:useBean id="oDK" class="dinghan.workflow.beans.DaKaRecord" scope="page" />
<jsp:useBean id="wctemp" class="dinghan.workflow.beans.Wctemp" scope="page" />
<jsp:useBean id="qingJia" class="dinghan.workflow.beans.Cctemp" scope="page" />
<jsp:useBean id="userInfo" class="dinghan.workflow.beans.UserInfo" scope="page" />
   <%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	JSONObject returnData = new JSONObject();

    try { 
	        String jb = request.getParameter("jiaban");//得到json字符串（明细表2信息）
	   		JSONObject jsonobj  = JSONObject.fromObject(jb);//将明细表2转化成json对象

	   		String userid=String.valueOf(jsonobj.get("userid"));  //获取json中userid  根据userid查重
	      	String jbrq=String.valueOf(jsonobj.get("jbrq"));  //获取json中开始日期（字符串）
	      	
	      	
    	    ArrayList<Wctemp> list_wc;
			ArrayList<Cctemp> list_cc;
    		userInfo = new UserInfo(Integer.parseInt(userid));
			oDK = new DaKaRecord(userInfo.getCode(),jbrq);
			list_wc = Wctemp.queryList(userInfo.getName(),jbrq);
			for (int i = 0; i < list_wc.size(); i++) {
				oDK.getDk_list().add(list_wc.get(i).getHdkssj());
				oDK.getDk_list().add(list_wc.get(i).getHdjssj());
			}
			list_cc = Cctemp.queryList(userInfo.getName(),jbrq);
			for (int i = 0; i < list_cc.size(); i++) {
				oDK.getDk_list().add(list_cc.get(i).getHdkssj());
				oDK.getDk_list().add(list_cc.get(i).getHdjssj());
			}
			// 考勤记录排序
			for (int i = 0; i < oDK.getDk_list().size(); i++) {
				if (oDK.getDk_list().get(i).equals("") || oDK.getDk_list().get(i).equalsIgnoreCase("null")) {
					oDK.getDk_list().remove(i);
					i--;
				}
			}
			Collections.sort(oDK.getDk_list());
			returnData.put("info", oDK.getDk_list());
			returnData.put("flag", "true");
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