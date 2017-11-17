package dinghan.workflow.kq.actions.genchecklist;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.kqdt.check.util.ZRChuChaiKQDTCheckUtil;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

/**
 * 重新核定出差明细
 * @author zhangxiaoyu / 10593 - 2017-10-29
 * 
 */
public class ReExZRChuChaiCheckAction implements Action {
	
	private Log log = LogFactory.getLog(ReExZRChuChaiCheckAction.class.getName());
	private RequestManager requestManager;	
	private ZRChuChaiKQDTCheckUtil zrChuChaiDTCheckUtil = new ZRChuChaiKQDTCheckUtil();
	//private ZRChuChaiCheckDTService zrCCCheckDTService = new ZRChuChaiCheckDTServiceImpl();
	@Override
	public String execute(RequestInfo requestInfo) {
		//zrChuChaiDTCheck.setCheckStatus(KQDTCheck.CHECKED);	//核定状态转化为已核定
		this.requestManager = requestInfo.getRequestManager();
		log.error("check DT Billid：： " + requestManager.getBillid());
		this.reCheckCheckDT(requestManager.getBillid());
		
		return Action.SUCCESS;
	}
	
	/**
	 * 重新核定考勤明细
	 * @param appId
	 */
	private void reCheckCheckDT(int appId){
		//if(zrCCCheckDTService.queryById(appId)!=null){
		zrChuChaiDTCheckUtil.executeCheckAll(appId);
		//}
	}

}
