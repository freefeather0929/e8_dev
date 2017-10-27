<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings,weaver.file.Prop,weaver.rtx.RTXConfig" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="weaver.file.FileUpload" %>
<%@ page import="java.util.*" %>
<%@ page import="weaver.conn.RecordSetDataSource" %>
<%@page import="net.sf.json.*"%>
<%@ page import="javax.servlet.*" %>
<jsp:useBean id="recordSet" class="weaver.conn.RecordSet" scope="page" />
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>
<%@page import="weaver.formmode.setup.ModeRightInfo"%>
<jsp:useBean id="ModeShareManager" class="weaver.formmode.view.ModeShareManager" scope="page" />

<% 
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	
	String loginid = Util.getCookie(request , "loginidweaver");
	
	//获取已经结束的当前用户新员工月度考核流程
	String neWFQuerySql = "select f.requestId,f.tutor from formtable_main_84 f, workflow_requestbase w where f.nename = "
	             + loginid +" and w.currentnodeid = 554 and f.requestId = w.requestid order by f.checkmonth";
	
	StringBuffer json = new StringBuffer();
	
	int requestId = 0;
	
	recordSet.executeSql(neWFQuerySql);	//获取填写人试用期月度考核成绩和流程ID（requestId）
	
	int count = recordSet.getCounts();
	int levelTmp_A = 0;
	int levelTmp_D = 0;
	if(count<1){
		json.append("{\"result\":0,\"isSatisfy\":0}");
	}else{
		int i = 0;
	    json.append("{\"result\":1,");
		json.append("\"links\":[");
	    if(count<3){
	    	while(recordSet.next()){
	    		if(i>0){
					json.append(",");
				}
				json.append("{\"measure\":");
				json.append("\""+recordSet.getInt("tutor")+"\",");		//月度考核成绩
				json.append("\"requestId\":");
				json.append("\""+recordSet.getInt("requestId")+"\"");	//流程id
				json.append("}");
				i++;
	    		if(recordSet.getInt("measure")==0){
	    			levelTmp_A++;
	    		}
	    	}
	    	json.append("]");
	    	if(levelTmp_A==2){
	    		json.append(",\"isSatisfy\":2");
	    	}else{
	    		json.append(",\"isSatisfy\":0");
	    	}
	    }else{
	    	while(recordSet.next()){
				if(i>0){
					json.append(",");
				}
				json.append("{\"measure\":");
				json.append("\""+recordSet.getInt("tutor")+"\",");		//月度考核成绩
				json.append("\"requestId\":");
				json.append("\""+recordSet.getInt("requestId")+"\"");	//流程id
				json.append("}");
				i++;
			}
			json.append("]");
			if(recordSet.getInt("measure")==0){
    			levelTmp_A++;
    		}else if(recordSet.getInt("measure")==3){
    			levelTmp_D++;
    			levelTmp_A = 0;
    		}else{
    			levelTmp_A = 0;
    		}
    		
    		if(levelTmp_A>1){
    			json.append(",\"isSatisfy\":2");
    		}else if(levelTmp_D > (count-3)){
    			json.append(",\"isSatisfy\":0");
    		}else{
    			json.append(",\"isSatisfy\":1");
    		}
	    }
		
		json.append("}");
	}
	
	out.println(json.toString());
	out.close();
%>