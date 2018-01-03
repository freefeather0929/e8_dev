package dinghan.workflow.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import dinghan.workflow.beans.Abnormal;

public class YcGenerateDetail implements Action {
	private Log log = LogFactory.getLog(YcGenerateDetail.class.getName());

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	@SuppressWarnings("finally")
	@Override
	public String execute(RequestInfo request) {
		String returninfo = FAILURE_AND_CONTINUE;
		try {
			String requestid = request.getRequestid();  //得到requestid
			int requestId = Integer.parseInt(requestid);
			Abnormal.update(requestId, 1);  //修改考勤异常的核定状态为1，此时异常信息有效
			returninfo = Action.SUCCESS;
		} catch (Exception e) {
			log.error("修改异常核定状态出错：" + e);
		} finally {
			return returninfo;
		}
	}
}
