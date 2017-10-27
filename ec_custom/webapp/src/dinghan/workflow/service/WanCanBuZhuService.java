package dinghan.workflow.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.beans.JiaBan1;
import dinghan.workflow.beans.QingJia;
import dinghan.workflow.beans.UserInfo;
import dinghan.workflow.beans.WanCanBuZhu;
import dinghan.workflow.kq.checkout.bean.CheckOutRecordSet;
import dinghan.workflow.kq.checkout.util.CheckOutUtil;
import weaver.conn.RecordSet;
import weaver.dh.interfaces.dingHanTools;

public class WanCanBuZhuService {
	private static Log log = LogFactory.getLog(QingJia.class.getName());
	private dingHanTools dhTools = new dingHanTools();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	//private DaKaRecord dakaRecord;
	private CheckOutUtil checkOutUtil;
	private static final int buzhuMenoy = 20;
	private static final String dateTmp = "2016-01-01";
	private Calendar calendarTmp = new GregorianCalendar();
	
	//判断个人是否已经填写过晚餐补助
	public boolean isFilledApp(int buzhuYear, int curMonth, int userId ,String requestId){
		int wfNum = 0;
		boolean filled = false;
		
		RecordSet rs = new RecordSet();
		String _monthStr = curMonth>10?(""+curMonth):("0"+curMonth);

		String sql = "select count(id) from formtable_main_82 where apppsn = " + userId + " and appmonth = '"+buzhuYear+"-"+_monthStr+"'";
		sql += " and requestId <> '"+requestId+"'";
		
		rs.executeSql(sql);
		
		while(rs.next()){
			wfNum = rs.getInt(1);
		}
		if(wfNum>0){
			filled = true;
		}
		return filled;
	}
	
