package dinghan.workflow.kq.kqdt.check;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.com.selectitem.SelectItemInfoImpl;
import dinghan.workflow.kq.appdata.entity.ZRJiaBanAppData;
import dinghan.workflow.kq.appdata.service.ZRJiaBanAppDataService;
import dinghan.workflow.kq.appdata.service.impl.ZRJiaBanAppDataServiceImpl;
import dinghan.workflow.kq.checkout.bean.CheckOutRecord;
import dinghan.workflow.kq.checkout.bean.CheckOutRecordSet;
import dinghan.workflow.kq.checkout.util.CheckOutUtil;
import dinghan.workflow.kq.holiday.dao.HolidayConfigDao;
import dinghan.workflow.kq.holiday.dao.HolidaySelectDao;
import dinghan.workflow.kq.holiday.dao.impl.HolidayConfigDaoImpl;
import dinghan.workflow.kq.holiday.dao.impl.HolidaySelectDaoImpl;
import dinghan.workflow.kq.holiday.entity.HolidayConfig;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRChuChaiCheckDTData;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRJiaBanCheckDTData;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRWaiChuCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRChuChaiCheckDTService;
import dinghan.workflow.kq.kqdt.service.ZRJiaBanCheckDTService;
import dinghan.workflow.kq.kqdt.service.ZRWaiChuCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRChuChaiCheckDTServiceImpl;
import dinghan.workflow.kq.kqdt.service.impl.ZRJiaBanCheckDTServiceImpl;
import dinghan.workflow.kq.kqdt.service.impl.ZRWaiChuCheckDTServiceImpl;
import dinghan.workflow.kq.stworktime.StandardWorkTime;
import dinghan.workflow.kq.stworktime.StandardWorkTimeImpl;
import dinghan.workflow.kq.stworktime.StandardWorkTimeInfo;
import dinghan.workflow.kq.userinfo.UserInfoDao;
import dinghan.workflow.kq.userinfo.UserInfoDaoImpl;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
/**
 * 中车加班申请流程核定类
 * <p>核定状态值说明： 0 - 未核定；1 - 已核定； 2 - 已确认
 * 
 * @author zhangxiaoyu / 10593 - 2017-11-08
 * 
 */
public class ZRJiaBanDTCheck implements KQDTCheck<ZRJiaBanCheckDTData> {
	//private Log log = LogFactory.getLog(ZRJiaBanDTCheck.class.getName());
	//加班核定明细Service
	private ZRJiaBanCheckDTService zrJBCheckDTService = new ZRJiaBanCheckDTServiceImpl(); 
	//加班申请数据Service
	private ZRJiaBanAppDataService zrJAppDataService = new ZRJiaBanAppDataServiceImpl();
	
	private ZRWaiChuCheckDTService zrWaiChuCheckDTService = new ZRWaiChuCheckDTServiceImpl();
	private ZRChuChaiCheckDTService zrChuChaiCheckDTService = new ZRChuChaiCheckDTServiceImpl();
	
	private ZRJiaBanAppData zrJiaBanAppData = null;
	
	private StandardWorkTime standardWorkTime = new StandardWorkTimeImpl();
	
	private CheckOutUtil checkoutUitl = new CheckOutUtil();
	
	private UserInfoDao userInfoDao = new UserInfoDaoImpl();
	
	private int userID;	//用户ID
	
	private String userWorkCode;	//用户工号
	private String overTimeDate;	//加班日期
	private String preStartTime;	//预计开始时间
	private String preEndTime;	//预计结束时间
	
	
	private String startTime_checked;	//核定开始时间	
	private String endTime_checked;	//核定结束时间	
	
