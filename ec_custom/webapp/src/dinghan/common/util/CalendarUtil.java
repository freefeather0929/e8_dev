package dinghan.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期时间工具类
 * @author zhangxiaoyu / 10593 - 2017-10-23
 * 
 */
public class CalendarUtil {
	/**
	 * 获取当前日期
	 * @return {String} 当前日期 - 格式 yyyy-MM-dd
	 */
	public static String getCurDate(){
		String curdate = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		curdate = calendar.get(GregorianCalendar.YEAR)+"";
		curdate += "-";
		curdate += calendar.get(GregorianCalendar.MONTH)+1>=10?(calendar.get(GregorianCalendar.MONTH)+1):("0"+(calendar.get(GregorianCalendar.MONTH)+1));
		curdate += "-";
		curdate += calendar.get(GregorianCalendar.DAY_OF_MONTH)>9?calendar.get(GregorianCalendar.DAY_OF_MONTH):("0"+calendar.get(GregorianCalendar.DAY_OF_MONTH));
		return curdate;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurTime(){
		String curTime = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		curTime = calendar.get(Calendar.HOUR_OF_DAY)>9?(""+calendar.get(Calendar.HOUR_OF_DAY)):("0"+calendar.get(Calendar.HOUR_OF_DAY));
		curTime += ":";
		curTime += calendar.get(Calendar.MINUTE)>9?(""+calendar.get(Calendar.MINUTE)):("0"+calendar.get(Calendar.MINUTE));
		curTime += ":";
		curTime += calendar.get(Calendar.SECOND)>9?(""+calendar.get(Calendar.SECOND)):("0"+calendar.get(Calendar.SECOND));
		return curTime;
	}
	
	/**
	 * 日期加减
	 * @param curDate - 需要移动的日期
	 * @param yearAmount - 年份
	 * @param monthAmount - 月份
	 * @param dateAmount - 日
	 * @return 
	 */
	public static String moveDate (String curDate,int yearAmount,int monthAmount,int dateAmount) {
		
		String _curDate = curDate; 
		String movedDate = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendar = Calendar.getInstance();
		
		try {
			calendar.setTime(sdf.parse(_curDate));
			calendar.add(Calendar.YEAR, yearAmount);
			calendar.add(Calendar.MONTH, monthAmount);
			calendar.add(Calendar.DATE, dateAmount);
			movedDate = sdf.format(calendar.getTime());
			
		} catch (ParseException e) {
			movedDate = null;
			e.printStackTrace();
		}
		return movedDate;
	}
	
	/**
	 * 时间增减
	 * @param curTime - 格式 [HH:mm:ss]
	 * @param hourAmount - 需要增减的小时数
	 * @param minuteAmount - 需要增减的分钟数
	 * @param secondAmount - 需要增减的秒数
	 * @return
	 */
	public static String moveTime (String curTime,int hourAmount,int minuteAmount,int secondAmount) {
		//String _curTime = "2017-07-01 " + curTime + ":00";
		String _curTime = curTime+ ":00";
		String movedDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		
		try {
			calendar.setTime(sdf.parse(_curTime));
			calendar.add(Calendar.HOUR_OF_DAY, hourAmount);
			calendar.add(Calendar.MINUTE, minuteAmount);
			calendar.add(Calendar.SECOND, secondAmount);
			movedDate = sdf.format(calendar.getTime());
		} catch (ParseException e) {
			movedDate = null;
			e.printStackTrace();
		}
		return movedDate;
	}
	
	/**
	 * 获取星期
	 * @param dateStr - 日期字符串 [yyyy-MM-dd]
	 * @return - 星期日、星期一 ...
	 */
	public static String judgeWeekDay(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String weekDay;
		Calendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(sdf.parse(dateStr));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		switch(calendar.get(Calendar.DAY_OF_WEEK)){
			case Calendar.SUNDAY:
				weekDay = "星期日";
				break;
			case Calendar.MONDAY :
				weekDay = "星期一";
				break;
			case Calendar.TUESDAY :
				weekDay = "星期二";
				break;
			case Calendar.WEDNESDAY:
				weekDay = "星期三";
				break;
			case Calendar.THURSDAY:
				weekDay = "星期四";
				break;
			case Calendar.FRIDAY:
				weekDay = "星期五";
				break;
			case Calendar.SATURDAY:
				weekDay = "星期六";
				break;
			default :
				weekDay = "";
		}	
		return weekDay;
	}
	
	/**
	 * 计算两个时间的差值毫秒数
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException 
	 */
	public static long timeBetween(String startTime, String endTime){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar cs = GregorianCalendar.getInstance();
		Calendar ce = GregorianCalendar.getInstance();
		try {
			cs.setTime(sdf.parse(startTime));
			ce.setTime(sdf.parse(endTime));
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
		long sTimeM = cs.getTimeInMillis();
		long eTimeM = ce.getTimeInMillis();
		return eTimeM - sTimeM;
	}
}