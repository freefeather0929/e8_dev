package dinghan.workflow.kq.userinfo;

import java.util.List;

import dinghan.workflow.kq.userinfo.entity.UserInfo;
/**
 * 考勤系统人员信息
 * <p>
 * @author - zhangxiaoyu / 10593 - 2017-09-09
 * 
 * 处理考勤系统中人员信息的查询、更新、新增
 * 
 */
public interface UserInfoDao {
	
	String UserInfoFormName = "uf_hr_userinfo1";
	
	/**
	 * 考勤系统人员信息查询
	 * <p>
	 * @param code - 员工信息中的工号
	 * @return - UserInfo - 考勤系统人员信息，或者 null，如果人员信息不存在
	 */
	UserInfo queryByCode(String code);
	
	/**
	 * 获取考勤系统单个账套的人员信息集合
	 * @param accountID - 账套ID
	 * @return
	 */
	List<UserInfo> queryUserListByAccount(int accountID);
}
