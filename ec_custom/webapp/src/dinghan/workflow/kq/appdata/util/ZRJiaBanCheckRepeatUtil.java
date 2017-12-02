package dinghan.workflow.kq.appdata.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.appdata.dao.ZRJiaBanAppDataDao;
import dinghan.workflow.kq.appdata.entity.ZRJiaBanAppData;
import dinghan.workflow.kq.appdata.service.ZRJiaBanAppDataService;
import dinghan.workflow.kq.appdata.service.impl.ZRJiaBanAppDataServiceImpl;
import dinghan.workflow.kq.kqdt.dao.ZRJiaBanCheckDTDao;
import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * 中车加班申请单 检测重复 工具类
 * <p> 功能：检测存在日期-时间重叠加班明细的加班申请
 * @author zhangxiaoyu / 10593 - 2017-11-02
 * 
 */
public class ZRJiaBanCheckRepeatUtil {
	private Log log = LogFactory.getLog(ZRJiaBanCheckRepeatUtil.class.getName());
	
	private ZRJiaBanAppDataService zrJiaBanAppDataService = new ZRJiaBanAppDataServiceImpl();
	
	/**
	 * 检测重复的加班申请
	 * @param userid
	 * @param preStartDate
	 * @param preEndDate
	 * @param preStartTime
	 * @param preEndTime
	 * @param appno - 检测重复时要排除的单号
	 * @return
	 */
	public List<ZRJiaBanAppData> executeCheck(
				int userid, String overTimeDate, String preStartTime, String preEndTime, String appno
			) throws Exception {
		
		List<ZRJiaBanAppData> repeatJiaBanList = null;
		
		ZRJiaBanAppData repeatZRJiaBanAppData = null;
		
		String _overTimeDate =  overTimeDate;	//申请日期
		String _appStartTime = preStartTime;	//申请开始时间
		String _appEndTime = preEndTime;
		
		String tmpStartTime;
		String tmpEndTime;
		
		int _userid = userid;
		String _appNo = appno;
		
		String sql = "SELECT " +
					" m.applicant," +
						" m.oddnum," +
							" d.id," +
								" d.mainid," +
									" d.checkeddate," +
										" d.starttime," +
											" d.starttimechecked," +
												" d.endtime," +
													" d.endtimechecked" +
				" FROM " +
					ZRJiaBanCheckDTDao.ZRJiaBanCheckDTDataFormName +" d, " +
						ZRJiaBanAppDataDao.ZRJiaBanAppDataFormName+" m " +
				" WHERE " +
					" d.mainid = m.id " +
							" AND d.checkeddate = '"+_overTimeDate+"' " +
								" AND m.applicant = '" + _userid +"' "+
									" AND m.oddnum <> ''" +
										" AND m.oddnum <> '"+_appNo+"'";
		log.error("检测加班重复 sql ：： " + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			repeatJiaBanList = new ArrayList<ZRJiaBanAppData>();
		}
		
		while(rs.next()){
			tmpStartTime = rs.getString("starttime");
			if(!"".equals(Util.null2String(rs.getString("starttimechecked")))){
				tmpStartTime = rs.getString("starttimechecked");
			}
			tmpEndTime = rs.getString("endtime");
			if(!"".equals(Util.null2String(rs.getString("endtimechecked")))){
				tmpEndTime = rs.getString("endtimechecked");
			}
			
			if(_appStartTime.compareTo(tmpEndTime) >= 0 || _appEndTime.compareTo(tmpStartTime) <= 0){
				continue;
			}else{
				repeatZRJiaBanAppData = zrJiaBanAppDataService.queryByID(rs.getInt("mainid"));
				if(repeatZRJiaBanAppData != null){
					if(repeatJiaBanList.indexOf(repeatZRJiaBanAppData) < 0){
						repeatJiaBanList.add(repeatZRJiaBanAppData);
					}
				}
			}
		}
		
		return repeatJiaBanList;
	}
	
}
