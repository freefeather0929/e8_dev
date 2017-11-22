package dinghan.workflow.kq.dailydetail.gen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.kq.appdata.entity.ZRJiaBanAppData;
import dinghan.workflow.kq.appdata.entity.ZRLouDaKaAppData;
import dinghan.workflow.kq.appdata.service.ZRJiaBanAppDataService;
import dinghan.workflow.kq.appdata.service.ZRLouDaKaAppDataService;
import dinghan.workflow.kq.appdata.service.impl.ZRJiaBanAppDataServiceImpl;
import dinghan.workflow.kq.appdata.service.impl.ZRLouDaKaAppDataServiceImpl;
import dinghan.workflow.kq.checkout.bean.CheckOutRecord;
import dinghan.workflow.kq.checkout.bean.ManCheckOutInfo;
import dinghan.workflow.kq.checkout.service.ManCheckOutService;
import dinghan.workflow.kq.checkout.service.impl.ManCheckOutServiceImpl;
import dinghan.workflow.kq.checkout.util.CheckOutUtil;
import dinghan.workflow.kq.dailydetail.entity.KQDetailData;
import dinghan.workflow.kq.holiday.dao.HolidayConfigDao;
import dinghan.workflow.kq.holiday.dao.impl.HolidayConfigDaoImpl;
import dinghan.workflow.kq.holiday.entity.HolidayConfig;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRChuChaiCheckDTData;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRJiaBanCheckDTData;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRQingJiaCheckDTData;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRWaiChuCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRChuChaiCheckDTService;
import dinghan.workflow.kq.kqdt.service.ZRJiaBanCheckDTService;
import dinghan.workflow.kq.kqdt.service.ZRQingJiaCheckDTService;
import dinghan.workflow.kq.kqdt.service.ZRWaiChuCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRChuChaiCheckDTServiceImpl;
import dinghan.workflow.kq.kqdt.service.impl.ZRJiaBanCheckDTServiceImpl;
import dinghan.workflow.kq.kqdt.service.impl.ZRQingJiaCheckDTServiceImpl;
import dinghan.workflow.kq.kqdt.service.impl.ZRWaiChuCheckDTServiceImpl;
import dinghan.workflow.kq.kqtype.UserKQType;
import dinghan.workflow.kq.kqtype.ZRLeaveType;
import dinghan.workflow.kq.stworktime.StandardWorkTime;
import dinghan.workflow.kq.stworktime.StandardWorkTimeImpl;
import dinghan.workflow.kq.stworktime.StandardWorkTimeInfo;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
import weaver.conn.RecordSet;
import weaver.hrm.User;

/**
 * 中车考勤系统 考勤 明细 
 * @author zhangxiaoyu / 10593 - 2017-11-21
 *
 */
public class ZRKQDetailDataGen implements KQDetailDataGen{
	private Log log = LogFactory.getLog(ZRKQDetailDataGen.class.getName());
	private ZRQingJiaCheckDTService zrQingJiaCheckDTService = new ZRQingJiaCheckDTServiceImpl();
	private ZRJiaBanCheckDTService zrJiaBanCheckDTService = new ZRJiaBanCheckDTServiceImpl();
	private ZRJiaBanAppDataService zrJiaBanAppDataService = new ZRJiaBanAppDataServiceImpl();
	private ZRChuChaiCheckDTService zrChuChaiCheckDTService = new ZRChuChaiCheckDTServiceImpl();
	private ZRWaiChuCheckDTService zrWaiChuCheckDTService = new ZRWaiChuCheckDTServiceImpl();
	private ZRLouDaKaAppDataService zrLouDaKaAppDataService = new ZRLouDaKaAppDataServiceImpl();
	private ManCheckOutService manCheckOutService = new ManCheckOutServiceImpl();
	private StandardWorkTime stardardWorkTime = new StandardWorkTimeImpl();
	private HolidayConfigDao holidayDao = new HolidayConfigDaoImpl();
	
	private int dataCreator = 1;
	
