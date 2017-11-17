package dinghan.workflow.kq.kqdt.gen;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.appdata.dtentity.ZRJiaBanAppDTData;
import dinghan.workflow.kq.appdata.dtservice.ZRJiaBanAppDTDataService;
import dinghan.workflow.kq.appdata.dtservice.impl.ZRJiaBanAppDTDataServiceImpl;
import dinghan.workflow.kq.appdata.service.ZRJiaBanAppDataService;
import dinghan.workflow.kq.appdata.service.impl.ZRJiaBanAppDataServiceImpl;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRJiaBanCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRJiaBanCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRJiaBanCheckDTServiceImpl;

/**
 * 中车加班核定明细数据生成类
 * @author zhangxiaoyu / 10593 - 2017-10-22
 * 
 */
public class ZRJiaBanCheckDataGen extends KQCheckDataGen<ZRJiaBanCheckDTData> {

	private Log log = LogFactory.getLog(ZRJiaBanCheckDataGen.class.getName());
	private ZRJiaBanAppDataService zrJiaBanAppService = new ZRJiaBanAppDataServiceImpl();
	private ZRJiaBanAppDTDataService jiabanAppDTService = new ZRJiaBanAppDTDataServiceImpl();
	private ZRJiaBanCheckDTService jiabanCheckDTService = new ZRJiaBanCheckDTServiceImpl();
	
	/**
	 * 思路： 
	 * 1. 通过主表ID获取所有加班明细数据；
	 * 2. 删除已经存在的所有核定明细；
	 * 3. 循环每个申请明细，根据申请明细生成核定明细
	 * 
	 */
	
	@Override
	public List<ZRJiaBanCheckDTData> createCheckData(int mainid) {
		List<ZRJiaBanCheckDTData> zrJiaBanCheckDTList = null;
		
		if(zrJiaBanAppService.queryByID(mainid) != null){
			
			zrJiaBanCheckDTList = jiabanCheckDTService.queryListByMainID(mainid);
			
			if(zrJiaBanCheckDTList!=null){	//删除已经存在的核定明细
				for(ZRJiaBanCheckDTData exsitData : zrJiaBanCheckDTList ){
					jiabanCheckDTService.deleteById(exsitData.getId());
				}
			}
			
			List<ZRJiaBanAppDTData> zrAppDTDataList = jiabanAppDTService.queryListByMainID(mainid);	//获取所有申请明细
			ZRJiaBanCheckDTData zrJiaBanCheckData = null;
			if(zrAppDTDataList != null ){
				zrJiaBanCheckDTList = new ArrayList<ZRJiaBanCheckDTData>();
				for(ZRJiaBanAppDTData appData : zrAppDTDataList){
					zrJiaBanCheckData = new ZRJiaBanCheckDTData();
					zrJiaBanCheckData.setMainid(appData.getMainid());
					zrJiaBanCheckData.setCheckeddate(appData.getOvertimeDate());
					zrJiaBanCheckData.setStarttime(appData.getStartingTime());
					zrJiaBanCheckData.setEndtime(appData.getEndTime());
					zrJiaBanCheckData.setWhetherturnoff(appData.getWhetherTurnoff());
					zrJiaBanCheckData.setChecked(0);
					zrJiaBanCheckDTList.add(zrJiaBanCheckData);
				}
			}
		}
		return zrJiaBanCheckDTList;
	}
	
	/**
	 * 获取人员编号
	 * @param userId
	 * @return
	private String getUserWorkCode(int userId){
		String workcode = null;
		String sql = "select top 1 workcode from Hrmresource where id = " + userId;
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			workcode = Util.null2String(rs.getString("workcode")) ;
		}
		return workcode;
	}
	*/
}
