package dinghan.zrac.sc.dao.nonswhdao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.zrac.sc.dao.nonswhdao.NonSWHNoticeTaskDataDao;
import dinghan.zrac.sc.entity.nonswhentity.NonSWHNoticTaskData;
import weaver.conn.RecordSet;
/**
 * 非标工时任务Dao实现类
 * @author zhangxiaoyu / 10593 - 2017-11-12
 * 
 */
public class NonSWHNoticeTaskDataDaoImpl implements NonSWHNoticeTaskDataDao {
	private Log log = LogFactory.getLog(NonSWHNoticeTaskDataDaoImpl.class.getName());
	@Override
	public NonSWHNoticTaskData queryById(int id) {
		NonSWHNoticTaskData nonSWHNoticTaskData = null;
		String sql = "select "
				+ "id, "
					+ "mainid, "
						+ "exsection, "
							+ "sectionmgr, "
								+ "startdate, "
									+ "finishdate, "
										+ "appworkhour, "
											+ "realworkhour, "
												+ "planworkhour, "
													+ "belongto, "
														+ "taskdesc, "
															+ "sended, "
																+ "tasksended "
			+ "from " + NonSWHNoticeTaskDataFromName
				+ " where id = " + id;
		
		log.error("NonSWHNoticTaskData sql :: " + sql);
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		while(rs.next()){
			nonSWHNoticTaskData = new NonSWHNoticTaskData();
			nonSWHNoticTaskData.setId(rs.getInt("id"));
			nonSWHNoticTaskData.setMainid(rs.getInt("mainid"));
			nonSWHNoticTaskData.setExsection(rs.getInt("exsection"));
			nonSWHNoticTaskData.setSectionmgr(rs.getInt("sectionmgr"));
			nonSWHNoticTaskData.setStartdate(rs.getString("startdate"));
			nonSWHNoticTaskData.setFinishdate(rs.getString("finishdate"));
			nonSWHNoticTaskData.setAppworkhour(rs.getDouble("appworkhour"));
			nonSWHNoticTaskData.setRealworkhour(rs.getDouble("realworkhour"));
			nonSWHNoticTaskData.setPlanworkhour(rs.getDouble("planworkhour"));
			nonSWHNoticTaskData.setBelongto(rs.getInt("belongto"));
			nonSWHNoticTaskData.setTaskdesc(rs.getString("taskdesc"));
			nonSWHNoticTaskData.setSended(rs.getInt("sended"));
			nonSWHNoticTaskData.setTasksended(rs.getInt("tasksended"));
		}
		log.error("id :: " + nonSWHNoticTaskData.getId());
		//nonSWHNoticTaskData.getRealworkhour();
		
		return nonSWHNoticTaskData;
	}

	@Override
	public boolean update(NonSWHNoticTaskData nonSWHNoticTaskData) {
		String sql = "update " + NonSWHNoticeTaskDataFromName +" set "
				+ "startdate = '"+nonSWHNoticTaskData.getStartdate()+"',"
					+"finishdate = '"+nonSWHNoticTaskData.getFinishdate()+"',"
						+"appworkhour = '"+nonSWHNoticTaskData.getAppworkhour()+"',"
							+ "realworkhour = '"+nonSWHNoticTaskData.getRealworkhour()+"',"
								+ "planworkhour = '"+nonSWHNoticTaskData.getPlanworkhour()+"'"
				+ " where id = " + nonSWHNoticTaskData.getId();

		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

}
