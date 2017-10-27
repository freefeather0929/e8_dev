package dinghan.zrac.hr.service.neeservice;

import java.util.List;

import dinghan.zrac.hr.entity.neeentity.NEECheckTargetDTData_Month;

public interface NEECheckTarget_Month_Service {
	
	/**
	 * 查询新员工月度考核目标明细表记录
	 * @param id - 新员工考核目标 明细表记录ID
	 * @return
	 */
	NEECheckTargetDTData_Month queryById(int id);
		
	/**
	 * 查询新员工月度考核目标明细表记录
	 * @param targetDataId - 新员工考核目标明细表记录ID
	 * @return
	 */
	NEECheckTargetDTData_Month queryByTargetdataid(int targetDataId);
	
	/**
	 * 新增新员工月度考核目标明细表
	 * @param target_month
	 * @return
	 */
	boolean insert(NEECheckTargetDTData_Month target_month);
	
	/**
	 * 更新新员工月度考核目标明细表
	 * @param target_month
	 * @return
	 */
	boolean update(NEECheckTargetDTData_Month target_month);
	
	/**
	 * 删除新员工月度考核目标明细表
	 * @param id
	 * @return
	 */
	boolean delete(int id);
	
	
	/**
	 * 获取新员工试用期考核单据中所有月度明细记录
	 * @param mainid
	 * @return
	 */
	List<NEECheckTargetDTData_Month> queryAllMonthTargetData(int mainid);
}
