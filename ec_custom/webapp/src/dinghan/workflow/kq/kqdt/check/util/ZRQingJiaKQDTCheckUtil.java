package dinghan.workflow.kq.kqdt.check.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.kq.kqdt.check.ZRQingJiaDTCheck;
import dinghan.workflow.kq.kqdt.dao.ZRQingJiaCheckDTDao;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRQingJiaCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRQingJiaCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRQingJiaCheckDTServiceImpl;
import weaver.conn.RecordSet;
/**
 * 中车请假考勤明细核定工具
 * @author zhangxiaoyu / 10593 - 2017-11-17
 * 
 */

public class ZRQingJiaKQDTCheckUtil implements KQDTCheckUtil<ZRQingJiaCheckDTData>{
	//private Log log = LogFactory.getLog(ZRQingJiaKQDTCheckUtil.class.getName());
	
	private ZRQingJiaDTCheck kqDTCheck = new ZRQingJiaDTCheck();
	private ZRQingJiaCheckDTService zrQJCheckDTService = new ZRQingJiaCheckDTServiceImpl();
	
	@Override
	public void executeCheck(int id) {
		ZRQingJiaCheckDTData qingjiaCheckData = kqDTCheck.executeCheck(id);
		zrQJCheckDTService.update(qingjiaCheckData);
	}

	@Override
	public void executeCheckAll(int mainid) {
		//log.error(" executeCheckAll 请假明细开始核定 ID  " + mainid);
		List<ZRQingJiaCheckDTData> qingjiaCheckDataList = kqDTCheck.executeCheckAll(mainid);
		//log.error(" executeCheckAll qingjiaCheckDataList ? null " + (qingjiaCheckDataList == null) );
		if(qingjiaCheckDataList!=null){
			for(ZRQingJiaCheckDTData data : qingjiaCheckDataList){
				//log.error(" ZRJiaBanCheckDTData data ? null :: " + (data == null));
				zrQJCheckDTService.update(data);
			}	
		}
	}

	@Override
	public void executeCronCheck() {
		
		String curDate = CalendarUtil.getCurDate();	//当前日期
		String preMonthDate = CalendarUtil.moveDate(curDate, 0, -1, 0);	//当前日期前一个月的日期
		//log.error("qingjia Check curDate :: " + curDate);
		//log.error("qingjia Check preMonthDate :: " + preMonthDate);
		String sql = "select id from " + ZRQingJiaCheckDTDao.ZRQingJiaCheckDTDataFormName
				+ " where (checked = '0' or checked = '1')"
					+ "	and checkeddate > '" + preMonthDate +"'"
						+ " and checkeddate < '" + curDate + "'";
		//log.error("QingJiaKQDTChec sql :: " + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			this.executeCheck(rs.getInt("id"));
		}
	}

}
