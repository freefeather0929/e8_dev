<%@page import="weaver.conn.RecordSet"%>
<%@page import="dinghan.workflow.beans.QingJia"%>
<%@page import="dinghan.workflow.beans.JiaBan1"%>
<%@page import="weaver.dh.interfaces.AutoTask"%>
<%@page import="org.json.JSONObject"%>
<%@page import="weaver.dh.interfaces.toU9"%>
<%@page import="weaver.email.EmailWorkRunnable"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	
	String sql = "select * from formtable_main_89_dt3 where rq like "+"'2017-07%'"+ "and hdzt <> 2";
	
	RecordSet rs = new RecordSet();
	
	rs.executeSql(sql);
	
	Set<Integer> idSet = new HashSet<Integer>();
	
	while(rs.next()){
		idSet.add(rs.getInt("mainid"));
	}
	
	List<QingJia> qj_dt3_List = new ArrayList<QingJia>();
	
	out.print(qj_dt3_List.size());
	
	Iterator<Integer> idIter = idSet.iterator();
	int i;
	while(idIter.hasNext()){
		i = idIter.next();
		QingJia.delete(i,0);
		qj_dt3_List = QingJia.generateFromQingJia_1_List(i);
		for(QingJia q : qj_dt3_List){
			q.insert();
		}
		QingJia.checkList(i);
		
	}
	
	out.print(1);
	out.close();
%>