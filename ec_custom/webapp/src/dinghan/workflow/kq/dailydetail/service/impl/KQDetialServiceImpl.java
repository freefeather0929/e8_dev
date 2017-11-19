package dinghan.workflow.kq.dailydetail.service.impl;

import java.util.List;

import dinghan.workflow.kq.dailydetail.dao.KQDetailDao;
import dinghan.workflow.kq.dailydetail.dao.impl.KQDetailDaoImpl;
import dinghan.workflow.kq.dailydetail.entity.KQDetailData;
import dinghan.workflow.kq.dailydetail.service.KQDetialService;
/**
 * 考勤明细Service实现类
 * @author - zhangxiaoyu / 10593 - 2017-11-18
 *
 */
public class KQDetialServiceImpl implements KQDetialService {
	KQDetailDao kqDetailDao = new KQDetailDaoImpl();
	@Override
	public boolean add(KQDetailData detailData) {
		return kqDetailDao.add(detailData);
	}

	@Override
	public boolean update(KQDetailData detailData) {
		return kqDetailDao.update(detailData);
	}

	@Override
	public KQDetailData queryById(int kqDetailId) {
		return kqDetailDao.queryById(kqDetailId);
	}

	@Override
	public KQDetailData queryByUserAndDate(int userID, String kqDate) {
		return kqDetailDao.queryByUserAndDate(userID, kqDate);
	}

	@Override
	public List<KQDetailData> queryListByUserAndDate(int userID, String startDate, String endDate) {
		return kqDetailDao.queryListByUserAndDate(userID, startDate, endDate);
	}

	@Override
	public boolean deleteById(int id) {
		return kqDetailDao.deleteById(id);
	}

}
