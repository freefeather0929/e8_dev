package dinghan.workflow.kq.userinfo.offdays;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.kq.appdata.dao.ZRQingJiaAppDataDao;
import dinghan.workflow.kq.kqdt.dao.ZRQingJiaCheckDTDao;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRJiaBanCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRJiaBanCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRJiaBanCheckDTServiceImpl;
import dinghan.workflow.kq.summary.entity.KQSummaryData;
import dinghan.workflow.kq.summary.service.KQSummaryDataService;
import dinghan.workflow.kq.summary.service.impl.KQSummaryDataServiceImpl;
import dinghan.workflow.kq.userinfo.UserInfoDao;
import dinghan.workflow.kq.userinfo.UserInfoDaoImpl;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
import weaver.conn.RecordSet;

/**
 * 调休假操作
 * @author zhangxiaoyu / 10593 - 2017-11-23
 * 
 */
public class BallanceDaysHaddle implements BallanceDays {
	private Log log = LogFactory.getLog(BallanceDaysHaddle.class.getName());
	private ZRJiaBanCheckDTService zrJBCheckDTSerivce = new ZRJiaBanCheckDTServiceImpl();
	private KQSummaryDataService kqSummaryDataService = new KQSummaryDataServiceImpl();
	private UserInfoDao userInfoDao = new UserInfoDaoImpl();
	
	@Override
	public double queryCurrentLeftHour(int userId, String curMonth, String appNo) {
		UserInfo userInfo = null;
		String preMonth = CalendarUtil.moveDate(curMonth+"-01", 0, -1, 0).substring(0, 7);
		log.error("获取前月月份 :: " + preMonth);
		KQSummaryData preMonthSummary = kqSummaryDataService.queryByUserIDAndMonth(userId, preMonth);
		String userWorkcode = this.getUserWorkcode(userId);
		double preMonthSyTiaoXiu = 0.0;
		double tiaoxiuHourUsed = 0.0;
		double tiaoxiuHourAdded = 0.0;
		if(userWorkcode!=null){
			userInfo = userInfoDao.queryByCode(userWorkcode);
		}
		
		if(preMonthSummary!=null){
			preMonthSyTiaoXiu = preMonthSummary.getSytx();
			log.error("前月剩余调休假 :: " + preMonthSummary.getSytx());
		}else if(userInfo != null){
			preMonthSyTiaoXiu = userInfo.getSYTiaoXiuJia()>-1?userInfo.getSYTiaoXiuJia():0;
		}
		
		tiaoxiuHourUsed = this.queryUsedHourCurMonth(userId, curMonth, appNo);
		tiaoxiuHourAdded = this.queryAddHourbyOverTime(userId, preMonth);
		
		return preMonthSyTiaoXiu + tiaoxiuHourAdded - tiaoxiuHourUsed;
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
						+" and d.leavecategoryid = " + ZR_BALLANCE_LEAVETYPE
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

	@Override
	public double queryAddHourbyOverTime(int userId, String month) {
		double addHour = 0.0d;
		List<ZRJiaBanCheckDTData> zrJBCheckDTDataList = null;
		
		String startDate = month + "-01";
		String endDate = month + "-31";
		
		while(startDate.compareTo(endDate)<=0){
			zrJBCheckDTDataList = zrJBCheckDTSerivce.queryByUserIDAndDate(userId, startDate);
			if(zrJBCheckDTDataList!=null){
				for(ZRJiaBanCheckDTData data : zrJBCheckDTDataList){
					if(isValidJiaBan(data.getMainid())){
						if(data.getWhetherturnoff() == 1 && startDate.equals(data.getWfenddate())){
							addHour += data.getCheckedhour()>-1?data.getCheckedhour():0;
						}
					}
				}
			}
			startDate = CalendarUtil.moveDate(startDate, 0, 0, 1);
		}
		return addHour;
	}
	
	/**
	 * 判断加班是否有效
	 * @param jiabanBillID
	 * @return
	 */
	private boolean isValidJiaBan(int jiabanBillID){
		boolean mark = false;
		int nodeType = 0;
		String sql = "select top 1 currentnodetype from workflow_requestbase where requestid = " + jiabanBillID;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			nodeType = rs.getInt("currentnodetype");
		}
		
		if(nodeType == 3){
			mark = true;
		}
		
		return mark;
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
