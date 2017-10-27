<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dinghan.workflow.beans.UserInfo"%>
<%@page import="dinghan.workflow.beans.ChuChai"%>
<%@page import="dinghan.workflow.beans.ChuChai2"%>
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
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	ChuChai cc_main = new ChuChai(25094);	//通过requestid得到主表信息
	out.print(cc_main.getProposer());
	int userid = cc_main.getProposer();		//用户id
	int mainid = cc_main.getId(); 	//主表id

	UserInfo userInfo = new UserInfo(userid,"19278");	//得到用户信息

	ChuChai2.delete(mainid, 0);		//删除明细表二的数据

	Date dsDate = sdf.parse(cc_main.getYjccsj());
	Date deDate = sdf.parse(cc_main.getCcsj2());

	long dNum = (deDate.getTime() - dsDate.getTime()) / (1000 * 60 * 60 * 24) + 1;	//出差天数

	Calendar calendar = new GregorianCalendar();
	calendar.setTime(dsDate);
	
	for (int j = 0; j < dNum; j++) {
		
		calendar.add(Calendar.DATE, 1);
		String dsDateString = sdf.format(calendar.getTime());	// 明细日期字符串

		ChuChai2 cc_two = new ChuChai2();
		cc_two.setCcrq(dsDateString);
		cc_two.setMainid(mainid);
		cc_two.setHdzt(1);
		cc_two.setUserid(userid);
		cc_two.setHdjssj("");
		cc_two.setHdkssj("");
		cc_two.setHdzt(0);
		cc_two.setRow_id(j + 1);
		// 根据日期的不同进行区分插入
		if (dsDate.equals(deDate)) {// 当开始日期等于结束日期时
			cc_two.setYjkssj(cc_main.getCcsj1());
			cc_two.setYjjssj(cc_main.getCcsj3());
		} else { // 开始日期不等于结束日期时
			if (j == 0) { // 第一天
				cc_two.setYjkssj(cc_main.getCcsj1());
				cc_two.setYjjssj(userInfo.getEndWorkTime());
			} else if (j + 1 == dNum) {// 最后一天
				cc_two.setYjkssj(userInfo.getStartWorkTime());
				cc_two.setYjjssj(cc_main.getCcsj3());
			} else { // 中间
				cc_two.setYjkssj(Util.null2String(userInfo.getStartWorkTime(),"08:30:00") );
				cc_two.setYjjssj(Util.null2String(userInfo.getEndWorkTime(),"18:00:00"));
			}
		}
		cc_two.insert();
	}
	ChuChai2.checkList(5751);
	out.print(1);
	out.close();
%>