package dinghan.workflow.kq.actions.checkrepeat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.appdata.entity.ZRWaiChuAppData;
import dinghan.workflow.kq.appdata.util.ZRWaiChuCheckRepeatUtil;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

public class ZRCheckRepeatWaiChuAppAction implements Action {

	private RequestManager requestManager;	
	private Log log = LogFactory.getLog(ZRCheckRepeatWaiChuAppAction.class.getName());
	
	private ZRWaiChuCheckRepeatUtil checkUtil = new ZRWaiChuCheckRepeatUtil();
	
	
	@Override
	public String execute(RequestInfo requestInfo) {
		
		this.requestManager = requestInfo.getRequestManager();
		
		log.error("检测重复外出 ：： ID "+requestManager.getBillid());
		log.error("检测重复外出 ：： requestId "+requestManager.getRequestid());
		this.executeCheck(requestManager.getBillid());
		
		return Action.SUCCESS;
	}
	
	private void executeCheck(int appId){
		
		ZRWaiChuAppData repeatAppData = checkUtil.executeCheck(appId);
		
		if(repeatAppData!=null){
			this.requestManager.setMessageid("提示：");
			this.requestManager.setMessagecontent("存在与当前申请单申请时间段重叠的流程，单号为："+repeatAppData.getAppno()+",请检查！");
		}
		
	}

}
