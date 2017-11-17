package dinghan.workflow.kq.kqdt.check;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.kqdt.entity.zrentity.ZRChuChaiCheckDTData;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRWaiChuCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRChuChaiCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRChuChaiCheckDTServiceImpl;
/**
 * 中车出差流程核定类
 * <p>核定状态值说明： 0 - 未核定；1 - 已核定； 2 - 已确认
 * 
 * <p>
 * <i> 核定规则：
 * <p> 出差核定时，预计时间即为核定时间
 * 
 * @author zhangxiaoyu / 10593 - 2017-10-24
 * 
 * 
 */
public class ZRChuChaiDTCheck implements KQDTCheck<ZRChuChaiCheckDTData> {
	
	private ZRChuChaiCheckDTService zrCCCheckDTService = new ZRChuChaiCheckDTServiceImpl(); 
	
	private int checkStatus;	//核定状态
	
	public ZRChuChaiDTCheck(int checkStatus){
		this.checkStatus = checkStatus;
	}
	
	@Override
	public ZRChuChaiCheckDTData executeCheck(int id) {
		ZRChuChaiCheckDTData chuchaiCheckData = zrCCCheckDTService.queryById(id);
		chuchaiCheckData.setStartTimeChecked(chuchaiCheckData.getStartTime());
		chuchaiCheckData.setEndTimeChecked(chuchaiCheckData.getEndTime());
		chuchaiCheckData.setChecked(this.checkStatus);
		return chuchaiCheckData;
	}

	@Override
	public List<ZRChuChaiCheckDTData> executeCheckAll(int mainid) {
		List<ZRChuChaiCheckDTData> chuchaiCheckDataList = zrCCCheckDTService.queryListByMainID(mainid);
		
		List<ZRChuChaiCheckDTData> chuchaiCheckDataList_checked = null;
		if( chuchaiCheckDataList!=null ){
			chuchaiCheckDataList_checked = new ArrayList<ZRChuChaiCheckDTData>();
			for(ZRChuChaiCheckDTData data : chuchaiCheckDataList){
				data = executeCheck(data.getId());
				
				chuchaiCheckDataList_checked.add(data);
			}
		}
		
		return chuchaiCheckDataList_checked;
	}

	public ZRChuChaiCheckDTService getZrCCCheckDTService() {
		return zrCCCheckDTService;
	}

	public void setZrCCCheckDTService(ZRChuChaiCheckDTService zrCCCheckDTService) {
		this.zrCCCheckDTService = zrCCCheckDTService;
	}
	/**
	 * 获取核定状态
	 * @return
	 */
	public int getCheckStatus() {
		return checkStatus;
	}
	/**
	 * 设置核定状态
	 * @return
	 */
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	
}
