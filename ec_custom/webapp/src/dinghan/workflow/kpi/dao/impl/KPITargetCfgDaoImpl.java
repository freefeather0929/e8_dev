package dinghan.workflow.kpi.dao.impl;

import dinghan.workflow.kpi.dao.KPITargetCfgDao;
import dinghan.workflow.kpi.entity.KPITargetCfg;
import weaver.conn.RecordSet;

/**
 * 绩效考核配置Dao实现类
 * @author zhangxiaoyu / 10593 2017-12-19
 * 
 */
public class KPITargetCfgDaoImpl implements KPITargetCfgDao {

	@Override
	public KPITargetCfg query(int year, int season, int detp1Id, int detp2Id, int dept3Id) {
		KPITargetCfg targetCfg = null;
		
		String sql = "select id, "
				+ "cfgpsn, "
					+ "cfgdate, "
						+ "cfgyear, "
							+ "cfgseason, "
								+ "cfgcompany, "
									+ "cfgdept1, "
										+ "cfgdept2, "
											+ "cfgdept3"
				+ " from " + KPITargetCFGFormName 
					+ " where cfgdept1 " + (detp1Id>-1?(" = " + detp1Id):"IS NULL") 
							+ " and cfgdept2 " + (detp2Id>-1?(" = " + detp2Id):"IS NULL")
								+ " and cfgdept3 " + (dept3Id>-1?(" = " + dept3Id):"IS NULL")
									+ " and cfgyear = " + year 
										+ " and cfgseason = " + season;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			targetCfg = new KPITargetCfg();
			targetCfg.setId(rs.getInt("id"));
			targetCfg.setCfgpsn(rs.getInt("cfgpsn"));
			targetCfg.setCfgdate(rs.getString("cfgdate"));
			targetCfg.setCfgyear(rs.getInt("cfgyear"));
			targetCfg.setCfgseason(rs.getInt("cfgseason"));
			targetCfg.setCfgcompany(rs.getInt("cfgcompany"));
			targetCfg.setCfgdept1(rs.getInt("cfgdept1"));
			targetCfg.setCfgdept2(rs.getInt("cfgdept2"));
			targetCfg.setCfgdept3(rs.getInt("cfgdept3"));
		}
		
		return targetCfg;
	}
}
