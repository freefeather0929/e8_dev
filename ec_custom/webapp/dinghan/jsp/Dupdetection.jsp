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
<jsp:useBean id="qingJia" class="dinghan.workflow.beans.QingJia" scope="page" />
   <%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	JSONObject returnData = new JSONObject();
    returnData.put("info", "检测通过");
	returnData.put("flag", "true");
	QingJia.checkList(0);
    try { 
        String mx3info = request.getParameter("mx3info");//得到json字符串（明细表3信息）
		JSONObject   jsonobj  = JSONObject.fromObject(mx3info);
		JSONArray jsonarray = jsonobj.getJSONArray("qingjia"); 
  	 	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
  		for(int i=0;i<jsonarray.size();i++){
    		JSONObject jsonObject = jsonarray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
    		String userid=String.valueOf(jsonObject.get("userid"));  //获取json中userid  根据userid查重
    		String ksrq=String.valueOf(jsonObject.get("ksrq"));  //获取json中开始日期（字符串）
			String jsrq=String.valueOf(jsonObject.get("jsrq"));//  获取json中结束日期（字符串）
			String sstime=String.valueOf(jsonObject.get("kssj"));//获取json中开始时间（字符串）
			String setime=String.valueOf(jsonObject.get("jssj"));//结束时间
			String requestid=String.valueOf(jsonObject.get("rqid"));//requestid

			ArrayList<QingJia> sqList = QingJia.queryList(userid,ksrq,jsrq,requestid);//通过userid和rq查询明细表三数据

			for(int j=0;j<sqList.size();j++){
				QingJia qingJia1=(QingJia)sqList.get(j);
				String mxrq=qingJia1.getRq();//明细日期
				String mxKssj=qingJia1.getKssj();//明细开始时间
				String mxjssj=qingJia1.getJssj();//明细结束时间
				
				if(ksrq.compareTo(jsrq)==0){//开始日期等于结束日期时，
					if(mxjssj.compareTo(sstime)<=0 || mxKssj.compareTo(setime)>=0){
					}else{
						returnData.put("info", "检测到重复明细");
						returnData.put("flag", "false");
						return;
					}
				}else{//开始日期不等于结束日期时
					if(mxrq.compareTo(ksrq)==0){//明细日期等于开始日期时
						if(mxjssj.compareTo(sstime)>0){
							returnData.put("info", "检测到重复明细");
							returnData.put("flag", "false");
							return;
						}
					} else if(mxrq.compareTo(jsrq)==0){//明细日期等于结束日期时
						if(mxKssj.compareTo(setime)<0){
							returnData.put("info", "检测到重复明细");
							returnData.put("flag", "false");
							return;
							}
					}else{
						returnData.put("info", "检测到重复明细");
						returnData.put("flag", "false");
						return;
					}
				}
			}
		}
      } catch (Exception e) {
		returnData.put("info", "程序异常，请与信息管理部联系。");
		returnData.put("flag", "false");
	}finally{ 
		out.write(returnData.toString());
		out.flush();
		out.close();
	} 
        
    	
   
    %>
