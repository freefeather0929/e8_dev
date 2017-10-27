package dinghan.workflow.kq.appdata.service.impl;

import dinghan.workflow.kq.appdata.dao.ZRWaiChuAppDataDao;
import dinghan.workflow.kq.appdata.dao.impl.ZRWaiChuAppDataDaoImpl;
import dinghan.workflow.kq.appdata.entity.ZRWaiChuAppData;
import dinghan.workflow.kq.appdata.service.ZRWaiChuAppDataService;

public class ZRWaiChuAppDataServiceImpl implements ZRWaiChuAppDataService {

	private ZRWaiChuAppDataDao wcAppDataDao = new ZRWaiChuAppDataDaoImpl();
	
	@Override
	public ZRWaiChuAppData queryByID(int id) {
		return wcAppDataDao.queryByID(id);
	}

	public ZRWaiChuAppDataDao getWcAppDataDao() {
		return wcAppDataDao;
	}

	public void setWcAppDataDao(ZRWaiChuAppDataDao wcAppDataDao) {
		this.wcAppDataDao = wcAppDataDao;
	}
	
}
