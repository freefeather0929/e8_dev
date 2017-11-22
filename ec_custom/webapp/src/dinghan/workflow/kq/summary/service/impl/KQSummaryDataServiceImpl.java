package dinghan.workflow.kq.summary.service.impl;

import dinghan.workflow.kq.summary.dao.KQSummaryDataDao;
import dinghan.workflow.kq.summary.dao.impl.KQSummaryDataDaoImpl;
import dinghan.workflow.kq.summary.entity.KQSummaryData;
import dinghan.workflow.kq.summary.service.KQSummaryDataService;
/**
 * 考勤汇总Service 实现类
 * @author zhangxiaoyu / 10593 - 2017-11-22
 * 
 */
public class KQSummaryDataServiceImpl implements KQSummaryDataService {
	private KQSummaryDataDao kqSummaryDataDao = new KQSummaryDataDaoImpl();
	@Override
	public KQSummaryData queryByUserIDAndMonth(int userId, String month) {
		return kqSummaryDataDao.queryByUserIDAndMonth(userId, month);
	}

	@Override
	public KQSummaryData queryByID(int id) {
		return kqSummaryDataDao.queryByID(id);
	}

	@Override
	public boolean add(KQSummaryData kqSummaryData) {
		return kqSummaryDataDao.add(kqSummaryData);
	}

	@Override
	public boolean update(KQSummaryData kqSummaryData) {
		return kqSummaryDataDao.update(kqSummaryData);
	}

	@Override
	public boolean delete(int id) {
		return kqSummaryDataDao.delete(id);
	}

}
