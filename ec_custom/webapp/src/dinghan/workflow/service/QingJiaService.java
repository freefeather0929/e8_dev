package dinghan.workflow.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.weaver.formmodel.util.DateHelper;

import dinghan.workflow.beans.JiaBan1;
import weaver.conn.RecordSet;

/**
 * 对请假的数据进行操作
 * -- 目前主要用于计算获取最新的剩余调休假、年休假小时数
 * @author zhangxiaoyu / 10593 - 2017-04-26
 * 
 * 
 */
public class QingJiaService {
	
	private Log log = LogFactory.getLog(QingJiaService.class.getName());
	
	private JiaBanService jbService = new JiaBanService();
	private static final String kq_collect_formName = "uf_kqhz";	//考勤汇总表名
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat monthSdf = new SimpleDateFormat("yyyy-MM");
	
	/**
	 * 事假对应的考勤类型
	 */
	public static final int PersonalLeave = 0;
	
	/**
	 * 年休假对应的考勤类型
	 */
	public static final int NianXiu = 2;	
	
	/**
	 * 调休假对应的考勤类型
	 */
	public static final int TiaoXiu = 3;	
	
	/**
	 * 计算某月调休或年休剩余小时数，如果计算当前月份并且当前月份并未结束，则计算的是最新的调休假或年休假
	 * @return
	 * @param - userId: 用户ID
	 * @param - leaveType: 请假类别
	 * @param - appNo: 需要排除计算的流水号
	 * @param - month: 需要计算的月份
	 * @throws ParseException 
	 * 
	 */
	public double countLastTiaoXiuorNianXiuHour(int userId, int leaveType, String appNo, int month) throws ParseException{	
		
		String curMonthStr = DateHelper.getCurrentYear() + "-" + (month>9?month:"0"+month);	//要计算的月份
		
		Calendar calendar = new GregorianCalendar();
		
		calendar.setTime(monthSdf.parse(curMonthStr));
		calendar.add(Calendar.MONTH, -1);
		
		String preMonthStr = monthSdf.format(calendar.getTime());
		
		log.error("前一个月份 :: " + preMonthStr + " :: type :: " + leaveType);
		
		//获取前月剩余调休假
		String sql = "select sytx,synx from " + kq_collect_formName +" where hzyf = '" + preMonthStr + "'"
						+ " and xm = " + userId;
		
		log.error("执行slq :: " + sql);
		
		RecordSet rs = new RecordSet();
		
		double preMonthSY = 0.00;
		double addedHourByOverTime = 0.00;
		double usedHourByLeave = 0.00;
		
		String startDate = curMonthStr + "-01";
		String endDate = curMonthStr + "-31";
		
		log.error("获取的开始日期 :: " + startDate);
		log.error("获取的结束日期 :: " + endDate);
		
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			while(rs.next()){
				if(leaveType == TiaoXiu){
					if(rs.getString("sytx").trim().equals("")){
						preMonthSY = 0.00;
					}else{
						preMonthSY = rs.getDouble("sytx");
					}
					log.error("前月剩余调休 :: " + preMonthSY);
				}else if(leaveType == NianXiu){
					if(rs.getString("synx").trim().equals("")){
						preMonthSY = 0.00;
					}else{
						preMonthSY = rs.getDouble("synx");
					}
					log.error("前月剩余年休 :: " + preMonthSY);
				}
			}
		}else{
			preMonthSY = 0.00;
		}
		
