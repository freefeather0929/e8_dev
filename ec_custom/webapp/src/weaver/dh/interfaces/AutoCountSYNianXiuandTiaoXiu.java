package weaver.dh.interfaces;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import dinghan.workflow.beans.UserInfo;
import dinghan.workflow.service.KQUserInfoService;
import dinghan.workflow.service.QingJiaService;
import weaver.conn.RecordSet;
import weaver.interfaces.schedule.BaseCronJob;

/**
 * 自动计算剩余年休、调休
 * 计算当前日期剩余的调休和年休假小时数（建议每月1日执行，以计算月末的剩余调休）
 * @author zhangxiaoyu / 10593 - 2017-04-22
 * 
 */
public class AutoCountSYNianXiuandTiaoXiu extends BaseCronJob {

	@Override
	public void execute() {
		
		QingJiaService qingjiaServier = new QingJiaService();
		KQUserInfoService userInfoService = new KQUserInfoService();
		RecordSet recordSet = new RecordSet();
		
		Calendar calendar = Calendar.getInstance();
		
		int curMonth = calendar.get(Calendar.MONTH);  //获取前一个月份，传入计算剩余年休、调休方法
		
		double lastTiaoXiu = 0.00;
		double lastNianXiu = 0.00;
		
		List<UserInfo> userInfoList = userInfoService.getAllUserInfo();
		
		String sql = "";
		
		for(UserInfo userInfo : userInfoList){
			
			try {
				lastTiaoXiu = qingjiaServier.countLastTiaoXiuorNianXiuHour(userInfo.getName(), QingJiaService.TiaoXiu, "", curMonth);
				lastNianXiu = qingjiaServier.countLastTiaoXiuorNianXiuHour(userInfo.getName(), QingJiaService.NianXiu, "", curMonth);
				
				if(curMonth == 12){
					
					double syNXHour = 0;
					int curYear = calendar.get(Calendar.YEAR);
					int joinYear = Integer.parseInt(userInfo.getJoinDate().substring(0, 7));
					int yearIn = curYear - joinYear;
					//double nianXiu_neo = 0;
					if(yearIn >= 20){
						syNXHour = 120;
					}else if(yearIn >= 10){
						syNXHour = 80;
					}else if(yearIn>0){
						syNXHour = 40;
					}else{	
						Calendar _curMonth = GregorianCalendar.getInstance();
						Calendar _joinMonth = GregorianCalendar.getInstance();
						//SimpleDateFormat sdf = ;
						try {
							_curMonth.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(curYear+"-"+curMonth+"-31"));
							_joinMonth.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(userInfo.getJoinDate()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
						long betwean = _curMonth.getTimeInMillis() - _joinMonth.getTimeInMillis();
						double daysForCount = (double)betwean/1000/60/60/24/365*5;
						daysForCount = new BigDecimal(daysForCount).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
						syNXHour = 8*daysForCount;
					}
					lastTiaoXiu += lastNianXiu;
					lastNianXiu = syNXHour;
				}
				
				sql = "update uf_hr_userinfo1 set SYTiaoXiuJia = '"+lastTiaoXiu+"',"
						+ "SYNianXiuJia = '"+lastNianXiu+"' where Name = " + userInfo.getName();
				
				recordSet.executeSql(sql);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		qingjiaServier = null;
		userInfoService = null;
		recordSet = null;
		
	}

}