	//private UserInfoDao userInfoDao = new UserInfoDaoImpl();
	private CheckOutUtil checkOutUtil = new CheckOutUtil();
	private List<String> dakaRecordList = new ArrayList<String>();	//打卡数据
	private String stStartTime;		//标准上班时间
	private String stEndTime;	//标准下班时间
	private String stNoonStartTime;	//标准午休开始时间
	private String stNoonEndTime;	//标准午休结束时间
	private double stNoonRestHours;		//标准午休小时数
	private int wangDaKaTime = 0;	//忘打卡次数
	private String firstDakaRecord="";		//第一次打卡记录
	private String lastDakaRecord="";	//最后一次打卡记录
	private double kuanggongHours = 0.0;	//旷工小时数
	private int chidaoMinute = 0;		//迟到分钟数
	private int zaotuiMinute = 0;	//早退分钟数
	
	/**
	 * 构造方法
	 * @param creatorID - 明细创建者的OA人员信息ID
	 */
	public ZRKQDetailDataGen(int dataCreatorID){
		this.dataCreator = dataCreatorID;
		checkOutUtil.setForwordDayCheckOutNode("02:00:00");
	}
	
	@Override
	public KQDetailData createDetailData(UserInfo userInfo, String kqDate){
		UserInfo _userInfo = userInfo;
		String _kqDate = kqDate;
		this.soluteStStartEndTime(_userInfo, _kqDate);	//处理标准开始、结束时间（不包括处理弹性制的标准开始时间）													// ** 弹性制的标准工作时间仅在计算迟到时使用
		
		KQDetailData kqDetailData = new KQDetailData();	//创建空明细
		
		this.updateValue_UserInfo(_userInfo,kqDetailData);	//用户信息赋值
		//this.dakaRecordList.clear();
		this.dakaRecordList = this.initCheckOutRecord(_userInfo, _kqDate);	//初始化打卡信息
		this.soluteLouDakaData(userInfo.getName(), kqDate);	//处理漏打卡明细数据
		
		/*
		 * 不同考勤类型的处理方式区别：
		 * 1 . 免打卡 仅统计 请假
		 * 2 . 长期驻外 仅 统计 请假 、加班
		 */
		this.soluteQingJiaDTData(userInfo.getName(), kqDate , kqDetailData);	//处理请假明细数据
		
		if(userInfo.getKaoQinType() != UserKQType.ATTENDANCE_FREE){	//免打卡类型判断
			kqDetailData = this.soluteJiaBanDTData(userInfo.getName(), _kqDate, kqDetailData);		//处理加班数据
			if(userInfo.getKaoQinType() != UserKQType.LONG_TREM_LOCAL){	//长期驻外类型判断
				this.soluteWaiChuDTData(userInfo.getName(), _kqDate);
				this.soluteChuChaiDTData(userInfo.getName(), _kqDate);
			}
		}
		/*
		 * 处理打卡记录
		 * 	- 1. 去除空值；
		 *  - 2. 去除早于 02:00:00 的打卡记录，这部分打卡记录算作前一天的
		 *  - 3. 进行排序
		 */
		for(String record : this.dakaRecordList){
			if(record == null || "".equals(record) || "02:00:00".compareTo(record)>=0){
				dakaRecordList.remove(record);
			}
		}
		Collections.sort(dakaRecordList);
		this.firstDakaRecord = "";
		this.lastDakaRecord = "";
		if(dakaRecordList.size() > 0){
			this.firstDakaRecord = dakaRecordList.get(0);
			this.lastDakaRecord = dakaRecordList.get(dakaRecordList.size()-1);
		}
		//免打卡 、长期驻外类型不进行迟到、早退、旷工计算
		if(userInfo.getKaoQinType() != UserKQType.ATTENDANCE_FREE && userInfo.getKaoQinType()!=UserKQType.LONG_TREM_LOCAL){	
			this.countChiDao_ZaoTui_KuangGong(_kqDate,this.firstDakaRecord, this.lastDakaRecord, _userInfo); //计算旷工、迟到、早退
		}
		
		if(!"".equals(this.firstDakaRecord)){
			this.firstDakaRecord = this.firstDakaRecord.substring(0, 5);
		}
		if(!"".equals(this.lastDakaRecord)){
			this.lastDakaRecord = this.lastDakaRecord.substring(0, 5);
		}
		
		kqDetailData.setKqrq(_kqDate);
		kqDetailData.setScdksj(this.firstDakaRecord);
		kqDetailData.setMcdksj(this.lastDakaRecord);
		kqDetailData.setCd(this.chidaoMinute);
		kqDetailData.setZaot(this.zaotuiMinute);
		kqDetailData.setKg(this.kuanggongHours);
		kqDetailData.setWdk(this.wangDaKaTime);
		kqDetailData.setFormmodeid(60);	//已经在Dao层指定模块ID
		kqDetailData.setModedatacreatedate(CalendarUtil.getCurDate());
		kqDetailData.setModedatacreater(this.dataCreator);
		kqDetailData.setModedatacreatertype(new User(this.dataCreator).getType());
		kqDetailData.setModedatacreatetime(CalendarUtil.getCurTime());
		
		return kqDetailData;
	}
	
