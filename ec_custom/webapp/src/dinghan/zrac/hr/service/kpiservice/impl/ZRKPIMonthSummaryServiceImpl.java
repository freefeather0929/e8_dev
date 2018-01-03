package dinghan.zrac.hr.service.kpiservice.impl;

import java.util.List;

import dinghan.zrac.hr.dao.kpidao.ZRKPIMonthSummaryDao;
import dinghan.zrac.hr.entity.kpientity.ZRKPIMonthSummary;
import dinghan.zrac.hr.service.kpiservice.ZRKPIMonthSummaryService;

/**
 * 中车季度绩效考核月度总结Service实现类
 * @author zhangxiaoyu / 10593
 * 
 */
public class ZRKPIMonthSummaryServiceImpl implements ZRKPIMonthSummaryService {
	
	private ZRKPIMonthSummaryDao<ZRKPIMonthSummary> zrKPIMonthSummaryDao;
	
	public ZRKPIMonthSummaryServiceImpl(ZRKPIMonthSummaryDao<ZRKPIMonthSummary> zrKPIMonthSummaryDao){
		this.zrKPIMonthSummaryDao = zrKPIMonthSummaryDao; 
	};
	
	@Override
	public ZRKPIMonthSummary queryById(int id) {
		return this.zrKPIMonthSummaryDao.queryById(id);
	}

	@Override
	public boolean add(ZRKPIMonthSummary kpiMonthSummary) {
		return this.zrKPIMonthSummaryDao.add(kpiMonthSummary);
	}

	@Override
	public boolean deleteById(int id) {
		return this.zrKPIMonthSummaryDao.deleteById(id);
	}

	@Override
	public boolean update(ZRKPIMonthSummary kpiMonthSummary) {
		return zrKPIMonthSummaryDao.update(kpiMonthSummary);
	}

	@Override
	public ZRKPIMonthSummary queryByKPIDTId(int kpiDTId) {
		return zrKPIMonthSummaryDao.queryByKPIDTId(kpiDTId);
	}
	
	@Override
	public List<ZRKPIMonthSummary> queryAllByMainId(int mainid){
		return zrKPIMonthSummaryDao.queryAllByMainId(mainid);
	}
	
	public void setZrKPIMonthSummaryDao(ZRKPIMonthSummaryDao<ZRKPIMonthSummary> zrKPIMonthSummaryDao) {
		this.zrKPIMonthSummaryDao = zrKPIMonthSummaryDao;
	}

}
