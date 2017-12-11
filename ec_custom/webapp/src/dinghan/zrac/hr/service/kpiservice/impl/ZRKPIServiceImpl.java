package dinghan.zrac.hr.service.kpiservice.impl;

import dinghan.zrac.hr.dao.kpidao.ZRSeasonKPIDAO;
import dinghan.zrac.hr.dao.kpidao.impl.ZRSeasonKPIDAOImpl;
import dinghan.zrac.hr.entity.kpientity.ZRSeasonKPI;
import dinghan.zrac.hr.service.kpiservice.ZRKPIService;

/**
 * 中车季度KPI考核Service实现类
 * @author zhangxiaoyu/10593 - 2017-12-07
 *	
 */
public class ZRKPIServiceImpl implements ZRKPIService {

	ZRSeasonKPIDAO zrSeasonKPIDao = new ZRSeasonKPIDAOImpl();
	
	@Override
	public ZRSeasonKPI queryByID(int id) {
		return zrSeasonKPIDao.queryByID(id);
	}

	@Override
	public ZRSeasonKPI queryByUserIdAndSeason(int userId, int year, String season) {
		return zrSeasonKPIDao.queryByUserIdAndSeason(userId, year, season);
	}

}
