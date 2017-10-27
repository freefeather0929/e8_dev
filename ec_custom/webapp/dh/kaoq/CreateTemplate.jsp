
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="weaver.general.Util" %>
<jsp:useBean id="rs" class="weaver.conn.RecordSet" scope="page" />

<%@ include file="/systeminfo/init_wev8.jsp" %>
<!DOCTYPE html><HEAD>
<script src="/js/tabs/jquery.tabs.extend_wev8.js"></script>
<link type="text/css" href="/js/tabs/css/e8tabs1_wev8.css" rel="stylesheet" />
<link rel="stylesheet" href="/css/ecology8/request/searchInput_wev8.css" type="text/css" />
<script type="text/javascript" src="/js/ecology8/request/searchInput_wev8.js"></script>

<link rel="stylesheet" href="/css/ecology8/request/seachBody_wev8.css" type="text/css" />
<link rel="stylesheet" href="/css/ecology8/request/hoverBtn_wev8.css" type="text/css" />
<script type="text/javascript" src="/js/ecology8/request/hoverBtn_wev8.js"></script>
<script type="text/javascript" src="/js/ecology8/request/titleCommon_wev8.js"></script>

<script type="text/javascript" src="/wui/theme/ecology8/jquery/js/zDrag_wev8.js"></script>
<script type="text/javascript" src="/wui/theme/ecology8/jquery/js/zDialog_wev8.js"></script>
<SCRIPT language="javascript" src="/js/datetime_wev8.js"></script>
<SCRIPT language="javascript" src="/js/selectDateTime_wev8.js"></script>
<SCRIPT language="javascript" src="/js/JSDateTime/WdatePicker_wev8.js"></script>
<LINK href="/wui/theme/ecology8/jquery/js/e8_zDialog_btn_wev8.css" type=text/css rel=STYLESHEET>
<%
RCMenu += "{"+SystemEnv.getHtmlLabelName(86,user.getLanguage())+",javascript:submitData(),mainFrame} " ;//保存
RCMenuHeight += RCMenuHeightStep ;
RCMenu += "{"+SystemEnv.getHtmlLabelName(309,user.getLanguage())+",javascript:closePrtDlgARfsh("+templateid+"),mainFrame} " ;//关闭
RCMenuHeight += RCMenuHeightStep ;
%>
<script type="text/javascript">
 
</script>
</head>
<BODY scroll="no">
	<div class="e8_box demo2">
		<div class="e8_boxhead">
			<div class="div_e8_xtree" id="div_e8_xtree"></div>
	        <div class="e8_tablogo" id="e8_tablogo"></div>
			<div class="e8_ultab">
				<div class="e8_navtab" id="e8_navtab">
					<span id="objName" title="<%=SystemEnv.getHtmlLabelName(16388 ,user.getLanguage()) %>"></span>
				</div>
				<div>	
			 
	      <div id="rightBox" class="e8_rightBox">
	    </div>
	   </div>
	  </div>
	</div>	
	
	    <div class="tab_box">
	        <div>
	            <FORM id=frmain name=frmain action="/formmode/template/SaveTemplateOperation.jsp" method=post >
					<input type="hidden" name="method" value="saveTemplate" />
					<input type="hidden" name="customid" value="" />
					<input type="hidden" name="sourcetype" value="" />
					<div style="margin-top:30px!important;">
					<wea:layout type="2col">
						<wea:group context="" attributes="{'groupDisplay':'none'}">
							<wea:item attributes="{'customAttrs':'nowrap=true'}">开始时间</wea:item>
							<wea:item>
								<wea:required id="templatename_span" required="true">
									<input id=templatename  name=templatename size="30" onchange="checkinput('templatename','templatename_span')" value=""  >
								</wea:required>
							</wea:item>
							<wea:item attributes="{'customAttrs':'nowrap=true'}">结束时间</wea:item>
							<wea:item>
								<button type=button  class="<%=classStr %>" onclick="onSearchWFQTDate(endDspan,endD,'')" ></button>
								<input type=hidden name="endD" id="endD" value="">
								<span name="endDspan" id="endDspan"></span>
							</wea:item>
							
						</wea:group>
					</wea:layout>
					</div>
				</FORM>
	        </div>
	    </div>
	</div>  
		<div id="zDialog_div_bottom" class="zDialog_div_bottom">
			<wea:layout type="2col">
				<wea:group context="">
					<wea:item type="toolbar"><!-- 关闭 -->
						<input type="button"
							value="<%=SystemEnv.getHtmlLabelName(309, user.getLanguage())%>"
							id="zd_btn_cancle" class="zd_btn_cancle" onclick="closePrtDlgARfsh('<%=templateid %>')">
					</wea:item>
				</wea:group>
			</wea:layout>
	   </div>
</body>
</html>

<script type="text/javascript">
function closeWinAFrsh(templateid){
	var parentWin = parent.getParentWindow(window);
	parentWin.closeDlgARfsh(templateid);
}
function onSearchWFQTDate(spanname,inputname,inputname1){
	var oncleaingFun = function(){
		  $(spanname).innerHTML = '';
		  inputname.value = '';
		}
		WdatePicker({el:spanname,onpicked:function(dp){
			var returnvalue = dp.cal.getDateStr();
			$dp.$(inputname).value = returnvalue;
			},oncleared:oncleaingFun});
}
</script>
