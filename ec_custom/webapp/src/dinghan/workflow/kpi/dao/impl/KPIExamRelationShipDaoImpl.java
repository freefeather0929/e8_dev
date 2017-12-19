package dinghan.workflow.kpi.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kpi.dao.KPIExamRelationShipDao;
import dinghan.workflow.kpi.entity.KPIExamRelationShip;
import weaver.conn.RecordSet;

/**
 * 绩效考核关系Dao 实现类
 * @author zhangxiaoyu/10593 - 2017-12-07
 * 
 */
public class KPIExamRelationShipDaoImpl implements KPIExamRelationShipDao {
	private Log log = LogFactory.getLog(KPIExamRelationShipDaoImpl.class.getName());
	@Override
	public KPIExamRelationShip query(int userId, int year, String season) {
		KPIExamRelationShip relationShip = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append("select id, cfgyear, cfgrempl, exampsn, reviewpsn, cfgseason from ");
		sql.append(KPIExamRelationShipFormName);
		sql.append(" where cfgyear = ");
		sql.append(year);
		sql.append(" and cfgrempl = ");
		sql.append(userId);
		sql.append(" and cfgseason = ");
		sql.append(this.getSeasonIndex(season));
		
		log.error("KPIExamRelationShip query sql :: " + sql.toString());
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql.toString());
		while(rs.next()){
			relationShip = new KPIExamRelationShip();
			relationShip.setId(rs.getInt("id"));
			relationShip.setCfgYear(rs.getInt("cfgyear"));
			relationShip.setCfgREmpl(rs.getInt("cfgrempl"));
			relationShip.setExamPsn(rs.getInt("exampsn"));
			relationShip.setReviewPsn(rs.getInt("reviewpsn"));
			relationShip.setSeason(rs.getInt("cfgseason"));
		}

		return relationShip;
	}

	/**
	 * 获取季度index
	 * @param season
	 * @return
	 */
	private int getSeasonIndex(String season){
		int index = -1;
		switch(season){
			case "一":
				index = 0;
				break;
			case "二":
				index = 1;
				break;
			case "三":
				index = 2;
				break;
			case "四":
				index = 3;
				break;
		}
		
		return index;
	}
	
}
