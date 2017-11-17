package dinghan.workflow.kq.kqdt.service.impl;

import java.util.List;

import dinghan.workflow.kq.kqdt.dao.ZRQingJiaCheckDTDao;
import dinghan.workflow.kq.kqdt.dao.impl.ZRQingJiaCheckDTDaoImpl;
import dinghan.workflow.kq.kqdt.entity.zrentity.ZRQingJiaCheckDTData;
import dinghan.workflow.kq.kqdt.service.ZRQingJiaCheckDTService;
/**
 * 中车请假核定明细Serivce实现类
 * @author zhangxiaoyu / 10593 - 2017-11-15
 *
 */
public class ZRQingJiaCheckDTServiceImpl implements ZRQingJiaCheckDTService {

	private ZRQingJiaCheckDTDao zrQingJiaCheckDTDao = new ZRQingJiaCheckDTDaoImpl();
	
	@Override
	public ZRQingJiaCheckDTData queryById(int id) {
		return zrQingJiaCheckDTDao.queryById(id);
	}

	@Override
	public boolean add(ZRQingJiaCheckDTData data) {
		return zrQingJiaCheckDTDao.add(data);
	}

	@Override
	public boolean deleteById(int id) {
		return zrQingJiaCheckDTDao.deleteById(id);
	}

	@Override
	public boolean update(ZRQingJiaCheckDTData data) {
		return zrQingJiaCheckDTDao.update(data);
	}

	@Override
	public List<ZRQingJiaCheckDTData> queryListByMainID(int mainID) {
		return zrQingJiaCheckDTDao.queryListByMainID(mainID);
	}

	@Override
	public List<ZRQingJiaCheckDTData> queryListBySEDate(String startDate, String endDate) {
		return zrQingJiaCheckDTDao.queryListBySEDate(startDate, endDate);
	}

}
