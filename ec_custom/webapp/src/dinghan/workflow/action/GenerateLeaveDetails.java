package dinghan.workflow.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import dinghan.workflow.beans.PublicVariant;
import dinghan.workflow.beans.QJtemp;
import dinghan.workflow.beans.QingJia;
import dinghan.workflow.beans.QingJia0;
import dinghan.workflow.beans.UserInfo;

public class GenerateLeaveDetails implements Action {
	private Log log = LogFactory.getLog(GenerateLeaveDetails.class.getName());
	
	/**
	 * 生成明细
	 */
	@SuppressWarnings("finally")
	@Override
	public String execute(RequestInfo request) {
		
		String returninfo = FAILURE_AND_CONTINUE;
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//SimpleDateFormat sdf1 = new SimpleDateFormat("EEEE");
		try {
			String requestid = request.getRequestid();// 得到请求id
			int nodeid = request.getRequestManager().getNodeid();	// 得到节点状态
			QingJia0 qj_main = new QingJia0(requestid);	// 得到主表信息
			int userid = qj_main.getProposer();
			int mainid = qj_main.getId();
			String code = "-1";
			
			String sqlCode = "select workcode from HrmResource where id = " + userid;

			RecordSet recordset = new RecordSet();
			
			recordset.executeSql(sqlCode);
			
			while(recordset.next()){
				code = recordset.getString("workcode");
			}
			
			int type = 0;	// 0未核定， 1系统核定 2考勤员复核
			if (580 == nodeid) {

				UserInfo userInfo = new UserInfo(userid,code);	// 得到人员信息表信息
				log.error("userInfo :: id :: "+userInfo.getName());
				QingJia.delete(mainid,0);	// 删除明细表三的数据
				List<QingJia> qingjia_3_List = QingJia.generateFromQingJia_1_List(mainid);
				
				for(QingJia q : qingjia_3_List){
					q.insert();
				}
				
			} else if (587 == nodeid) {		//587考勤员复核节点
				type = 2;
				QingJia.update(mainid, type);	//更新请假明细表三的核定状态 将状态修改为2
				ArrayList<QingJia> qj_threeList = QingJia.queryList(mainid);// 得到明细表list
				String nowDate = PublicVariant.DateToStr(new Date(),"YYYY-MM-dd");
				String nowMon = nowDate.substring(5, 7);	//核定月份
				QJtemp.delete(mainid);	//删除请假中间表信息
				
				for(int i = 0; i < qj_threeList.size(); i++){

					QingJia qj_three = qj_threeList.get(i);
					QJtemp qJtemp = new QJtemp();
					qJtemp.setFlowno(qj_main.getAppnom());
					qJtemp.setHdgs(String.valueOf(qj_three.getHdgs()));
					qJtemp.setHdjssj(qj_three.getHdjssj());
					qJtemp.setHdkssj(qj_three.getHdkssj());
					qJtemp.setHdyf(nowMon);
					qJtemp.setHrmid(qj_three.getUserid());
					qJtemp.setHrmno(qj_three.getGh());
					qJtemp.setKqr(qj_three.getKqy());
					qJtemp.setMainid(String.valueOf(qj_three.getMainid()));
					qJtemp.setQjlx(Integer.parseInt(qj_three.getQjlb()));
					qJtemp.setQjrq(qj_three.getRq());
					qJtemp.setXq(qj_three.getXq());
					qJtemp.setDataid("");
					qJtemp.setWf_dt3_id(qj_three.getId());
					qJtemp.insert();	//插入请假中间表信息
				}
			} else {
				return returninfo;
			}
			returninfo = Action.SUCCESS;

		} catch (Exception e) {
			log.error("生成明细失败" + e);
		} finally {
			return returninfo;
		}
	}
}