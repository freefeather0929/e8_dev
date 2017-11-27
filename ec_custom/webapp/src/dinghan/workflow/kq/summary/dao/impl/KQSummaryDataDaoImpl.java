package dinghan.workflow.kq.summary.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.summary.dao.KQSummaryDataDao;
import dinghan.workflow.kq.summary.entity.KQSummaryData;
import dinghan.workflow.kq.userinfo.offdays.AnnualDaysHaddle;
import weaver.conn.RecordSet;
import weaver.general.Util;
/**
 * 考勤汇总DAO 实现类
 * @author zhangxiaoyu / 10593 - 2017-11-22
 * 
 */
public class KQSummaryDataDaoImpl implements KQSummaryDataDao {
	private Log log = LogFactory.getLog(KQSummaryDataDaoImpl.class.getName());
	@Override
	public KQSummaryData queryByUserIDAndMonth(int userId, String month) {
		KQSummaryData kqSummaryData = null;
		String sql = "select id from " + KQSummaryFormName + " where xm = " + userId + " and hzyf = '"+month+"'";
		log.error("获取月考勤汇总  sql :: " + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			kqSummaryData = this.queryByID(rs.getInt("id")); 
		}
		
		return kqSummaryData;
	}

	@Override
	public KQSummaryData queryByID(int id) {
		KQSummaryData kqSummaryData = null;
		
		String sql = "select "
				+ "id, "
					+ "requestId, "
						+ "gh, "
							+ "xm, "
								+ "yjbm, "
									+ "ejbm, "
										+ "rzzt, "
											+ "ycqts, "
												+ "jbgs, "
													+ "jbztx, "
														+ "sj, "
															+ "bj, "
																+ "nxj, "
																	+ "txj, "
																		+ "hj, "
																			+ "sangj, "
																				+ "cj, "
																					+ "pcj, "
																						+ "cjj, "
				+ "lcj, "
					+ "jyj, "
						+ "jsj, "
							+ "grgs, "
								+ "trgs, "
									+ "brj, "
										+ "kg, "
											+ "wdk, "
												+ "cd, "
													+ "zt, "
														+ "synx, "
															+ "sytx, "
																+ "kqrq, "
																	+ "kqlb, "
																		+ "hzyf, "
																			+ "cdTime, "
																				+ "ztTime, "
																			
				+ "formmodeid, "
					+ "modedatacreater, "
						+ "modedatacreatertype, "
							+ "modedatacreatedate, "
								+ "modedatacreatetime,"
				+ " ssgs,"
					+ " gw,"
						+ " hjj"
				+ " from "+ KQSummaryFormName + " where id = " + id;
		
		
		log.error("获取月考勤汇总  By ID sql :: " + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			kqSummaryData = new KQSummaryData();
			kqSummaryData.setId(rs.getInt("id"));
			kqSummaryData.setGh(Util.null2String(rs.getString("gh")));
			kqSummaryData.setXm(rs.getInt("xm"));
			kqSummaryData.setYjbm(Util.null2String(rs.getString("yjbm")));
			kqSummaryData.setEjbm(Util.null2String(rs.getString("ejbm")));
			kqSummaryData.setRzzt(Util.null2String(rs.getString("rzzt")));
			kqSummaryData.setYcqts(rs.getDouble("ycqts"));
			kqSummaryData.setJbgs(rs.getDouble("jbgs"));
			kqSummaryData.setJbztx(rs.getDouble("jbztx"));
			kqSummaryData.setSj(rs.getDouble("sj"));
			kqSummaryData.setBj(rs.getDouble("bj"));
			kqSummaryData.setNxj(rs.getDouble("nxj"));
			kqSummaryData.setTxj(rs.getDouble("txj"));
			kqSummaryData.setHj(rs.getDouble("hj"));
			kqSummaryData.setSangj(rs.getDouble("sangj"));
			kqSummaryData.setCj(rs.getDouble("cj"));
			kqSummaryData.setPcj(rs.getDouble("pcj"));
			kqSummaryData.setCjj(rs.getDouble("cjj"));
			kqSummaryData.setLcj(rs.getDouble("lcj"));
			kqSummaryData.setJyj(rs.getDouble("jyj"));
			kqSummaryData.setJsj(rs.getDouble("jsj"));
			kqSummaryData.setGrgs(rs.getDouble("grgs"));
			kqSummaryData.setTrgs(rs.getDouble("trgs"));
			kqSummaryData.setBrj(rs.getDouble("brj"));
			kqSummaryData.setKg(rs.getDouble("kg"));
			kqSummaryData.setWdk(rs.getDouble("wdk"));
			kqSummaryData.setCd(rs.getDouble("cd"));
			kqSummaryData.setZt(rs.getDouble("zt"));
			kqSummaryData.setSynx(rs.getDouble("synx"));
			kqSummaryData.setSytx(rs.getDouble("sytx"));
			kqSummaryData.setKqrq(Util.null2String(rs.getString("kqrq")));
			kqSummaryData.setKqlb(rs.getInt("kqlb"));
			kqSummaryData.setHzyf(Util.null2String(rs.getString("hzyf")));
			kqSummaryData.setCdTime(rs.getDouble("cdTime"));
			kqSummaryData.setZtTime(rs.getDouble("ztTime"));
			kqSummaryData.setFormmodeid(rs.getInt("formmodeid"));
			kqSummaryData.setModedatacreater(rs.getInt("modedatacreater"));
			kqSummaryData.setModedatacreatertype(rs.getInt("modedatacreatertype"));
			kqSummaryData.setModedatacreatedate(Util.null2String(rs.getString("modedatacreatedate")));
			kqSummaryData.setModedatacreatetime(Util.null2String(rs.getString("modedatacreatetime")));
			kqSummaryData.setSsgs(rs.getInt("ssgs"));
			kqSummaryData.setGw(rs.getInt("gw"));
			kqSummaryData.setHjj(rs.getDouble("hjj"));
		}
				
		return kqSummaryData;
	}

