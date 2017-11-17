package dinghan.workflow.kq.kqdt.gen;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.com.selectitem.SelectItemInfoImpl;
import dinghan.workflow.com.selectitem.leavetype.ZRLeaveTypeSelect;
import dinghan.workflow.com.selectitem.leavetype.ZRLeaveTypeSelectImpl;
import dinghan.workflow.com.selectitem.timeitem.ZRQingJiaTimeSelect;
import dinghan.workflow.com.selectitem.timeitem.ZRQingJiaTimeSelectImpl;
import dinghan.workflow.kq.appdata.dtentity.ZRQingJiaAppDTData;
import dinghan.workflow.kq.appdata.dtservice.ZRQingJiaAppDTDataService;
import dinghan.workflow.kq.appdata.dtservice.impl.ZRQingJiaAppDTDataServiceImpl;
import dinghan.workflow.kq.appdata.entity.ZRQingJiaAppData;
import dinghan.workflow.kq.appdata.service.ZRQingJiaAppDataService;
import dinghan.workflow.kq.appdata.service.impl.ZRQingJiaAppDataServiceImpl;
import dinghan.workflow.kq.holiday.dao.HolidayConfigDao;
import dinghan.workflow.kq.holiday.dao.HolidaySelectDao;
import dinghan.workflow.kq.holiday.dao.impl.HolidayConfigDaoImpl;
import dinghan.workflow.kq.holiday.dao.impl.HolidaySelectDaoImpl;
import dinghan.workflow.kq.holiday.entity.HolidayConfig;
import dinghan.workflow.kq.kqdt.check.KQDTCheck;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRQingJiaCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRQingJiaCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRQingJiaCheckDTServiceImpl;
import dinghan.workflow.kq.stworktime.StandardWorkTime;
import dinghan.workflow.kq.stworktime.StandardWorkTimeImpl;
import dinghan.workflow.kq.stworktime.StandardWorkTimeInfo;
import dinghan.workflow.kq.userinfo.UserInfoDao;
import dinghan.workflow.kq.userinfo.UserInfoDaoImpl;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * 中车请假明细数据生成类
 * @author zhangxiaoyu / 10593 - 2017-10-29
 * 
 */
public class ZRQingJiaCheckDataGen extends KQCheckDataGen<ZRQingJiaCheckDTData> {
	
	private Log log = LogFactory.getLog(ZRQingJiaCheckDataGen.class.getName());
	
	private UserInfoDao userInfodao = new UserInfoDaoImpl();
	
	private ZRQingJiaAppDataService zrChuChaiAppDataService = new ZRQingJiaAppDataServiceImpl();
	
	private ZRQingJiaAppDTDataService zrQingJiaAppDTDataService = new ZRQingJiaAppDTDataServiceImpl();
	
	private ZRQingJiaCheckDTService zrQingJiaCheckDTService = new ZRQingJiaCheckDTServiceImpl();
	
	private ZRQingJiaTimeSelect zrQJTimeSelect_start = 
			new ZRQingJiaTimeSelectImpl(ZRQingJiaTimeSelect.ZRQingJiaPreStartTimeFieldName);
	
	private ZRQingJiaTimeSelect zrQJTimeSelect_end = 
			new ZRQingJiaTimeSelectImpl(ZRQingJiaTimeSelect.ZRQingJiaPreEndTimeFieldName);
	
	private ZRLeaveTypeSelect zeLeaveTypeSelect = new ZRLeaveTypeSelectImpl(ZRLeaveTypeSelect.ZRLeaveTypeFieldName);
	
	private HolidaySelectDao holidaySelectDao = 
			new HolidaySelectDaoImpl(new SelectItemInfoImpl());
	
	HolidayConfigDao holidayConfigDao = new HolidayConfigDaoImpl();
	
	StandardWorkTime stWorkTime = new StandardWorkTimeImpl();
	
	/**
	 * 思路： 
	 * <p>
	 * 1. 通过id获取请假申请单的数据
	 * <p>
	 * 2. 每行的 开始日期 至 结束日期，进行循环，每行的 每个日期创建一个核定明细
	 * 
	 */
	
