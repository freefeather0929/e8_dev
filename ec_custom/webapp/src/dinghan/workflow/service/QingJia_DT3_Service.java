package dinghan.workflow.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.beans.QingJia;
import weaver.conn.RecordSet;

/**
 * 请假明细表3
 * @author zhangxiaoyu / 10593 2017-07-07
 * 
 */
public class QingJia_DT3_Service {
	private static Log log = LogFactory.getLog(QingJia_DT3_Service.class.getName());
	/**
	 * 创建请假申请明细3 数据 
	 * @param qingjia_dt3
	 */
	public void addQingJiaDT3(QingJia qingjia_dt3){
		RecordSet rs = new RecordSet();
		String sql = "intert into formtable_main_89_dt3 ("
				+ "userid,"
					+ "mainid,"
						+ "rq,"
							+ "qjlb,"
								+ "kssj,"
									+ "jssj,"
										+ "hdzt,"
											+ "xq,"
												+ "hdkssj,"
													+ "hdjssj,"
														+ "dkjl,"
															+ "appnom,"
																+ "gh,"
																	+ "hdgs,"
																		+ "jdid,"
																			+ "bzgzsj,"
																				+ "kqhdyf,"
																					+ "lcspjsrq,"
																						+ "kqy,"
																							+ "row_id)"
		+ " values("
			+ "'" + qingjia_dt3.getUserid() + "',"
				+ "'" + qingjia_dt3.getMainid() + "',"
					+ "'" + qingjia_dt3.getRq() + "',"
						+ "'" + qingjia_dt3.getQjlb() + "',"
							+ "'" + qingjia_dt3.getKssj() + "',"
								+ "'" + qingjia_dt3.getJssj() + "',"
									+ "'" + qingjia_dt3.getHdzt() + "',"
										+ "'" + qingjia_dt3.getXq() + "',"
											+ "'" + qingjia_dt3.getHdkssj() + "',"
												+ "'" + qingjia_dt3.getHdjssj() + "',"
													+ "'" + qingjia_dt3.getDkjl() + "',"
														+ "'" + qingjia_dt3.getAppnom() + "',"
															+ "'" + qingjia_dt3.getGh() + "',"
																+ "'" + qingjia_dt3.getHdgs() + "',"
																	+ "'" + qingjia_dt3.getJdid() + "',"
																		+ "'" + qingjia_dt3.getBzgzsj() + "',"
																			+ "'" + qingjia_dt3.getKqhdyf() + "',"
																				+ "'" + qingjia_dt3.getLcspjsrq() + "',"
																					+ "'" + qingjia_dt3.getKqy() + "',"
																						+ "'" + qingjia_dt3.getRow_id() + "'"
																							+ ")";
		rs.executeSql(sql);
	}
	
	/**
	 * 删除请假申请明细3 数据
	 * @param qingjia_dt3 - 明细3Id
	 */
	public boolean deleteQingJiaDT3(int qingjia_dt3_id){
		RecordSet rs = new RecordSet();
		String sql = "delete from formtable_main_89_dt3 where id = " + qingjia_dt3_id;
		return rs.executeSql(sql);
	}
	
	/**
	 * 删除请假申请的所有明细三数据
	 * @param mainid - 主表Id
	 * 
	 */
	public boolean deleteAllQingJiaDT3ByMainId(int mainid){
		RecordSet rs = new RecordSet();
		String sql = "delete from formtable_main_89_dt3 where mainid = " + mainid;
		return rs.executeSql(sql);
	}
	
	/**
	 * 更新请假申请明细3 数据
	 * @param qingjia_dt3
	 */
	public void updateQingJiaDT3(QingJia qingjia_dt3){
		RecordSet rs = new RecordSet();
		String sql = "update "
				+ "set "
					//+ "mainid = "+qingjia_dt3.getId()+","
						+ "rq = '"+qingjia_dt3.getRq()+"',"
							+ "kssj = '"+qingjia_dt3.getKssj()+"',"
								+ "jssj = '"+qingjia_dt3.getJssj()+"',"
									+ "qjlb = '"+qingjia_dt3.getQjlb()+"',"
										+ "hdkssj = '"+qingjia_dt3.getHdkssj()+"',"
											+ "hdjssj = '"+qingjia_dt3.getHdjssj()+"',"
												+ "hdgs = '"+qingjia_dt3.getHdgs()+"',"
													+ "dkjl = '"+qingjia_dt3.getDkjl()+"',"
														+ "userid = '"+qingjia_dt3.getUserid()+"',"
															+ "appnom = '"+qingjia_dt3.getAppnom()+"',"
																+ "gh = '"+qingjia_dt3.getGh()+"',"
																	+ "jdid = '"+qingjia_dt3.getJdid()+"',"
																		+ "bzgzsj = '"+qingjia_dt3.getBzgzsj()+"',"
																			+ "kqhdyf = '"+qingjia_dt3.getKqhdyf()+"',"
																				+ "lcspjsrq = '"+qingjia_dt3.getLcspjsrq()+"',"
																					+ "hdzt = '"+qingjia_dt3.getHdzt()+"',"
																						+ "kqy = '"+qingjia_dt3.getKqy()+"',"
																							+ "xq = '"+qingjia_dt3.getXq()+"',"
																								+ "row_id = '"+qingjia_dt3.getRow_id()+"'"
																									+ "where "
																										+ "id = " + qingjia_dt3.getId();
		rs.executeSql(sql);
		
	}
	
	
}
