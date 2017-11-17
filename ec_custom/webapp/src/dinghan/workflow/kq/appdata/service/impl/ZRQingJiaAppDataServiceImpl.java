package dinghan.workflow.kq.appdata.service.impl;

import dinghan.workflow.kq.appdata.dao.ZRQingJiaAppDataDao;
import dinghan.workflow.kq.appdata.dao.impl.ZRQingJiaAppDataDaoImpl;
import dinghan.workflow.kq.appdata.entity.ZRQingJiaAppData;
import dinghan.workflow.kq.appdata.service.ZRQingJiaAppDataService;
/**
 * 中车请假流程申请流程数据Service实现类
 * @author zhangxiaoyu / 10593 - 2017-11-15
 * 
 */
public class ZRQingJiaAppDataServiceImpl implements ZRQingJiaAppDataService {
	private ZRQingJiaAppDataDao zrQingJiaAppDataDao = new ZRQingJiaAppDataDaoImpl();
	@Override
	public ZRQingJiaAppData queryByID(int id) {
		return zrQingJiaAppDataDao.queryByID(id);
	}

}
