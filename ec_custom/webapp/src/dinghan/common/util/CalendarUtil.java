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
	 * @return
	 */
	public static String getCurDate(){
		String curdate = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		curdate = calendar.get(GregorianCalendar.YEAR) + "-";
		curdate += calendar.get(GregorianCalendar.MONTH)+1 + "-";;
		curdate += calendar.get(GregorianCalendar.DAY_OF_MONTH);
		return curdate;
	}
	
	/**
	 * 日期加减
	 * @param yearAmount - 年
	 * @param monthAmount - 月
	 * @param dateAmount - 日
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
	 * 获取星期
	 * @param dateStr - 日期字符串 [yyyy-MM-dd]
	 * @return
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
	
}
