package dinghan.workflow.action.financialaction;
 
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.action.ChangeReDelStatueAction;
import dinghan.workflow.beans.ReceiptList;
import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

public class AutoCopyReceiptListAction implements Action {
	
	private RequestManager requestManager;	
	private Log log = LogFactory.getLog(ChangeReDelStatueAction.class.getName());
	
	@Override
	public String execute(RequestInfo request) {
		this.requestManager = request.getRequestManager();
		String requestId = request.getRequestid();
		int formid = requestManager.getFormid();
		copyReiptList(requestId,formid);
		log.error("CurrentOperator :: "+this.requestManager.getCurrentOperator());
		log.error("Lastoperator :: "+this.requestManager.getLastoperator());
		log.error("Creater :: "+this.requestManager.getCreater());
		
		return Action.SUCCESS;
	}
	
	public void copyReiptList(String requestid,int formid){
		log.error("开始自动复制票据台账信息到明细表中...");
		RecordSet rs = new RecordSet();
		
		int wfMainId = -1;
		
		String sql = "select id from formtable_main_" + Math.abs(formid) + " where requestId = "+requestid;
		
		rs.executeSql(sql);
		
		while(rs.next()){
			wfMainId = rs.getInt("id");
		}
		
		sql = "delete from formtable_main_"+ Math.abs(formid) +"_dt2 where mainid = "+wfMainId;
		
		rs.executeSql(sql);
		
		sql = "select mainid,d_contracode,d_contramenoy,d_appmoney from formtable_main_"+ Math.abs(formid) +"_dt1 where mainid = "+wfMainId;
		
		rs.executeSql(sql);
		
		ArrayList<ReceiptList> rList = new ArrayList<ReceiptList>();
		
		while(rs.next()){
			ReceiptList r = new ReceiptList();
			r.setMainId(wfMainId);
			r.setCode("");  
			r.setContraNo(rs.getString("d_contracode"));
			r.setContraMoney(rs.getDouble("d_contramenoy"));
			r.setMoney(rs.getDouble("d_appmoney"));
			r.setStatue(0);	//默认为有效
			r.setMark("");
			rList.add(r);
		}
		
		for(int i=0;i<rList.size();i++){
			sql = "insert into formtable_main_"+ Math.abs(formid) +"_dt2 (mainid, d_contracode, d_contramoney, d_code, d_kpmoney, d_kpstatus, d_mark)" +
					" values('"+rList.get(i).getMainId()+"','"+rList.get(i).getContraNo()+"','"+rList.get(i).getContraMoney()+"','"+rList.get(i).getCode()+"','"+rList.get(i).getMoney()+"','"+rList.get(i).getStatue()+"','"+rList.get(i).getMark()+"')";
			rs.executeSql(sql);
		}
		
		log.error("自动复制票据台账信息到明细表结束...");
	}

}
