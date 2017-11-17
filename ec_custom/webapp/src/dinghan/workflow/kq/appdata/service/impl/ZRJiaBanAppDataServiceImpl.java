package dinghan.workflow.kq.appdata.service.impl;

import dinghan.workflow.kq.appdata.dao.ZRJiaBanAppDataDao;
import dinghan.workflow.kq.appdata.dao.impl.ZRJiaBanAppDataDaoImpl;
import dinghan.workflow.kq.appdata.entity.ZRJiaBanAppData;
import dinghan.workflow.kq.appdata.service.ZRJiaBanAppDataService;

/**
 * 中车加班流程申请流程数据Service实现类
 * @author zhangxiaoyu / 10593 - 2017-11-02
 * 
 */
public class ZRJiaBanAppDataServiceImpl implements ZRJiaBanAppDataService {

	private ZRJiaBanAppDataDao zrJiaBanAppDataDao = new ZRJiaBanAppDataDaoImpl();
	@Override
	public ZRJiaBanAppData queryByID(int id) {
		return zrJiaBanAppDataDao.queryByID(id);
	}

}
