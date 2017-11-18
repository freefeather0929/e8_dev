package dinghan.workflow.kq.kqdt.check;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.com.selectitem.SelectItemInfoImpl;
import dinghan.workflow.kq.appdata.dao.ZRQingJiaAppDataDao;
import dinghan.workflow.kq.appdata.dao.impl.ZRQingJiaAppDataDaoImpl;
import dinghan.workflow.kq.appdata.entity.ZRQingJiaAppData;
import dinghan.workflow.kq.checkout.bean.CheckOutRecord;
import dinghan.workflow.kq.checkout.bean.CheckOutRecordSet;
import dinghan.workflow.kq.checkout.util.CheckOutUtil;
import dinghan.workflow.kq.holiday.dao.HolidayConfigDao;
import dinghan.workflow.kq.holiday.dao.HolidaySelectDao;
import dinghan.workflow.kq.holiday.dao.impl.HolidayConfigDaoImpl;
import dinghan.workflow.kq.holiday.dao.impl.HolidaySelectDaoImpl;
import dinghan.workflow.kq.holiday.entity.HolidayConfig;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRQingJiaCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRQingJiaCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRQingJiaCheckDTServiceImpl;
import dinghan.workflow.kq.kqtype.LeaveType;
import dinghan.workflow.kq.stworktime.StandardWorkTime;
import dinghan.workflow.kq.stworktime.StandardWorkTimeImpl;
import dinghan.workflow.kq.stworktime.StandardWorkTimeInfo;

/**
 * 中车请假流程核定类
 * <p>核定状态值说明： 0 - 未核定；1 - 已核定； 2 - 已确认；3 - 手工复核
 * 
 * <p>
 * <i> 核定规则：
 * 		
 * <p> 
 * 
 * @author zhangxiaoyu / 10593 - 2017-10-24
 * 
 * 
 */
import dinghan.workflow.kq.userinfo.UserInfoDao;
import dinghan.workflow.kq.userinfo.UserInfoDaoImpl;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
import weaver.conn.RecordSet;

public class ZRQingJiaDTCheck implements KQDTCheck<ZRQingJiaCheckDTData> {
	
	private Log log = LogFactory.getLog(ZRQingJiaDTCheck.class.getName());
	
	CheckOutUtil checkoutUitl = new CheckOutUtil();
	private ZRQingJiaCheckDTService zrQJCheckDTService = new ZRQingJiaCheckDTServiceImpl(); 
	private ZRQingJiaAppDataDao zrQingJiaAppDataDao = new ZRQingJiaAppDataDaoImpl();
	private StandardWorkTime standardWorkTime = new StandardWorkTimeImpl();
	private UserInfoDao userInfoDao = new UserInfoDaoImpl();	//考勤人员信息接口，用于获取考勤平台中的人员信息
	HolidayConfigDao hdayCfgDao = new HolidayConfigDaoImpl();
	HolidaySelectDao hdaySelectDao = new HolidaySelectDaoImpl(new SelectItemInfoImpl());
	
	private int userId;
	private int mainId;
	private String workcode;
	private String leaveDate;
	private double restHour;
	private double validHour;
	/**
	 * 是否执行核定
	 */
	private boolean isExecuteCheck = true;
	
	/**
	 * 是否允许销假
	 */
	private boolean isAllowXiaoJia = true;
	
	/**
	 * 临时开始时间，用于获取核定开始时间
	 */
	private String startTime_forCheck = "";
	/**
	 * 临时结束时间，用于获取核定结束时间
	 */
	private String endTime_forCheck = "";
	/**
	 * 核定开始时间
	 */
	private String startTime_checked;
	/**
	 * 核定结束时间
	 */
	private String endTime_checked;
	/**
	 * 申请结束时间前30分钟时间点
	 */
	private String endTime_before_30;
	/**
	 * 申请结束时间后30分钟时间点
	 */
	private String endTime_after_30;
	/**
	 * 标准开始、结束时间配置ID
	 */
	private int stStartEndTimeCfg;
	/**
	 * 标准上班时间
	 */
	private String stStartTime;
	/**
	 * 标准下班时间
	 */
	private String stEndTime;
	/**
	 * 标准午休开始时间
	 */
	private String stNoonStartTime;
	/**
	 * 标准午休结束时间
	 */
	private String stNoonEndTime;
	/**
	 * 打卡记录
	 */
	private String dakaRecord;
	/**
	 * 星期
	 */
	private String weekDay;
	
	/**
	 * 请假类型
	 */
	private int leaveType;
	
