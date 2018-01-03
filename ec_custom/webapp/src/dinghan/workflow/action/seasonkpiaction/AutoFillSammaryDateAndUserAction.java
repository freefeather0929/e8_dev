package dinghan.workflow.action.seasonkpiaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

/**
 * 功能：绩效考核电子流，自动填写月度总结日期
 * @author 张肖宇
 * @version 1.0
 * 
 */
public class AutoFillSammaryDateAndUserAction implements Action {
	private RequestManager requestManager;	
	private Log log = LogFactory.getLog(AutoFillSammaryDateAndUserAction.class.getName());
	public String execute(RequestInfo request) {
		this.requestManager= request.getRequestManager();
		String requestid = request.getRequestid();
		int formid = requestManager.getFormid();
		int userid = requestManager.getUserId();
		
		FillSammaryDateAndUser(requestid,formid,userid);
		
		return Action.SUCCESS;
	}
	
	/**
	 * 自动填写总结日期与人员信息
	 * @param requestId
	 * @param formid
	 * @param userid
	 */
	private void FillSammaryDateAndUser(String requestId,int formid,int userid){
		//log.error("开始自动填写总结日期和人员...");
		
		String sql = "select top 1 id,monthchecked from formtable_main_" + Math.abs(formid) + " where requestId = "+ requestId;
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		int mainId = -1;
		int mmmark = -1;
		
		while(rs.next()){
			mainId = rs.getInt("id");
			mmmark = rs.getInt("monthchecked");
		}
		String curDate = requestManager.getCurrentDate();
		//获取当前日期
		//log.error("当前日期："+curDate);
		
		if(mmmark == 2){	//只更新第一个月的总结日期和人员ID
			sql = "update formtable_main_"+ Math.abs(formid) +"_dt3 set d_m1_sumpsn = '"+userid+"', d_m1_sumdate = '" + curDate + "' where mainid = "+mainId;
			//log.error("开始执行更新："+sql);
			rs.executeSql(sql);
		}else{
			//查看第一个月明细是否已经填写了月度总结日期和人员ID
			//sql = "select count(id) from formtable_main_"+ Math.abs(formid) +"_dt3 where d_m1_sumpsn <> '' where mainid = " + mainId;
			sql = "select count(id) from formtable_main_"+ Math.abs(formid) +"_dt3 where ISNULL(d_m1_sumpsn, '') <> ''  and mainid = " + mainId;
			if(rs.getInt(1) == 0){
				sql = "update formtable_main_"+ Math.abs(formid) +"_dt4 set d_m2_sumpsn = '"+userid+"', d_m2_sumdate = '" + curDate + "' where mainid = "+mainId;
				//log.error("开始执行更新："+sql);
				rs.executeSql(sql);
			}else{
				sql = "update formtable_main_"+ Math.abs(formid) +"_dt3 set d_m1_sumpsn = '"+userid+"', d_m1_sumdate = '" + curDate + "' where mainid = "+mainId;
				//log.error("开始执行更新："+sql);
				rs.executeSql(sql);
				sql = "update formtable_main_"+ Math.abs(formid) +"_dt4 set d_m2_sumpsn = '"+userid+"', d_m2_sumdate = '" + curDate + "' where mainid = "+mainId;
				//log.error("开始执行更新："+sql);
				rs.executeSql(sql);
			}
		}
		
		//log.error("自动填写总结日期和人员结束。");
	}
}
