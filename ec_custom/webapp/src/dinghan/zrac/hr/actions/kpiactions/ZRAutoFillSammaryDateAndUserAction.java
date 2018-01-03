package dinghan.zrac.hr.actions.kpiactions;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.zrac.hr.dao.kpidao.impl.ZRKPI1stMonthSummaryDaoImpl;
import dinghan.zrac.hr.dao.kpidao.impl.ZRKPI2ndMonthSummaryDaoImpl;
import dinghan.zrac.hr.entity.kpientity.ZRKPIMonthSummary;
import dinghan.zrac.hr.entity.kpientity.ZRSeasonKPI;
import dinghan.zrac.hr.service.kpiservice.ZRKPIMonthSummaryService;
import dinghan.zrac.hr.service.kpiservice.ZRKPIService;
import dinghan.zrac.hr.service.kpiservice.impl.ZRKPIMonthSummaryServiceImpl;
import dinghan.zrac.hr.service.kpiservice.impl.ZRKPIServiceImpl;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

public class ZRAutoFillSammaryDateAndUserAction implements Action{
	private Log log = LogFactory.getLog(ZRAutoFillSammaryDateAndUserAction.class.getName());
	private ZRKPIService zrKPIService = new ZRKPIServiceImpl();
	private ZRKPIMonthSummaryService zrKPIMonthSummaryService_1st = new ZRKPIMonthSummaryServiceImpl(new ZRKPI1stMonthSummaryDaoImpl());
	private ZRKPIMonthSummaryService zrKPIMonthSummaryService_2nd = new ZRKPIMonthSummaryServiceImpl(new ZRKPI2ndMonthSummaryDaoImpl());
	private RequestManager requestManager;	
	
	public String execute(RequestInfo request) {
		this.requestManager= request.getRequestManager();
		//String requestid = request.getRequestid();
		requestManager.getBillId();
		int billId = requestManager.getBillid();
		int userId = requestManager.getUserId();
		
		fillSummaryDateAndUser(billId,userId);
		
		return Action.SUCCESS;
	}
	
	/**
	 * 自动填写总结日期与人员信息
	 * @param requestId
	 * @param formid
	 * @param userid
	 */
	private void fillSummaryDateAndUser(int billId,int userId){
		ZRSeasonKPI zrSeasonKPI = zrKPIService.queryByID(billId);
		//log.error("fillSummaryDateAndUser :: zrSeasonKPI.getMonthChecked() :: " + zrSeasonKPI.getMonthChecked());
		if(zrSeasonKPI.getMonthChecked() == 2){
			updateSummaryDateAndUser(billId,userId,zrKPIMonthSummaryService_1st);
		}else if(zrSeasonKPI.getMonthChecked() > -1){
			List<ZRKPIMonthSummary> zrKPIMonthSummaryList = zrKPIMonthSummaryService_1st.queryAllByMainId(billId);
			if(zrKPIMonthSummaryList.get(0).getSumpsn() > -1 && !"".equals(zrKPIMonthSummaryList.get(0).getSumdate()) ){
				updateSummaryDateAndUser(billId,userId,zrKPIMonthSummaryService_2nd);
			}else{
				updateSummaryDateAndUser(billId,userId,zrKPIMonthSummaryService_1st);
				updateSummaryDateAndUser(billId,userId,zrKPIMonthSummaryService_2nd);
			}
		}
	}
	
	private void updateSummaryDateAndUser(int billId, int userId, ZRKPIMonthSummaryService zrKPIMonthSummaryDao){
		List<ZRKPIMonthSummary> zrKPIMonthSummaryList = zrKPIMonthSummaryDao.queryAllByMainId(billId);
		if(zrKPIMonthSummaryList != null){
			for(ZRKPIMonthSummary s : zrKPIMonthSummaryList){
				s.setSumdate(CalendarUtil.getCurDate());
				s.setSumpsn(userId);
				zrKPIMonthSummaryDao.update(s);
			}
		}
	}
}
