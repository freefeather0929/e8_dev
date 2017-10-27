package dinghan.workflow.action;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

public class AutoChangeDocToUnBrowAbleAction implements Action {

	private RequestManager requestManager;
	private Log log = LogFactory.getLog(ChangeReDelStatueAction.class.getName());
	
	@Override
	public String execute(RequestInfo request) {
		this.requestManager = request.getRequestManager();
		String requestid = request.getRequestid();
		int formid = requestManager.getFormid();
		String src = requestManager.getSrc();
		//int nNodeId = this.requestManager.getNextNodeid();
		int cNodeId = this.requestManager.getNodeid();
		
		AutoChangeDocStatueAction(requestid, formid, src, cNodeId);
		return Action.SUCCESS;
		
	}
	
	public void AutoChangeDocStatueAction(String requestId, int formid ,String requestType,int cNodeId){
		
		//System.out.println("当前节点ID ： " + cNodeId);
		//System.out.println("页面请求类型：" + this.requestManager.getSrc());
		
		RecordSet rs = new RecordSet();
		
		ArrayList<String> docIdList = new ArrayList<String>();
		
		String formName = "formtable_main_" + Math.abs(formid);
		String dtFormName = "formtable_main_" + Math.abs(formid) + "_dt1";
		
		String sql = "select id from " + formName +" where requestId = "+ requestId;
		
		int mainId = -1;
		
		rs.executeSql(sql);
		
		while(rs.next()){
			mainId = rs.getInt("id");
		}
		
		sql = "select * from " + dtFormName + " where mainid = " + mainId + " and d_scan = '0'";
		
		rs.executeSql(sql);
		
		while(rs.next()){
			docIdList.add(rs.getString("d_docname"));
		}
		
		int borwStatue = 1;	//借阅状态 ： 1 -- 在借；0 -- 可借
		
		if(docIdList.size() > 0){
			for(String docId : docIdList){
				sql = "update uf_documentlist set docbrowstatue = '" +borwStatue+ "' where id = "+docId;	
				rs.executeSql(sql);
			}
		}
		
	}

}