	/**
	 * 计算迟到、早退、旷工
	 * @param kqDate
	 * @param firstDakaRecord
	 * @param lastDakaRecord
	 * @param userInfo
	 */
	private void countChiDao_ZaoTui_KuangGong(
			String kqDate, String firstDakaRecord, String lastDakaRecord, UserInfo userInfo){
		
		this.kuanggongHours = 0.0d;	//旷工小时数归零
		this.chidaoMinute = 0;		//迟到分钟数归零
		this.zaotuiMinute = 0;	//早退分钟数归零
		boolean isNeedAttendance = this.isNeedAttendance(kqDate,userInfo.getName());	//判断当前日期是否需要考勤
		log.error("isNeedAttendance :: " + isNeedAttendance);
		if(isNeedAttendance){	
			double restHour = this.stNoonRestHours;
			String _stStartTime = this.stStartTime;
			String _stEndTime = this.stEndTime;
			String _stNoonStartTime = this.stNoonStartTime;
			String _stNoonEndTime = this.stNoonEndTime;
			String _startTime_forCount = _stStartTime;
			String _endTime_forCount = _stEndTime;
			double cd_or_zt_Minute = 0.0d;
			double kuanggongHour = 0.0d;
			if("".equals(firstDakaRecord) || "".equals(lastDakaRecord) || firstDakaRecord.equals(lastDakaRecord)){
				_startTime_forCount = _stStartTime;
				_endTime_forCount = _stEndTime;
				kuanggongHour += this.countHoursBetween(_startTime_forCount, _endTime_forCount) - restHour;
			}else{
				if(userInfo.getKaoQinType() == UserKQType.FLEXIBLE){	//弹性制员工调整标准上班时间
					if(_stStartTime.compareTo(UserKQType.UN_FLEXIBLE_STARTTIME) < 0){
						_stStartTime = UserKQType.UN_FLEXIBLE_STARTTIME;
					}
				}
				//计算迟到、早退
				/*
				 * 计算 迟到、早退 的分钟数，大于 60 分钟则计算旷工小时数
				 * 一、计算迟到的规则：
				 * 	1. 如果第一次打卡时间 大于 标准上班开始时间，需要计算迟到
				 * 		（1） 确定迟到计算 的 开始时间
				 * 			1）迟到 开始时间 按标准上班时间计算；
				 * 		（2） 确定 迟到计算的 结束时间
				 * 			1）第一次打卡时间如果在 落在的 中午 休息 起止 时间之间，则以 午休开始时间 作为 计算迟到的结束时间
				 * 			2）如果第一次打卡时间 在 标准下班时间之后，则以标准下班时间作为 结算 迟到的结束时间
				 * 
				 * 二、计算早退的规则：
				 * 	1. 如果最后一次打卡时间 小于 标准下班时间，则需要计算早退
				 * 		（1） 确定计算早退的开始时间
				 * 			1）最后一次打卡时间如果落在午休 起止 时间之间，则以午休结束时间  作为 计算 早退 的 开始时间；
				 * 			2） 如果 最后 一次打卡时间 在 标准上班时间 之前，则以标准上班时间作为计算早退 的开始时间；
				 * 		（2） 确定计算早退的结束时间
				 * 			1） 早退 结束时间 按标准下班时间计算；
				 */		
				//log.error("第一次打卡：" + firstDakaRecord);
				//log.error("最后一次打卡：" + lastDakaRecord);
				//计算迟到
				if(firstDakaRecord.compareTo(_stStartTime)>0){	
					log.error("计算迟到：");
					_startTime_forCount = _stStartTime;
					_endTime_forCount = firstDakaRecord;
					if(firstDakaRecord.compareTo(_stNoonStartTime)>0){	
						 if(firstDakaRecord.compareTo(_stNoonEndTime)<=0){
							 _endTime_forCount = _stNoonStartTime;
							 restHour = 0.0;
						 }else if(firstDakaRecord.compareTo(_stEndTime)>0){	
							 _endTime_forCount = _stEndTime;
						 }
					}
					//log.error("计算迟到StartTime :: " + _startTime_forCount);
					//log.error("计算迟到EndTime :: " + _endTime_forCount);
					cd_or_zt_Minute = countMunuteBetween(_startTime_forCount, _endTime_forCount);
					//log.error("计算迟到的时间::" + cd_or_zt_Minute);
					if(cd_or_zt_Minute<1) {cd_or_zt_Minute = 1.0d;}
					if(cd_or_zt_Minute > 60){	//迟到超过60分钟计算旷工
						kuanggongHour += countHoursBetween(_startTime_forCount, _endTime_forCount) - restHour;
					}else{
						this.chidaoMinute = (int)cd_or_zt_Minute;
					}
				}
				//计算早退
				if(lastDakaRecord.compareTo(_stEndTime)<0){
					//log.error("计算早退");
					_startTime_forCount = lastDakaRecord;
					_endTime_forCount = _stEndTime;
					if(lastDakaRecord.compareTo(_stNoonEndTime)<0){
						if(lastDakaRecord.compareTo(_stNoonStartTime)>=0){
							_startTime_forCount = _stNoonEndTime;
							restHour = 0.0;
						}else if(lastDakaRecord.compareTo(_stStartTime)<0){
							_startTime_forCount = _stStartTime;
						}
					}
					
					//log.error("计算早退StartTime :: " + _startTime_forCount);
					//log.error("计算早退EndTime :: " + _endTime_forCount);
					cd_or_zt_Minute = countMunuteBetween(_startTime_forCount, _endTime_forCount);
					//log.error("计算早退的时间::" + cd_or_zt_Minute);
					if(cd_or_zt_Minute<1) {cd_or_zt_Minute = 1.0d;}
					if(cd_or_zt_Minute>60){		//早退超过60分钟计算旷工
						kuanggongHour += countHoursBetween(_startTime_forCount, _endTime_forCount) - restHour;
					}else{
						this.zaotuiMinute = (int)cd_or_zt_Minute;
					}
				}
			}
			this.kuanggongHours = kuanggongHour;
		}
	}
	
