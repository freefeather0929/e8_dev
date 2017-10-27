package dinghan.zrac.hr.service.neeservice;

import java.util.List;

import dinghan.zrac.hr.entity.neeentity.NEECheckTargetDTData;

/**
 * 新员工培养目标明细
 * @author zhangxiaoyu/10593 - 2017-10-10
 * 
 */
public interface NEECheckTargetDTDataService {
	
	/**
	 * 获取试用期考核目标明细单行数据
	 * @param targetDTId
	 * @return
	 */
	NEECheckTargetDTData queryById(int targetDTId);
	
	/**
	 * 获取单个申请单中所有试用期考核目标明细行数据集合
	 * @param mainid
	 * @return
	 */
	List<NEECheckTargetDTData> queryAllCheckTargetDTData(int mainid);
	
}
