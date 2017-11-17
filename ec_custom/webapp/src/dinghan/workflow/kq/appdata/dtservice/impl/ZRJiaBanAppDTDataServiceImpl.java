package dinghan.workflow.kq.appdata.dtservice.impl;

import java.util.List;

import dinghan.workflow.kq.appdata.dtdao.ZRJiaBanAppDTDataDao;
import dinghan.workflow.kq.appdata.dtdao.impl.ZRJiaBanAppDTDataDaoImpl;
import dinghan.workflow.kq.appdata.dtentity.ZRJiaBanAppDTData;
import dinghan.workflow.kq.appdata.dtservice.ZRJiaBanAppDTDataService;

public class ZRJiaBanAppDTDataServiceImpl implements ZRJiaBanAppDTDataService {

	private ZRJiaBanAppDTDataDao zrJiaBanAppDTDataDao = new ZRJiaBanAppDTDataDaoImpl();
	
	@Override
	public ZRJiaBanAppDTData queryByID(int id) {
		return zrJiaBanAppDTDataDao.queryByID(id);
	}

	@Override
	public List<ZRJiaBanAppDTData> queryListByMainID(int mainid) {
		return zrJiaBanAppDTDataDao.queryListByMainID(mainid);
	}

}
