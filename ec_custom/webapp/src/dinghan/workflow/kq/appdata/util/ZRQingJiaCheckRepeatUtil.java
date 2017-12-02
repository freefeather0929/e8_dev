package dinghan.workflow.kq.appdata.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.appdata.dao.ZRQingJiaAppDataDao;
import dinghan.workflow.kq.appdata.entity.ZRQingJiaAppData;
import dinghan.workflow.kq.appdata.service.ZRQingJiaAppDataService;
import dinghan.workflow.kq.appdata.service.impl.ZRQingJiaAppDataServiceImpl;
import dinghan.workflow.kq.kqdt.dao.ZRQingJiaCheckDTDao;
import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * 中车请假申请单 检测重复 工具类
 * <p> 功能：检测存在日期-时间重叠加班明细的请假申请
 * @author zhangxiaoyu / 10593 - 2017-11-16
 * 
 */
public class ZRQingJiaCheckRepeatUtil {
	private Log log = LogFactory.getLog(ZRQingJiaCheckRepeatUtil.class.getName());
	
	private ZRQingJiaAppDataService zrQJAppDataService = new ZRQingJiaAppDataServiceImpl();
	/**
	 * 检测重复的请假申请
	 * @param userid
	 * @param preStartDate
	 * @param preEndDate
	 * @param preStartTime
	 * @param preEndTime
	 * @param appno - 检测重复时要排除的单号
	 * @return
	 */
	public List<ZRQingJiaAppData> executeCheck(
				int userid, String preStartDate, String preEndDate, String preStartTime, String preEndTime, String appno
			) throws Exception {
		
		List<ZRQingJiaAppData> repeatQingJiaList = null;
		
		ZRQingJiaAppData repeatZRQingJiaAppData = null;
		
		String _preStartDate = preStartDate;	//申请开始日期
		String _preEndDate = preEndDate;	//申请结束日期
		String _appStartTime = preStartTime;	//申请开始时间
		String _appEndTime = preEndTime;	//申请结束时间
		String _checkedDateTmp;
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
					ZRQingJiaCheckDTDao.ZRQingJiaCheckDTDataFormName +" d, " +
						ZRQingJiaAppDataDao.ZRQingJiaAppDataFormName+" m " +
				" WHERE " +
					" d.mainid = m.id " +
							" AND d.checkeddate >= '"+ _preStartDate +"' "
									+ "and d.checkeddate <= '" +_preEndDate+ "'" +
								" AND m.applicant = '" + _userid +"' "+
									" AND m.oddnum <> ''" +
										" AND m.oddnum <> '"+_appNo+"'";
		//log.error("检测请假重复 sql ：： " + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			repeatQingJiaList = new ArrayList<ZRQingJiaAppData>();
		}
		
		while(rs.next()){
			_checkedDateTmp = rs.getString("checkeddate");
			
			tmpStartTime =  rs.getString("starttime");	//查询目标记录中的开始时间
			if(!"".equals(Util.null2String(rs.getString("starttimechecked")))){
				tmpStartTime = rs.getString("starttimechecked");
			}
			tmpEndTime = rs.getString("endtime");	//查询目标记录中的结束时间
			if(!"".equals(Util.null2String(rs.getString("endtimechecked")))){
				tmpEndTime = rs.getString("endtimechecked");
			}
			//log.error("_appStartTime :: " + _appStartTime);
			//log.error("tmpEndTime :: " + tmpEndTime);
			//log.error("_appStartTime.compareTo(tmpEndTime) ::" + _appStartTime.compareTo(tmpEndTime));
			if(( _checkedDateTmp.equals(_preStartDate) && _appStartTime.compareTo(tmpEndTime)>=0) || 
					( _checkedDateTmp.equals(_preEndDate) && _appEndTime.compareTo(tmpStartTime)<=0 )){
				continue;
			}else{
				repeatZRQingJiaAppData = zrQJAppDataService.queryByID(rs.getInt("mainid"));
				if(repeatZRQingJiaAppData != null){
					if(!repeatQingJiaList.contains(repeatZRQingJiaAppData)){
						repeatQingJiaList.add(repeatZRQingJiaAppData);
					}
				}
			}
		}
		
		return repeatQingJiaList;
	}
	
}
