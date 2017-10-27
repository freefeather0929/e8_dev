package dinghan.workflow.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import dinghan.workflow.beans.QingJia;
import dinghan.workflow.beans.QingJia0;

public class CreateLeaveDetail implements Action {
	private Log log = LogFactory.getLog(CreateLeaveDetail.class.getName());
	
	/**
	 * 生成明细
	 */
	@Override
	public String execute(RequestInfo request) {
		
		String returninfo = FAILURE_AND_CONTINUE;
		try {
			String requestid = request.getRequestid();  //得到请求id
			QingJia0 qj_main = new QingJia0(requestid);	//得到主表信息
			int mainid = qj_main.getId();
			QingJia.delete(mainid,0);	//删除明细表三的数据
			List<QingJia> qingjia_3_List = QingJia.generateFromQingJia_1_List(mainid);
			
			for(QingJia q : qingjia_3_List){
				q.insert();
			}
				
			returninfo = Action.SUCCESS;

		} catch (Exception e) {
			log.error("生成明细失败" + e);
		}
		return returninfo;
	}
}