	@Override
	public ZRJiaBanCheckDTData executeCheck(int id) {
		//ZRJiaBanCheckDTData checked_data = null;
		ZRJiaBanCheckDTData jiabanCheckData = zrJBCheckDTService.queryById(id);
		if(jiabanCheckData != null){
			zrJiaBanAppData = zrJAppDataService.queryByID(jiabanCheckData.getMainid());
			//log.error("获取到加班申请单  ：： " + zrJiaBanAppData.getOddNum());
			if(zrJiaBanAppData != null){
				userWorkCode = zrJiaBanAppData.getAppWorkCode();	// 《《《《工号
				userID = zrJiaBanAppData.getApplicant(); 	// 《《《《userID 
				overTimeDate = jiabanCheckData.getCheckeddate();	// 《《《《加班日期
				
				List<String> checkOutTimeList = LoadCheckOutTimeSet(userID,userWorkCode,overTimeDate);	
				
				if(checkOutTimeList.isEmpty() || checkOutTimeList.size() < 2){	//仅有一条打卡记录或这无打卡记录，当天加班记为 0
					jiabanCheckData.setStarttimechecked("");
					jiabanCheckData.setEndtimechecked("");
					jiabanCheckData.setWeekday("");
					jiabanCheckData.setValidhour(0);
					jiabanCheckData.setResthour(0);
					//jiabanCheckData.setOtcoefficient(0);
					jiabanCheckData.setCheckedhour(0);
					jiabanCheckData.setWfenddate("");
					jiabanCheckData.setDakarecord("");
				}else{
					startTime_checked = checkOutTimeList.get(0);	 // 《《《《 核定开始时间
					endTime_checked = checkOutTimeList.get(checkOutTimeList.size()-1);	// 《《《《核定结束时间
					preStartTime = jiabanCheckData.getStarttime();	//预计开始使时间
					preEndTime = jiabanCheckData.getEndtime();	//预计结束使时间
					
					if(startTime_checked.compareTo(preStartTime)<0){
						startTime_checked = preStartTime;
					}
					if(endTime_checked.compareTo(preEndTime)>0){
						endTime_checked = preEndTime;
					}
					jiabanCheckData = this.executeCheck(jiabanCheckData,userWorkCode,userID,overTimeDate,startTime_checked,endTime_checked,zrJiaBanAppData.getStStartEndTime());
				}
				jiabanCheckData.setChecked(1);	// *** 核定状态赋值
			}
		}
		
		return jiabanCheckData;
	}
	
	/**
	 * 核定加班明细 - Reload
	 * @param jiabanCheckData - 加班
	 * @param workCode
	 * @param userId
	 * @param overtimeDate
	 * @param checkedStartTime
	 * @param checkedEndTime
	 * @param stStartEndTimeCfgId
	 * @return
	 */
	public ZRJiaBanCheckDTData executeCheck(ZRJiaBanCheckDTData jiabanCheckData, String workCode, int userId, String overtimeDate,
			String checkedStartTime,String checkedEndTime ,int stStartEndTimeCfgId) {
			String _userWorkCode = workCode;
			int _userID = userId;
			String _overTimeDate = overtimeDate;
			String _startTime_checked = checkedStartTime;
			String _endTime_checked = checkedEndTime;
			int _stSETimeCfgId = stStartEndTimeCfgId;
			
			//if(jiabanCheckData != null){
			zrJiaBanAppData = zrJAppDataService.queryByID(jiabanCheckData.getMainid());
			//log.error("获取到加班申请单  ：： " + zrJiaBanAppData.getOddNum());
			if(zrJiaBanAppData != null){
				List<String> checkOutTimeList = LoadCheckOutTimeSet(_userID,_userWorkCode,_overTimeDate);	//获取打卡信息
				//休息小时数
				double restHour = countRestHour(_startTime_checked,_endTime_checked, _stSETimeCfgId);	// 《《《《 标准开始、结束时间
				//有效工时数
				double validHour = countValidOverTimeHour(_startTime_checked,_endTime_checked, restHour);
				//加班系数Map
				Map<String,Object> otCoefficientMap = countOTCoefficient(_overTimeDate,_userID);
				
				String overTimeDayType = (String) otCoefficientMap.get("day_type");	// 加班日期类型（星期 或者 节假日名称）
				double otCoefficient = (double) otCoefficientMap.get("day_coefficient"); // 加班系数
				double checkedHour = validHour * otCoefficient;
				String dakaRecord = "";
				if(checkOutTimeList!=null){
					for(String cr : checkOutTimeList){
						if(!"".equals(dakaRecord)){
							dakaRecord += " - ";
						}
						dakaRecord += cr;
					}
				}
				jiabanCheckData.setStarttimechecked(_startTime_checked);
				jiabanCheckData.setEndtimechecked(_endTime_checked);
				jiabanCheckData.setWeekday(overTimeDayType);
				jiabanCheckData.setResthour(restHour);
				jiabanCheckData.setValidhour(validHour);
				jiabanCheckData.setOtcoefficient(otCoefficient);
				jiabanCheckData.setCheckedhour(checkedHour);
				jiabanCheckData.setDakarecord(dakaRecord);
				jiabanCheckData.setChecked(1);	// *** 核定状态赋值
			}
		
		return jiabanCheckData;
	}

