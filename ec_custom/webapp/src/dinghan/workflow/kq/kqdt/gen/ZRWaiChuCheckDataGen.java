package dinghan.workflow.kq.kqdt.gen;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.com.selectitem.SelectItemInfoImpl;
import dinghan.workflow.com.selectitem.timeitem.ZRWaiChuTimeSelect;
import dinghan.workflow.com.selectitem.timeitem.ZRWaiChuTimeSelectImpl;
import dinghan.workflow.kq.appdata.dao.ZRWaiChuAppDataDao;
import dinghan.workflow.kq.appdata.dao.impl.ZRWaiChuAppDataDaoImpl;
import dinghan.workflow.kq.appdata.entity.ZRWaiChuAppData;
import dinghan.workflow.kq.holiday.dao.HolidayConfigDao;
import dinghan.workflow.kq.holiday.dao.HolidaySelectDao;
import dinghan.workflow.kq.holiday.dao.impl.HolidayConfigDaoImpl;
import dinghan.workflow.kq.holiday.dao.impl.HolidaySelectDaoImpl;
import dinghan.workflow.kq.holiday.entity.HolidayConfig;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRWaiChuCheckDTData;
import dinghan.workflow.kq.userinfo.UserInfoDao;
import dinghan.workflow.kq.userinfo.UserInfoDaoImpl;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * 中车外出公干核定明细数据生成类
 * @author zhangxiaoyu / 10593 - 2017-10-22
 * 
 */
public class ZRWaiChuCheckDataGen extends KQCheckDataGen<ZRWaiChuCheckDTData> {

	private Log log = LogFactory.getLog(ZRWaiChuCheckDataGen.class.getName());
	
	private UserInfoDao userInfodao = new UserInfoDaoImpl();
	
	private ZRWaiChuAppDataDao zrWaiChuAppDataDao = new ZRWaiChuAppDataDaoImpl();
	
	private ZRWaiChuTimeSelect zrWCTimeSelect_start = 
			new ZRWaiChuTimeSelectImpl(ZRWaiChuTimeSelect.ZRWaiChuStartTimeFieldName);
	
	private ZRWaiChuTimeSelect zrWCTimeSelect_end = 
			new ZRWaiChuTimeSelectImpl(ZRWaiChuTimeSelect.ZRWaiChuEndTimeFieldName);
	
	private HolidaySelectDao holidaySelectDao = 
			new HolidaySelectDaoImpl(new SelectItemInfoImpl());
	
	HolidayConfigDao holidayConfigDao = new HolidayConfigDaoImpl();
	
	/**
	 * 思路： 
	 * 1. 通过id获取外出公干单的数据
	 * 2. 开始日期 至 结束日期，进行循环，每个日期创建一个核定明细
	 * 
	 */
	
	@Override
	public List<ZRWaiChuCheckDTData> createCheckData(int mainid) {
		
		List<ZRWaiChuCheckDTData> zrWCCheckDTDataList = null;
		ZRWaiChuCheckDTData zrWaiChuCheckDTData = null;
		
		ZRWaiChuAppData zrWaiChuAppData = zrWaiChuAppDataDao.queryByID(mainid);
		
		String startDate;
		String endDate;
		
		String startTime;
		String endTime;
		
		String stStartTime = "08:00";
		String stEndTime = "17:30";
		
		UserInfo userInfo = userInfodao.queryByCode(this.getUserWorkCode(zrWaiChuAppData.getAppspn()));
		
		if(userInfo != null){
			stStartTime = userInfo.getStartWorkTime();
			stEndTime = userInfo.getEndWorkTime();
		}
		
		HolidayConfig holidayConfig;
		
		if(zrWaiChuAppData!=null){
			zrWCCheckDTDataList = new ArrayList<ZRWaiChuCheckDTData>();
			startDate = zrWaiChuAppData.getPrestartdate();
			endDate = zrWaiChuAppData.getPreenddate();
			startTime = zrWCTimeSelect_start.queryTimeSelectItem(
												zrWaiChuAppData.getPrestarttime()
																		).getSelectName();
			endTime = zrWCTimeSelect_end.queryTimeSelectItem(
												zrWaiChuAppData.getPreendtime()
																		).getSelectName();;
			
			String tmpDate = startDate;
			
			while(tmpDate.compareTo(endDate) < 1){
				zrWaiChuCheckDTData = new ZRWaiChuCheckDTData();
				
				zrWaiChuCheckDTData.setChecked(0);
				zrWaiChuCheckDTData.setCheckeddate(tmpDate);
				zrWaiChuCheckDTData.setMainid(mainid);
				zrWaiChuCheckDTData.setWeekday(CalendarUtil.judgeWeekDay(tmpDate));
				holidayConfig = holidayConfigDao.query(tmpDate, zrWaiChuAppData.getAppspn());
				if(holidayConfig != null){
					zrWaiChuCheckDTData.setWeekday(
							holidaySelectDao.queryHolidaySelectItemInfo(
									holidayConfig.getJrmc()
											).getSelectName());
				}
				
				zrWaiChuCheckDTData.setStarttime(stStartTime);
				zrWaiChuCheckDTData.setEndtime(stEndTime);
				
				if(tmpDate.equals(startDate)){
					zrWaiChuCheckDTData.setStarttime(startTime);
				}else if(tmpDate.equals(endDate)){
					zrWaiChuCheckDTData.setEndtime(endTime);
				}
				
				zrWaiChuCheckDTData.setStarttimechecked("");
				zrWaiChuCheckDTData.setEndtimechecked("");
				zrWaiChuCheckDTData.setWfenddate("");
				zrWCCheckDTDataList.add(zrWaiChuCheckDTData);
				tmpDate = CalendarUtil.moveDate(tmpDate,0,0,1);
				//log.error("tmpDate 向后加一天 :: " + tmpDate);
			}
		}
		return zrWCCheckDTDataList;
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