	/**
	 * 计算两个时间的小时数
	 *  - 以半小时为单位
	 * @return
	 */
	private double countHoursBetween(String startTime, String endTime){
		double tmpHours = 0.0d;
		double subHours = 0.0d;
		double tmpMillinTime = CalendarUtil.timeBetween(startTime, endTime);
		
		tmpHours = (tmpMillinTime/1000/60/60);
		subHours = tmpHours - (int)(tmpHours);
		
		if(subHours > 0.75){
			tmpHours = (int)(tmpHours) + 1.0d;
		}else if(subHours > 0.25){
			tmpHours = (int)(tmpHours) + 0.5d;
		}else{
			tmpHours = (int)(tmpHours);
		}
		return tmpHours;
	}
	
	/**
	 * 计算两个时间间的分钟数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private double countMunuteBetween(String startTime, String endTime){
		double minute = 0;
		//double subMinute = 0;
		double tmpMillinTime = CalendarUtil.timeBetween(startTime, endTime);
		minute = tmpMillinTime/1000/60;
		
		return minute;
	}
	/**
	 * 初始化打卡信息
	 * 	-- 步骤
	 * 		1. 获取打卡信息
	 * 		2. 获取手工考勤
	 * @param workcode
	 * @param dakaDate
	 * @return
	 */
	private List<String> initCheckOutRecord(UserInfo userInfo, String dakaDate){
		this.wangDaKaTime = 0;
		List<String> _dakaRecordList = new ArrayList<String>();
		
		List<CheckOutRecord> checkOutRecordList = 
				checkOutUtil.getPersonalCheckOutSetByDay(userInfo.getCode(), dakaDate, userInfo.getMobileAtten()).getCheckOutRecordList();
		
		for(CheckOutRecord record : checkOutRecordList){
			_dakaRecordList.add(record.getTime());
		}
		
		List<ManCheckOutInfo> manCheckOutInfoList = manCheckOutService.queryByUserCodeAndDate(userInfo.getCode(), dakaDate);
		
		if(manCheckOutInfoList != null){
			for(ManCheckOutInfo manCheckOutInfo : manCheckOutInfoList){
				if(!"".equals(manCheckOutInfo.getDakatime())){
					_dakaRecordList.add(manCheckOutInfo.getDakatime()+":00");
					if(manCheckOutInfo.getDakatype() == 1){
						this.wangDaKaTime += 1;
					}
				}
			}
		}
		
		return _dakaRecordList;
	}
	
	
	/**
	 * 处理标准上下班时间
	 * 	** 注意 ： 不包括弹性制员工上班时间的处理，弹性制员工开始时间仅在计算迟到、早退时进行处理
	 * @param userInfo
	 * @param kqDate
	 */
	private void soluteStStartEndTime(UserInfo userInfo,String kqDate){
		//处理标准上下班时间
		/*
		 * 思路： 
		 * 	1. 获取员工标准上下班时间
		 * 	2. 获取节假日信息
		 * 	3. 调整午休小时数
		 * 		 - 如果通过 1-3 步调整后的 标准上班时间 或 下班时间落在标准午休起止时间之间，则午休时间为0；
		 */
		this.stStartTime = userInfo.getStartWorkTime()+":59";
		this.stEndTime = userInfo.getEndWorkTime()+":00";
		this.stNoonStartTime = userInfo.getAmStartWorkTime()+":00";
		this.stNoonEndTime = userInfo.getPmEndWorkTime()+":00";
		this.stNoonRestHours = userInfo.getRest();
		
		HolidayConfig holidayCfg = holidayDao.query(kqDate, userInfo.getName());
		int holidayType = -1;
		if(holidayCfg != null){
			holidayType = holidayCfg.getJrmc();
			if(holidayType == 8){
				this.stStartTime = holidayCfg.getKssj()+":59";
				this.stEndTime = holidayCfg.getJssj()+":00";
			}
		}
		
		if(this.stStartTime.compareTo(this.stNoonStartTime)>=0 || this.stEndTime.compareTo(this.stNoonEndTime)<=0){
			this.stNoonRestHours = 0.0d;
		}
	}
	