	@Override
	public List<ZRJiaBanCheckDTData> executeCheckAll(int mainid) {
		//log.error("开始核定加班单据，mainid :: " + mainid);
		List<ZRJiaBanCheckDTData> jiabanCheckDataList = zrJBCheckDTService.queryListByMainID(mainid);
		//log.error("获取加班List " + jiabanCheckDataList.size() +"");
		List<ZRJiaBanCheckDTData> jiabanCheckDataList_checked = null;
		if(jiabanCheckDataList!=null){
			jiabanCheckDataList_checked = new ArrayList<ZRJiaBanCheckDTData>();
			for(ZRJiaBanCheckDTData data : jiabanCheckDataList){
				data = executeCheck(data.getId());
				jiabanCheckDataList_checked.add(data);
			}
		}
		
		return jiabanCheckDataList_checked;
	}
	
	/**
	 * 计算加班有效小时数
	 * @return
	 */
	private double countValidOverTimeHour(String checkedStartTime,String checkedEndTime, double resHour){
		double validHour = 0.0d;
		double startHour = Double.parseDouble(checkedStartTime.substring(0, 2));
		double endHour = Double.parseDouble(checkedEndTime.substring(0, 2));
		
		double startMinute = Double.parseDouble(checkedStartTime.substring(3, 5));
		double endMinute = Double.parseDouble(checkedEndTime.substring(3, 5));
		
		if(startMinute > 45){
			startHour += 1.0d;
		}else if(startMinute > 15){
			startHour += 0.5d;
		}
		
		if(endMinute > 45){
			endHour += 1.0d;
		}else if(endMinute > 15) {
			endHour += 0.5d;
		}
		
		validHour = endHour - startHour - resHour;
		
		if(validHour<0){
			validHour = 0;
		}
			
		return validHour;
	}
	
	/**
	 * 计算加班系数
	 * @return Map<String,Object> 
	 */
	private Map<String,Object> countOTCoefficient(String overTimeDate,int userID){
		HolidayConfigDao hdayCfgDao = new HolidayConfigDaoImpl();
		HolidaySelectDao hdaySelectDao = new HolidaySelectDaoImpl(new SelectItemInfoImpl());
		Map<String,Object> coefficientMap = new HashMap<String,Object>();
		
		String day_type;
		double day_Coefficient;
		
		day_type = CalendarUtil.judgeWeekDay(overTimeDate);
		
		if("星期日".equals(day_type) || "星期六".equals(day_type)){
			day_Coefficient = 2.0d;
		}else{
			day_Coefficient = 1.5d;
		}
		
		HolidayConfig hdayCfg = hdayCfgDao.query(overTimeDate, userID);
		
		if(hdayCfg != null){
			day_type = hdaySelectDao.queryHolidaySelectItemInfo(hdayCfg.getJrmc()).getSelectName();
			day_Coefficient = hdayCfg.getOvernum();
		}
		
		coefficientMap.put("day_type", day_type);
		coefficientMap.put("day_coefficient", day_Coefficient);
		
		return coefficientMap;
	}
	
	/**
	 * 计算休息时间
	 * @return
	 */
	private double countRestHour(String checkedStartTime,String checkedEndTime, int startEndTimeCfgID){
		StandardWorkTimeInfo stStartEndTimeInfo = standardWorkTime.queryByID(startEndTimeCfgID);
		double restHours = 0.0d;		//午休小时数
		//String stStartWorkTime;			//标准上班时间
		//String stEndWorkTime;		//标准下班时间
		//String restStartTime;		//午休开始时间
		//String restEndTime;				//午休结束时间
		
		//String stStartWorkTime = stStartEndTimeInfo.getStstartendtime();
		//String stEndWorkTime = stStartEndTimeInfo.getStworkendtime();
		//String restStartTime = stStartEndTimeInfo.getStrestendtime();
		String restEndTime = stStartEndTimeInfo.getStrestendtime();
		String noonRestMarkTime = "13:30"; //默认为13:30
		double stNoonRestHours = stStartEndTimeInfo.getNoonresthour();	//获取标准工作时间配置中的午休时间
		
		if("13:00".equals(restEndTime)){
			noonRestMarkTime = "13:00";
		}
		
		if("18:30".compareTo(checkedStartTime) >= 0){	//开始时间在 18:30 之前才有可能扣除休息时间
			if("12:00".compareTo(checkedStartTime) >= 0){	//开始时间 在 12点之前
				if(noonRestMarkTime.compareTo(checkedEndTime) <=0){
					restHours += stNoonRestHours;
					if("18:30".compareTo(checkedEndTime) <=0){
						restHours += 1;
					}
				}
			}else{
				if("18:30".compareTo(checkedEndTime) <=0){
					restHours += 1;
				}
			}
		}
		
		return restHours;
	}
	
