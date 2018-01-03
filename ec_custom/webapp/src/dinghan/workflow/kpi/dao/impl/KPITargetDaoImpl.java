package dinghan.workflow.kpi.dao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kpi.dao.KPITargetDao;
import dinghan.workflow.kpi.entity.KPITarget;
import weaver.conn.RecordSet;

/**
 * 绩效考核目标Dao实现类
 * @author zhangxiaoyu / 10593 2017-12-19
 * 
 */
public class KPITargetDaoImpl implements KPITargetDao{

	@Override
	public KPITarget query(int id) {
		KPITarget target = null;
		
		String sql = "select id, mainid, target, standard from "
				+ KPITargetFormName
					+ " where id = " + id;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			target = new KPITarget();
			target.setId(rs.getInt("id"));
			target.setMainid(rs.getInt("mainid"));
			target.setTarget(rs.getString("target"));
			target.setStandard(rs.getString("standard"));
		}
		return target;
	}

	@Override
	public List<KPITarget> queryAllByMainId(int mainid) {
		List<KPITarget> kpiTargetList = null;
		KPITarget target = null;
		
		String sql = "select id from "
				+ KPITargetFormName 
					+ " where mainid = " + mainid;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			kpiTargetList = new ArrayList<KPITarget>();
			while(rs.next()){
				target = this.query(rs.getInt("id"));
				if(target != null){
					kpiTargetList.add(target);
				}
			}
		}
		
		return kpiTargetList;
	}
	
}
