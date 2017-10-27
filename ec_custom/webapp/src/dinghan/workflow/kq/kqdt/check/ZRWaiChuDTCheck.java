package dinghan.workflow.kq.kqdt.check;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.kqdt.entity.zrentity.ZRWaiChuCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRWaiChuCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRWaiChuCheckDTServiceImpl;
/**
 * 中车外出公干流程核定类
 * @author zhangxiaoyu / 10593 - 2017-10-24
 * 
 */
public class ZRWaiChuDTCheck implements KQDTCheck<ZRWaiChuCheckDTData> {
	
	private ZRWaiChuCheckDTService zrWCCheckDTService = new ZRWaiChuCheckDTServiceImpl(); 
	
	@Override
	public ZRWaiChuCheckDTData executeCheck(int id) {
		ZRWaiChuCheckDTData waichuCheckData = zrWCCheckDTService.queryById(id);
		waichuCheckData.setStarttimechecked(waichuCheckData.getStarttime());
		waichuCheckData.setEndtimechecked(waichuCheckData.getEndtime());
		waichuCheckData.setChecked(2);
		return waichuCheckData;
	}

	@Override
	public List<ZRWaiChuCheckDTData> executeCheckAll(int mainid) {
		List<ZRWaiChuCheckDTData> waichuCheckDataList = zrWCCheckDTService.queryListByMainID(mainid);
		
		List<ZRWaiChuCheckDTData> waichuCheckDataList_checked = null;
		if( waichuCheckDataList!=null ){
			waichuCheckDataList_checked = new ArrayList<ZRWaiChuCheckDTData>();
			for(ZRWaiChuCheckDTData data : waichuCheckDataList){
				data = executeCheck(data.getId());
				waichuCheckDataList_checked.add(data);
			}
		}
		
		return waichuCheckDataList_checked;
	}
	
}
