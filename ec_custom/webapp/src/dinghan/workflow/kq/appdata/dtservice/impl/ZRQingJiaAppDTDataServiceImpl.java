package dinghan.workflow.kq.appdata.dtservice.impl;

import java.util.List;

import dinghan.workflow.kq.appdata.dtdao.ZRQingJiaAppDTDataDao;
import dinghan.workflow.kq.appdata.dtdao.impl.ZRQingJiaAppDTDataDaoImpl;
import dinghan.workflow.kq.appdata.dtentity.ZRQingJiaAppDTData;
import dinghan.workflow.kq.appdata.dtservice.ZRQingJiaAppDTDataService;
/**
 * 中车请假申请明细表Service实现类
 * @author zhangxiaoyu / 10593 - 2017-11-15
 *
 */
public class ZRQingJiaAppDTDataServiceImpl implements ZRQingJiaAppDTDataService {
	private ZRQingJiaAppDTDataDao zrQingJiaAppDTDataDao = new ZRQingJiaAppDTDataDaoImpl();
	@Override
	public ZRQingJiaAppDTData queryByID(int id) {
		return zrQingJiaAppDTDataDao.queryByID(id);
	}

	@Override
	public List<ZRQingJiaAppDTData> queryListByMainID(int mainid) {
		return zrQingJiaAppDTDataDao.queryListByMainID(mainid);
	}

}
