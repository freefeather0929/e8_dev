package dinghan.workflow.kpi.service.impl;

import java.util.List;

import dinghan.workflow.kpi.dao.KPITargetCfgDao;
import dinghan.workflow.kpi.dao.KPITargetDao;
import dinghan.workflow.kpi.dao.impl.KPITargetCfgDaoImpl;
import dinghan.workflow.kpi.dao.impl.KPITargetDaoImpl;
import dinghan.workflow.kpi.entity.KPITarget;
import dinghan.workflow.kpi.entity.KPITargetCfg;
import dinghan.workflow.kpi.service.KPITargetCfgService;

/**
 * 绩效考核目标Service实现类
 * @author zhangxiaoyu / 10593 2017-12-20
 * 
 */
public class KPITargetCfgServiceImpl implements KPITargetCfgService {
	
	private KPITargetCfgDao kpiTargetCfgDao = new KPITargetCfgDaoImpl();
	private KPITargetDao kpiTargetDao = new KPITargetDaoImpl();
	@Override
	public KPITargetCfg query(int year, int season, int detp1Id, int detp2Id, int dept3Id) {
		KPITargetCfg kpiTargetCfg = null;
		kpiTargetCfg = kpiTargetCfgDao.query(year, season, detp1Id, detp2Id, dept3Id);
		
		if(kpiTargetCfg != null){
			List<KPITarget> kpiTargetList = kpiTargetDao.queryAllByMainId(kpiTargetCfg.getId());
			kpiTargetCfg.setTartgetList(kpiTargetList);
		}
		
		return kpiTargetCfg;
	}

}
