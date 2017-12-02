package weaver.dh.interfaces;

import java.text.ParseException;
import java.util.Calendar;
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
				//考勤账套属于中车轨道的，不进行更新
			if("20".equals(userInfo.getCompany()) || "21".equals(userInfo.getCompany()) || "22".equals(userInfo.getCompany())){
				continue;
			}
			try {
				lastTiaoXiu = qingjiaServier.countLastTiaoXiuorNianXiuHour(userInfo.getName(), QingJiaService.TiaoXiu, "", curMonth);
				lastNianXiu = qingjiaServier.countLastTiaoXiuorNianXiuHour(userInfo.getName(), QingJiaService.NianXiu, "", curMonth);
				
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