	/**
	 * 考勤明细的用户信息赋值
	 * @param userInfo
	 * @param kqDetailData
	 * @return
	 */
	private KQDetailData updateValue_UserInfo(UserInfo userInfo,KQDetailData kqDetailData){
		KQDetailData _kqDetailData = kqDetailData;
		UserInfo _userInfo = userInfo;
		User user = new User(_userInfo.getName());
		_kqDetailData.setXm(_userInfo.getName());
		_kqDetailData.setGh(_userInfo.getCode());
		_kqDetailData.setKqlb_n(_userInfo.getKaoQinType());
		_kqDetailData.setZt(_userInfo.getInCompany()>0?"离职":"在职");
		_kqDetailData.setGw(Integer.valueOf(user.getJobtitle()));
		_kqDetailData.setYjbm_n(_userInfo.getDeptOneNameText());
		_kqDetailData.setEjbm_n(_userInfo.getDeptTwoNameText());
		_kqDetailData.setSsgs(_userInfo.getCompany());
		
		return _kqDetailData;
	}
	
	//判断是否需要考勤
	/*
	 * 获取星期，如果为周六、日则不需要考勤
	 * 获取节假日，如果为调整上班日则需要进行考勤
	 */
	private boolean isNeedAttendance(String kqDate,int userID){
		String weekDay = CalendarUtil.judgeWeekDay(kqDate);
		int holidayType = -1;
		boolean mark = true;
		if("星期六".equals(weekDay) || "星期日".equals(weekDay)){
			mark = false;
		}else{
			HolidayConfig holidayCfg = holidayDao.query(kqDate, userID);
			if(holidayCfg!=null){
				holidayType = holidayCfg.getJrmc();
				if(holidayType == 8){
					mark = true;
				}
			}
		}
		return mark;
	}
	
