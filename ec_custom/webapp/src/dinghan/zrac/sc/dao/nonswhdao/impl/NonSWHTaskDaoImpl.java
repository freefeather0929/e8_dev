package dinghan.zrac.sc.dao.nonswhdao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.zrac.sc.dao.nonswhdao.NonSWHTaskDao;
import dinghan.zrac.sc.entity.nonswhentity.NonSWHTaskAppData;
import weaver.conn.RecordSet;
import weaver.general.Util;

/**
 * 非标公式任务执行流程数据DAO实现类
 * @author zhangxiaoyu / 10593 - 2017-11-12
 * 
 */
public class NonSWHTaskDaoImpl implements NonSWHTaskDao {
	private Log log = LogFactory.getLog(NonSWHTaskDaoImpl.class.getName());
	@Override
	public NonSWHTaskAppData queryById(int id) {
		NonSWHTaskAppData data = null;
		String sql = "select "
				+ "id, "
					+ "requestId, "
						+ "appno, "
							+ "appcreator, "
								+ "appcreatedate, "
									+ "taskdesc, "
										+ "belongto, "
											+ "planworkhour, "
												+ "startdate, "
													+ "enddate, "
														+ "appworkhour, "
															+ "realworkhour, "
																+ "reviewdate, "
																	+ "taskno, "
																		+ "mainwflink, "
																			+ "projectname, "
																				+ "producttype, "
																					+ "featurenumber, "
																						+ "rectification, "
																							+ "metabill, "
																								+ "taskexecuter, "
																									+ "exsection, "
																										+ "mainwfno, "
																											+ "mainwfid "
				+ " from " + NonSWHTaskFromName + " where id = " + id;
		log.error("NonSWHTaskAppData sql :: " + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			data = new NonSWHTaskAppData();
			data.setId(rs.getInt("id"));
			data.setRequestId(rs.getInt("requestId"));
			data.setAppno(rs.getString("appno"));
			data.setAppcreator(rs.getInt("appcreator"));
			data.setAppcreatedate(Util.null2String(rs.getString("appcreatedate")));
			data.setTaskdesc(rs.getString("taskdesc"));
			data.setBelongto(rs.getInt("belongto"));
			data.setStartdate(rs.getString("startdate"));
			data.setEnddate(rs.getString("enddate"));
			data.setAppworkhour(rs.getDouble("appworkhour"));
			data.setPlanworkhour(rs.getDouble("planworkhour"));
			data.setRealworkhour(rs.getDouble("realworkhour"));
			data.setReviewdate(Util.null2String(rs.getString("reviewdate")));
			data.setTaskno(rs.getInt("taskno"));
			data.setMainwflink(rs.getInt("mainwflink"));
			data.setProjectname(rs.getString("projectname"));
			data.setProducttype(rs.getString("producttype"));
			data.setFeaturenumber(rs.getString("featurenumber"));
			data.setRectification(rs.getString("rectification"));
			data.setMetabill(rs.getString("metabill"));
			data.setTaskexecuter(rs.getInt("taskexecuter"));
			data.setExsection(rs.getInt("exsection"));
			data.setMainwfno(rs.getString("mainwfno"));
			data.setMainwfid(rs.getInt("mainwfid"));
		}
		//log.error("id :: "+ data.getId());
		//log.error("taskno :: "+ data.getTaskno());
		return data;
	}

	@Override
	public List<NonSWHTaskAppData> queryAllNonSWHTaskByMainWFReqID(int mainWFReqID) {
		
		List<NonSWHTaskAppData> dataList = null;
		NonSWHTaskAppData nonSWHTaskAppData = null;
		String sql = "select id from " + NonSWHTaskFromName + " where mainwfid = " + mainWFReqID;
		
		//log.error("List<NonSWHTaskAppData>  sql ：： "  + sql);
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			dataList = new ArrayList<NonSWHTaskAppData>();
		}
		
		while(rs.next()){
			nonSWHTaskAppData = this.queryById(rs.getInt("id"));
			dataList.add(nonSWHTaskAppData);
		}
		return dataList;
	}
	
}
