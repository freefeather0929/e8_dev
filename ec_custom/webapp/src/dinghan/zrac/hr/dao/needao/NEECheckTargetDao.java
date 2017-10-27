package dinghan.zrac.hr.dao.needao;

import java.util.List;

import dinghan.zrac.hr.entity.neeentity.NEECheckTargetDTData;

/**
 * 新员工试用期考核目标明细DAO
 * @author zhangxiaoyy / 10593 - 2017-10-10
 *
 */
public interface NEECheckTargetDao {
	
	/**
	 * 试用期考核目标明细表名
	 */
	String NeeCheckTargetDTFormName = "formtable_main_244_dt2";
	
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