	/**
	 * 处理补漏打卡数据
	 * @param userId
	 * @param kqDate
	 */
	private void soluteLouDakaData(int userId, String kqDate){
		this.wangDaKaTime = 0;
		ZRLouDaKaAppData zrLouDakaAppData = zrLouDaKaAppDataService.queryByUserIDAndDate(userId, kqDate);
		
		if(zrLouDakaAppData!=null){
			if(judgeIsValidLouDaKa(zrLouDakaAppData)){
				if(zrLouDakaAppData.getFillCardType() == 1){	//忘带卡
					StandardWorkTimeInfo standardWorkTimeInfo = stardardWorkTime.queryByID(zrLouDakaAppData.getKqStStartEndTime());
					switch(zrLouDakaAppData.getForgetTimes()){
					 	case 0:	// 全天
					 		this.dakaRecordList.add(standardWorkTimeInfo.getStworkstarttime()+":00");
					 		this.dakaRecordList.add(standardWorkTimeInfo.getStreststarttime()+":00");
					 		this.dakaRecordList.add(standardWorkTimeInfo.getStrestendtime()+":00");
					 		this.dakaRecordList.add(standardWorkTimeInfo.getStworkendtime()+":00");
						break;
					 	case 1:	// 上午
					 		this.dakaRecordList.add(standardWorkTimeInfo.getStworkstarttime()+":00");
					 		this.dakaRecordList.add(standardWorkTimeInfo.getStreststarttime()+":00");
							break;
					 	case 2:	// 下午
					 		this.dakaRecordList.add(standardWorkTimeInfo.getStrestendtime()+":00");
					 		this.dakaRecordList.add(standardWorkTimeInfo.getStworkendtime()+":00");
							break;
					 }
				}else{
					if(!"".equals(zrLouDakaAppData.getFillTime1st())){
						this.dakaRecordList.add(zrLouDakaAppData.getFillTime1st()+":00");
						this.wangDaKaTime +=1;
					}
					if(!"".equals(zrLouDakaAppData.getFillTime2nd())){
						this.dakaRecordList.add(zrLouDakaAppData.getFillTime2nd()+":00");
						this.wangDaKaTime +=1;
					}
					if(!"".equals(zrLouDakaAppData.getFillTime3rd())){
						this.dakaRecordList.add(zrLouDakaAppData.getFillTime3rd()+":00");
						this.wangDaKaTime +=1;
					}
					if(!"".equals(zrLouDakaAppData.getFillTime4th())){
						this.dakaRecordList.add(zrLouDakaAppData.getFillTime4th()+":00");
						this.wangDaKaTime +=1;
					}
				}
			}
		}
	}
	
	/**
	 * 处理外出公干数据
	 * @param userID
	 * @param kqDate
	 * @param kqDetailData
	 * @return
	 */
	private void soluteWaiChuDTData(int userID, String kqDate){
		List<ZRWaiChuCheckDTData> zrWCCheckDTDataList = zrWaiChuCheckDTService.queryByUserIDAndDate(userID, kqDate);
		
		if(zrWCCheckDTDataList != null){
			//log.error("soluteWaiChuDTData 获取外出 公干 记录 数 :: " + zrWCCheckDTDataList.size());
			for(ZRWaiChuCheckDTData zrWCCheckData : zrWCCheckDTDataList){
				this.dakaRecordList.add(zrWCCheckData.getStarttimechecked()+":00");
				this.dakaRecordList.add(zrWCCheckData.getEndtimechecked()+":00");
			}
		}
	}
	
	/**
	 * 处理出差明细数据
	 * @param userID
	 * @param kqDate
	 * @param kqDetailData
	 * @return
	 */
	private void soluteChuChaiDTData(int userID, String kqDate){
		
		List<ZRChuChaiCheckDTData> zrCCCheckDTDataList = zrChuChaiCheckDTService.queryByUserIDAndDate(userID, kqDate);
		
		if(zrCCCheckDTDataList != null){
			//log.error("soluteChuChaiDTData 获取 出差 记录 数 :: " + zrCCCheckDTDataList.size());
			for(ZRChuChaiCheckDTData zrCCCheckData : zrCCCheckDTDataList){
				this.dakaRecordList.add(zrCCCheckData.getStartTimeChecked()+":00");
				this.dakaRecordList.add(zrCCCheckData.getEndTimeChecked()+":00");
			}
		}
	}
	
