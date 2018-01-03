package dinghan.workflow.kq.kqdt.check.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.kq.kqdt.check.ZRWaiChuDTCheck;
import dinghan.workflow.kq.kqdt.dao.ZRWaiChuCheckDTDao;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRWaiChuCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRWaiChuCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRWaiChuCheckDTServiceImpl;
import weaver.conn.RecordSet;
/**
 * 中车外出考勤明细核定工具
 * @author zhangxiaoyu / 10593 - 2017-10-24
 *
 */
public class ZRWaiChuKQDTCheckUtil implements KQDTCheckUtil<ZRWaiChuCheckDTData>{
	//private Log log = LogFactory.getLog(ZRChuChaiKQDTCheckUtil.class.getName());
	private ZRWaiChuDTCheck kqDTCheck = new ZRWaiChuDTCheck();
	private ZRWaiChuCheckDTService zrWCCheckDTService = new ZRWaiChuCheckDTServiceImpl();
	
	@Override
	public void executeCheck(int id) {
		ZRWaiChuCheckDTData waichuCheckData = kqDTCheck.executeCheck(id);
		zrWCCheckDTService.update(waichuCheckData);
	}

	@Override
	public void executeCheckAll(int mainid) {
		List<ZRWaiChuCheckDTData> waichuCheckDataList = kqDTCheck.executeCheckAll(mainid);
		for(ZRWaiChuCheckDTData data : waichuCheckDataList){
			zrWCCheckDTService.update(data);
		}		
	}

	@Override
	public void executeCronCheck() {
		String curDate = CalendarUtil.getCurDate();	//当前日期
		String preMonthDate = CalendarUtil.moveDate(curDate, 0, -1, 0);	//当前日期前一个月的日期
		String sql = "select id from " + ZRWaiChuCheckDTDao.ZRWaiChuCheckDTDataFormName + ""
				+ " where (checked = '0' or checked = '1')"
						+ "	and checkeddate > '" + preMonthDate +"'"
								+ " and checkeddate < '" + curDate + "'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			this.executeCheck(rs.getInt("id"));
		}
	}
	
}