	/**
	 * 初始化考勤打卡记录
	 * <p>加班核定中，获取以下数据作为考勤打卡记录
	 * 	<p> 1. 考勤打卡数据
	 *  <p> 2. 开通了移动考勤权限人员的移动打卡记录
	 *  <p> 3. 外出公干申请中核定开始、结束时间
	 *  <p> 4. 出差申请的核定开始、结束时间
	 *  <p> ** 此方法为 获取 某个人、某天的考勤打卡记录
	 * @param userID - 用户ID
	 * @param workCode - 用户工号
	 * @param overTimeDate - 加班日期
	 * 
	 * @return
	 */
	private List<String> LoadCheckOutTimeSet(int userID, String workCode,String overTimeDate){
		//log.error("开始获取打卡信息：  ");
		UserInfo userInfo = userInfoDao.queryByCode(userWorkCode);
		
		int allowMobileAttend = 1;
		if(userInfo != null){
			allowMobileAttend = userInfo.getMobileAtten();
		}
		// **** 获取加班日期打卡时间集合
		checkoutUitl.setForwordDayCheckOutNode("02:00:00");
		CheckOutRecordSet checkOutSet = checkoutUitl.getPersonalCheckOutSetByDay(workCode, overTimeDate, allowMobileAttend);	//获取打卡记录集合
		List<CheckOutRecord> checkOutRecordList = checkOutSet.getCheckOutRecordList();
		//log.error("checkOutRecordList :: size - " + checkOutRecordList.size());
		// **** 获取加班当天出差和外出公干的明细
		List<ZRWaiChuCheckDTData> waichuCheckDTList = zrWaiChuCheckDTService.queryByUserIDAndDate(userID, overTimeDate);
		List<ZRChuChaiCheckDTData> chuchaiCheckDTList = zrChuChaiCheckDTService.queryByUserIDAndDate(userID, overTimeDate);
		
		// **** 整理打卡记录 
		List<String> checkOutTimeList = new ArrayList<String>();
		
		for(CheckOutRecord record : checkOutRecordList){
			if(checkOutTimeList.indexOf(record.getTime())<0){
				checkOutTimeList.add(record.getTime());
			}
		}
		
		if(waichuCheckDTList!=null){ //将外出公干的核定时间 当作 打卡时间计入 打卡时间集合中
			for(ZRWaiChuCheckDTData data : waichuCheckDTList){
				if(checkOutTimeList.indexOf(data.getStarttimechecked())<0){
					checkOutTimeList.add(data.getStarttimechecked());
				}
				if(checkOutTimeList.indexOf(data.getEndtimechecked())<0){
					checkOutTimeList.add(data.getEndtimechecked());
				}
			}
		}
		
		if(chuchaiCheckDTList!=null){	//将出差的核定时间 当作 打卡时间计入 打卡时间集合中
			for(ZRChuChaiCheckDTData data : chuchaiCheckDTList){
				if(checkOutTimeList.indexOf(data.getStartTimeChecked())<0){
					checkOutTimeList.add(data.getStartTimeChecked());
				}
				if(checkOutTimeList.indexOf(data.getEndTimeChecked())<0){
					checkOutTimeList.add(data.getEndTimeChecked());
				}
			}
		}
		
		for(String time : checkOutTimeList){	//去除空的打卡记录
			if("".equals(time) || time == null ){
				checkOutTimeList.remove(time);
			}
		}
		
		Collections.sort(checkOutTimeList);	//打卡记录排序
		return checkOutTimeList;
	}
	
	
}
