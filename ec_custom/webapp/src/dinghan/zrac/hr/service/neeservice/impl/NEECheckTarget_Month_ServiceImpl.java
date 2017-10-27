package dinghan.zrac.hr.service.neeservice.impl;

import java.util.List;

import dinghan.zrac.hr.dao.needao.NEECheckTarget_Month_Dao;
import dinghan.zrac.hr.entity.neeentity.NEECheckTargetDTData_Month;
import dinghan.zrac.hr.service.neeservice.NEECheckTarget_Month_Service;

/**
 * 新员工培养目标Service实现类
 * @author ==== zhangxiaoyu / 10593 - 2017-10-15 ====
 *
 */
public class NEECheckTarget_Month_ServiceImpl implements NEECheckTarget_Month_Service {

	private NEECheckTarget_Month_Dao neeCheckTarget_Month_Dao;
	
	public NEECheckTarget_Month_ServiceImpl(NEECheckTarget_Month_Dao neeCheckTarget_Month_Dao){
		this.neeCheckTarget_Month_Dao = neeCheckTarget_Month_Dao;
	}
	
	@SuppressWarnings("unused")
	private NEECheckTarget_Month_ServiceImpl(){}
	
	@Override
	public NEECheckTargetDTData_Month queryById(int id) {
		return neeCheckTarget_Month_Dao.queryById(id);
	}
	
	@Override
	public NEECheckTargetDTData_Month queryByTargetdataid(int targetDataId) {
		// TODO Auto-generated method stub
		return neeCheckTarget_Month_Dao.queryByTargetdataid(targetDataId);
	}

	@Override
	public boolean insert(NEECheckTargetDTData_Month target_month) {
		return neeCheckTarget_Month_Dao.insert(target_month);
	}

	@Override
	public boolean update(NEECheckTargetDTData_Month target_month) {
		return neeCheckTarget_Month_Dao.update(target_month);
	}

	@Override
	public boolean delete(int id) {
		return neeCheckTarget_Month_Dao.delete(id);
	}
	

	@Override
	public List<NEECheckTargetDTData_Month> queryAllMonthTargetData(int mainid) {
		return neeCheckTarget_Month_Dao.queryAllMonthTargetData(mainid);
	}

	public NEECheckTarget_Month_Dao getNeeCheckTarget_Month_Dao() {
		return neeCheckTarget_Month_Dao;
	}

	public void setNeeCheckTarget_Month_Dao(NEECheckTarget_Month_Dao neeCheckTarget_Month_Dao) {
		this.neeCheckTarget_Month_Dao = neeCheckTarget_Month_Dao;
	}


	
}
