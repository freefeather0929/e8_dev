package dinghan.workflow.kq.kqdt.service.impl;

import java.util.List;

import dinghan.workflow.kq.kqdt.dao.ZRWaiChuCheckDTDao;
import dinghan.workflow.kq.kqdt.dao.impl.ZRWaiChuCheckDTDaoImpl;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRWaiChuCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRWaiChuCheckDTService;


/**
 * 中车外出公干核定明细Serivce实现类
 * @author zhangxiaoyu / 10593 - 2017-10-25
 *
 */
public class ZRWaiChuCheckDTServiceImpl implements ZRWaiChuCheckDTService {

	private ZRWaiChuCheckDTDao zrWaiChuCheckDTDao = new ZRWaiChuCheckDTDaoImpl();
	
	@Override
	public ZRWaiChuCheckDTData queryById(int id) {
		return zrWaiChuCheckDTDao.queryById(id);
	}

	@Override
	public boolean deleteById(int id) {
		return zrWaiChuCheckDTDao.deleteById(id);
	}

	@Override
	public boolean update(ZRWaiChuCheckDTData zrWaiChuCheckDTData) {
		return zrWaiChuCheckDTDao.update(zrWaiChuCheckDTData);
	}

	@Override
	public List<ZRWaiChuCheckDTData> queryListByMainID(int mainID) {
		
		return zrWaiChuCheckDTDao.queryListByMainID(mainID);
	}

	@Override
	public List<ZRWaiChuCheckDTData> queryListBySEDate(String startDate, String endDate) {
		return zrWaiChuCheckDTDao.queryListBySEDate(endDate, endDate);
	}

	@Override
	public boolean add(ZRWaiChuCheckDTData zrWaiChuCheckDTData) {
		return zrWaiChuCheckDTDao.add(zrWaiChuCheckDTData);
	}

}