	/**
	 * 功能：计算某个员工在前月21日志当月20日的晚餐补助
	 * @param Year
	 * @param Month
	 * @param UserCode
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<WanCanBuZhu> getAllWanCanBuZhuRecord(int buzhuYear,int curMonth,int userId,String userCode) throws Exception{
		
		log.error("======================== 生成晚餐补助： 用户ID :: " + userId + "工号 :: " +userCode+ " ==============================");
		
		UserInfo userInfo = new UserInfo(userId, userCode);
		
		boolean flag = false;
		String weekDay = "";
		int buzhuTimes = 1;
		
		ArrayList<WanCanBuZhu> wanCanBuZhuList = new ArrayList<WanCanBuZhu>();
		
		String endDateStr = buzhuYear + "-";
		endDateStr += curMonth>10?curMonth:"0"+curMonth;
		
		endDateStr += "-21";
		
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.setTime(sdf.parse(endDateStr));

		String startDateStr = buzhuYear + "-";
		startDateStr += (curMonth-1)>10?(curMonth-1):"0"+(curMonth-1);
		startDateStr += "-21";
		
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.setTime(sdf.parse(startDateStr));
		
		HashMap<String, ArrayList<JiaBan1>> jiabanMap = new JiaBanService().getAllJiaBanRecordByUserCode(startDateStr, endDateStr, userCode);
		
		while(startCalendar.compareTo(endCalendar)<0){
			flag = false;
			buzhuTimes = 1;
			Calendar lastTime = new GregorianCalendar();
			Calendar startTime = new GregorianCalendar();
			if(jiabanMap.get(sdf.format(startCalendar.getTime())) != null ) {	//填写加班的情况不计算补助
				startCalendar.add(Calendar.DATE, 1);
				continue;
			} else {
				
				HashMap<String, String> jjrMap = dhTools.getJJR(String.valueOf(userId), sdf.format(startCalendar.getTime()));
				checkOutUtil = new CheckOutUtil();
				CheckOutRecordSet checkOutRecord =  checkOutUtil.getPersonalCheckOutSetByDay(userInfo.getCode(), sdf.format(startCalendar.getTime()), userInfo.getMobileAtten());
				
		 		checkOutRecord.getEndTime();
				
				//this.dakaRecord = new DaKaRecord(userCode, sdf.format(startCalendar.getTime()));
				
				if(checkOutRecord.getEndTime().equals("")){
					startCalendar.add(Calendar.DATE, 1);
					continue;
				}

				lastTime.setTime(sdfTime.parse(dateTmp + " " + checkOutRecord.getEndTime()));
				startTime.setTime(sdfTime.parse(dateTmp + " " + checkOutRecord.getStartTime()));
				
				if(!jjrMap.isEmpty()){
					if("8".equals(jjrMap.get(userId+"_jrlx"))){		//调整上班日
						calendarTmp.setTime(sdfTime.parse(dateTmp + " 19:59"));
						if(lastTime.compareTo(calendarTmp) > 0){	//下班超过晚8:00	计算补助
							flag = true;
						}
					}else{	//法定节假日
						calendarTmp.setTime(sdfTime.parse(dateTmp + " 12:59"));
						if(lastTime.compareTo(calendarTmp) > 0){	//下午上班时间以后下班打卡
							calendarTmp.setTime(sdfTime.parse(dateTmp + " 19:59"));
							if(lastTime.compareTo(calendarTmp) > 0){	//下班超过晚8:00	计算补助
								flag = true;
								calendarTmp.setTime(sdfTime.parse(dateTmp + " 10:00"));
								if(startTime.compareTo(calendarTmp) < 0){	//上班时间小于上午10:00，增加午餐补助
									buzhuTimes = 2;
								}else {
									if(startTime.compareTo(calendarTmp) > 0){	//上班时间大于上午10:00，不计算补助
										flag = false;
									}else{
										flag = true;
									}
								}	
							}
						}
					}
					
					switch(jjrMap.get(userId+"_jrlx")){
						case "0": 
							weekDay = "元旦";
							break;
						case "1":
							weekDay = "清明节";
							break;
						case "2":
							weekDay = "劳动节";
							break;
						case "3":
							weekDay = "端午节";
							break;
						case "4":
							weekDay = "中秋节";
							break;
						case "5":
							weekDay = "国庆节";
							break;
						case "6":
							weekDay = "春节";
							break;
						case "7":
							weekDay = "抗战胜利日";
							break;
						case "8":
							weekDay = "调整上班";
							break;
						case "9":
							weekDay = "调整休息";
							break;
					}
					
				}else{
					int week_day = startCalendar.get(Calendar.DAY_OF_WEEK);
					switch(week_day){
					  case 1 :	//周日
					  case 7 :	//周六
						  calendarTmp.setTime(sdfTime.parse(dateTmp + " 12:59"));
							if(lastTime.compareTo(calendarTmp) > 0){	//下午上班时间以后下班打卡
								calendarTmp.setTime(sdfTime.parse(dateTmp + " 19:59"));
								if(lastTime.compareTo(calendarTmp) > 0){	//下班超过晚8:00	计算补助
									flag = true;
									calendarTmp.setTime(sdfTime.parse(dateTmp + " 10:00"));
									if(startTime.compareTo(calendarTmp) < 0){	//上班时间小于上午10:00，增加午餐补助
										buzhuTimes = 2;
									}else {
										if(startTime.compareTo(calendarTmp) > 0){	//上班时间大于上午10:00，不计算补助
											flag = false;
										}else{
											flag = true;
										}
									}	
								}
							}
					  break;
					  
					  default :	//工作日
						calendarTmp.setTime(sdfTime.parse(dateTmp + " 19:59"));
						if(lastTime.compareTo(calendarTmp) > 0){	//下班超过晚8:00	计算补助	
							flag = true;
						}
					}
					
					switch(week_day){
						case 1:
							weekDay = "星期日";
							break;
						case 2:
							weekDay = "星期一";
							break;
						case 3:
							weekDay = "星期二";
							break;
						case 4:
							weekDay = "星期三";
							break;
						case 5:
							weekDay = "星期四";
							break;
						case 6:
							weekDay = "星期五";
							break;
						case 7:
							weekDay = "星期六";
							break;
					}
				}

				if(flag){	
					WanCanBuZhu wcBuZhu = createWanCanBuZhu(
							checkOutRecord.getStartTime(),
							checkOutRecord.getEndTime(),
							sdf.format(startCalendar.getTime()),
							buzhuMenoy*buzhuTimes,
							weekDay
						);
					wanCanBuZhuList.add(wcBuZhu);
				}
			}
			startCalendar.add(Calendar.DATE, 1);
		}
		log.error("======================== 生成晚餐补助： 用户ID :: " + userId + "工号 :: " +userCode+ "生成完毕  ==============================");
		return wanCanBuZhuList;
	}
	
	/**
	 * 创建晚餐补助实例
	 * @param firstTime - 开始时间
	 * @param lastTime - 结束时间
	 * @param dateStr - 日期
	 * @param buzhuMenoy - 补助金额
	 * @param weekDay - 星期
	 * @return WanCanBuZhu
	 */
	private WanCanBuZhu createWanCanBuZhu(String firstTime,String lastTime, String dateStr, double buzhuMenoy, String weekDay){
		
		WanCanBuZhu wcBuZhu = new WanCanBuZhu();
		
		wcBuZhu.setDakaRecord(firstTime+"--"+lastTime);
		wcBuZhu.setDate(dateStr);
		wcBuZhu.setMenoy(buzhuMenoy);
		wcBuZhu.setWeekDay(weekDay);
		
		return wcBuZhu;
	}
	
	
}
