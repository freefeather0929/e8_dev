package dinghan.zrac.hr.dao.kpidao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.zrac.hr.dao.kpidao.ZRKPI2ndMonthSummaryDao;
import dinghan.zrac.hr.entity.kpientity.ZRKPI2ndMonthSummary;
import dinghan.zrac.hr.entity.kpientity.ZRKPIMonthSummary;
import weaver.conn.RecordSet;

/**
 * 季度绩效考核第二个月度总结Dao实现类
 * @author zhangxiaoyu / 10593 - 2017-12-28
 * 
 */
public class ZRKPI2ndMonthSummaryDaoImpl implements ZRKPI2ndMonthSummaryDao{
	
	@Override
	public ZRKPIMonthSummary queryById(int id) {
		ZRKPIMonthSummary kpiMonthSummary = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append("select ");
		sql.append("id,"
					+ " mainid,"
						+ " d_m2_kpitarget,"
							+ " d_m2_kpistandard,"
								+ " d_m2_kpiweight,"
									+ " d_m2_summary,"
										+ " d_m2_sumpsn,"
											+ " d_m2_sumdate,"
												+ "d_m2_kpidtid");
		sql.append(" from ");
		sql.append(ZRKPIMonthSummaryFormName);
		sql.append(" where id = ");
		sql.append(id);
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql.toString());
		while(rs.next()){
			kpiMonthSummary = new ZRKPI2ndMonthSummary();
			kpiMonthSummary.setId(rs.getInt("id"));
			kpiMonthSummary.setMainid(rs.getInt("mainid"));
			kpiMonthSummary.setKpitarget(rs.getString("d_m2_kpitarget"));
			kpiMonthSummary.setKpistandard(rs.getString("d_m2_kpistandard"));
			kpiMonthSummary.setKpiweight(rs.getInt("d_m2_kpiweight"));
			kpiMonthSummary.setSummary(rs.getString("d_m2_summary"));
			kpiMonthSummary.setSumpsn(rs.getInt("d_m2_sumpsn"));
			kpiMonthSummary.setSumdate(rs.getString("d_m2_sumdate"));
			kpiMonthSummary.setKpidtid(rs.getInt("d_m2_kpidtid"));
		}
		
		return kpiMonthSummary;
	}

	@Override
	public boolean add(ZRKPIMonthSummary kpiMonthSummary) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ");
		sql.append(ZRKPIMonthSummaryFormName);
		sql.append(" ("
				+ " mainid,"
					+ " d_m2_kpitarget,"
						+ " d_m2_kpistandard,"
							+ " d_m2_kpiweight,"
								+ " d_m2_summary,"
									+ " d_m2_sumpsn,"
										+ " d_m2_sumdate,"
											+ " d_m2_kpidtid"
												+ ")");
		sql.append(" values ");
		sql.append("(");
		sql.append("'"+kpiMonthSummary.getMainid()+"'");
		sql.append(",'"+kpiMonthSummary.getKpitarget()+"'");
		sql.append(",'"+kpiMonthSummary.getKpistandard()+"'");
		sql.append(",'"+kpiMonthSummary.getKpiweight()+"'");
		sql.append(",'"+kpiMonthSummary.getSummary()+"'");
		sql.append(",NULL");
		sql.append(",''");
		sql.append(",'"+kpiMonthSummary.getKpidtid()+"'");
		sql.append(")");
		
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql.toString());
	}

	@Override
	public boolean deleteById(int id) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append(ZRKPIMonthSummaryFormName);
		sql.append(" where id = ");
		sql.append(id);
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql.toString());
	}

	@Override
	public boolean update(ZRKPIMonthSummary kpiMonthSummary) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ");
		sql.append(ZRKPIMonthSummaryFormName);
		sql.append(" set ");
		sql.append("mainid = '");
		sql.append(kpiMonthSummary.getMainid());
		sql.append("',");
		sql.append("d_m2_kpitarget = '");
		sql.append(kpiMonthSummary.getKpitarget());
		sql.append("',");
		sql.append("d_m2_kpistandard = '");
		sql.append(kpiMonthSummary.getKpistandard());
		sql.append("',");
		sql.append("d_m2_kpiweight = '");
		sql.append(kpiMonthSummary.getKpiweight());
		sql.append("',");
		sql.append("d_m2_summary = '");
		sql.append(kpiMonthSummary.getSummary());
		sql.append("',");
		sql.append("d_m2_sumpsn = '");
		sql.append(kpiMonthSummary.getSumpsn());
		sql.append("',");
		sql.append("d_m2_sumdate = '");
		sql.append(kpiMonthSummary.getSumdate());
		sql.append("',");
		sql.append("d_m2_kpidtid = '");
		sql.append(kpiMonthSummary.getKpidtid());
		sql.append("'");
		sql.append(" where id = ");
		sql.append(kpiMonthSummary.getId());
		
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql.toString());
	}
	
	@Override
	public ZRKPIMonthSummary queryByKPIDTId(int kpiDTId) {
		ZRKPIMonthSummary kpiMonthSummary = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select");
		sql.append(" id");
		sql.append(" from ");
		sql.append(ZRKPIMonthSummaryFormName);
		sql.append(" where d_m2_kpidtid = ");
		sql.append(kpiDTId);
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql.toString());
		
		while(rs.next()){
			kpiMonthSummary = this.queryById(rs.getInt("id"));
		}
		
		return kpiMonthSummary;
	}
	
	@Override
	public List<ZRKPIMonthSummary> queryAllByMainId(int mainid) {
		List<ZRKPIMonthSummary> kpiMonthSummaryList = null;
		ZRKPIMonthSummary kpiMonthSummary = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select");
		sql.append(" id");
		sql.append(" from ");
		sql.append(ZRKPIMonthSummaryFormName);
		sql.append(" where mainid = ");
		sql.append(mainid);
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql.toString());
		
		if(rs.getCounts() > 0){
			kpiMonthSummaryList = new ArrayList<ZRKPIMonthSummary>();
		}
		
		while(rs.next()){
			kpiMonthSummary = this.queryById(rs.getInt("id"));
			if( kpiMonthSummary!=null ){
				kpiMonthSummaryList.add(kpiMonthSummary);
			}
		}
		
		return kpiMonthSummaryList;
	}
	
}
