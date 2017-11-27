package dinghan.zrac.hr.dao.needao;

import java.util.List;

import dinghan.zrac.hr.entity.neeentity.NEECheckTargetDTData_Month;

/**
 * 新员工试用期考核目标明细DAO
 * @author zhangxiaoyy / 10593 - 2017-10-10
 * 
 */
public interface NEECheckTarget_Month_Dao{
	
	String NEETARGET_1ST_MONTH_DTFORM_NAME = "formtable_main_266_dt3";
	
	String NEETARGET_2ND_MONTH_DTFORM_NAME = "formtable_main_266_dt4";
	
	String NEETARGET_3RD_MONTH_DTFORM_NAME = "formtable_main_266_dt5";
	
	/**
	 * 查询新员工月度考核目标明细表记录
	 * @param id
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
	 * 查询所有月度考核明细
	 * @param mainid - 员工试用期考核流程主表ID
	 * @return
	 */
	List<NEECheckTargetDTData_Month> queryAllMonthTargetData(int mainid);
}
