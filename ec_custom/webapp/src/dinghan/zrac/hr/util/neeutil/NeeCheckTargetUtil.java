package dinghan.zrac.hr.util.neeutil;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.zrac.hr.entity.neeentity.NEECheckTargetDTData;
import dinghan.zrac.hr.entity.neeentity.NEECheckTargetDTData_Month;
import dinghan.zrac.hr.service.neeservice.NEECheckTargetDTDataService;
import dinghan.zrac.hr.service.neeservice.NEECheckTarget_Month_Service;
import dinghan.zrac.hr.service.neeservice.impl.NEECheckTargetDTDataServiceImpl;

/**
 * 新员工考核目标信息处理类
 * @author zhangxiaoyu / 10593 - 2017-10-21 
 * 
 */
public class NeeCheckTargetUtil {
	private Log log = LogFactory.getLog(NeeCheckTargetUtil.class.getName());
	private NEECheckTarget_Month_Service monthServer;
	
	private NEECheckTargetDTDataService server = new NEECheckTargetDTDataServiceImpl();
	
	public NeeCheckTargetUtil(NEECheckTarget_Month_Service monthServer){
		this.monthServer = monthServer;
	}
	
	@SuppressWarnings("unused")
	private NeeCheckTargetUtil(){
		
	}
	
	/**
	 * 更新新员工月度培养目标
	 * <p>
	 * 更新步骤：
	 * <p>
	 * 1. 获取所有考核目标
	 * 2. 循环获取考核目标中的每一条记录的ID，以ID到月度明细表中查询是否有 targetid 与此ID相同的记录，
	 * <br> 如果有，则更新，
	 * <br> 如果没有，则新增。
	 * 
	 * @param mainid
	 */
	public void updateAllNEECheckTargetMonthDTData(int mainid){
		List<NEECheckTargetDTData_Month> month_list = monthServer.queryAllMonthTargetData(mainid);
		if(month_list!=null){
			for(NEECheckTargetDTData_Month mt : month_list){
				//log.error("ID 为 ::" + mt.getTargetDataID() +" 月度目标存在:: " +hasOriginTargetData(mt.getTargetDataID()));
				if(hasOriginTargetData(mt.getTargetDataID()) == false){
					monthServer.delete(mt.getId());
				}
				
				//monthServer.delete(mt.getId());
			}
		}

		//deleteAllNoOriginMonthTargetData(mainid);
		List<NEECheckTargetDTData> targetList = server.queryAllCheckTargetDTData(mainid);
		if(targetList!=null){
			int dataID = -1;
			
			for(NEECheckTargetDTData t : targetList){
				dataID = t.getId();	//目标ID
				NEECheckTargetDTData_Month monthTargetData = monthServer.queryByTargetdataid(dataID);
				if(monthTargetData != null){  //检测是否存在targetID = 目标ID 的月度目标；
					monthServer.update(updateNEECheckTargetMonthDTData(monthTargetData,t));
				}else{
					monthServer.insert(createNEECheckTargetMonthDTData(t));
				}
			}
		}	
		
	}
	
	/**
	 * 删除全部没有原始考核月度考核目标记录
	 * @param mainid
	 
	private void deleteAllNoOriginMonthTargetData(int mainid){
		
		List<NEECheckTargetDTData_Month> month_TargetList = monthServer.queryAllMonthTargetData(mainid);
		
		int targetID = -1;
		if(month_TargetList!=null){
			for(NEECheckTargetDTData_Month mt : month_TargetList){
				targetID = mt.getTargetDataID();
				if(!hasOriginTargetData(targetID)){
					monthServer.delete(mt.getId());
				}
			}
		}
	}
	*/
	/**
	 * 创建新员工月度目标
	 * 
	 */
	
	private NEECheckTargetDTData_Month createNEECheckTargetMonthDTData(NEECheckTargetDTData targetData){
		
		NEECheckTargetDTData_Month _monthTarget = new NEECheckTargetDTData_Month();
		
		_monthTarget.setMainid(targetData.getMainid());
		_monthTarget.setBreakdownwork(targetData.getBreakdownwork());
		_monthTarget.setCheckbasis(targetData.getCheckbasis());
		_monthTarget.setCheckfinishstate(targetData.getCheckfinishstate());
		_monthTarget.setCheckgoal(targetData.getCheckgoal());
		_monthTarget.setTargetDataID(targetData.getId());
		_monthTarget.setRemark("");
		_monthTarget.setMonthevaluation("");
		return _monthTarget;
	}
	
	/**
	 * 更新月度考核目标
	 * @param monthTarget - 需要更新的月度考核数据
	 * @param targetData - 用于更新的考核明细数据
	 * @return - NEECheckTargetDTData_Month 
	 */
	private NEECheckTargetDTData_Month updateNEECheckTargetMonthDTData(NEECheckTargetDTData_Month monthTarget,NEECheckTargetDTData targetData){
		
		NEECheckTargetDTData_Month _monthTarget = monthTarget;
		
		_monthTarget.setMainid(targetData.getMainid());
		_monthTarget.setBreakdownwork(targetData.getBreakdownwork());
		_monthTarget.setCheckbasis(targetData.getCheckbasis());
		_monthTarget.setCheckfinishstate(targetData.getCheckfinishstate());
		_monthTarget.setCheckgoal(targetData.getCheckgoal());
		_monthTarget.setTargetDataID(targetData.getId());
		//_monthTarget.setRemark(targetData.getRemark());
		
		return _monthTarget;
	}
	
	/**
	 * 判定月度明细对应的源明细是否存在
	 * <p>
	 * @param targetDataId
	 * @return true - 表示存在； false - 表示不存在
	 */
	private boolean hasOriginTargetData(int targetDataId){
		NEECheckTargetDTData checkTargetDTData = server.queryById(targetDataId);
		if(checkTargetDTData!=null){
			return true;
		}
		return false;
	}
	
	
	public NEECheckTarget_Month_Service getMonthServer() {
		return monthServer;
	}


	public void setMonthServer(NEECheckTarget_Month_Service monthServer) {
		this.monthServer = monthServer;
	}

	
}
