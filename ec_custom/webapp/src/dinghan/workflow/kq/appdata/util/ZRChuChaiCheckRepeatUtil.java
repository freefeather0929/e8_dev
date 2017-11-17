package dinghan.workflow.kq.appdata.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.com.selectitem.timeitem.ZRChuChaiTimeSelect;
import dinghan.workflow.com.selectitem.timeitem.ZRChuChaiTimeSelectImpl;
import dinghan.workflow.kq.appdata.dao.ZRChuChaiAppDataDao;
import dinghan.workflow.kq.appdata.entity.ZRChuChaiAppData;
import dinghan.workflow.kq.appdata.service.ZRChuChaiAppDataService;
import dinghan.workflow.kq.appdata.service.impl.ZRChuChaiAppDataServiceImpl;
import weaver.conn.RecordSet;

/**
 * 中车出差申请单 检测重复 工具类
 * <p> 功能：
 * @author zhangxiaoyu / 10593 - 2017-10-28
 * 
 */
public class ZRChuChaiCheckRepeatUtil implements CheckRepeatApp<ZRChuChaiAppData>{
	private Log log = LogFactory.getLog(ZRChuChaiCheckRepeatUtil.class.getName());
	
	ZRChuChaiAppDataService zrChuChaiAppDataService = 
			new ZRChuChaiAppDataServiceImpl();
	
	ZRChuChaiTimeSelect timeSelect_pre_start = new ZRChuChaiTimeSelectImpl(
			ZRChuChaiTimeSelect.ZRChuChaiPreStartTimeFieldName
			);
	
	ZRChuChaiTimeSelect timeSelect_pre_end = new ZRChuChaiTimeSelectImpl(
			ZRChuChaiTimeSelect.ZRChuChaiPreEndTimeFieldName
			);
	
	private int userid;
	private String preStartDate; 
	private String preEndDate;
	private String preStartTime;
	private String preEndTime;
	private String appno;
	private ZRChuChaiAppData zrChuChaiAppData;
	
	@Override
	public ZRChuChaiAppData executeCheck(int id) throws Exception {
		this.zrChuChaiAppData = zrChuChaiAppDataService.queryByID(id);
		ZRChuChaiAppData repeatChuChaiAppData = null;				
		if(zrChuChaiAppData != null){
			userid = zrChuChaiAppData.getAppPsn();
			preStartDate = zrChuChaiAppData.getPreStartDate();
			preEndDate = zrChuChaiAppData.getPreEndDate();
			preStartTime = timeSelect_pre_start.queryTimeSelectItem(zrChuChaiAppData.getPreStartTime()).getSelectName();
			preEndTime = timeSelect_pre_end.queryTimeSelectItem(zrChuChaiAppData.getPreEndTime()).getSelectName();
			appno = zrChuChaiAppData.getAppNo();
			repeatChuChaiAppData = executeCheck(userid,preStartDate,preEndDate,preStartTime,preEndTime,appno);
		}
		return repeatChuChaiAppData;
	}
	
	/**
	 * 检测重复的出差申请
	 * @param userid
	 * @param preStartDate
	 * @param preEndDate
	 * @param preStartTime
	 * @param preEndTime
	 * @param appno - 检测重复时要排除的单号
	 * @return
	 */
	public ZRChuChaiAppData executeCheck(
			int userid, String preStartDate, String preEndDate, String preStartTime, String preEndTime, String appno) throws Exception {
		ZRChuChaiAppData repeatZRChuChaiAppData = null;
		
		String _appStartDate =  preStartDate;	//申请开始时间
		String _appEndDate = preEndDate;	//申请结束时间
		
		String _appStartTime = preStartTime;
		String _appEndTime = preEndTime;
		int _userid = userid;
		String _appNo = appno;
		
		String repeatStartTime;
		String repeatEndTime;

		String sql = "select id from " + ZRChuChaiAppDataDao.ZRChuChaiAppDataFormName + " where"
				+ " apppsn = '" + _userid + "'"
					+ " and realenddate >= '" + _appStartDate +"' and realstartdate <= '" +_appEndDate + "'"
						+ " and appno <> '' and appno <> '" + _appNo + "'";
		
		log.error("检测出差重复 sql ：： " + sql);
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			repeatZRChuChaiAppData = zrChuChaiAppDataService.queryByID(rs.getInt("id"));
			if(repeatZRChuChaiAppData!=null){
				//预计开始日期 与 其他某单的  预计结束  日期相同，
				// 或者  预计结束日期  与  其他  某单的  预计开始  日期相同时
				//   比较两单的时间，如果结束时间大于另一单的开始时间，并且 开始时间 小于 另一单的结束时间 则 查询到的申请为重复申请。
				if(repeatZRChuChaiAppData.getRealEndDate().equals(preStartDate) || repeatZRChuChaiAppData.getRealStartDate().equals(preEndDate)){
					repeatStartTime = timeSelect_pre_start.queryTimeSelectItem(repeatZRChuChaiAppData.getRealStartTime()).getSelectName();
					repeatEndTime = timeSelect_pre_end.queryTimeSelectItem(repeatZRChuChaiAppData.getRealEndTime()).getSelectName();
					if(repeatStartTime.compareTo(_appEndTime) < 0 && repeatEndTime.compareTo(_appStartTime) > 0){
						break;
					}
				}
			}
		}
		log.error("检测出差重复 repeatZRWaiChuAppData ：：isNull " + repeatZRChuChaiAppData == null);
		return repeatZRChuChaiAppData;
	}
}
