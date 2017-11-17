package dinghan.zrac.sc.actions.nswhaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.zrac.sc.util.nonswhutil.NonSWHTaskAppUtil;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

/**
 * 更新非标工时通知单明任务执行状态
 * @author zhangxiaoyu / 10593 - 2017-11-11
 * 
 */
public class UpdateNonSWHNoticeTasksExStatueAction implements Action {
	private Log log = LogFactory.getLog(UpdateNonSWHNoticeTasksExStatueAction.class.getName());
	private NonSWHTaskAppUtil util = new NonSWHTaskAppUtil();
	private RequestManager requestManager;
	@Override
	public String execute(RequestInfo requestInfo) {
		this.requestManager = requestInfo.getRequestManager();
		int billid = requestManager.getBillid();
		this.updateTaskExStatues(billid);
		return Action.SUCCESS;
	}
	
	/**
	 * 更新主流程和子流程中的任务执行状态
	 * @param billId - 非标工时任务执行流程的ID
	 */
	private void updateTaskExStatues(int billId){
		log.error("");
		util.updateMainWFTaskDTData(billId);
	}

}
