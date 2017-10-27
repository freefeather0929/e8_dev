<%@page import="weaver.conn.RecordSet"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings,weaver.file.Prop,weaver.rtx.RTXConfig"%>
<%@ page import="java.lang.reflect.*"%>
<%@ page import="weaver.file.FileUpload"%>
<%@ page import="java.util.*"%>
<%@ page import="weaver.conn.RecordSetDataSource"%>
<%@page import="net.sf.json.*"%>
<%@ page import="javax.servlet.*"%>
<jsp:useBean id="recordSet" class="weaver.conn.RecordSet" scope="page" />
<jsp:useBean id="recordSet2" class="weaver.conn.RecordSet" scope="page" />
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
	
	String deptId = Util.null2String(request.getParameter("deptid"));
	
	
	String supDeptMark = "";
	int tlevel = 0;
	int deptLelel = 0;
	int supDeptId = 0;
	if(deptId != ""){
		String sql = "select top 1 id,departmentmark,departmentname,subcompanyid1,supdepid,tlevel from HrmDepartment where id ="+ deptId;
		recordSet.executeSql(sql);
		if(recordSet.first()){
			tlevel = recordSet.getInt("tlevel");
			supDeptId = recordSet.getInt("id");
			supDeptMark = recordSet.getString("departmentmark");
		}
		/* out.println("tlevel :: "+tlevel);
		out.println("supDeptId :: "+supDeptId);
		out.println("supDeptMark :: "+supDeptMark); */
		if(tlevel>2){
			int i = tlevel-2;
			while(i>-1){
				sql = "select top 1 id,departmentmark,departmentname,subcompanyid1,supdepid,tlevel from HrmDepartment where id ="+ deptId;
				recordSet2.executeSql(sql);
				if(recordSet2.first()){
					supDeptMark = recordSet2.getString("departmentmark");	
					supDeptId = recordSet2.getInt("id");	
					deptId = String.valueOf(recordSet2.getInt("supdepid"));	
				}
				//out.println("deptId :: "+(i)+" :: "+supDeptMark);
				i--;
			}
		}
		
		StringBuffer json = new StringBuffer();
		
		json.append("{'flag':'1',");
		json.append("'id':'"+supDeptId+"',");
		json.append("'departmentmark':'"+supDeptMark+"'");
		json.append("}");
		out.print(json.toString());
		out.flush();
		out.close();
		
	}else{
		out.close();
	}

%>