		if(leaveType == TiaoXiu){
			//获取所有在本月内核定后的加班转调休的小时数
			String workcode = "";
			sql = "select id,workcode from HrmResource where id = "+ userId;
			rs.executeSql(sql);
			
			while(rs.next()){
				workcode = rs.getString("workcode");
			}
			Map<String,ArrayList<JiaBan1>> jiabanMap = new HashMap<String, ArrayList<JiaBan1>>();
			jiabanMap = jbService.getAllJiaBanRecordByUserCode(startDate, endDate, workcode);
			if(!jiabanMap.isEmpty()){
				Calendar startCalendar = new GregorianCalendar();
				startCalendar.setTime(sdf.parse(startDate));
				Calendar endCalendar = new GregorianCalendar();
				endCalendar.setTime(sdf.parse(endDate));
				List<JiaBan1> jiabanList;
				
				while(startCalendar.compareTo(endCalendar) < 1){
					log.error("获取加班日期  :: " + sdf.format(startCalendar.getTime()));
					if(jiabanMap.containsKey(sdf.format(startCalendar.getTime()))){
						jiabanList = jiabanMap.get(sdf.format(startCalendar.getTime()));
						
						for(JiaBan1 jb : jiabanList){
							if(jb.getSfztx() == 0 && jb.getHdzt() != 0)
								addedHourByOverTime += jb.getHdgs();
						}	
					}
					startCalendar.add(Calendar.DATE, 1);
				}
			}
		}
		
		usedHourByLeave = this.queryAppTiaoXiuorNianXiuHour(userId, startDate, endDate, appNo, leaveType);
		
		log.error("请假的小时数为 :: " + usedHourByLeave);
		log.error("加班的小时数为 :: " + addedHourByOverTime);
		return preMonthSY + addedHourByOverTime - usedHourByLeave;
	}
	
	/**
	 * 获取一段时间内个人申请的调休假或年休假小时数（提交即算为申请）
	 * @param -- appNo ： 当前申请流水号需要排除
	 * @param -- 
	 * @return
	 * @throws ParseException 
	 */
    public double queryAppTiaoXiuorNianXiuHour(int userID, String startDate, String endDate, String appNo, int leaveType) throws ParseException{
    	
    	double tiaoXiuAppHour = 0.00;
    	
    	//计算当月申请的调休假小时数
		String	sql = "SELECT m.appnom, d.mainid, d.rq, d.kssj, d.jssj, d.hdgs, d.qjlb FROM formtable_main_89_dt3 d, formtable_main_89 m"
					+ " WHERE d.mainid = m.id"
						+ " and m.proposer = " + userID
							+ " and m.appnom <> ''"
								+ " and m.appnom <> '"+appNo+"'"
									+ " and qjlb = " + leaveType
										+ " and rq >= '" +startDate+ "'"
											+ "and rq <= '" +endDate+ "'";
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			tiaoXiuAppHour += this.getTiaoXiuorNianXiuHours(rs.getString("kssj"), rs.getString("jssj"));
		}

    	return tiaoXiuAppHour;
    }
    
    /**
     * 计算单条年休假或调休假申请明细小时数
     * @param startTimeStr
     * @param endTimeStr
     * @return
     * @throws ParseException
     */
    private double getTiaoXiuorNianXiuHours(String startTimeStr, String endTimeStr) throws ParseException{
    	SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    	String tempDateStr = "2017-01-01";
		Calendar startTime = new GregorianCalendar();
		Calendar endTime = new GregorianCalendar();
    	
		long sTimeMil = 0; //开始时间（毫秒）
		long eTimeMil = 0; //结束时间（毫秒）
		long cTimeMil = 0; //相差时间（毫秒）
		long mTime = 0; //相差分钟
		
		double cHour = 0; //相差小时数
    	
		startTime.setTime(sdfTime.parse(tempDateStr + " " +startTimeStr));
		endTime.setTime(sdfTime.parse(tempDateStr + " " +endTimeStr));
		
		sTimeMil = startTime.getTimeInMillis();
		eTimeMil = endTime.getTimeInMillis();
		cTimeMil = eTimeMil - sTimeMil;
		
		mTime = cTimeMil/(1000*60);
		cHour = mTime/60 + (double)mTime%60/60;
		
		if(cHour > 8){
			cHour = 8.0d;
    	}
		
		return cHour;
    }
    
}
