package dinghan.workflow.kq.kqdt.check.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.kq.kqdt.check.ZRJiaBanDTCheck;
import dinghan.workflow.kq.kqdt.dao.ZRJiaBanCheckDTDao;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRJiaBanCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRJiaBanCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRJiaBanCheckDTServiceImpl;
import weaver.conn.RecordSet;
/**
 * 中车加班考勤明细核定工具
 * @author zhangxiaoyu / 10593 - 2017-10-24
 * 
 */
public class ZRJiaBanKQDTCheckUtil implements KQDTCheckUtil<ZRJiaBanCheckDTData>{
	//private Log log = LogFactory.getLog(ZRJiaBanKQDTCheckUtil.class.getName());
	private ZRJiaBanDTCheck kqDTCheck = new ZRJiaBanDTCheck();
	private ZRJiaBanCheckDTService zrJBCheckDTService = new ZRJiaBanCheckDTServiceImpl();
	
	@Override
	public void executeCheck(int id) {
		ZRJiaBanCheckDTData jiabanCheckData = kqDTCheck.executeCheck(id);
		zrJBCheckDTService.update(jiabanCheckData);
	}

	@Override
	public void executeCheckAll(int mainid) {
		//log.error(" executeCheckAll 加班明细开始核定 ID  " + mainid);
		List<ZRJiaBanCheckDTData> jiabanCheckDataList = kqDTCheck.executeCheckAll(mainid);
		//log.error(" executeCheckAll jiabanCheckDataList ? null " + (jiabanCheckDataList == null) );
		if(jiabanCheckDataList!=null){
			
			for(ZRJiaBanCheckDTData data : jiabanCheckDataList){
				//log.error(" ZRJiaBanCheckDTData data ? null :: " + (data == null));
				zrJBCheckDTService.update(data);
			}	
		}
	}

	@Override
	public void executeCronCheck() {
		String curDate = CalendarUtil.getCurDate();	//当前日期
		String preMonthDate = CalendarUtil.moveDate(curDate, 0, -1, 0);	//当前日期前一个月的日期
		//log.error("jiaban Check curDate :: " + curDate);
		//log.error("jiaban Check preMonthDate :: " + preMonthDate);
		String sql = "select id from " + ZRJiaBanCheckDTDao.ZRJiaBanCheckDTDataFormName
				+ " where (checked = '0' or checked = '1')"
					+ "	and checkeddate > '" + preMonthDate +"'"
						+ " and checkeddate < '" + curDate + "'";
		//log.error("JiaBanKQDTChec sql :: " + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			this.executeCheck(rs.getInt("id"));
		}
	}

}
