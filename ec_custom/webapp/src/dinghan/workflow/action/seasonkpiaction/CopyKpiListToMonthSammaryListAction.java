package dinghan.workflow.action.seasonkpiaction;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;
import dinghan.workflow.action.ChangeReDelStatueAction;
import dinghan.workflow.beans.seasonkpibeans.SeasonKpi;

/**
 * 功能：绩效考核电子流，将KPI目标列表中的目标、标准、权重复制到月度总结列表中
 * @author 张肖宇
 * @version 1.0
 * 
 */
public class CopyKpiListToMonthSammaryListAction implements Action {

	private RequestManager requestManager;	
	private Log log = LogFactory.getLog(ChangeReDelStatueAction.class.getName());
	
	@Override
	public String execute(RequestInfo request) {
		this.requestManager= request.getRequestManager();
		String requestid = request.getRequestid();
		String workflowId = request.getWorkflowid();
		int formid = requestManager.getFormid();
		CopyKLToMSL(requestid,workflowId,formid);
		return Action.SUCCESS;
	}

	public void CopyKLToMSL(String requestId,String workflowId,int formid){
		log.error("开始执行拷贝KPI项目到月度总结列表action，requestId = "+requestId+"; workFlowId = "+workflowId+"; formid = " +formid+ "...");
		
		String sql = "select top 1 id from formtable_main_" + Math.abs(formid) + " where requestId = "+ requestId;
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		int mainId = -1;
		
		while(rs.next()){
			mainId = rs.getInt("id");
		}
		
		//删除所有在月度总结列表中对应的明细
		sql = "delete from formtable_main_"+ Math.abs(formid) +"_dt3 where mainid = " + mainId;
		rs.executeSql(sql);
		sql = "delete from formtable_main_"+ Math.abs(formid) +"_dt4 where mainid = " + mainId;
		rs.executeSql(sql);
		
		sql = "select id,mainid,d2_kpitarget,d2_kpistandard,d2_kpiweight from formtable_main_"+ Math.abs(formid) +"_dt2 where mainid = "+mainId;
		
		rs.executeSql(sql);
		
		ArrayList<SeasonKpi> kpiList = new ArrayList<SeasonKpi>();
		
		while(rs.next()){
			SeasonKpi kpi = new SeasonKpi();
			kpi.setDtId(rs.getInt("id"));
			kpi.setMainId(rs.getInt("mainid"));
			kpi.setKpitarget(rs.getString("d2_kpitarget"));
			kpi.setStandard(rs.getString("d2_kpistandard"));
			kpi.setWeight(rs.getInt("d2_kpiweight"));
			kpiList.add(kpi);
		}
		
		for(int i=0;i<kpiList.size();i++){
			sql = "insert into formtable_main_"+ Math.abs(formid) +"_dt3 (mainid,d_m1_kpitarget,d_m1_kpistandard,d_m1_kpiweight)" +
					" values(" +
					"'"+ kpiList.get(i).getMainId() +"'," +
							"'"+kpiList.get(i).getKpitarget()+"'," +
									"'"+ kpiList.get(i).getStandard() +"'," +
											"'"+kpiList.get(i).getWeight()+"'" + 
												")";
			log.error("执行插入操作："+sql);
			rs.executeSql(sql);
			
			sql = "insert into formtable_main_"+ Math.abs(formid) +"_dt4 (mainid,d_m2_kpitarget,d_m2_kpistandard,d_m2_kpiweight)" +
					" values(" +
					"'"+ kpiList.get(i).getMainId() +"'," +
							"'"+kpiList.get(i).getKpitarget()+"'," +
									"'"+ kpiList.get(i).getStandard() +"'," +
											"'"+kpiList.get(i).getWeight()+"'" + 
												")";
			log.error("执行插入操作："+sql);
			rs.executeSql(sql);
		}
		
		log.error("执行拷贝KPI项目到月度总结列表action结束");
	}
}
