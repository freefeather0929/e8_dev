package dinghan.workflow.kq.account.entity;

import java.util.Set;

/**
 * 公司账套
 * <p>
 * 企业的工作地点分布在全国各地，各地的考勤在上下班时间上的存在差异，并且由不同的人员进行管理，
 * 所以分别设置不同的账套，以便于在核定不同账套的数据时能够使用不同的标准，以及操作权限的划分。
 * @author zhangxiaoyu / 10593 - 2017-08-03
 * 
 */
public class Account {
	
	/**
	 * ID
	 */
	private int id;
	
	/**
	 * 账套名称
	 */
	private String accountName;
	
	/**
	 * 账套管理员人员 ID 集合
	 */
	private Set<Integer> userIDsSet;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Set<Integer> getUserIDsSet() {
		return userIDsSet;
	}

	public void setUserIDsSet(Set<Integer> userIDsSet) {
		this.userIDsSet = userIDsSet;
	}

}
