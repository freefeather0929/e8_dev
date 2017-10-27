<%@page import="org.json.JSONObject"%>
<%@page import="weaver.dh.interfaces.toU9"%>
<%@page import="weaver.email.EmailWorkRunnable"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.*"%>
<jsp:useBean id="recordSet" class="weaver.conn.RecordSet" scope="page" />
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>

<%
	User user = HrmUserVarify.getUser(request, response);
	if(user == null){
		response.sendRedirect("/login/Login.jsp");
		return;
	}
	String userIds = Util.null2String(request.getParameter("userids"));
	String accountId = Util.null2String(request.getParameter("accountid"));
	String year = Util.null2String(request.getParameter("year"));
	String month = Util.null2String(request.getParameter("month"));
	String operatorId = Util.null2String(request.getParameter("operatorid"));
	int userId= 0 ;
	String userName = "";
	String userCode = "";
	String sql = "";
	int mailed = -1;
	int num = 0;	//传输数量
	StringBuffer json = new StringBuffer();
	json.append("{'infos':[");
	//先判断人员，后判断帐套
	if(!"".equals(userIds)){	
		//按人员发送
		String[] arrUserId = userIds.split(",");
		for(int i=0; i<arrUserId.length; i++){
			sql = "select Code,Name,NameCN from uf_hr_userinfo1 where Name = " + arrUserId[i];
			recordSet.executeSql(sql);
			while(recordSet.next()){
				userName = recordSet.getString("NameCN");
				userCode = recordSet.getString("Code");
			}
			toU9 t9 = new toU9();
			t9.setUserId(Integer.parseInt(arrUserId[i]));
			t9.setMonth(month);
			t9.setYear(year);
			t9.setOperatorid(Integer.parseInt(operatorId));
			String resInfo = t9.getJump();
			
			JSONObject resJson = new JSONObject(resInfo);
			int flag = (Integer)resJson.get("flag");
			String info = (String)resJson.get("info");
			
			if(i>0) json.append(",");
			json.append("{");
			json.append("'id':'"+arrUserId[i]+"',");
			json.append("'namecn':'"+userName+"',");
			json.append("'code':'"+userCode+"',");
			json.append("'flag':"+flag+",");
			json.append("'info':'"+info+"'");
			json.append("}");
			num = i+1;
		}
		json.append("],'num':'"+num+"'");
		
	}else if(!"".equals(accountId)){	
		//按帐套发送
		sql = "select Code,Name,NameCN from uf_hr_userinfo1 where Company = " + accountId;
		Map<String,Object> userInfoMap = new HashMap<String,Object>();
		recordSet.executeSql(sql);
		while(recordSet.next()){
			userId = recordSet.getInt("Name");
			if(userId != -1){
				toU9 t9 = new toU9();
			
				t9.setUserId(userId);
				t9.setMonth(month);
				t9.setYear(year);
				t9.setOperatorid(Integer.parseInt(operatorId));
				String resInfo = t9.getJump();
				
				JSONObject resJson = new JSONObject(resInfo);
				int flag = (Integer)resJson.get("flag");
				String info = (String)resJson.get("info");
				
				if(num>0) json.append(",");
				json.append("{");
				json.append("'id':'"+userId+"',");
				json.append("'namecn':'"+recordSet.getString("NameCN")+"',");
				json.append("'code':'"+recordSet.getString("Code")+"',");
				json.append("'flag':"+flag+",");
				json.append("'info':'"+info+"'");
				json.append("}");
				num++;
			}
		}
		json.append("],'num':'"+num+"'");
		
	}else if("".equals(userIds) && "".equals(accountId)){	//所有当月的考勤汇总
		sql = "select k.gh,k.xm,h.NameCN from uf_kqhz k,uf_hr_userinfo1 h where k.xm = h.Name";
		recordSet.executeSql(sql);
		String resInfo = "";
		JSONObject resJson = null;
		int flag = 0;
		String info = "";
		toU9 t9 = null;
		while(recordSet.next()){
			userId = recordSet.getInt("xm");
			//if(userId != -1){
			t9 = new toU9();
			t9.setUserId(userId);
			t9.setMonth(month);
			t9.setYear(year);
			t9.setOperatorid(Integer.parseInt(operatorId));
			resInfo = t9.getJump();
			
			resJson = new JSONObject(resInfo);
			
			flag = (Integer)resJson.get("flag");
			info = (String)resJson.get("info"); 
		
			if(num>0) json.append(",");
			json.append("{");
			json.append("'id':'"+userId+"',");
			json.append("'namecn':'"+recordSet.getString("NameCN")+"',");
			json.append("'code':'"+recordSet.getString("gh")+"',");
			json.append("'flag':"+flag+",");
			json.append("'info':'"+info+"'");
			json.append("}");
			num++;
			//}
		}
		json.append("],'num':'"+num+"'");
	}
	
	json.append("}");
	
	out.print(json.toString());
	out.close();
%>