package dinghan.workflow.kq.summary.gen;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.kq.dailydetail.entity.KQDetailData;
import dinghan.workflow.kq.dailydetail.service.KQDetialService;
import dinghan.workflow.kq.dailydetail.service.impl.KQDetialServiceImpl;
import dinghan.workflow.kq.holiday.dao.HolidayConfigDao;
import dinghan.workflow.kq.holiday.dao.impl.HolidayConfigDaoImpl;
import dinghan.workflow.kq.holiday.entity.HolidayConfig;
import dinghan.workflow.kq.kqtype.UserKQType;
import dinghan.workflow.kq.summary.dao.KQSummaryDataDao;
import dinghan.workflow.kq.summary.entity.KQSummaryData;
import dinghan.workflow.kq.summary.service.KQSummaryDataService;
import dinghan.workflow.kq.summary.service.impl.KQSummaryDataServiceImpl;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
import weaver.conn.RecordSet;
import weaver.hrm.User;

/**
 * 中车考勤汇总生成
 * @author zhangxiaoyu / 10593 - 2017-11-22
 * 
 */
public class ZRKQSummaryDataGen implements KQSummaryDataGen {
	private Log log = LogFactory.getLog(ZRKQSummaryDataGen.class.getName());
	private KQDetialService kqDetailService = new KQDetialServiceImpl();
	private KQSummaryDataService kqSummaryService = new KQSummaryDataServiceImpl();
	private List<KQDetailData> kqDetailDataList = null;
	private HolidayConfigDao holidayDao = new HolidayConfigDaoImpl();
	private String startDateForCount;
	private String endDateForCount;
	private String userJoinDate;
	private String userLeaveDate;
	private int isLeave = 0;
	private double effectDays = 0;
	private double wangdakaTime = 0;
	private int chidao_less_10 = 0;
	private double chidaoTime = 0;
	private double zaotuiTime = 0;
	private double kuanggongHour = 0;
	private double jiabanHour = 0;
	private double jiabanToTiaoXiu = 0;
	private double totleChidaoMinute = 0;
	private double totleZaotuiMinute = 0;
	//private double leftNianxiuHour;
	//private double leftTiaoxiuHour;
	
	private int userId;
	
	private int dataCreator;
	
	public ZRKQSummaryDataGen(int dataCreatorID){
		this.dataCreator = dataCreatorID;
	}
	
