package dinghan.workflow.kq.appdata.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.appdata.dao.ZRLouDaKaAppDataDao;
import dinghan.workflow.kq.appdata.entity.ZRLouDaKaAppData;
import dinghan.workflow.kq.appdata.service.ZRLouDaKaAppDataService;
import dinghan.workflow.kq.appdata.service.impl.ZRLouDaKaAppDataServiceImpl;
import weaver.conn.RecordSet;

/**
 * 中车补漏刷卡申请单 检测重复 工具类
 * <p> 功能：
 * @author zhangxiaoyu / 10593 - 2017-10-22
 * 
 */
public class ZRLouDaKaCheckRepeatUtil implements CheckRepeatApp<ZRLouDaKaAppData>{
	private Log log = LogFactory.getLog(ZRWaiChuCheckRepeatUtil.class.getName());
	ZRLouDaKaAppDataService zrLouDaKaAppDataService = 
			new ZRLouDaKaAppDataServiceImpl();
	
	private String fillDaKaDate;
	private int fillDaKaType;
	private String fillMonth;
	
	@Override
	public ZRLouDaKaAppData executeCheck(int id) {
		ZRLouDaKaAppData repeatZRLouDaKaAppData = null;
		ZRLouDaKaAppData zrLouDaKaAppData = zrLouDaKaAppDataService.queryByID(id);
		fillDaKaDate = zrLouDaKaAppData.getFillCardDate();	//补漏打卡日期
		fillDaKaType = zrLouDaKaAppData.getFillCardType();		//补漏打卡类型
		
		repeatZRLouDaKaAppData = this.executeCheckSameDate(zrLouDaKaAppData.getAppsn(),fillDaKaDate,zrLouDaKaAppData.getAppno());
		if(repeatZRLouDaKaAppData != null){
			return repeatZRLouDaKaAppData;
		}
		
		if(fillDaKaType == 1){
			repeatZRLouDaKaAppData = this.executeCheck(zrLouDaKaAppData.getAppsn(),fillDaKaDate,zrLouDaKaAppData.getAppno());
		}
		
		return repeatZRLouDaKaAppData;
	}
	
	/**
	 * 检当月重复的补忘带卡单据
	 * @param fillCardDate
	 * @param appno - 检测重复时要排除的单号
	 * @return
	 */
	public ZRLouDaKaAppData executeCheck(int userid, String fillCardDate, String appno) {
		ZRLouDaKaAppData repeatZRLouDaKaAppData = null;
		//ZRLouDaKaAppData zrLouDaKaAppData = zrLouDaKaAppDataService.queryByID(id);
		fillDaKaDate = fillCardDate;
		fillMonth = fillDaKaDate.substring(0, 7);
		
		String sql = "select top 1 id from " + ZRLouDaKaAppDataDao.ZRLouDaKaAppDataFormName + ""
				+ " where fillcarddate like '" + fillMonth + "%'"
						+ " and fillcardtype = '1' and appno <> '' and appno <> '" +appno+ "' and apppsn = " + userid;
		//log.error("ZRLouDaKaAppData 检当月重复的补忘带卡单据  sql :: " + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			repeatZRLouDaKaAppData = zrLouDaKaAppDataService.queryByID(rs.getInt("id"));
		}
		
		return repeatZRLouDaKaAppData;
	}
	
	/**
	 * 检当是否已存在补卡日期是同一天的申请单
	 * @param userid - 用户id
	 * @param fillCardDate 
	 * @param appno - 检测重复时要排除的单号
	 * @return
	 */
	public ZRLouDaKaAppData executeCheckSameDate(int userid, String fillCardDate, String appno) {
		ZRLouDaKaAppData repeatZRLouDaKaAppData = null;
		//ZRLouDaKaAppData zrLouDaKaAppData = zrLouDaKaAppDataService.queryByID(id);
		fillDaKaDate = fillCardDate;
		
		String sql = "select top 1 id from " + ZRLouDaKaAppDataDao.ZRLouDaKaAppDataFormName
				+ " where fillcarddate = '" + fillDaKaDate + "'"
						+ " and appno <> '' and appno <> '" +appno+ "' and apppsn = " + userid;
		//log.error("ZRLouDaKaAppData 检当是否已存在补卡日期是同一天的申请单  sql :: " + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			repeatZRLouDaKaAppData = zrLouDaKaAppDataService.queryByID(rs.getInt("id"));
		}
		
		return repeatZRLouDaKaAppData;
	}
}