	@Override
	public boolean add(KQSummaryData kqSummaryData) {
		String sql = "insert into "+KQSummaryFormName+" ("
				//+ "id, "
				+ "requestId, "
					+ "gh, "
						+ "xm, "
							+ "yjbm, "
								+ "ejbm, "
									+ "rzzt, "
										+ "ycqts, "
											+ "jbgs, "
												+ "jbztx, "
													+ "sj, "
														+ "bj, "
															+ "nxj, "
																+ "txj, "
																	+ "hj, "
																		+ "sangj, "
																			+ "cj, "
																				+ "pcj, "
																					+ "cjj, "
				+ "lcj, "
					+ "jyj, "
						+ "jsj, "
							+ "grgs, "
								+ "trgs, "
									+ "brj, "
										+ "kg, "
											+ "wdk, "
												+ "cd, "
													+ "zt, "
														+ "synx, "
															+ "sytx, "
																+ "kqrq, "
																	+ "kqlb, "
																		+ "hzyf, "
																			+ "cdTime, "
																				+ "ztTime, "
																			
				+ "formmodeid, "
					+ "modedatacreater, "
						+ "modedatacreatertype, "
							+ "modedatacreatedate, "
								+ "modedatacreatetime,"
				+ " ssgs,"
					+ " gw,"
						+ " hjj"
				+ ") values ("
					+ "NULL,"
						+ " '"+kqSummaryData.getGh()+"',"
							+ " '"+kqSummaryData.getXm()+"',"
								+ " '"+kqSummaryData.getYjbm()+"',"
									+ " '"+kqSummaryData.getEjbm()+"',"
										+ " '"+kqSummaryData.getRzzt()+"',"
											+ " '"+kqSummaryData.getYcqts()+"',"
												+ " '"+kqSummaryData.getJbgs()+"',"
													+ " '"+kqSummaryData.getJbztx()+"',"
														+ " '"+kqSummaryData.getSj()+"',"
															+ " '"+kqSummaryData.getBj()+"',"
																+ " '"+kqSummaryData.getNxj()+"',"
																	+ " '"+kqSummaryData.getTxj()+"',"
																		+ " '"+kqSummaryData.getHj()+"',"
																			+ " '"+kqSummaryData.getSangj()+"',"
																				+ " '"+kqSummaryData.getCj()+"',"
																					+ "'"+kqSummaryData.getPcj()+"',"
																						+ " '"+kqSummaryData.getCjj()+"',"
						+ " '"+kqSummaryData.getLcj()+"',"
							+ " '"+kqSummaryData.getJyj()+"',"
								+ " '"+kqSummaryData.getJsj()+"',"
									+ " '"+kqSummaryData.getGrgs()+"',"
										+ " '"+kqSummaryData.getTrgs()+"',"
											+ " '"+kqSummaryData.getBrj()+"',"
												+ " '"+kqSummaryData.getKg()+"',"
													+ " '"+kqSummaryData.getWdk()+"',"
														+ " '"+kqSummaryData.getCd()+"',"
															+ " '"+kqSummaryData.getZt()+"',"
																+ " '"+kqSummaryData.getSynx()+"',"
																	+ " '"+kqSummaryData.getSytx()+"',"
																		+ " '"+kqSummaryData.getKqrq()+"',"
																			+ " '"+kqSummaryData.getKqlb()+"',"
																				+ " '"+kqSummaryData.getHzyf()+"',"
																					+ " '"+kqSummaryData.getCdTime()+"',"
																						+ " '"+kqSummaryData.getZtTime()+"',"
						+ " '"+kqSummaryData.getFormmodeid()+"',"
							+ " '"+kqSummaryData.getModedatacreater()+"',"
								+ " '"+kqSummaryData.getModedatacreatertype()+"',"
									+ " '"+kqSummaryData.getModedatacreatedate()+"',"
										+ " '"+kqSummaryData.getModedatacreatetime()+"',"
						+ " '"+kqSummaryData.getSsgs()+"',"
							+ " '"+kqSummaryData.getGw()+"',"
								+ " '"+kqSummaryData.getHjj()+"'"
						+ ")";
		
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public boolean update(KQSummaryData kqSummaryData) {
		String sql = "UPDATE "+KQSummaryFormName+" SET "
				//+ "id='2529', "
				//+ "requestId=NULL, "
				+ "gh='"+kqSummaryData.getGh()+"', "
					+ "xm='"+kqSummaryData.getXm()+"', "
						+ "yjbm='"+kqSummaryData.getYjbm()+"', "
							+ "ejbm='"+kqSummaryData.getEjbm()+"', "
								+ "rzzt='"+kqSummaryData.getRzzt()+"', "
									+ "ycqts='"+kqSummaryData.getYcqts()+"', "
										+ "jbgs='"+kqSummaryData.getJbgs()+"', "
											+ "jbztx='"+kqSummaryData.getJbztx()+"', "
												+ "sj='"+kqSummaryData.getSj()+"', "
													+ "bj='"+kqSummaryData.getBj()+"', "
														+ "nxj='"+kqSummaryData.getNxj()+"', "
															+ "txj='"+kqSummaryData.getTxj()+"', "
																+ "hj='"+kqSummaryData.getHj()+"', "
																	+ "sangj='"+kqSummaryData.getSangj()+"', "
																		+ "cj='"+kqSummaryData.getCj()+"', "
																			+ "pcj='"+kqSummaryData.getPcj()+"', "
																				+ "cjj='"+kqSummaryData.getCjj()+"', "
					+ "lcj='"+kqSummaryData.getLcj()+"', "
						+ "jyj='"+kqSummaryData.getJyj()+"', "
							+ "jsj='"+kqSummaryData.getJsj()+"', "
								+ "grgs='"+kqSummaryData.getGrgs()+"', "
									+ "trgs='"+kqSummaryData.getTrgs()+"', "
										+ "brj='"+kqSummaryData.getBrj()+"', "
											+ "kg='"+kqSummaryData.getKg()+"', "
												+ "wdk='"+kqSummaryData.getWdk()+"', "
													+ "cd='"+kqSummaryData.getCd()+"', "
														+ "zt='"+kqSummaryData.getZt()+"', "
															+ "synx='"+kqSummaryData.getSynx()+"', "
																+ "sytx='"+kqSummaryData.getSytx()+"', "
																	+ "kqrq='"+kqSummaryData.getKqrq()+"', "
																		+ "kqlb='"+kqSummaryData.getKqlb()+"', "
																			+ "hzyf='"+kqSummaryData.getHzyf()+"', "
																				+ "cdTime='"+kqSummaryData.getCdTime()+"', "
																					+ "ztTime='"+kqSummaryData.getZtTime()+"', "
				+ "formmodeid='"+kqSummaryData.getFormmodeid()+"', "
					+ "modedatacreater='"+kqSummaryData.getModedatacreater()+"', "
						+ "modedatacreatertype='"+kqSummaryData.getModedatacreatertype()+"', "
							+ "modedatacreatedate='"+kqSummaryData.getModedatacreatedate()+"', "
								+ "modedatacreatetime='"+kqSummaryData.getModedatacreatetime()+"', "
				+ "ssgs='"+kqSummaryData.getSsgs()+"', "
					+ "gw='"+kqSummaryData.getGw()+"', "
						+ "hjj='"+kqSummaryData.getHjj()+"'"
				+ " WHERE id= " + kqSummaryData.getId();

		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from " + KQSummaryFormName + " where id = " + id;
		
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

}