	/**
	 * 打卡记录集合
	 */
	private List<String> dakaRecordList = null;
	
	
	@Override
	public ZRQingJiaCheckDTData executeCheck(int id) {
		log.error("ZRQingJiaCheckDTData 开始核定 请假 id :: " + id);
		ZRQingJiaCheckDTData qingjiaCheckData = zrQJCheckDTService.queryById(id);
		if(qingjiaCheckData!=null){
			this.mainId = qingjiaCheckData.getMainid();
			
			ZRQingJiaAppData appData = zrQingJiaAppDataDao.queryByID(mainId);
			
			if(appData!=null){
				this.userId = appData.getApplicant();	//获取申请人 ID 》》》》》
				this.workcode = this.queryWorkCode(userId);		//获取工号		》》》》》
				this.leaveDate = qingjiaCheckData.getCheckeddate();	//获取请假日期		》》》》
				this.weekDay = getWeekDayName(this.userId,this.leaveDate);	//获取请假日期的 类型星期 ， 节假日
				this.leaveType = qingjiaCheckData.getLeavecategoryid();			// 假别，获取的是加别选择框的 value ，int 类型
				//重置是否执行核定 标识 为 是
				isExecuteCheck = true;
				//重置是否允许销假标识 为 是
				isAllowXiaoJia = true;
				
				/**
				 * 默认情况下，周六日、法定节假日不进行核定
				 * 根据中车考勤制度
				 * 病假、年休假、婚假、丧假、产假、陪产假、流产假、计生假、工伤假 的天数核算包含周六日；
				 * 事假、调休假 不包含周六日
				 */
				if(("星期六".equals(weekDay) || "星期日".equals(weekDay)) && "上班调整".equals(weekDay) == false){
					isExecuteCheck = false;
					switch(leaveType){
						case LeaveType.SICK_LEAVE:
						case LeaveType.ANNUAL_LEAVE:
						case LeaveType.MARRIAGE_HOLIDAY:
						case LeaveType.FUNERAL_LEAVE:
						case LeaveType.MATENITY_LEAVE:
						case LeaveType.ACCOMP_DELIVERY_LEAVE:
						case LeaveType.ABORTION_LEAVE:
						case LeaveType.PLANNED_BIRTH_LEAVE:
						case LeaveType.INJUNY_LEAVE_FOR_OTHER:
						case LeaveType.INJUNY_LEAVE_PERSONAL:
							isExecuteCheck = true;
							break;
						default:
							isExecuteCheck = false;	
					}
				}
				
				if(isExecuteCheck == true){
					//*** 请假起止时间临时属性赋值  以临时属性计算 ***//
					this.startTime_forCheck = qingjiaCheckData.getStarttime();	
					this.endTime_forCheck = qingjiaCheckData.getEndtime();
					
					//** 获取标准上下班时间  start ** //
					this.stStartEndTimeCfg = appData.getStstartendtime();	//标准开始、结束时间ID  》》》》
					StandardWorkTimeInfo stWorkTimeInfo = standardWorkTime.queryByID(this.stStartEndTimeCfg);	
					this.stStartTime = stWorkTimeInfo.getStworkstarttime();	
					this.stEndTime = stWorkTimeInfo.getStworkendtime();
					this.stNoonStartTime = stWorkTimeInfo.getStreststarttime();
					this.stNoonEndTime = stWorkTimeInfo.getStrestendtime();
					this.restHour = stWorkTimeInfo.getNoonresthour();	//午休小时
					//**  获取标准上下班时间    end ** //
					
					//如果 在节假日中有上班调整的则 用 上班调整的 上下班时间  覆盖 当前 标准开始、结束时间
					modifyStStartEndTime(this.userId,this.leaveDate);	
					
					// 核定开始时间 即 申请开始时间
					//this.startTime_forCheck = qingjiaCheckData.getStarttime();
					
					//获取打卡记录
					this.dakaRecordList = LoadCheckOutTimeSet(userId,workcode,leaveDate);
					
					if(dakaRecordList.isEmpty() || this.dakaRecordList.size() == 0 ){	//无打卡记录的情况
						this.dakaRecord = " - 无打卡记录 - ";
						//this.endTime_checked = qingjiaCheckData.getEndtime();
					}else{
						//有打卡时间记录，获取打卡时间列表字符串
						this.dakaRecord = "";
						for(String record : dakaRecordList){
							if(!"".equals(dakaRecord)){
								this.dakaRecord += " - ";
							}
							this.dakaRecord += record;
						}
					}	
					log.error("获取到打卡记录 :: " + dakaRecord);
					/**
					 * 处理不允许销假的请假： 请假时间即为核定时间
					 * 年休假、调休假、婚假、产检、产检假、哺乳假、婚检假、陪产假
					 */
					switch(leaveType){
						case LeaveType.ANNUAL_LEAVE:
						case LeaveType.BALANCE_LEAVE:
						case LeaveType.MARRIAGE_HOLIDAY:
						case LeaveType.MATENITY_LEAVE:
						case LeaveType.PREGNANCY_CHECK_LEAVE:
						case LeaveType.BREASTFEEDING_LEAVE:
						case LeaveType.PRE_MARITAL_LEAVE:
						case LeaveType.ACCOMP_DELIVERY_LEAVE:
							isAllowXiaoJia = false;
							break;
						default :
							isAllowXiaoJia = true;
					}
					
					if(isAllowXiaoJia == true){
						this.endTime_forCheck = qingjiaCheckData.getEndtime();
						this.endTime_before_30 = CalendarUtil.moveTime(endTime_forCheck, 0, -30, 0);
						this.endTime_after_30 = CalendarUtil.moveTime(endTime_forCheck, 0, 31, 0);
						
						/**
						 * 开始时间 等于 标准上班时间 时：其后 的 第一次 打卡 时间为 结束时间
						 */
						if(startTime_forCheck.equals(this.stStartTime)){
							for(String record : this.dakaRecordList){
								if(record.compareTo(this.startTime_forCheck)>=0){
									if(record.compareTo(endTime_forCheck) <=0){
										this.endTime_forCheck = record;
										break;
									}else if(record.compareTo(this.endTime_after_30)<0){
										break;
									}else{
										this.endTime_forCheck = record;
										break;
									}
								}
							}
						}else{
							/**
							 * 上班后才开始的请假：允许提前或延后半小时回来
							 */
							for(String record : this.dakaRecordList){
								if(record.compareTo(this.endTime_before_30) >=0){
									if(record.compareTo(this.endTime_forCheck)<=0){
										this.endTime_forCheck = record;
										break;
									}else if(record.compareTo(this.endTime_after_30)<0){
										break;
									}else{
										this.endTime_forCheck = record;
										break;
									}
								}
							}
						}
					}
					//为空时取标准上下班时间
					if("".equals(startTime_forCheck)){
						startTime_forCheck = stStartTime;
					}
					
					if("".equals(endTime_forCheck)){
						endTime_forCheck = stEndTime;
					}
					if(startTime_forCheck.compareTo(this.stStartTime) < 0){
						startTime_forCheck = this.stStartTime;
					}
					if(endTime_forCheck.compareTo(this.stEndTime) > 0){
						endTime_forCheck = this.stEndTime;
					}
					log.error("核定开始时间 :: " + startTime_forCheck);
					log.error("核定结束时间 :: " + endTime_forCheck);
					this.validHour = countValidLeaveHour(startTime_forCheck,endTime_forCheck,restHour);
					qingjiaCheckData.setWeekday(weekDay);
					qingjiaCheckData.setStarttimechecked(startTime_forCheck);
					qingjiaCheckData.setEndtimechecked(endTime_forCheck);
					qingjiaCheckData.setLeavehour(validHour);
					qingjiaCheckData.setDakaRecord(dakaRecord);
					
				}else{	//不计算请假的日期 - 周六、日、法定节假日
					qingjiaCheckData.setStarttimechecked("");
					qingjiaCheckData.setEndtimechecked("");
					qingjiaCheckData.setDakaRecord("");
					qingjiaCheckData.setLeavehour(0);
				}
				qingjiaCheckData.setChecked(CHECKED);	//改变核定状态 为 已核定
			}
		}
		return qingjiaCheckData;
	}

