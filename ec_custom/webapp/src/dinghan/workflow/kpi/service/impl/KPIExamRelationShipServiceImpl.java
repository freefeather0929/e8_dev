package dinghan.workflow.kpi.service.impl;

import dinghan.workflow.kpi.dao.CrossKPIProjectInfoDao;
import dinghan.workflow.kpi.dao.KPIExamRelationShipDao;
import dinghan.workflow.kpi.dao.impl.CrossKPIProjectInfoDaoImpl;
import dinghan.workflow.kpi.dao.impl.KPIExamRelationShipDaoImpl;
import dinghan.workflow.kpi.entity.KPIExamRelationShip;
import dinghan.workflow.kpi.service.KPIExamRelationShipService;
/**
 * 绩效考核关系Service
 * @author zhangxiaoyu/10593 - 2017-12-15
 * 
 */
public class KPIExamRelationShipServiceImpl implements KPIExamRelationShipService {
	
	KPIExamRelationShipDao kpiExamRelationShipDao = new KPIExamRelationShipDaoImpl();
	CrossKPIProjectInfoDao crossKPIProjectInfoDao = new CrossKPIProjectInfoDaoImpl();
	
	@Override
	public KPIExamRelationShip query(int userId, int year, String season) {
		KPIExamRelationShip relationShip = kpiExamRelationShipDao.query(userId, year, season);
		relationShip.setCrossKPIProjectInfoList(crossKPIProjectInfoDao.queryAllByMainId(relationShip.getId()));
		return relationShip;
	}

}
