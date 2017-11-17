package dinghan.zrac.hr.actions.neeactions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.zrac.hr.dao.needao.impl.NEECheckTarget_1STMonth_DaoImpl;
import dinghan.zrac.hr.dao.needao.impl.NEECheckTarget_2NDMonth_DaoImpl;
import dinghan.zrac.hr.dao.needao.impl.NEECheckTarget_3RDMonth_DaoImpl;
import dinghan.zrac.hr.service.neeservice.impl.NEECheckTarget_Month_ServiceImpl;
import dinghan.zrac.hr.util.neeutil.NeeCheckTargetUtil;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

public class UpdateNEEMonthTargetDataAction implements Action {

	private NeeCheckTargetUtil util_month_1 = new NeeCheckTargetUtil(new NEECheckTarget_Month_ServiceImpl(
			new NEECheckTarget_1STMonth_DaoImpl()));
	
	private NeeCheckTargetUtil util_month_2 = new NeeCheckTargetUtil(new NEECheckTarget_Month_ServiceImpl(
			new NEECheckTarget_2NDMonth_DaoImpl()));
			
	private NeeCheckTargetUtil util_month_3 = new NeeCheckTargetUtil(new NEECheckTarget_Month_ServiceImpl(
			new NEECheckTarget_3RDMonth_DaoImpl()));
	
	private RequestManager requestManager;
	
	private String requsetName;
	
	private Log log = LogFactory.getLog(UpdateNEEMonthTargetDataAction.class.getName());
	
	@Override
	public String execute(RequestInfo requestInfo) {
		
		this.requestManager = requestInfo.getRequestManager();
		//requestManager.getRequestid();
		//requestManager.getBillId();
		
		this.requsetName = requestManager.getRequestname();
		
		this.updateAllMonthTargetData(requestManager.getBillid());
		
		return "1";
	}
	
	/**
	 * 更新全部月度中
	 * @param billId
	 */
	private void updateAllMonthTargetData(int billId){
		
		log.error("开始更新 标题 为  " + requsetName + " 流程 的月度考核目标");
		
		util_month_1.updateAllNEECheckTargetMonthDTData(billId);

		util_month_2.updateAllNEECheckTargetMonthDTData(billId);

		util_month_3.updateAllNEECheckTargetMonthDTData(billId);
		
	}
	
}
