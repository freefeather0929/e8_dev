package dinghan.workflow.kq.userinfo.offdays;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.kq.appdata.dao.ZRQingJiaAppDataDao;
import dinghan.workflow.kq.kqdt.dao.ZRQingJiaCheckDTDao;
import dinghan.workflow.kq.summary.entity.KQSummaryData;
import dinghan.workflow.kq.summary.service.KQSummaryDataService;
import dinghan.workflow.kq.summary.service.impl.KQSummaryDataServiceImpl;
import dinghan.workflow.kq.userinfo.UserInfoDao;
import dinghan.workflow.kq.userinfo.UserInfoDaoImpl;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
import weaver.conn.RecordSet;
/**
 * 年休假操作
 * @author zhangxiaoyu / 10593 - 2017-11-23
 * 
 */
public class AnnualDaysHaddle implements AnnualDays {
	//private Log log = LogFactory.getLog(AnnualDaysHaddle.class.getName());
	//private ZRJiaBanCheckDTService zrCheckDTSerivce = new ZRJiaBanCheckDTServiceImpl();
	private KQSummaryDataService kqSummaryDataService = new KQSummaryDataServiceImpl();
	private UserInfoDao userInfoDao = new UserInfoDaoImpl();
	@Override
	public double queryCurrentLeftHour(int userId, String curMonth, String appNo) {
		UserInfo userInfo = null;
		String preMonth = CalendarUtil.moveDate(curMonth+"-01", 0, -1, 0).substring(0, 7);
		KQSummaryData preMonthSummary = kqSummaryDataService.queryByUserIDAndMonth(userId, preMonth);
		String userWorkcode = this.getUserWorkcode(userId);
		double preMonthSyNianXiu = 0.0d;
		double nianxiuHourUsed = 0.0d;
		
		if(userWorkcode!=null){
			userInfo = userInfoDao.queryByCode(userWorkcode);
		}
		
		if(preMonthSummary!=null){
			preMonthSyNianXiu = preMonthSummary.getSynx();
			//log.error("");
		}else if(userInfo != null){
			preMonthSyNianXiu = userInfo.getSYTiaoXiuJia()>-1?userInfo.getSYNianXiuJia():0;
		}
		
		nianxiuHourUsed = this.queryUsedHourCurMonth(userId, curMonth, appNo);
		
		return preMonthSyNianXiu - nianxiuHourUsed;
	}

	@Override
	public double queryUsedHourCurMonth(int userId, String month, String appNo) {
		double usedHour = 0.0d;
		String sql = " select "
					+" m.oddnum, "
						+" d.mainid, "
							+" d.id, "
								+" d.leavecategory, "
									+" d.leavecategoryid, "
										+" d.checkeddate, "
											+" d.leavehour "
				+" from "
					+ ZRQingJiaAppDataDao.ZRQingJiaAppDataFormName + " m, "
						+ ZRQingJiaCheckDTDao.ZRQingJiaCheckDTDataFormName + " d "
				+" where "
					+"d.mainid = m.id "
						+" and d.leavecategoryid = " + ZR_ANNUAL_LEAVETYPE
							+" and d.checkeddate like '"+month+"%' "
								+" and m.applicant = " + userId
									+" and m.oddnum <> '' "
										+" and m.oddnum <> '"+appNo+"' ";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			usedHour += rs.getDouble("leavehour")>-1?rs.getDouble("leavehour"):0;
		}
		return usedHour;
	}
	
	/**
	 * 获取员工工号
	 * @param userId
	 * @return
	 */
	private String getUserWorkcode(int userId){
		String workcode = null;
		String sql = "select workcode from HrmResource where id = " + userId;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);		
		
		while(rs.next()){
			workcode = rs.getString("workcode");
		} 
		
		return workcode;
	}
}