	@Override
	public KQSummaryData createSummaryData(UserInfo userInfo, String kqMonth) {
		KQSummaryData kqSummaryData = null;
		
		this.startDateForCount = kqMonth + "-01";
		this.endDateForCount = kqMonth + "-31";
		this.userId = userInfo.getName();
		this.isLeave = userInfo.getInCompany();
		this.userJoinDate = userInfo.getJoinDate();
		this.userLeaveDate = userInfo.getLeaveDate();
		
		kqDetailDataList = kqDetailService.queryListByUserAndDate(userId, startDateForCount, endDateForCount);
		
		if(kqDetailDataList != null){
			
			//创建空汇总
			kqSummaryData = new KQSummaryData();	
			/*
			 * 赋值人员信息
			 */
			kqSummaryData.setGh(userInfo.getCode());
			kqSummaryData.setXm(userId);
			kqSummaryData.setYjbm(userInfo.getDeptOneNameText());
			kqSummaryData.setEjbm(userInfo.getDeptTwoNameText());
			kqSummaryData.setKqlb(userInfo.getKaoQinType());
			kqSummaryData.setRzzt(userInfo.getInCompany()==0?"在职":"离职");
			kqSummaryData.setSsgs(userInfo.getCompany());
			if(this.getJobTitle(userId)>-1){
				kqSummaryData.setGw(this.getJobTitle(userId));
			}
			//旷工工时归零
			this.kuanggongHour = 0;
			//加班工时归零
			this.jiabanHour = 0;
			//加班转调休工时归零
			this.jiabanToTiaoXiu = 0;
			//十分钟内迟到归零
			this.chidao_less_10 = 0;
			//早退次数归零
			this.zaotuiTime = 0;
			//迟到次数归零
			this.chidaoTime = 0;
			//早迟到总分钟数归零
			this.totleChidaoMinute = 0;
			//早退总分钟数数归零
			this.totleZaotuiMinute = 0;
			//忘打卡次数归零
			this.wangdakaTime = 0;
			//应出勤天数归零
			this.effectDays = 0;
			//各类请假小时数归零
			kqSummaryData.setSj(0);
			kqSummaryData.setBj(0);
			kqSummaryData.setTxj(0);
			kqSummaryData.setNxj(0);
			kqSummaryData.setHj(0);
			kqSummaryData.setCj(0);
			kqSummaryData.setCjj(0);
			kqSummaryData.setPcj(0);
			kqSummaryData.setLcj(0);
			kqSummaryData.setSangj(0);
			kqSummaryData.setJsj(0);
			kqSummaryData.setJyj(0);
			kqSummaryData.setTrgs(0);
			kqSummaryData.setGrgs(0);
			kqSummaryData.setHjj(0);
			kqSummaryData.setBrj(0);
			
			for(KQDetailData dtData : this.kqDetailDataList){
				if(dtData.getKqrq().compareTo(this.userJoinDate) <0 ){
					continue;
				}
				if(isLeave>0){
					if(dtData.getKqrq().compareTo(this.userLeaveDate) >0 ){
						continue;
					}
				}
				/*
				 * 不同考勤类型的处理方式区别：
				 * 1 . 免打卡 仅统计 请假
				 * 2 . 长期驻外 仅 统计 请假 、加班
				 */
				if(isNeedAttendance(dtData.getKqrq(),userId)){
					this.effectDays += 1;	//增加
					kqSummaryData = this.soluteQingJiaData(kqSummaryData,dtData);	//处理请假数据
				}
				if(userInfo.getKaoQinType() != UserKQType.ATTENDANCE_FREE ){	//排除免打卡的员工
					this.jiabanHour += dtData.getJbgs();
					this.jiabanToTiaoXiu += dtData.getJbztx();
					
					if(userInfo.getKaoQinType() != UserKQType.LONG_TREM_LOCAL ){	//排除处理长期驻外的员工
						this.wangdakaTime += dtData.getWdk();		//统计忘打卡次数
						this.kuanggongHour += dtData.getKg();	//统计旷工共工时
						/*
						 * 计算迟到次数
						 *  1. 每个月 有 3 次 10 分钟（包含）内的迟到机会；
						 *  2. 超过30分钟的迟到 记为 2 次；
						 *  3. 30分钟（包含）内的迟到记为 1次；
						 */
						
						if(dtData.getCd() > 30){
							//this.chi
							this.chidaoTime += 2;
						}else if(dtData.getCd() > 10){
							this.chidaoTime += 1;
						}else if(dtData.getCd() >0){
							this.chidao_less_10 += 1;
						}
						
						/*
						 * 计算早退
						 */
						if(dtData.getZaot() > 30){
							this.zaotuiTime +=2;
						}else if(dtData.getZaot()>0){
							this.zaotuiTime +=1;
						}
						
						this.totleChidaoMinute += dtData.getCd();
						this.totleZaotuiMinute += dtData.getZaot();
					}
				}	
			}
			if((chidao_less_10 - 3) > 0){
				this.chidaoTime += (chidao_less_10 - 3);
			}
			//log.error("create 汇总月份 " + kqMonth);
			kqSummaryData.setHzyf(kqMonth);
			kqSummaryData.setYcqts(this.effectDays);	//应出勤天数赋值
			kqSummaryData.setWdk(this.wangdakaTime);
			kqSummaryData.setCd(this.chidaoTime);
			kqSummaryData.setZt(this.zaotuiTime);
			kqSummaryData.setKg(this.kuanggongHour);
			kqSummaryData.setJbgs(this.jiabanHour);
			kqSummaryData.setJbztx(this.jiabanToTiaoXiu>-1?this.jiabanToTiaoXiu:0);
			kqSummaryData.setCdTime(this.totleChidaoMinute);
			kqSummaryData.setZtTime(this.totleZaotuiMinute);
			kqSummaryData = soluteNianXiu_TiaoXiu(kqMonth,userId,kqSummaryData);
			//kqSummaryData.set
			kqSummaryData.setKqrq(CalendarUtil.getCurDate());
			kqSummaryData.setFormmodeid(KQSummaryDataDao.KQSummaryModeID);
			kqSummaryData.setModedatacreatedate(CalendarUtil.getCurDate());
			kqSummaryData.setModedatacreatetime(CalendarUtil.getCurTime());
			kqSummaryData.setModedatacreater(this.dataCreator);
			kqSummaryData.setModedatacreatertype(new User(this.dataCreator).getType());
		}
		return kqSummaryData;
	}
	
