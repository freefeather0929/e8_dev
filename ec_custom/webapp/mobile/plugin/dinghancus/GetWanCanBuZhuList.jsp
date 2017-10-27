<%@page import="dinghan.workflow.beans.WanCanBuZhu"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="weaver.conn.WeaverConnection"%>
<%@page import="weaver.conn.RecordSet"%>
<%@ page import="weaver.general.Util"%>
<%@ page import="java.util.*"%>
<%@page import="net.sf.json.*"%>
<%@ page import="javax.servlet.*"%>
<jsp:useBean id="recordSet" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="wanCanBuZhuService" class="dinghan.workflow.service.WanCanBuZhuService" scope="page" />
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	
	String requestId = request.getParameter("requestId");
	int userId = Integer.parseInt(request.getParameter("userId"));
	//out.println(userId);
	Calendar calendar = new GregorianCalendar();
	int curYear = calendar.get(Calendar.YEAR);
	int curMonth = calendar.get(Calendar.MONTH)+1;
	
	String curMonthStr = curMonth>10?""+curMonth:"0"+curMonth; 
	
	StringBuffer json = new StringBuffer();
	//out.println(curYear+"::"+curMonth);
	if(wanCanBuZhuService.isFilledApp(curYear, curMonth, userId, requestId) == true){
		json.append("{'filled':'1','month':'"+curYear+"-"+curMonthStr+"'}");
	}else{
		String sql = "select workcode from HrmResource where id = " + userId;
		String workcode = "";
		recordSet.executeSql(sql);
		while(recordSet.next()){
			workcode = recordSet.getString("workcode");
		}
		ArrayList<WanCanBuZhu> wancanBuZhuList = new ArrayList<WanCanBuZhu>();
		wancanBuZhuList = wanCanBuZhuService.getAllWanCanBuZhuRecord(curYear,curMonth,userId,workcode);
		//if(wancanBuZhuList.size() > 0){
		    
			json.append("{'filled':'0','month':'"+curYear+"-"+curMonthStr+"','num':'"+wancanBuZhuList.size()+"',");
			json.append("'records':[");
			for(int i=0;i<wancanBuZhuList.size();i++){
				if(i>0){
					json.append(",");
				}
				json.append("{");
				json.append("'date':'"+wancanBuZhuList.get(i).getDate()+"',");
				json.append("'weekday':'"+wancanBuZhuList.get(i).getWeekDay()+"',");
				json.append("'daka':'"+wancanBuZhuList.get(i).getDakaRecord()+"',");
				json.append("'money':'"+wancanBuZhuList.get(i).getMenoy()+"'");
				json.append("}");
			}
			json.append("]");
			json.append("}");
		//}
	}
 	out.print(json.toString());
	out.flush();
	out.close();
%>