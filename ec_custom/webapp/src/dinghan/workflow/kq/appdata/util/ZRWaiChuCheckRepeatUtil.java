package dinghan.workflow.kq.appdata.util;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import dinghan.workflow.com.selectitem.timeitem.ZRWaiChuTimeSelect;
import dinghan.workflow.com.selectitem.timeitem.ZRWaiChuTimeSelectImpl;
import dinghan.workflow.kq.appdata.dao.ZRWaiChuAppDataDao;
import dinghan.workflow.kq.appdata.entity.ZRWaiChuAppData;
import dinghan.workflow.kq.appdata.service.ZRWaiChuAppDataService;
import dinghan.workflow.kq.appdata.service.impl.ZRWaiChuAppDataServiceImpl;
import weaver.conn.RecordSet;

/**
 * 中车外出公干申请单 检测重复 工具类
 * @author zhangxiaoyu / 10593 - 2017-10-22
 * 
 */
public class ZRWaiChuCheckRepeatUtil implements CheckRepeatApp<ZRWaiChuAppData>{
	private Log log = LogFactory.getLog(ZRWaiChuCheckRepeatUtil.class.getName());
	private ZRWaiChuAppDataService wcAppDataService = new ZRWaiChuAppDataServiceImpl();
	
	private ZRWaiChuTimeSelect timeSelect_start = 
			new ZRWaiChuTimeSelectImpl(ZRWaiChuTimeSelect.ZRWaiChuStartTimeFieldName);
	
	private ZRWaiChuTimeSelect timeSelect_end = 
			new ZRWaiChuTimeSelectImpl(ZRWaiChuTimeSelect.ZRWaiChuEndTimeFieldName);
	
	@Override
	public ZRWaiChuAppData executeCheck(int wcAppDataId) {
		
		ZRWaiChuAppData zrWaiChuAppData = wcAppDataService.queryByID(wcAppDataId);
		
		ZRWaiChuAppData repeatZRWaiChuAppData = null;
		
		if(zrWaiChuAppData != null){
	
			String appStartDate =  zrWaiChuAppData.getPrestartdate();	//申请开始时间
			String appEndDate = zrWaiChuAppData.getPreenddate();	//申请结束时间
			
			String appStartTime = timeSelect_start.queryTimeSelectItem(zrWaiChuAppData.getPrestarttime()).getSelectName();
			String appEndTime = timeSelect_end.queryTimeSelectItem(zrWaiChuAppData.getPreendtime()).getSelectName();
		
			repeatZRWaiChuAppData = executeCheck(zrWaiChuAppData.getAppspn(),appStartDate,appEndDate,appStartTime,appEndTime,zrWaiChuAppData.getAppno());

		}
		return repeatZRWaiChuAppData;
	}

	/**
	 * 检测重复的中车-外出公干申请
	 * @param userid
	 * @param appStartDate - 预计开始日期
	 * @param appEndDate - 预计结束日期
	 * @param appStartTime - 预计开始时间
	 * @param appEndTime - 预计结束时间
	 * @param appNo - 申请编号
	 * @return
	 */
	public ZRWaiChuAppData executeCheck(int userid,String appStartDate, String appEndDate, String appStartTime, String appEndTime, String appNo) {
		
		ZRWaiChuAppData repeatZRWaiChuAppData = null;
		
		String _appStartDate =  appStartDate;	//申请开始时间
		String _appEndDate = appEndDate;	//申请结束时间
		
		String _appStartTime = appStartTime;
		String _appEndTime = appEndTime;
		
		String _appNo = appNo;
		
		String repeatStartTime;
		String repeatEndTime;

		String sql = "select id from " + ZRWaiChuAppDataDao.ZRWaiChuAppDataFormName + " where"
				+ " appspn = '" + userid + "'"
				+ " and preenddate >= '" + _appStartDate +"' and prestartdate <= '" +_appEndDate + "'"
					+ " and appno <> '' and appno <> '" + _appNo + "'";
		
		log.error("检测外出重复 sql ：： " + sql);
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			repeatZRWaiChuAppData = wcAppDataService.queryByID(rs.getInt("id"));
			if(repeatZRWaiChuAppData!=null){
				//预计开始日期 与 其他某单的  预计结束  日期相同，
				// 或者  预计结束日期  与  其他  某单的  预计开始  日期相同时
				//   比较两单的时间，如果结束时间大于另一单的开始时间，并且 开始时间 小于 另一单的结束时间 则 查询到的申请为重复申请。
				if(repeatZRWaiChuAppData.getPreenddate().equals(appStartDate) || repeatZRWaiChuAppData.getPrestartdate().equals(appEndDate)){
					repeatStartTime = timeSelect_start.queryTimeSelectItem(repeatZRWaiChuAppData.getPrestarttime()).getSelectName();
					repeatEndTime = timeSelect_end.queryTimeSelectItem(repeatZRWaiChuAppData.getPreendtime()).getSelectName();
					if(repeatStartTime.compareTo(_appEndTime) < 0 && repeatEndTime.compareTo(_appStartTime) > 0){
						break;
					}
				}
			}
		}
		log.error("检测外出重复 repeatZRWaiChuAppData ：：isNull " + repeatZRWaiChuAppData == null);
		
		return repeatZRWaiChuAppData;
		
	}
	
}
