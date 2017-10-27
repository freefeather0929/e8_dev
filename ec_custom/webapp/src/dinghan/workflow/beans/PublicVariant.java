package dinghan.workflow.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PublicVariant {
	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date, String format) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(format);
		String str = df.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date StrToDate(String str, String format) throws Exception {
		// 要求str的组成格式和format一致
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = df.parse(str);
		return date;

	}

	/**
	 * 日期加减操作
	 * 
	 * @param args
	 *            -数组，顺序为：年、月、日、时、分、秒
	 */
	public static Date AdjustDateTime(Date date, int... args) throws Exception {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		switch (args.length) {
		case 6: // 秒
			calendar.add(Calendar.SECOND, args[5]);
		case 5: // 分
			calendar.add(Calendar.MINUTE, args[4]);
		case 4: // 时
			calendar.add(Calendar.HOUR, args[3]);
		case 3: // 日
			calendar.add(Calendar.DATE, args[2]);
		case 2: // 月
			calendar.add(Calendar.MONTH, args[1]);
		case 1: // 年
			calendar.add(Calendar.YEAR, args[0]);
		}
		return calendar.getTime();
	}

	public static String toUTF8(String ss) throws Exception {
		if (ss == null)
			ss = "";
		ss = new String(ss.getBytes("ISO-8859-1"), "UTF8");
		return ss;
	}

	/*	*//**
	 * 计算两个时间差
	 * 
	 * @param startTime
	 * @param endTime
	 * @return 返回豪秒数
	 * @throws Exception
	 */

	public static long getTimeDifference(String startTime, String endTime)
			throws Exception {
		long l = 0;
		Date dt = PublicVariant.StrToDate(startTime, "yyyy-MM-dd HH:mm");
		l = dt.getTime();
		dt = PublicVariant.StrToDate(endTime, "yyyy-MM-dd HH:mm");
		l -= dt.getTime();
		return Math.abs(l);
	}

	/*
	 * public static String getWeek(String date) throws Exception { String week
	 * = "";
	 * 
	 * LocalDate dd = LocalDate.parse(date, DateTimeFormatter.ISO_DATE); switch
	 * (dd.get(ChronoField.DAY_OF_WEEK)) { case 1: week = "星期一"; break; case 2:
	 * week = "星期二"; break; case 3: week = "星期三"; break; case 4: week = "星期四";
	 * break; case 5: week = "星期五"; break; case 6: week = "星期六"; break; case 7:
	 * week = "星期日"; break;
	 * 
	 * } return week; }
	 */
}
