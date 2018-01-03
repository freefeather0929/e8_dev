package dinghan.zrac.hr.dao.kpidao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.zrac.hr.dao.kpidao.ZRSeasonKPIDAO;
import dinghan.zrac.hr.entity.kpientity.ZRSeasonKPI;
import weaver.conn.RecordSet;

/**
 * 中车季度KPI考核DAO实现类
 * @author zhangxiaoyu/10593 - 2017-12-15
 * 
 * @param <T>
 */
public class ZRSeasonKPIDAOImpl implements ZRSeasonKPIDAO {
	private Log log = LogFactory.getLog(ZRSeasonKPIDAOImpl.class.getName());
	@Override
	public ZRSeasonKPI queryByID(int id) {
		
		ZRSeasonKPI zrSeasonKPI = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append(
				"id,"
					+ "requestId,"
						+ "appno,"
							+ "apppsn,"
								+ "appdate,"
									+ "company,"
										+ "appdept1,"
											+ "appdept2,"
												+ "appdept3,"
													+ "appposi,"
														+ "exampsn,"
															+ "reviewpsn,"
																+ "appyear,"
																	+ "appseason,"
																		+ "selfmeasure,"
																			+ "measureresult,"
																				+ "finalresult,"
																					+ "reviewresult,"
																						+ "monthchecked"
				);
		
		sql.append(" from ");
		sql.append(ZRSeasonKPIFormName);
		sql.append(" where ");
		sql.append(" id = " + id);
		//log.error("ZRSeasonKPI queryByID sql :: " + sql.toString());
		RecordSet rs = new RecordSet();
		rs.executeSql(sql.toString());
		
		while(rs.next()){
			zrSeasonKPI = new ZRSeasonKPI();
			zrSeasonKPI.setId(rs.getInt("id"));
			zrSeasonKPI.setRequestId(rs.getInt("requestId"));
			zrSeasonKPI.setAppno(rs.getString("appno"));
			zrSeasonKPI.setAppPsn(rs.getInt("apppsn"));
			zrSeasonKPI.setAppDate(rs.getString("appdate"));
			zrSeasonKPI.setCompany(rs.getInt("company"));
			zrSeasonKPI.setAppDept1(rs.getInt("appdept1"));
			zrSeasonKPI.setAppDept2(rs.getInt("appdept2"));
			zrSeasonKPI.setAppDept3(rs.getInt("appdept3"));
			zrSeasonKPI.setAppPosi(rs.getInt("appposi"));
			zrSeasonKPI.setExamPsn(rs.getInt("exampsn"));
			zrSeasonKPI.setReviewPsn(rs.getInt("reviewpsn"));
			zrSeasonKPI.setAppYear(rs.getInt("appyear"));
			zrSeasonKPI.setAppSeason(rs.getString("appseason"));
			zrSeasonKPI.setSelfMeasure(rs.getInt("selfmeasure"));
			zrSeasonKPI.setMeasureResult(rs.getInt("measureresult"));
			zrSeasonKPI.setFinalResult(rs.getInt("finalresult"));
			zrSeasonKPI.setReviewResult(rs.getInt("reviewresult"));
			zrSeasonKPI.setMonthChecked(rs.getInt("monthchecked"));
		}
		
		return zrSeasonKPI;
	}

	@Override
	public ZRSeasonKPI queryByUserIdAndSeason(int userId, int year, String season) {
		ZRSeasonKPI zrSeasonKPI = null;
		StringBuilder sql = new StringBuilder();
			sql.append("select top 1 id from ");
				sql.append(ZRSeasonKPIFormName);
					sql.append(" where apppsn = ");
						sql.append(userId);
							sql.append(" and ");
								sql.append(" appyear = ");
									sql.append(year);
										sql.append(" and ");
											sql.append(" appseason = '");
												sql.append(season);
													sql.append("'");
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql.toString());
		
		while(rs.next()){
			zrSeasonKPI = this.queryByID(rs.getInt("id"));
		}
		
		return zrSeasonKPI;
	}

}
