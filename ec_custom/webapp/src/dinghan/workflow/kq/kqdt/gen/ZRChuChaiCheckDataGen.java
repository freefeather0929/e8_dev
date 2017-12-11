package dinghan.workflow.kq.kqdt.gen;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.com.selectitem.SelectItemInfoImpl;
import dinghan.workflow.com.selectitem.timeitem.ZRChuChaiTimeSelect;
import dinghan.workflow.com.selectitem.timeitem.ZRChuChaiTimeSelectImpl;
import dinghan.workflow.kq.appdata.entity.ZRChuChaiAppData;
import dinghan.workflow.kq.appdata.service.ZRChuChaiAppDataService;
import dinghan.workflow.kq.appdata.service.impl.ZRChuChaiAppDataServiceImpl;
import dinghan.workflow.kq.holiday.dao.HolidayConfigDao;
import dinghan.workflow.kq.holiday.dao.HolidaySelectDao;
import dinghan.workflow.kq.holiday.dao.impl.HolidayConfigDaoImpl;
import dinghan.workflow.kq.holiday.dao.impl.HolidaySelectDaoImpl;
import dinghan.workflow.kq.holiday.entity.HolidayConfig;
import dinghan.workflow.kq.kqdt.check.KQDTCheck;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRChuChaiCheckDTData;
import dinghan.workflow.kq.userinfo.UserInfoDao;
import dinghan.workflow.kq.userinfo.UserInfoDaoImpl;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * 中车出差核定明细数据生成类
 * @author zhangxiaoyu / 10593 - 2017-10-29
 * 
 */
public class ZRChuChaiCheckDataGen extends KQCheckDataGen<ZRChuChaiCheckDTData> {
	private Log log = LogFactory.getLog(ZRChuChaiCheckDataGen.class.getName());
	
	private UserInfoDao userInfodao = new UserInfoDaoImpl();
	private ZRChuChaiAppDataService zrChuChaiAppDataService = new ZRChuChaiAppDataServiceImpl();
	private ZRChuChaiTimeSelect zrCCTimeSelect_start = 
			new ZRChuChaiTimeSelectImpl(ZRChuChaiTimeSelect.ZRChuChaiPreStartTimeFieldName);
	private ZRChuChaiTimeSelect zrCCTimeSelect_end = 
			new ZRChuChaiTimeSelectImpl(ZRChuChaiTimeSelect.ZRChuChaiPreEndTimeFieldName);
	private HolidaySelectDao holidaySelectDao = 
			new HolidaySelectDaoImpl(new SelectItemInfoImpl());
	HolidayConfigDao holidayConfigDao = new HolidayConfigDaoImpl();
	
	/**
	 * 思路： 
	 * <p>
	 * 1. 通过id获取出差申请单的数据
	 * <p>
	 * 2. 开始日期 至 结束日期，进行循环，每个日期创建一个核定明细
	 * 
	 */
	@Override
	public List<ZRChuChaiCheckDTData> createCheckData(int mainid) {
		
		List<ZRChuChaiCheckDTData> zrCCCheckDTDataList = null;
		ZRChuChaiCheckDTData zrChuChaiCheckDTData = null;
		
		ZRChuChaiAppData zrChuChaiAppData = zrChuChaiAppDataService.queryByID(mainid);
		
		String startDate;
		String endDate;
		
		String startTime;
		String endTime;
		
		String stStartTime = "08:00";
		String stEndTime = "17:30";
		
		UserInfo userInfo = userInfodao.queryByCode(this.getUserWorkCode(zrChuChaiAppData.getAppPsn()));
		if(userInfo != null){
			stStartTime = userInfo.getStartWorkTime();
			stEndTime = userInfo.getEndWorkTime();
		}
		
		HolidayConfig holidayConfig;
		
		if(zrChuChaiAppData!=null){
			zrCCCheckDTDataList = new ArrayList<ZRChuChaiCheckDTData>();
			startDate = zrChuChaiAppData.getRealStartDate();
			endDate = zrChuChaiAppData.getRealEndDate();
			startTime = zrCCTimeSelect_start.queryTimeSelectItem(
										zrChuChaiAppData.getRealStartTime()
																		).getSelectName();
			endTime = zrCCTimeSelect_end.queryTimeSelectItem(
										zrChuChaiAppData.getRealEndTime()
																		).getSelectName();
			String tmpDate = startDate;
			//log.error("KQDTCheck.PRE_TO_CHECK :: " + KQDTCheck.PRE_TO_CHECK);
			while(tmpDate.compareTo(endDate) < 1){
				zrChuChaiCheckDTData = new ZRChuChaiCheckDTData();
				zrChuChaiCheckDTData.setChecked(KQDTCheck.PRE_TO_CHECK);
				
				zrChuChaiCheckDTData.setCheckedDate(tmpDate);
				zrChuChaiCheckDTData.setMainId(mainid);
				zrChuChaiCheckDTData.setWeekday(CalendarUtil.judgeWeekDay(tmpDate));
				holidayConfig = holidayConfigDao.query(tmpDate, zrChuChaiAppData.getAppPsn());
				if(holidayConfig != null){
					zrChuChaiCheckDTData.setWeekday(holidaySelectDao.queryHolidaySelectItemInfo(holidayConfig.getJrmc()).getSelectName());
					//log.error("holidayConfig :: " + holidaySelectDao.queryHolidaySelectItemInfo(holidayConfig.getJrmc()).getSelectName());
				}
				
				zrChuChaiCheckDTData.setStartTime(stStartTime);
				zrChuChaiCheckDTData.setEndTime(stEndTime);
				
				if(tmpDate.equals(startDate)){	//处理第一天的开始时间：取填写的预计开始时间
					zrChuChaiCheckDTData.setStartTime(startTime);
				}else if(tmpDate.equals(endDate)){	//处理最后一天的结束时间：取填写的预计结束时间
					zrChuChaiCheckDTData.setEndTime(endTime);
				}
				
				zrChuChaiCheckDTData.setStartTimeChecked("");
				zrChuChaiCheckDTData.setEndTimeChecked("");
				zrChuChaiCheckDTData.setWfendDate("");
				zrCCCheckDTDataList.add(zrChuChaiCheckDTData);
				tmpDate = CalendarUtil.moveDate(tmpDate,0,0,1);
				//log.error("tmpDate 向后加一天 :: " + tmpDate);
			}
		}
		return zrCCCheckDTDataList;
	}
	
	/**
	 * 获取人员编号
	 * @param userId
	 * @return
	 */
	private String getUserWorkCode(int userId){
		String workcode = null;
		String sql = "select top 1 workcode from Hrmresource where id = " + userId;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			workcode = Util.null2String(rs.getString("workcode")) ;
		}
		return workcode;
	}
}
