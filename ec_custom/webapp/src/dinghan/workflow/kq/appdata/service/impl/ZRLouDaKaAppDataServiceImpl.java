package dinghan.workflow.kq.appdata.service.impl;

import dinghan.workflow.kq.appdata.dao.ZRLouDaKaAppDataDao;
import dinghan.workflow.kq.appdata.dao.impl.ZRLouDaKaAppDataDaoImpl;
import dinghan.workflow.kq.appdata.entity.ZRLouDaKaAppData;
import dinghan.workflow.kq.appdata.service.ZRLouDaKaAppDataService;

/**
 * 中车外出公干流程申请流程数据Service实现类
 * @author zhangxiaoyu / 10593 - 2017-10-25
 * 
 */
public class ZRLouDaKaAppDataServiceImpl implements ZRLouDaKaAppDataService {

	private ZRLouDaKaAppDataDao zrLouDaKaAppDataDao = new ZRLouDaKaAppDataDaoImpl();
	
	@Override
	public ZRLouDaKaAppData queryByID(int id) {
		return zrLouDaKaAppDataDao.queryByID(id);
	}

	public ZRLouDaKaAppDataDao getZrLouDaKaAppDataDao() {
		return zrLouDaKaAppDataDao;
	}

	public void setZrLouDaKaAppDataDao(ZRLouDaKaAppDataDao zrLouDaKaAppDataDao) {
		this.zrLouDaKaAppDataDao = zrLouDaKaAppDataDao;
	}

}
