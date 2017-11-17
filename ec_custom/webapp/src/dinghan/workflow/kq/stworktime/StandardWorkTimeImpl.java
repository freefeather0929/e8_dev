package dinghan.workflow.kq.stworktime;

import weaver.conn.RecordSet;

public class StandardWorkTimeImpl implements StandardWorkTime{

	@Override
	public StandardWorkTimeInfo queryByID(int id) {
		StandardWorkTimeInfo stWorkTimeInfo = null;
		
		String sql = "select top 1 "
				+ "id, "
					+ "stworkstarttime, "
						+ "stworkendtime, "
							+ "streststarttime, "
								+ "strestendtime, "
									+ "isvalid, "
										+ "noonresthour, "
											+ "ststartendtime"
				+ " from " + StandardWorkTimeForm 
				+ " where id = " + id;
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			stWorkTimeInfo = new StandardWorkTimeInfo();
			stWorkTimeInfo.setId(rs.getInt("id"));
			stWorkTimeInfo.setStworkstarttime(rs.getString("stworkstarttime"));
			stWorkTimeInfo.setStworkendtime(rs.getString("stworkendtime"));
			stWorkTimeInfo.setStreststarttime(rs.getString("streststarttime"));
			stWorkTimeInfo.setStrestendtime(rs.getString("strestendtime"));
			stWorkTimeInfo.setIsvalid(rs.getInt("isvalid"));
			stWorkTimeInfo.setNoonresthour(rs.getDouble("noonresthour"));
			stWorkTimeInfo.setStstartendtime(rs.getString("ststartendtime"));
		}
		
		return stWorkTimeInfo;
	}

}
