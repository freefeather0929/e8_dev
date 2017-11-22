package dinghan.workflow.kq.userinfo.verify;

import dinghan.workflow.kq.userinfo.UserInfoDao;
import dinghan.workflow.kq.userinfo.UserInfoDaoImpl;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
import weaver.conn.RecordSet;
import weaver.hrm.User;

/**
 * 考勤人员信息校验类
 * @author zhangxiaoyu / 10593 - 2017-11-20
 * 
 * <p>校验内容
 * 	<br>1. 工号所对应的人员信息是否存在；
 * 	<br>2. 工号对应的OA系统人员信息是否存在；
 * 	<br>3. 人员信息是否完整：
 * 		<br> (1) 人员信息中的人员ID是否为空
 * 		<br> (2) 工号对应的系统人员信息是否存在
 * 		<br> (3) 人员的入职日期是否存在
 * 		<br> (4) 离职人员的离职日期是否存在
 * 		<br> (3) 人员的标准上下班、午休起止、午休小时数是否存在
 */
public class UserInfoVerify {
	
	private UserInfoDao userInfoDao = new UserInfoDaoImpl();
	private UserInfo userInfo;
	
	/**
	 * 校验工号对应的人员信息是否存在
	 * @param code - 人员工号
	 * @return true - 如果人员信息存在  或者  false - 如果未查到对应工号的人员信息
	 */
	public boolean hasUserInfo(String code){
		this.userInfo =  userInfoDao.queryByCode(code);
		if(userInfo == null){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断人员信息中的系统人员信息ID是否存在
	 * @param userInfo
	 * @return {int} 人员的ID 或者 -1 如果人员ID为空
	 */
	public int hasUserID(UserInfo userInfo){
		int userID = userInfo.getName();
		if(userID > 0){
			return userID;
		}
		return -1;
	}
	
	/**
	 * 判定考勤人员信息对应系统人员信息是否存在
	 * @param userInfo
	 * @return {User} - 如果对应系统人员信息存在 或者 null - 如果人员信息不存在
	 */
	public User hasSysUserInfo(UserInfo userInfo){
		int sysUserId = -1;
		User user = null;
		String sql = "select id from HrmResource where code = " + userInfo.getCode();
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			sysUserId = rs.getInt("id");
			user = new User(sysUserId);
		}
		return user;
	}
	
	/**
	 * 检测是否填写了入职日期
	 * @param userInfo
	 * @return true - 如果人员入职日期已填写 或者 false - 如果人员入职日期为空
	 */
	public boolean hasJoinDate(UserInfo userInfo){
		if("".equals(userInfo.getJoinDate())){
			return false;
		}
		return true;
	}
	
	/**
	 * 检测是否填写了离职日期
	 * @param userInfo
	 * @return true - 如果人员离职日期已填写 或者 false - 如果人员离职日期为空
	 */
	public boolean hasLeaveDate(UserInfo userInfo){
		if("".equals(userInfo.getLeaveDate())){
			return false;
		}
		return true;
	}
}
