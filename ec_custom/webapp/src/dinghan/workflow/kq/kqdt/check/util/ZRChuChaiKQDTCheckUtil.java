package dinghan.workflow.kq.kqdt.check.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.kq.kqdt.check.KQDTCheck;
import dinghan.workflow.kq.kqdt.check.ZRChuChaiDTCheck;
import dinghan.workflow.kq.kqdt.dao.ZRChuChaiCheckDTDao;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRChuChaiCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRChuChaiCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRChuChaiCheckDTServiceImpl;
import weaver.conn.RecordSet;
/**
 * 中车出差申请考勤明细核定工具
 * @author zhangxiaoyu / 10593 - 2017-10-29
 * 
 */
public class ZRChuChaiKQDTCheckUtil implements KQDTCheckUtil<ZRChuChaiCheckDTData>{
	
	private ZRChuChaiDTCheck kqDTCheck = new ZRChuChaiDTCheck(KQDTCheck.CHECKED);
	private ZRChuChaiCheckDTService zrCCCheckDTService = new ZRChuChaiCheckDTServiceImpl();
	
	@Override
	public void executeCheck(int id) {
		ZRChuChaiCheckDTData waichuCheckData = kqDTCheck.executeCheck(id);
		zrCCCheckDTService.update(waichuCheckData);
	}

	@Override
	public void executeCheckAll(int mainid) {
		List<ZRChuChaiCheckDTData> chuchaiCheckDataList = kqDTCheck.executeCheckAll(mainid);
		for(ZRChuChaiCheckDTData data : chuchaiCheckDataList){
			zrCCCheckDTService.update(data);
		}		
	}

	@Override
	public void executeCronCheck() {
		
		String curDate = CalendarUtil.getCurDate();	//当前日期
		String preMonthDate = CalendarUtil.moveDate(curDate, 0, -1, 0);	//当前日期前一个月的日期
		String sql = "select id from " + ZRChuChaiCheckDTDao.ZRChuChaiCheckDTDataFormName + ""
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
