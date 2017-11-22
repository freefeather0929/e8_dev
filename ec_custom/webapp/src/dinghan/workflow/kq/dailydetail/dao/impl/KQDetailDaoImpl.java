package dinghan.workflow.kq.dailydetail.dao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.dailydetail.dao.KQDetailDao;
import dinghan.workflow.kq.dailydetail.entity.KQDetailData;
import weaver.conn.RecordSet;
/**
 * 考勤明细Dao实现类
 * @author zhangxiaoyu / 10593 - 2017-11-20
 *	
 */
public class KQDetailDaoImpl implements KQDetailDao {

	@Override
	public boolean add(KQDetailData detailData) {
		String sql = "insert into " + KQDetailFormName + " ("
				+ "requestId,"
					+ "kqrq, "
						+ "gh, "
							+ "wdk, "
								+ "xm, "
									+ "cd, "
										+ "zaot, "
											+ "ssgs, "
												+ "gw, "
													+ "zt, "
														+ "jbgs, "
															+ "sj, "
																+ "bj, "
				+ "nxj, "
					+ "txj, "
						+ "hj, "
							+ "sangj, "
								+ "cj, "
									+ "cjj, "
										+ "lcj, "
											+ "jyj, "
												+ "jsj, "
													+ "grgs, "
														+ "trgs, "
															+ "brj, "
				+ "kg, "
					+ "formmodeid, "
						+ "modedatacreater, "
							+ "modedatacreatertype, "
								+ "modedatacreatedate, "
									+ "modedatacreatetime, "
										+ "scdksj, "
											+ "mcdksj, "
												+ "kqlb_n, "
													+ "yjbm_n, "
														+ "ejbm_n, "
															+ "jbztx, "
																+ "pcj, "
																	+ "hjj "
				+ ") values ("
				+ "NULL,"
					+ " '"+detailData.getKqrq()+"',"
							+ " '"+detailData.getGh()+"',"
									+ " '"+detailData.getWdk()+"',"
											+ " '"+detailData.getXm()+"',"
													+ " '"+detailData.getCd()+"',"
															+ " '"+detailData.getZaot()+"',"
																	+ " '"+detailData.getSsgs()+"',"
																			+ " '"+detailData.getGw()+"',"
																					+ " '"+detailData.getZt()+"',"
																							+ " '"+detailData.getJbgs()+"',"
																									+ " '"+detailData.getSj()+"',"
																											+ " '"+detailData.getBj()+"',"
				+ " '"+detailData.getNxj()+"',"
						+ " '"+detailData.getTxj()+"',"
								+ " '"+detailData.getHj()+"',"
										+ " '"+detailData.getSangj()+"',"
												+ " '"+detailData.getCj()+"',"
														+ " '"+detailData.getCjj()+"',"
																+ " '"+detailData.getLcj()+"',"
																		+ " '"+detailData.getJyj()+"',"
																				+ " '"+detailData.getJsj()+"',"
																						+ " '"+detailData.getGrgs()+"',"
																								+ " '"+detailData.getTrgs()+"',"
																										+ " '"+detailData.getBrj()+"',"
		        + " '"+detailData.getKg()+"',"
		        		+ " '"+detailData.getFormmodeid()+"',"
		        				+ " '"+detailData.getModedatacreater()+"',"
		        						+ " '"+detailData.getModedatacreatertype()+"',"
		        								+ " '"+detailData.getModedatacreatedate()+"',"
		        										+ " '"+detailData.getModedatacreatetime()+"',"
		        												+ " '"+detailData.getScdksj()+"',"
		        														+ " '"+detailData.getMcdksj()+"',"
		        																+ " '"+detailData.getKqlb_n()+"',"
		        																		+ " '"+detailData.getYjbm_n()+"',"
		        																				+ " '"+detailData.getEjbm_n()+"',"
		        																						+ " '"+detailData.getJbztx()+"',"
		        																								+ " '"+detailData.getPcj()+"',"
		        																										+ " '"+detailData.getHjj()+"' "
				
				+ ")";
		
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public boolean update(KQDetailData detailData) {
		String sql = "update "+KQDetailFormName+" set"
				//+ " id='67452',"
					//+ " requestId=NULL,"
						+ " kqrq='"+detailData.getKqrq()+"',"
							+ " gh='"+detailData.getGh()+"',"
								+ " wdk='"+detailData.getWdk()+"',"
									+ " xm='"+detailData.getXm()+"',"
										+ " cd='"+detailData.getCd()+"',"
											+ " zaot='"+detailData.getZaot()+"',"
												+ " ssgs='"+detailData.getSsgs()+"',"
													+ " gw='"+detailData.getGw()+"',"
														+ " zt='"+detailData.getZt()+"',"
															+ " jbgs='"+detailData.getJbgs()+"',"
																+ " sj='"+detailData.getSj()+"',"
																	+ " bj='"+detailData.getBj()+"',"
																		+ " nxj='"+detailData.getNxj()+"',"
																			+ " txj='"+detailData.getTxj()+"',"
				+ " hj='"+detailData.getHj()+"',"
					+ " sangj='"+detailData.getSangj()+"',"
						+ " cj='"+detailData.getCj()+"',"
							+ " cjj='"+detailData.getCjj()+"',"
								+ " lcj='"+detailData.getLcj()+"',"
									+ " jyj='"+detailData.getJyj()+"',"
										+ " jsj='"+detailData.getJsj()+"',"
											+ " grgs='"+detailData.getGrgs()+"',"
												+ " trgs='"+detailData.getTrgs()+"',"
													+ " brj='"+detailData.getBrj()+"',"
														+ " kg='"+detailData.getKg()+"',"
				+ " formmodeid='"+KQDetailFormModeID+"',"
					+ " modedatacreater='"+detailData.getModedatacreater()+"',"
						+ " modedatacreatertype='"+detailData.getModedatacreatertype()+"',"
							+ " modedatacreatedate='"+detailData.getModedatacreatedate()+"',"
								+ " modedatacreatetime='"+detailData.getModedatacreatetime()+"',"
				+ " scdksj='"+detailData.getScdksj()+"',"
					+ " mcdksj='"+detailData.getMcdksj()+"',"
						+ " kqlb_n='"+detailData.getKqlb_n()+"',"
							+ " yjbm_n='"+detailData.getYjbm_n()+"',"
								+ " ejbm_n='"+detailData.getEjbm_n()+"',"
									+ " jbztx='"+detailData.getJbztx()+"',"
										+ " pcj='"+detailData.getPcj()+"',"
											+ " hjj='"+detailData.getHjj()+"'"
				+ " where id = " + detailData.getId();
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public KQDetailData queryById(int kqDetailId) {
		KQDetailData kqDetailData = null;
				
		String sql = "select id,"
				+ " kqrq,"
					+ " gh,"
						+ " wdk,"
							+ " xm,"
								+ " cd,"
									+ " zaot,"
										+ " ssgs,"
											+ " gw,"
												+ " zt,"
													+ " jbgs,"
														+ " sj,"
															+ " bj,"
																+ " nxj,"
																	+ " txj,"
																		+ " hj,"
																			+ " sangj,"
				+ " cj,"
					+ " cjj,"
						+ " lcj,"
							+ " jyj,"
								+ " jsj,"
									+ " grgs,"
										+ " trgs,"
											+ " brj,"
												+ " kg,"
				+ " formmodeid,"
					+ " modedatacreater,"
						+ " modedatacreatertype,"
							+ " modedatacreatedate,"
								+ " modedatacreatetime,"
				+ " scdksj,"
					+ " mcdksj,"
						+ " kqlb_n,"
							+ " yjbm_n,"
								+ " ejbm_n,"
									+ " jbztx,"
										+ " pcj,"
											+ " hjj"
				+ " from " + KQDetailFormName + " where id = " + kqDetailId;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			kqDetailData = new KQDetailData();
			kqDetailData.setId(rs.getInt("id"));
			kqDetailData.setKqrq(rs.getString("kqrq"));
			kqDetailData.setGh(rs.getString("gh"));
			kqDetailData.setWdk(rs.getDouble("wdk"));
			kqDetailData.setXm(rs.getInt("xm"));
			kqDetailData.setCd(rs.getDouble("cd"));
			kqDetailData.setZaot(rs.getDouble("zaot"));
			kqDetailData.setSsgs(rs.getInt("ssgs"));
			kqDetailData.setGw(rs.getInt("gw"));
			kqDetailData.setZt(rs.getString("zt"));
			kqDetailData.setJbgs(rs.getDouble("jbgs"));
			kqDetailData.setSj(rs.getDouble("sj"));
			kqDetailData.setBj(rs.getDouble("bj"));
			kqDetailData.setNxj(rs.getDouble("nxj"));
			kqDetailData.setTxj(rs.getDouble("txj"));
			kqDetailData.setHj(rs.getDouble("hj"));
			kqDetailData.setSangj(rs.getDouble("sangj"));
			kqDetailData.setCj(rs.getDouble("cj"));
			kqDetailData.setCjj(rs.getDouble("cjj"));
			kqDetailData.setLcj(rs.getDouble("lcj"));
			kqDetailData.setJyj(rs.getDouble("jyj"));
			kqDetailData.setJsj(rs.getDouble("jsj"));
			kqDetailData.setGrgs(rs.getDouble("grgs"));
			kqDetailData.setTrgs(rs.getDouble("trgs"));
			kqDetailData.setBrj(rs.getDouble("brj"));
			kqDetailData.setKg(rs.getDouble("kg"));
			kqDetailData.setFormmodeid(rs.getInt("formmodeid"));
			kqDetailData.setModedatacreater(rs.getInt("modedatacreater"));
			kqDetailData.setModedatacreatertype(rs.getInt("modedatacreatertype"));
			kqDetailData.setModedatacreatedate(rs.getString("modedatacreatedate"));
			kqDetailData.setModedatacreatetime(rs.getString("modedatacreatetime"));
			kqDetailData.setScdksj(rs.getString("scdksj"));
			kqDetailData.setMcdksj(rs.getString("mcdksj"));
			kqDetailData.setKqlb_n(rs.getInt("kqlb_n"));
			kqDetailData.setYjbm_n(rs.getString("yjbm_n"));
			kqDetailData.setEjbm_n(rs.getString("ejbm_n"));
			kqDetailData.setJbztx(rs.getDouble("jbztx"));
			kqDetailData.setPcj(rs.getDouble("pcj"));
			kqDetailData.setHjj(rs.getDouble("hjj"));
		}
		
		return kqDetailData;
	}

	@Override
	public KQDetailData queryByUserAndDate(int userID, String kqDate) {
		KQDetailData kqDetailData = null;
		String sql = "select id,kqrq,xm from " + KQDetailFormName + " where xm = " + userID + " and kqrq = '"+kqDate+"'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			kqDetailData = this.queryById(rs.getInt("id"));
		}
		return kqDetailData;
	}

	@Override
	public List<KQDetailData> queryListByUserAndDate(int userID, String startDate, String endDate) {
		List<KQDetailData> kqDetailDataList = null;
		String sql = "select id,kqrq,xm from " + KQDetailFormName + " where xm = " + userID + " and kqrq >= '"+startDate+"' and kqrq <= '"+endDate+"'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		if(rs.getCounts() > 0){
			kqDetailDataList = new ArrayList<KQDetailData>();
			while(rs.next()){
				kqDetailDataList.add(this.queryById(rs.getInt("id")));
			}
		}
		return kqDetailDataList;
	}

	@Override
	public boolean deleteById(int id) {
		String sql = "delete from " + KQDetailFormName + " where id = " + id;
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

}
