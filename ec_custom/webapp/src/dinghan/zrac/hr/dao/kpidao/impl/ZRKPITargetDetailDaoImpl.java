package dinghan.zrac.hr.dao.kpidao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kpi.dao.impl.KPITargetDetailDaoImpl;
import dinghan.workflow.kpi.entity.KPITargetDetail;
import dinghan.zrac.hr.dao.kpidao.ZRKPITargetDetailDao;
import weaver.conn.RecordSet;
import weaver.general.Util;

public class ZRKPITargetDetailDaoImpl extends KPITargetDetailDaoImpl implements ZRKPITargetDetailDao {
	@Override
	public KPITargetDetail queryById(int id) {
		KPITargetDetail kpiTargetDetail = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append("id, "
				+ "mainid, "
					+ "d2_evalpsngrade_w, "
						+ "d2_selfgrade_w, "
							+ "d2_kpitarget, "
								+ "d2_kpistandard, "
									+ "d2_kpiweight, "
										+ "d2_kpistatue, "
											+ "d2_selfgrade, "
												+ "d2_evalpsngrade, "
													+ "d2_evalpsn, "
														+ "d2_evaladvice, "
															+ "d2_evaldate, "
																+ "d2_iscross");
		sql.append(" from ");
		sql.append(KPITargetDetailFormName);
		sql.append(" where id = ");
		sql.append(id);
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql.toString());
		while(rs.next()){
			kpiTargetDetail = new KPITargetDetail();
			kpiTargetDetail.setId(rs.getInt("id"));
			kpiTargetDetail.setMainid(rs.getInt("mainid"));
			kpiTargetDetail.setD2_evalpsngrade_w(rs.getDouble("d2_evalpsngrade_w")>-1?rs.getDouble("d2_evalpsngrade_w"):0.0d);
			kpiTargetDetail.setD2_selfgrade_w(rs.getDouble("d2_selfgrade_w")>-1?rs.getDouble("d2_selfgrade_w"):0.0d);
			kpiTargetDetail.setD2_kpitarget(Util.null2String(rs.getString("d2_kpitarget")));
			kpiTargetDetail.setD2_kpistandard(Util.null2String(rs.getString("d2_kpistandard")));
			kpiTargetDetail.setD2_kpiweight(rs.getInt("d2_kpiweight")>-1?rs.getInt("d2_kpiweight"):0);
			kpiTargetDetail.setD2_kpistatue(Util.null2String(rs.getString("d2_kpistatue")));
			kpiTargetDetail.setD2_selfgrade(rs.getInt("d2_selfgrade")>-1?rs.getInt("d2_selfgrade"):0);
			kpiTargetDetail.setD2_evalpsngrade(rs.getInt("d2_evalpsngrade")>-1?rs.getInt("d2_evalpsngrade"):0);
			kpiTargetDetail.setD2_evalpsn(rs.getInt("d2_evalpsn"));
			kpiTargetDetail.setD2_evaladvice(Util.null2String(rs.getString("d2_evaladvice")));
			kpiTargetDetail.setD2_evaldate(Util.null2String(rs.getString("d2_evaldate")));
			kpiTargetDetail.setD2_iscross(rs.getInt("d2_iscross")>-1?rs.getInt("d2_iscross"):0);
		}
		
		return kpiTargetDetail;
	}

	@Override
	public List<KPITargetDetail> queryAllByMianId(int mainId) {
		List<KPITargetDetail> kpiTargetDetailList = null;
		KPITargetDetail kpiTargetDetail = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select id from ");
			sql.append(KPITargetDetailFormName);
				sql.append(" where mainid = ");
					sql.append(mainId);
					
		RecordSet rs = new RecordSet();
		rs.executeSql(sql.toString());
		
		if(rs.getCounts() > 0){
			kpiTargetDetailList = new ArrayList<KPITargetDetail>();
		}
		
		while(rs.next()){
			kpiTargetDetail = this.queryById(rs.getInt("id"));
			if(kpiTargetDetail!=null){
				kpiTargetDetailList.add(kpiTargetDetail);
			}
		}
					
		return kpiTargetDetailList;
	}
}