	/**
	 * 
	 * 判断是否需要考勤
	 * @param kqDate
	 * @param userID
	 * @return
	 */
	private boolean isNeedAttendance(String kqDate,int userID){
		/*
		 * 判断方法：
		 * 获取星期，如果为周六、日则不需要考勤
		 * 获取节假日，如果为调整上班日则需要进行考勤
		 */
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
	 * 处理请假数据
	 * 
	 */
	private KQSummaryData soluteQingJiaData(KQSummaryData kqSummaryData, KQDetailData dtData){
		KQSummaryData _kqSummaryData = kqSummaryData;
		/*
		 * 各加别工时归零
		 */
		_kqSummaryData.setSj(_kqSummaryData.getSj() + dtData.getSj());
		_kqSummaryData.setBj(_kqSummaryData.getBj() + dtData.getBj());
		_kqSummaryData.setTxj(_kqSummaryData.getTxj() + dtData.getTxj());
		_kqSummaryData.setNxj(_kqSummaryData.getNxj() + dtData.getNxj());
		_kqSummaryData.setHj(_kqSummaryData.getHj() + dtData.getHj());
		_kqSummaryData.setCj(_kqSummaryData.getCj() + dtData.getCj());
		_kqSummaryData.setCjj(_kqSummaryData.getCjj() + dtData.getCjj());
		_kqSummaryData.setPcj(_kqSummaryData.getPcj() + dtData.getPcj());
		_kqSummaryData.setLcj(_kqSummaryData.getLcj() + dtData.getLcj());
		_kqSummaryData.setSangj(_kqSummaryData.getSangj() + dtData.getSangj());
		_kqSummaryData.setJsj(_kqSummaryData.getJsj() + dtData.getJsj());
		_kqSummaryData.setJyj(_kqSummaryData.getJyj() + dtData.getJyj());
		_kqSummaryData.setTrgs(_kqSummaryData.getTrgs() + dtData.getTrgs());
		_kqSummaryData.setGrgs(_kqSummaryData.getGrgs() + dtData.getGrgs());
		_kqSummaryData.setHjj(_kqSummaryData.getHjj() + dtData.getHjj());
		_kqSummaryData.setBrj(_kqSummaryData.getBrj() + dtData.getBrj());
		
		return _kqSummaryData;
	}

	/**
	 * 处理年休假、调休假
	 * @param kqMont
	 * @param userId
	 */
	private KQSummaryData soluteNianXiu_TiaoXiu(String kqMonth,int userId,KQSummaryData kqSummaryData){
		KQSummaryData _kqSummaryData = kqSummaryData;
		double preMonthNianxiu = 0;	//前月剩余年休
		double preMonthTiaoXiu = 0;		//前月剩余调休
		double syNXHour = 0;
		String preMonth = CalendarUtil.moveDate(kqMonth+"-01", 0, -1, 0).substring(0, 7);
		String monthStr = kqMonth.substring(5);	//截取当前计算的考勤月份 的 月 部分
		//log.error("preMonth :: " + preMonth);
		KQSummaryData preMonthSummaryData = kqSummaryService.queryByUserIDAndMonth(userId, preMonth);
		
		/*
		 * 12月份时的年休和调休处理
		 *  1. 年休假 按照 员工 的 入职日期 至 下一年 之间的 年 数
		 *  	（1） 超过 20 年（包含） ， 年休假为 15天， 即赋值 120 小时
		 *  		（2） 小于 20年 超过 10 年（包含）， 年休假 为 10天， 赋值 80小时
		 *  			（3）小于 10年 年休假为 5 天，赋值 40 小时
		 *  				（4）本月计算后的年休假 增加至剩余调休假 中
		 *  2. 调休假 计算 与其他月份相同，仅需要增加 计算后的 剩余年休假
		 *  
		 */
		if("12".equals(monthStr)){	//计算12月考勤时要重新计算年休假
			int curYear = Integer.parseInt(kqMonth.substring(0, 4),10);
			int joinYear = Integer.parseInt(userJoinDate.substring(0, 4),10);
			int yearIn = curYear - joinYear;
			//double nianXiu_neo = 0;
			if(yearIn >= 20){
				syNXHour = 120;
			}else if(yearIn >= 10){
				syNXHour = 80;
			}else if(yearIn>0){
				syNXHour = 40;
			}else{	
				Calendar curMonth = GregorianCalendar.getInstance();
				Calendar joinMonth = GregorianCalendar.getInstance();
				//SimpleDateFormat sdf = ;
				try {
					curMonth.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(kqMonth+"-31"));
					joinMonth.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(this.userJoinDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				long betwean = curMonth.getTimeInMillis() - joinMonth.getTimeInMillis();
				double daysForCount = (double)betwean/1000/60/60/24/365*5;
				daysForCount = new BigDecimal(daysForCount).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
				syNXHour = 8*daysForCount;
			}
			_kqSummaryData.setSynx(syNXHour);
			
			if(preMonthSummaryData != null){
				//上一个月度的汇总存在的情况，获取上个月结余年休 和 调休
				preMonthNianxiu = preMonthSummaryData.getSynx();
				preMonthTiaoXiu = preMonthSummaryData.getSytx();
				//log.error("preMonthNianxiu :: " + preMonthNianxiu);
				//log.error("preMonthTiaoXiu :: " + preMonthTiaoXiu);
				_kqSummaryData.setSytx(preMonthTiaoXiu - _kqSummaryData.getTxj() + _kqSummaryData.getJbztx() + (preMonthNianxiu - _kqSummaryData.getNxj()));
			}else{
				_kqSummaryData.setSytx(_kqSummaryData.getJbztx() - _kqSummaryData.getTxj());
			}
			
		}else{
			if(preMonthSummaryData != null){
				//上一个月度的汇总存在的情况，获取上个月结余年休 和 调休
				preMonthNianxiu = preMonthSummaryData.getSynx();
				preMonthTiaoXiu = preMonthSummaryData.getSytx();
				//log.error("preMonthNianxiu :: " + preMonthNianxiu);
				//log.error("preMonthTiaoXiu :: " + preMonthTiaoXiu);
				//log.error("preMonthTiaoXiu - _kqSummaryData.getTxj() + _kqSummaryData.getJbztx()"
						//+ preMonthTiaoXiu + " - " + _kqSummaryData.getTxj() +" - "+ _kqSummaryData.getJbztx());
				_kqSummaryData.setSynx(preMonthNianxiu - _kqSummaryData.getNxj());
				_kqSummaryData.setSytx(preMonthTiaoXiu - _kqSummaryData.getTxj() + _kqSummaryData.getJbztx());
			}else{
				_kqSummaryData.setSynx(0);
				_kqSummaryData.setSytx(0);
			}
		}
		
		return _kqSummaryData;
	}
	
	/**
	 * 获取岗位名称
	 * @param userId - 人员用户ID
	 * @return
	 */
	private int getJobTitle(int userId){
		int jobTitleID = -1;
		String slq = "select jobtitle from HrmResource where id = " + userId;
		RecordSet rs = new RecordSet();
		rs.executeSql(slq);
		while(rs.next()){
			jobTitleID = rs.getInt("jobtitle");
		}
		return jobTitleID;
	}

	public void setDataCreator(int dataCreator) {
		this.dataCreator = dataCreator;
	}
	
	
}
