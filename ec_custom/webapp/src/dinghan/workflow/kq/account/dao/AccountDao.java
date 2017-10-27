package dinghan.workflow.kq.account.dao;

import dinghan.workflow.kq.account.entity.Account;

/**
 * 考勤账套DAO
 * 
 * 
 * 
 * @author zhangxiaoyu / 10593 2017-08-22
 *	
 */
public interface AccountDao {
	
	/**
	 * 查询考勤账套信息
	 * @param id
	 * @return
	 */
	public Account queryAccount(int id);
	
}