	/**
	 * 处理加班明细数据
	 * @param userID
	 * @param kqDate
	 * @param kqDetailData
	 * @return
	 */
	private KQDetailData soluteJiaBanDTData(int userID, String kqDate , KQDetailData kqDetailData){
		kqDetailData.setJbgs(0);
		kqDetailData.setJbztx(0);
		
		KQDetailData _kqDetailData = kqDetailData;
		List<ZRJiaBanCheckDTData> zrJiaBanCheckDTDataList = zrJiaBanCheckDTService.queryByUserIDAndDate(userID, kqDate);
		
		if(zrJiaBanCheckDTDataList != null){
			for(ZRJiaBanCheckDTData zrJiaBanCheckDTData : zrJiaBanCheckDTDataList){
				if(judgeIsValidJiaBan(zrJiaBanCheckDTData,kqDate) == true){
					if(zrJiaBanCheckDTData.getWhetherturnoff() == 1){	// 1 - 转调休 ；0 - 不转调休
						kqDetailData.setJbztx(kqDetailData.getJbztx() + zrJiaBanCheckDTData.getValidhour());
					}else{
						kqDetailData.setJbgs(kqDetailData.getJbgs() + zrJiaBanCheckDTData.getCheckedhour());
					}
				}
				if(zrJiaBanCheckDTData.getCheckeddate().equals(kqDate)){	//加班日期为当前计算的考勤日期时，核定的起止时间加入打卡时间
					this.dakaRecordList.add(zrJiaBanCheckDTData.getStarttimechecked()+":00");
					this.dakaRecordList.add(zrJiaBanCheckDTData.getEndtimechecked()+":00");
				}
			}
		}
		return _kqDetailData;
	}
	
	/**
	 * 判定是否为有效的加班明细
	 * @param jiabanCheckDTData
	 * @param kqDate
	 * @return
	 */
	private boolean judgeIsValidJiaBan(ZRJiaBanCheckDTData jiabanCheckDTData, String kqDate){
		ZRJiaBanAppData zrJiaBanAppData = zrJiaBanAppDataService.queryByID(jiabanCheckDTData.getMainid());
		boolean mark = false;
		
		int requestId = -1;
		int currentNodeType = -1;
		if(kqDate.equals(jiabanCheckDTData.getWfenddate())){
			if(zrJiaBanAppData != null){
				requestId = zrJiaBanAppData.getRequestID();
				
				String sql = "select requestid,currentnodetype from workflow_requestbase where requestid = '"+requestId+"'";
				RecordSet rs = new RecordSet();
				
				rs.executeSql(sql);
				while(rs.next()){
					currentNodeType = rs.getInt("currentnodetype");
				}
				
				if(currentNodeType == 3){	//流程当前环节为 “结束” 时 currentnodetype 字段 值 为 3
					mark = true;
				}
			}
		}
		return mark;
	}
	
	/**
	 * 判定是否为有效的补漏打卡申请数据
	 *
	 * @param loudakaAppData
	 * @return
	 * 
	 */
	private boolean judgeIsValidLouDaKa(ZRLouDaKaAppData loudakaAppData){
		boolean mark = false;
		
		int requestId = -1;
		int currentNodeType = -1;
		requestId = loudakaAppData.getRequestID();
		
		String sql = "select requestid,currentnodetype from workflow_requestbase where requestid = '"+requestId+"'";
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		while(rs.next()){
			currentNodeType = rs.getInt("currentnodetype");
		}
		
		if(currentNodeType == 3){	//流程当前环节为 “结束” 时 currentnodetype 字段 值 为 3
			mark = true;
		}
		return mark;
	}
	
