package dinghan.zrac.hr.actions.kpiactions;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kpi.entity.KPITargetDetail;
import dinghan.zrac.hr.dao.kpidao.ZRKPITargetDetailDao;
import dinghan.zrac.hr.dao.kpidao.impl.ZRKPI1stMonthSummaryDaoImpl;
import dinghan.zrac.hr.dao.kpidao.impl.ZRKPI2ndMonthSummaryDaoImpl;
import dinghan.zrac.hr.dao.kpidao.impl.ZRKPITargetDetailDaoImpl;
import dinghan.zrac.hr.entity.kpientity.ZRKPIMonthSummary;
import dinghan.zrac.hr.service.kpiservice.ZRKPIMonthSummaryService;
import dinghan.zrac.hr.service.kpiservice.impl.ZRKPIMonthSummaryServiceImpl;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

/**
 * 功能：绩效考核电子流，自动填写月度总结日期
 * @author @author zhangxiaoyu/10593 - 2017-12-28
 * @version 1.0
 * 
 */
public class ZRCopyKpiDTToMSDTAction implements Action{
	private Log log = LogFactory.getLog(ZRCopyKpiDTToMSDTAction.class.getName());
	
	private ZRKPIMonthSummaryService zrKPIMonthSummaryDao_1st = new ZRKPIMonthSummaryServiceImpl(new ZRKPI1stMonthSummaryDaoImpl());
	private ZRKPIMonthSummaryService zrKPIMonthSummaryDao_2nd = new ZRKPIMonthSummaryServiceImpl(new ZRKPI2ndMonthSummaryDaoImpl());
	private ZRKPITargetDetailDao zrKPITargetDetailDao = new ZRKPITargetDetailDaoImpl();
	
	private RequestManager requestManager;	
	private int billId;
	
	@Override
	public String execute(RequestInfo request) {
		this.requestManager= request.getRequestManager();
		this.billId = requestManager.getBillid();
		this.fillKPIDTToMonthSammaryDT(billId);
		return Action.SUCCESS;
	}
	
	private void fillKPIDTToMonthSammaryDT(int billId){
		this.deleteUnExistKPIDTSummary(billId, zrKPIMonthSummaryDao_1st);
		this.deleteUnExistKPIDTSummary(billId, zrKPIMonthSummaryDao_2nd);
		
		List<KPITargetDetail> kpITargetDetailList = zrKPITargetDetailDao.queryAllByMianId(billId);
		
		if(kpITargetDetailList != null){
			for(KPITargetDetail t : kpITargetDetailList){
				
				ZRKPIMonthSummary s1 = zrKPIMonthSummaryDao_1st.queryByKPIDTId(t.getId());
				ZRKPIMonthSummary s2 = zrKPIMonthSummaryDao_1st.queryByKPIDTId(t.getId());
				if(s1 != null){
					updateKPISummary(zrKPIMonthSummaryDao_1st,t,s1);
				}else{
					addKPISummary(zrKPIMonthSummaryDao_1st,t);
				}
				if(s2 != null){
					updateKPISummary(zrKPIMonthSummaryDao_1st,t,s2);
				}else{
					addKPISummary(zrKPIMonthSummaryDao_2nd,t);
				}
			}
		}
	}
	
	/**
	 * 删除不存在对应KPI指标明细数据的月度总结数据
	 *  -- KPI指标明细有可能被删除
	 * @param billid
	 * @param zrKPIMonthSummaryDao
	 */
	private void deleteUnExistKPIDTSummary(int billId, ZRKPIMonthSummaryService zrKPIMonthSummaryDao){
		List<ZRKPIMonthSummary> zrKPIMonthSummaryList = zrKPIMonthSummaryDao.queryAllByMainId(billId);
		if(zrKPIMonthSummaryList != null){
			for(ZRKPIMonthSummary s : zrKPIMonthSummaryList){
				KPITargetDetail t = zrKPITargetDetailDao.queryById(s.getKpidtid());
				if(t == null){
					zrKPIMonthSummaryDao.deleteById(s.getId());
				}
			}
		}
	}
	
	/**
	 * 更新月度总结数据
	 * @param zrKPIMonthSummaryDao
	 * @param t
	 * @param s
	 */
	private void updateKPISummary(ZRKPIMonthSummaryService zrKPIMonthSummaryDao, KPITargetDetail t, ZRKPIMonthSummary s){
		s.setKpitarget(t.getD2_kpitarget());
		s.setKpistandard(t.getD2_kpistandard());
		s.setKpiweight(t.getD2_kpiweight());
		s.setSumdate("");
		//s.setSumpsn(-1);
		s.setKpidtid(t.getId());
		zrKPIMonthSummaryDao.update(s);
	}
	
	/**
	 * 新增月度总结数据
	 * @param zrKPIMonthSummaryDao
	 * @param t
	 */
	private void addKPISummary(ZRKPIMonthSummaryService zrKPIMonthSummaryDao, KPITargetDetail t){
		ZRKPIMonthSummary s = new ZRKPIMonthSummary();
		//s.setKpidtid(t.getId());
		s.setMainid(billId);
		s.setKpitarget(t.getD2_kpitarget());
		s.setKpistandard(t.getD2_kpistandard());
		s.setKpiweight(t.getD2_kpiweight());
		s.setSummary("");
		s.setSumdate("");
		//s.setSumpsn(-1);
		zrKPIMonthSummaryDao.add(s);
	}
}
