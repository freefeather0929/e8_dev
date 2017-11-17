package dinghan.workflow.kq.appdata.service.impl;

import dinghan.workflow.kq.appdata.dao.ZRChuChaiAppDataDao;
import dinghan.workflow.kq.appdata.dao.impl.ZRChuChaiAppDataDaoImpl;
import dinghan.workflow.kq.appdata.entity.ZRChuChaiAppData;
import dinghan.workflow.kq.appdata.service.ZRChuChaiAppDataService;
/**
 * 中车出差流程申请流程数据Service实现类
 * @author zhangxiaoyu / 10593 - 2017-10-28
 * 
 */
public class ZRChuChaiAppDataServiceImpl implements ZRChuChaiAppDataService {

	private ZRChuChaiAppDataDao ccAppDataDao = new ZRChuChaiAppDataDaoImpl();

	@Override
	public ZRChuChaiAppData queryByID(int id) {
		return this.ccAppDataDao.queryByID(id);
	}

	public ZRChuChaiAppDataDao getCcAppDataDao() {
		return ccAppDataDao;
	}

	public void setCcAppDataDao(ZRChuChaiAppDataDao ccAppDataDao) {
		this.ccAppDataDao = ccAppDataDao;
	}

}
