package dinghan.workflow.action.financialaction;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.beans.ReceiptAccount;
import dinghan.workflow.beans.ReceiptList;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.Util;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

/**
 * 自动生成票据明细接口（发票、收据）
 * @author zhangxiaoyu - 10593
 * 修改：
 * 	1. 增加赋值申请人邮箱、电话字段
 */
public class AutoCreateReceiptAccountAction implements Action {

	private RequestManager requestManager;	
	private Log log = LogFactory.getLog(AutoCreateReceiptAccountAction.class.getName());
	
	@Override
	public String execute(RequestInfo request) {
		this.requestManager= request.getRequestManager();
		String lastOpter = request.getLastoperator();
		//log.error("最后操作人ID：：" + request.getLastoperator());
		String requestid = request.getRequestid();
		int formid = requestManager.getFormid();
		int workFlowId = requestManager.getWorkflowid();
		createReceiptList(requestid, formid ,workFlowId,lastOpter);
		return Action.SUCCESS;
	}
	
	public void createReceiptList(String requestid,int formid,int workFlowId,String lastOperatorId){
		//log.error("当前流程类型ID为：" + workFlowId);
		DecimalFormat df = new DecimalFormat("0.00");
		Map<String,String> userInfoMap;
		
		RecordSet rs = new RecordSet();
		String sql = "";
		if(workFlowId != 117){	// 117  - 鼎汉检测发票申请流程类别ID
			sql = "select id,appno,apppsn,hkdate,pjtype,kppsn,kpdate,contratype,unitname,apptotal from formtable_main_" + Math.abs(formid) + " where requestId = "+requestid;
		}else{
			sql = "select id,appno,apppsn,hkdate,pjtype,kppsn,kpdate,unitname,apptotal from formtable_main_" + Math.abs(formid) + " where requestId = "+requestid;
		}
		
		rs.executeSql(sql);
		ReceiptAccount ra = new ReceiptAccount();
		int wfMainId = -1; 
		//获取当前流程中要向发票模块写入的主表信息
		while(rs.next()){
			wfMainId = rs.getInt("id");
			//System.out.println(rs.getString("appno"));
			ra.setAppno(rs.getString("appno"));
			ra.setApppsn(rs.getInt("apppsn"));
			
			userInfoMap = getAppPsnInfo(rs.getInt("apppsn"));
			
			if(workFlowId != 117){
				ra.setContraType(rs.getInt("contratype"));
			}
			ra.setHkDate(rs.getString("hkdate"));
			ra.setKpDate(this.requestManager.getCurrentDate());
			ra.setKppsn(rs.getInt("kppsn"));
			ra.setKptotal(rs.getDouble("apptotal"));
			ra.setKpUnit(rs.getString("unitname"));
			ra.setPjType(rs.getInt("pjtype"));
			ra.setApppsnmobile(userInfoMap.get("mobile"));
			ra.setApppsnmail(userInfoMap.get("email"));
			ra.setRequestId(Integer.parseInt(requestid));
		}
		//获取发票明细集合
		sql = "select * from formtable_main_" + Math.abs(formid) + "_dt2 where mainid = " +wfMainId;
		rs.executeSql(sql);
		ArrayList<ReceiptList> rList = new ArrayList<ReceiptList>();
		while(rs.next()){
			ReceiptList r = new ReceiptList();
			r.setMainId(wfMainId);
			r.setCode(rs.getString("d_code"));
			r.setContraNo(rs.getString("d_contracode"));
			r.setContraMoney(rs.getDouble("d_contramoney"));
			r.setMoney(rs.getDouble("d_kpmoney"));
			r.setStatue(0);
			r.setMark(rs.getString("d_mark"));
			log.error("r.getContraMoney :: "+new BigDecimal(r.getMoney()).setScale(2,BigDecimal.ROUND_HALF_UP));
			log.error("r.getMoney :: "+new BigDecimal(r.getContraMoney()).setScale(2,BigDecimal.ROUND_HALF_UP));
			rList.add(r);
		}
		ra.setReceiptList(rList);
		
		String receiptFormName = "uf_invoiceaccount";
		int modeId = 18;  //技术发票及收据申请台账模块ID
		
		if(workFlowId != 117){ 
			receiptFormName = "uf_receiptaccount";  
			modeId = 32;  //检测发票及收据申请台账模块ID
		}
		
		sql = "select id from "+receiptFormName+" where requestId = " + requestid;
		rs.executeSql(sql);
		int raId = -1;
		if(rs.getCounts() > 0){
			if(rs.next()){
				raId = rs.getInt("id");
			}
			sql = "delete from "+receiptFormName+" where requestId = " + requestid;
			rs.executeSql(sql);
			sql = "delete from "+receiptFormName+"_dt1 where mainid = " + raId;
			rs.executeSql(sql);
		}
		//插入新的开票台账信息
		if(workFlowId != 117){
			sql = "insert into "+receiptFormName+" (requestId, appno, apppsn, apppsnmail,apppsnmobile, kpdate, pjtype, kppsn, hkdate, contratype, kpunit, kptotal, formmodeid, modedatacreater, modedatacreatertype, modedatacreatedate, modedatacreatetime)" +
					" values('"+requestid+"','"+ra.getAppno()+"','"+ra.getApppsn()+"','"+ra.getApppsnmail()+"','"+ra.getApppsnmobile()+"','"+ra.getKpDate()+"','"+ra.getPjType()+"','"+lastOperatorId+"','"+ra.getHkDate()+"','"+ra.getContraType()+"','"+ra.getKpUnit()+"','"+new BigDecimal(ra.getKptotal()).setScale(2,BigDecimal.ROUND_HALF_UP)+"','32','"+lastOperatorId+"','0','"+this.requestManager.getCurrentDate()+"','"+this.requestManager.getCurrentTime()+"')";
		}else{
			sql = "insert into "+receiptFormName+" (requestId, appno, apppsn, apppsnmail,apppsnmobile, kpdate, pjtype, kppsn, hkdate, kpunit, kptotal, formmodeid, modedatacreater, modedatacreatertype, modedatacreatedate, modedatacreatetime)" +
					" values('"+requestid+"','"+ra.getAppno()+"','"+ra.getApppsn()+"','"+ra.getApppsnmail()+"','"+ra.getApppsnmobile()+"','"+ra.getKpDate()+"','"+ra.getPjType()+"','"+lastOperatorId+"','"+ra.getHkDate()+"','"+ra.getKpUnit()+"','"+new BigDecimal(ra.getKptotal()).setScale(2,BigDecimal.ROUND_HALF_UP)+"','18','"+lastOperatorId+"','0','"+this.requestManager.getCurrentDate()+"','"+this.requestManager.getCurrentTime()+"')";
		}
		
		//log.error("执行：" + sql);
		
		rs.executeSql(sql);
		sql = "select id from "+receiptFormName+" where requestId ="+requestid;
		rs.executeSql(sql);
		int receiptAccountId = -1;
		
		while(rs.next()){
			receiptAccountId = rs.getInt("id");
		}
		double contraMenoy = 0.0d;
		double money = 0.0d;
		for(int i=0;i<ra.getReceiptList().size();i++){
			contraMenoy = ra.getReceiptList().get(i).getContraMoney();
			money = ra.getReceiptList().get(i).getMoney();
			sql = "insert into "+receiptFormName+"_dt1 (mainid, d_contracode, d_contramoney, d_code, d_kpmoney, d_kpstatus, d_mark)" +
					" values ('"+receiptAccountId+"','"+ra.getReceiptList().get(i).getContraNo()+"',"
							+ new BigDecimal(contraMenoy).setScale(2, BigDecimal.ROUND_HALF_UP) +","
								//+ "'" + df.format(ra.getReceiptList().get(i).getContraMoney()) +"',"
									+ "'" +ra.getReceiptList().get(i).getCode()+ "',"
											+ new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_UP)+","
												//+ df.format(ra.getReceiptList().get(i).getMoney())+","
													+ "'"+ra.getReceiptList().get(i).getStatue()+"',"
															+ "'"+ra.getReceiptList().get(i).getMark()+"')"; 
			rs.executeSql(sql);
		}
		//重置权限
		ModeRightInfo modeRightInfo = new ModeRightInfo();
		modeRightInfo.rebuildModeDataShareByEdit(Integer.parseInt(lastOperatorId), modeId, receiptAccountId);
	}
	
	private Map<String,String> getAppPsnInfo(int psnid){
		Map<String,String> userInfoMap = new HashMap<String, String>();
		String sql = "select top 1 id,lastname,mobile,email from HrmResource where id = " + psnid;
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		while(rs.next()){
			userInfoMap.put("mobile", Util.null2String(rs.getString("mobile")));
			userInfoMap.put("email", Util.null2String(rs.getString("email")));
		}
		return userInfoMap;
	}
}
