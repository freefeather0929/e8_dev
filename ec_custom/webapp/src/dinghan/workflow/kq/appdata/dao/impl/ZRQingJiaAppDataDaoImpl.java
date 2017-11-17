package dinghan.workflow.kq.appdata.dao.impl;

import dinghan.workflow.kq.appdata.dao.ZRQingJiaAppDataDao;
import dinghan.workflow.kq.appdata.entity.ZRQingJiaAppData;
import weaver.conn.RecordSet;
/**
 * 中车请假申请数据DAO实现类
 * @author zhangxiaoyu / 10593 - 2017-11-15
 * 
 */
public class ZRQingJiaAppDataDaoImpl implements ZRQingJiaAppDataDao {

	@Override
	public ZRQingJiaAppData queryByID(int id) {
		ZRQingJiaAppData zrQingJiaAppData = null;
		String sql = "select "
			+ "id, "
				+ "requestId, "
					+ "oddnum, "
						+ "applicant, "
							+ "applicantdate, "
								+ "firstdept, "
									+ "seconddept, "
										+ "post, "
											+ "leavedays, "
												+ "directsupervisor, "
													+ "supervisor, "
														+ "approvedopinions, "
															+ "preplace, "
																+ "attendancepsn, "
																	+ "ststartendtime "
				+ " from " + ZRQingJiaAppDataFormName
					+ " where id = " + id;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			zrQingJiaAppData = new ZRQingJiaAppData();
			zrQingJiaAppData.setId(rs.getInt("id"));
			zrQingJiaAppData.setRequestId(rs.getInt("requestId"));
			zrQingJiaAppData.setOddnum(rs.getString("oddnum"));
			zrQingJiaAppData.setApplicant(rs.getInt("applicant"));
			zrQingJiaAppData.setApplicantdate(rs.getString("applicantdate"));
			zrQingJiaAppData.setFirstdept(rs.getInt("firstdept"));
			zrQingJiaAppData.setSeconddept(rs.getInt("seconddept"));
			zrQingJiaAppData.setPost(rs.getInt("post"));
			zrQingJiaAppData.setLeavedays(rs.getDouble("leavedays"));
			zrQingJiaAppData.setDirectsupervisor(rs.getInt("directsupervisor"));
			zrQingJiaAppData.setSupervisor(rs.getInt("supervisor"));
			zrQingJiaAppData.setApprovedopinions(rs.getInt("approvedopinions"));
			zrQingJiaAppData.setPreplace(rs.getInt("preplace"));
			zrQingJiaAppData.setAttendancepsn(rs.getInt("attendancepsn"));
			zrQingJiaAppData.setStstartendtime(rs.getInt("ststartendtime"));
		}
		
		return zrQingJiaAppData;
	}

}