	/**
	 * 处理请假明细数据
	 * @param userID
	 * @param kqDate
	 * @param kqDetailData
	 * @return {KQDetailData}
	 */
	private KQDetailData soluteQingJiaDTData(int userID, String kqDate , KQDetailData kqDetailData){
		KQDetailData _kqDetailData = kqDetailData;
		_kqDetailData.setSj(0);
		_kqDetailData.setBj(0);
		_kqDetailData.setTxj(0);
		_kqDetailData.setNxj(0);
		_kqDetailData.setHj(0);
		_kqDetailData.setCj(0);
		_kqDetailData.setCjj(0);
		_kqDetailData.setPcj(0);
		_kqDetailData.setLcj(0);
		_kqDetailData.setSangj(0);
		_kqDetailData.setJsj(0);
		_kqDetailData.setJyj(0);
		_kqDetailData.setTrgs(0);
		_kqDetailData.setGrgs(0);
		_kqDetailData.setHjj(0);
		_kqDetailData.setBrj(0);
		List<ZRQingJiaCheckDTData> zrQingJiaCheckDTDataList = zrQingJiaCheckDTService.queryByUserIDAndDate(userID, kqDate);
		if(zrQingJiaCheckDTDataList!=null){
			for(ZRQingJiaCheckDTData zrQingJiaData : zrQingJiaCheckDTDataList){
				switch(zrQingJiaData.getLeavecategoryid()){
					case ZRLeaveType.PERSONAL_LEAVE :
						_kqDetailData.setSj(_kqDetailData.getSj() + zrQingJiaData.getLeavehour());
					break;
					case ZRLeaveType.SICK_LEAVE:
						_kqDetailData.setBj(_kqDetailData.getBj() + zrQingJiaData.getLeavehour());
						break;
					case ZRLeaveType.ANNUAL_LEAVE:
						_kqDetailData.setNxj(_kqDetailData.getNxj() + zrQingJiaData.getLeavehour());
						break;
					case ZRLeaveType.BALANCE_LEAVE:	
						_kqDetailData.setTxj(_kqDetailData.getTxj() + zrQingJiaData.getLeavehour());
						break;
					case ZRLeaveType.MARRIAGE_HOLIDAY:	//婚嫁
						_kqDetailData.setHj(_kqDetailData.getHj() + zrQingJiaData.getLeavehour());
						break;	
					case ZRLeaveType.MATENITY_LEAVE:	//产假
						_kqDetailData.setCj(_kqDetailData.getCj() + zrQingJiaData.getLeavehour());
						break;	
					case ZRLeaveType.PREGNANCY_CHECK_LEAVE:	//产检假
						_kqDetailData.setCjj(_kqDetailData.getCjj() + zrQingJiaData.getLeavehour());
						break;	
					case ZRLeaveType.ACCOMP_DELIVERY_LEAVE:	//陪检假
						_kqDetailData.setPcj(_kqDetailData.getPcj() + zrQingJiaData.getLeavehour());
						break;	
					case ZRLeaveType.ABORTION_LEAVE:	//流产假
						_kqDetailData.setLcj(_kqDetailData.getLcj() + zrQingJiaData.getLeavehour());
						break;
					case ZRLeaveType.FUNERAL_LEAVE:	//丧假
						_kqDetailData.setSangj(_kqDetailData.getSangj() + zrQingJiaData.getLeavehour());
						break;
					case ZRLeaveType.PLANNED_BIRTH_LEAVE:	//计生假
						_kqDetailData.setJsj(_kqDetailData.getJsj() + zrQingJiaData.getLeavehour());
						break;	
					case ZRLeaveType.INJUNY_LEAVE_FOR_OTHER:	//他人工伤假
						_kqDetailData.setTrgs(_kqDetailData.getTrgs() + zrQingJiaData.getLeavehour());
						break;	
					case ZRLeaveType.INJUNY_LEAVE_PERSONAL:		//婚检假
						_kqDetailData.setHjj(_kqDetailData.getHjj() + zrQingJiaData.getLeavehour());
						break;	
					case ZRLeaveType.PRE_MARITAL_LEAVE:	//个人工伤假
						_kqDetailData.setGrgs(_kqDetailData.getGrgs() + zrQingJiaData.getLeavehour());
						break;
					case ZRLeaveType.BREASTFEEDING_LEAVE:	//哺乳假
						_kqDetailData.setBrj(_kqDetailData.getBrj() + zrQingJiaData.getLeavehour());
						break;
				}
				//请假起止时间写入打卡信息
				this.dakaRecordList.add(zrQingJiaData.getStarttimechecked()+":00");
				this.dakaRecordList.add(zrQingJiaData.getEndtimechecked()+":00");
			}
		}
		return _kqDetailData;
	}

	public void setDataCreator(int dataCreator) {
		this.dataCreator = dataCreator;
	}
	
}