	@Override
	public List<ZRQingJiaCheckDTData> createCheckData(int mainid) {
		
		List<ZRQingJiaCheckDTData> zrQJCheckDTDataList = null;	//请假核定明细List for return 
		ZRQingJiaCheckDTData zrQingJiaCheckDTData = null;
		//**** --- 删除原有核定明细 --- start ----  // 
		List<ZRQingJiaCheckDTData> zrQJCheckDTDataList_forDelete = zrQingJiaCheckDTService.queryListByMainID(mainid);
		
		if(zrQJCheckDTDataList_forDelete!=null){
			for(ZRQingJiaCheckDTData data : zrQJCheckDTDataList_forDelete){
				zrQingJiaCheckDTService.deleteById(data.getId());
			}
		}
		//**** --- 删除原有核定明细 ---- end ----- *****//
		
		//获取申请单中的所有 申请 明细  -- 准备进行循环
		List<ZRQingJiaAppDTData> zrQingJiaAppDTDataList = zrQingJiaAppDTDataService.queryListByMainID(mainid);
		log.equals("zrQingJiaAppDTDataList ? null :: " + (zrQingJiaAppDTDataList == null));
		if(zrQingJiaAppDTDataList!=null){
			zrQJCheckDTDataList = new ArrayList<ZRQingJiaCheckDTData>();
			ZRQingJiaAppData zrQJappdata = zrChuChaiAppDataService.queryByID(mainid);
			//appdata.getStstartendtime();
			log.error("ZRQingJiaAppData :: " + zrQJappdata.getOddnum());
			
			int userID = zrQJappdata.getApplicant();
			
			String startDate;
			String endDate;
			
			String startTime;
			String endTime;
			
			String stStartTime;// = "08:00";
			String stEndTime;// = "17:30";
			
			String leaveType;
			int leaveTypeId;
			String weekDay;
			String tmpDate;
			
			UserInfo userInfo = userInfodao.queryByCode(this.getUserWorkCode(zrQJappdata.getApplicant()));
			//log.error("userInfo :: " + userInfo.getCode());
			if(userInfo != null){
				stStartTime = userInfo.getStartWorkTime();
				stEndTime = userInfo.getEndWorkTime();
			}else{
				StandardWorkTimeInfo stWorkTimeInfo = stWorkTime.queryByID(zrQJappdata.getStstartendtime());
				stStartTime = stWorkTimeInfo.getStreststarttime();
				stEndTime = stWorkTimeInfo.getStrestendtime();
			}
			
			HolidayConfig holidayConfig;
			for(ZRQingJiaAppDTData appDTData : zrQingJiaAppDTDataList){
				
				startDate = appDTData.getStartdate();	//获取开始日期
				endDate = appDTData.getEnddate();	//获取结束日期
				leaveTypeId = appDTData.getLeavecategory();	//请假类别select框的值
				startTime = zrQJTimeSelect_start.queryTimeSelectItem(appDTData.getPrestarttime()).getSelectName();
				endTime = zrQJTimeSelect_end.queryTimeSelectItem(appDTData.getPreendtime()).getSelectName();
				
				tmpDate = startDate;
				while(tmpDate.compareTo(endDate) < 1){
					zrQingJiaCheckDTData = new ZRQingJiaCheckDTData();
					zrQingJiaCheckDTData.setMainid(appDTData.getMainid());	//mainid
					zrQingJiaCheckDTData.setCheckeddate(tmpDate);	//checkedData
					zrQingJiaCheckDTData.setStarttime(stStartTime);	//startTime
					if(tmpDate.equals(startDate)){
						zrQingJiaCheckDTData.setStarttime(startTime);
					}
					zrQingJiaCheckDTData.setEndtime(stEndTime);	//endTime
					if(tmpDate.equals(endDate)){
						zrQingJiaCheckDTData.setEndtime(endTime);
					}
					
					
					
					//** 设置星期 、 节假日 信息
					weekDay = CalendarUtil.judgeWeekDay(tmpDate);
					
					holidayConfig = holidayConfigDao.query(tmpDate, userID);
					if(holidayConfig!=null){
						weekDay = holidaySelectDao.queryHolidaySelectItemInfo(holidayConfig.getJrmc()).getSelectName();
					}
					zrQingJiaCheckDTData.setWeekday(weekDay);
					
					//** 设置节假日类别
					leaveType = zeLeaveTypeSelect.queryTimeSelectItem(appDTData.getLeavecategory()).getSelectName();
					zrQingJiaCheckDTData.setLeavecategory(leaveType);
					zrQingJiaCheckDTData.setLeavecategoryid(leaveTypeId);
					zrQingJiaCheckDTData.setStarttimechecked("");
					zrQingJiaCheckDTData.setEndtimechecked("");
					zrQingJiaCheckDTData.setWfenddate("");
					zrQingJiaCheckDTData.setLeavehour(0);
					zrQingJiaCheckDTData.setDakaRecord("");
					zrQingJiaCheckDTData.setChecked(KQDTCheck.PRE_TO_CHECK);	//核定状态
					zrQJCheckDTDataList.add(zrQingJiaCheckDTData);
					tmpDate = CalendarUtil.moveDate(tmpDate,0,0,1);
				}
			}
		}
		
		return zrQJCheckDTDataList;
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
