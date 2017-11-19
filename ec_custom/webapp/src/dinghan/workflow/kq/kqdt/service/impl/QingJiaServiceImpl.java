package dinghan.workflow.kq.kqdt.service.impl;

import java.util.List;

import dinghan.workflow.beans.QingJia;
import dinghan.workflow.kq.kqdt.dao.QingJiaCheckDTDao;
import dinghan.workflow.kq.kqdt.service.QingJiaService;

public class QingJiaServiceImpl extends KQDetailBaseServiceImpl<QingJia> implements QingJiaService {
	
	private QingJiaCheckDTDao qingjiaDao;
	
	public QingJiaServiceImpl(QingJiaCheckDTDao _qingjiaDao){
		qingjiaDao = _qingjiaDao;
	}
	
	public void setQingjiaDao(QingJiaCheckDTDao qingjiaDao) {
		this.qingjiaDao = qingjiaDao;
	}

	@Override
	public QingJia queryById(int id) {
		return qingjiaDao.queryById(id);
	}

	@Override
	public boolean deleteById(int id) {
		return qingjiaDao.deleteById(id);
	}

	@Override
	public boolean update(QingJia t) {
		return qingjiaDao.update(t);
	}

	@Override
	public List<QingJia> queryListByMainID(int mainID) {
		return qingjiaDao.queryListByMainID(mainID);
	}

	@Override
	public List<QingJia> queryListBySEDate(String startDate, String endDate) {
		return qingjiaDao.queryListBySEDate(startDate, endDate);
	}

	@Override
	public boolean add(QingJia t) {
		return qingjiaDao.add(t);
	}

	@Override
	public List<QingJia> queryByUserIDAndDate(int userID, String kqDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