	@Override
	public List<ZRQingJiaCheckDTData> executeCheckAll(int mainid) {
		List<ZRQingJiaCheckDTData> qingjiaCheckDTDataList_checked = null;
		
		List<ZRQingJiaCheckDTData> qingjiaCheckDTDataList = zrQJCheckDTService.queryListByMainID(mainid);
		if(qingjiaCheckDTDataList!=null){
			qingjiaCheckDTDataList_checked = new ArrayList<ZRQingJiaCheckDTData>();
			for(ZRQingJiaCheckDTData data : qingjiaCheckDTDataList){
				data = this.executeCheck(data.getId());
				if(data!=null){
					qingjiaCheckDTDataList_checked.add(data);
				}
			}
		}
		
		return qingjiaCheckDTDataList_checked;
	}
	
	/**
	 * 计算请假小时数
	 * @return
	 */
	private double countValidLeaveHour(String checkedStartTime,String checkedEndTime, double resHour){
		//** 处理 开始或结束时间落在午休时间内的情况
		if(checkedStartTime.compareTo(this.stNoonStartTime) >= 0 && checkedStartTime.compareTo(this.stNoonEndTime) <= 0){
			checkedStartTime = this.stNoonEndTime;
		}
		if(checkedEndTime.compareTo(this.stNoonStartTime) >= 0 && checkedEndTime.compareTo(this.stNoonEndTime) <= 0){
			checkedEndTime = this.stNoonStartTime;
		}
		//** 处理 开始或结束时间落在午休时间内的情况 end *****************//
		//** 计算休息时间
		if(checkedStartTime.compareTo(this.stNoonStartTime)<=0){
			if(checkedEndTime.compareTo(this.stNoonStartTime)<=0){
				resHour = 0;
			}else if(checkedEndTime.compareTo(this.stNoonEndTime)<=0){
				checkedEndTime = this.stNoonEndTime;
			}
		}else if(checkedStartTime.compareTo(this.stNoonEndTime)<=0){
			if(checkedEndTime.compareTo(this.stNoonEndTime)<=0){
				checkedStartTime = checkedEndTime;
				resHour = 0;
			}else{
				checkedStartTime = this.stNoonStartTime;
			}
		}else{
			resHour = 0;
		}
		
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
		log.error( " endHour - startHour - resHour  ：： " + endHour + " - " +startHour+ " - " +resHour);
		if(validHour<1){
			validHour = 1;
		}else if(validHour > 8){
			validHour = 8;
		}
		log.error("validHour :: " + validHour);
		return validHour;
	}
	
