package dinghan.workflow.kq.appdata.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.appdata.dao.ZRJiaBanAppDataDao;
import dinghan.workflow.kq.appdata.entity.ZRJiaBanAppData;
import weaver.conn.RecordSet;

public class ZRJiaBanAppDataDaoImpl implements ZRJiaBanAppDataDao {
	private Log log = LogFactory.getLog(ZRJiaBanAppDataDaoImpl.class.getName());
	
	@Override
	public ZRJiaBanAppData queryByID(int id) {
		ZRJiaBanAppData zrJiaBanAppData = null;
		String sql = "select id,"
				+ "requestID,"
					+ "oddnum,"
						+ "applicant,"
							+ "appworkcode,"
								+ "applicationdate,"
									+ "firstdept,"
										+ "seconddept,"
											+ "post,"
												+ "preplace,"
													+ "deptmanager,"
														+ "firstdeptManager,"
															+ "attendancepsn,"
																+ "attendanceclerkopinion,"
																	+ "ststartendtime "
				+ "from " + ZRJiaBanAppDataDao.ZRJiaBanAppDataFormName + " where id =" + id;
		RecordSet rs = new RecordSet();
		log.error("查询加班申请单数据 :: " + sql);
		rs.executeSql(sql);
		
		while(rs.next()){
			zrJiaBanAppData = new ZRJiaBanAppData();
			zrJiaBanAppData.setId(rs.getInt("id"));
			zrJiaBanAppData.setRequestID(rs.getInt("requestID"));
			zrJiaBanAppData.setOddNum(rs.getString("oddnum"));
			zrJiaBanAppData.setApplicant(rs.getInt("applicant"));
			zrJiaBanAppData.setAppWorkCode(rs.getString("appWorkCode"));
			zrJiaBanAppData.setApplicationDate(rs.getString("applicationDate"));
			zrJiaBanAppData.setFirstDept(rs.getInt("firstDept"));
			zrJiaBanAppData.setSecondDept(rs.getInt("zrJiaBanAppData"));
			zrJiaBanAppData.setPost(rs.getInt("post"));
			zrJiaBanAppData.setPrePlace(rs.getInt("prePlace"));
			zrJiaBanAppData.setDeptManager(rs.getInt("deptManager"));
			zrJiaBanAppData.setFirstDeptManager(rs.getInt("firstDeptManager"));
			zrJiaBanAppData.setAttendancePsn(rs.getInt("attendancePsn"));
			zrJiaBanAppData.setAttendanceClerkOpinion(rs.getInt("attendanceclerkopinion"));
			zrJiaBanAppData.setStStartEndTime(rs.getInt("ststartendtime"));
		}
		
		return zrJiaBanAppData;
	}
	
	

	
}
