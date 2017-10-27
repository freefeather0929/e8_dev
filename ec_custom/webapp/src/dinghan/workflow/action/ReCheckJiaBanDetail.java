package dinghan.workflow.action;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;
import dinghan.workflow.beans.Jbtemp;
import dinghan.workflow.beans.JiaBan;
import dinghan.workflow.beans.JiaBan1;
import dinghan.workflow.beans.PublicVariant;
import dinghan.workflow.beans.UserInfo;

public class ReCheckJiaBanDetail implements Action {
	private Log log = LogFactory.getLog(ReCheckJiaBanDetail.class.getName());
	private RequestManager requestManager;
	
	@Override
	public String execute(RequestInfo request) {
		
		requestManager = request.getRequestManager();
		
			String requestid = request.getRequestid();// 得到requestid
			
			//log.error("加班流程节点ID :: " + nodeid);
			JiaBan jb_main = new JiaBan(requestid);
			
			String userCode = "-1";
			String sqlCode = "";
			int userId = -1;
			
			userId = jb_main.getProposer();
			
			log.error("加班流程的申请人ID :: " + jb_main.getProposer());
			
			sqlCode = "select top 1 workcode from HrmResource where id = " + userId;
			
			log.error("sqlCode :: " + sqlCode);
			
			RecordSet rsHrm = new RecordSet();
			
			rsHrm.executeSql(sqlCode);
			
			while(rsHrm.next()){
				userCode = rsHrm.getString("workcode");
			}
			
			log.error("加班流程申请人的工号 :: " + userCode);
			
			//UserInfo userInfo = new UserInfo(jb_main.getProposer(),userCode);
			
			ArrayList<JiaBan1> jb_onelist = JiaBan1.queryList(jb_main.getId());		// 得到明细表list

			log.error("获取到明细数量：：" + jb_onelist.size());
			
			Jbtemp.delete(jb_main.getId());	//删除流程主表对应所有中间表数据
			
			log.error("删除中间表明细");
			
			for (int i = 0; i < jb_onelist.size(); i++) {
				JiaBan1 jb_one = jb_onelist.get(i);
				JiaBan1.update(jb_one, 0);	//将所有明细表核定状态回归0，待重新核定。
			}
			//log.error("核定加班流程 :: " + requestManager.getRequestname() + "结束。");
			return "1";
			//}
	}

}
