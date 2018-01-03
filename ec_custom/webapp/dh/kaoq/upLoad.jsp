<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.security.*,weaver.general.Util,weaver.hrm.settings.RemindSettings,weaver.file.Prop,weaver.rtx.RTXConfig" %>
<%@ page import="java.util.*" %>
<%@ page import="weaver.conn.RecordSetDataSource" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="weaver.hrm.User"%>
<%@ page import="weaver.hrm.HrmUserVarify"%>
<jsp:useBean id="toCollect" class="weaver.dh.interfaces.toCollect" scope="page"/>
<jsp:useBean id="toCollectMon" class="weaver.dh.interfaces.toCollectMon" scope="page"/>
<jsp:useBean id="toU9" class="weaver.dh.interfaces.toU9" scope="page"/>

<%	
User user = HrmUserVarify.getUser (request,response) ;
if(user == null){
    response.sendRedirect("/login/Login.jsp");
    return;
}
int userid  = user.getUID() ;
//dm.selectNewsDocInfo("", user,3, 1);
int index = 4;
String temp  = Util.null2String(request.getParameter("type"));
String zt  = Util.null2String(request.getParameter("zt"));
String hrid  = Util.null2String(request.getParameter("hrid"));
String date1  = Util.null2String(request.getParameter("date1"));
String date2  = Util.null2String(request.getParameter("date2"));
System.out.println(date2+"@@");
if("1".equals(temp)){	//
  toCollect.getJump(zt,hrid,date1,date2);
}else if("2".equals(temp)){	//
  toCollectMon.getJump(zt,hrid,date1,userid);
}else if("3".equals(temp)){//
  toU9.getJump();
}
	out.println("");	
%>