package dinghan.workflow.service;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.beans.UserInfo;
import weaver.conn.RecordSet;

public class KQUserInfoService {
	
	/**
	 * 获取所有考勤人员信息
	 * @return
	 */
	public List<UserInfo> getAllUserInfo(){
		
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		
		String sql = "select Name from uf_hr_userinfo1";
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			UserInfo userInfo = new UserInfo(rs.getInt("Name"),rs.getString("Code"));
			userInfoList.add(userInfo);		
		}
		
		return userInfoList;
	}
	
}