	/**
	 * 初始化考勤打卡记录
	 *  <p>请假核定中，获取以下数据作为考勤打卡记录
	 * 	<p> 1. 考勤打卡数据
	 *  <p> 2. 开通了移动考勤权限人员的移动打卡记录
	 *  
	 *  <p> ** 此方法为 获取 某个人、某天的考勤打卡记录
	 * @param userID - 用户ID
	 * @param workCode - 用户工号
	 * @param leaveDate - 请假日期
	 * 
	 * @return
	 */
	private List<String> LoadCheckOutTimeSet(int userID, String workCode,String leaveDate){
		log.error("开始获取打卡信息：  ");
		UserInfo userInfo = userInfoDao.queryByCode(workCode);
		
		int allowMobileAttend = 1;
		if(userInfo != null){
			allowMobileAttend = userInfo.getMobileAtten();
		}
		// **** 获取请假日期打卡时间集合
		checkoutUitl.setForwordDayCheckOutNode("02:00:00");
		CheckOutRecordSet checkOutSet = checkoutUitl.getPersonalCheckOutSetByDay(workCode, leaveDate, allowMobileAttend);	//获取打卡记录集合
		List<CheckOutRecord> checkOutRecordList = checkOutSet.getCheckOutRecordList();
		log.error("checkOutRecordList :: size - " + checkOutRecordList.size());
		
		// **** 整理打卡记录 
		List<String> checkOutTimeList = new ArrayList<String>();
		
		for(CheckOutRecord record : checkOutRecordList){
			//if(checkOutTimeList.indexOf(record.getTime())<0){
				if(record!=null){
					checkOutTimeList.add(record.getTime());
				}
			//}
		}
		
		for(String time : checkOutTimeList){	//去除空的打卡记录
			if(time == null || "".equals(time) || "null".equals(time)){
				checkOutTimeList.remove(time);
			}
		}
		
		Collections.sort(checkOutTimeList);	//打卡记录排序
		return checkOutTimeList;
	}
	
	/**
	 * 获取日期类型
	 *  -- 星期，节假日
	 * @param UserId
	 * @param leaveDate
	 */
	private String getWeekDayName(int userId,String leaveDate){
		String weekDayName = CalendarUtil.judgeWeekDay(leaveDate);
		HolidayConfig holidayCfg = hdayCfgDao.query(leaveDate, userId);
		if(holidayCfg!=null){
			weekDayName = hdaySelectDao.queryHolidaySelectItemInfo(holidayCfg.getJrmc()).getSelectName();
		}
		
		return weekDayName;
	}
	
	/**
	 * 请假时间为“上班调整时”，调整标准工作时间上班调整 日 的上下班时间
	 * @param userId - 用户ID
	 * @param leaveDate - 请假日期
	 */
	private void modifyStStartEndTime(int userId,String leaveDate){
		HolidayConfig holidayCfg = hdayCfgDao.query(leaveDate, userId);
		String weekDayName = null;
		if(holidayCfg!=null){
			weekDayName = hdaySelectDao.queryHolidaySelectItemInfo(holidayCfg.getJrmc()).getSelectName();
		}
		if(weekDayName!=null){
			if("上班调整".equals(weekDayName)){
				this.stStartTime = holidayCfg.getKssj();
				this.stEndTime = holidayCfg.getJssj();
			}
		}
	}
	
	/**
	 * 查询员工工号
	 * @return
	 */
	private String queryWorkCode(int userId){
		String _workcode = null;
		String sql = "select id,workcode from HrmResource where id = " + userId;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			_workcode = rs.getString("workcode");
		}
		return _workcode;
	}
}
