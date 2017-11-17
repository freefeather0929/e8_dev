package dinghan.workflow.kq.kqdt.service.impl;

import java.util.List;

import dinghan.workflow.kq.kqdt.dao.ZRJiaBanCheckDTDao;
import dinghan.workflow.kq.kqdt.dao.impl.ZRJiaBanCheckDTDaoImpl;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRJiaBanCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRJiaBanCheckDTService;

public class ZRJiaBanCheckDTServiceImpl implements ZRJiaBanCheckDTService {

	ZRJiaBanCheckDTDao zrJiaBanCheckDTDao = new ZRJiaBanCheckDTDaoImpl();
	
	@Override
	public ZRJiaBanCheckDTData queryById(int id) {
		return zrJiaBanCheckDTDao.queryById(id);
	}

	@Override
	public boolean add(ZRJiaBanCheckDTData zrJiaBanCheckDTData) {
		return zrJiaBanCheckDTDao.add(zrJiaBanCheckDTData);
	}

	@Override
	public boolean deleteById(int id) {
		return zrJiaBanCheckDTDao.deleteById(id);
	}

	@Override
	public boolean update(ZRJiaBanCheckDTData zrJiaBanCheckDTData) {
		return zrJiaBanCheckDTDao.update(zrJiaBanCheckDTData);
	}

	@Override
	public List<ZRJiaBanCheckDTData> queryListByMainID(int mainID) {
		return zrJiaBanCheckDTDao.queryListByMainID(mainID);
	}

	@Override
	public List<ZRJiaBanCheckDTData> queryListBySEDate(String startDate, String endDate) {
		return zrJiaBanCheckDTDao.queryListBySEDate(startDate, endDate);
	}

}
