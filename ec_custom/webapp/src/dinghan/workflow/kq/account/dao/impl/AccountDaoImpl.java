package dinghan.workflow.kq.account.dao.impl;

import java.util.HashSet;
import java.util.Set;

import dinghan.workflow.kq.account.dao.AccountDao;
import dinghan.workflow.kq.account.entity.Account;
import weaver.conn.RecordSet;

public class AccountDaoImpl implements AccountDao{
	
	/**
	 * 考核账套表名
	 */
	public static final String AccountFormName = "uf_hr_company";
	
	/**
	 * 查询考勤账套
	 * @param id - 考勤账套表对应的ID
	 * @return - Account 考勤账套实例，null - 如果没有对应ID的考勤账套
	 */
	@Override
	public Account queryAccount(int id) {
		String sql = "select id,company,username from " + AccountFormName + " where id = " + id;
		
		Account account = null;
		RecordSet rs = new RecordSet();
		
		Set<Integer> userNameSet = new HashSet<Integer>();
		
		String[] userNameArray = {};
		
		rs.executeSql(sql);
		while(rs.next()){
			account = new Account();
			account.setId(rs.getInt("id"));
			account.setAccountName(rs.getString("company"));
			userNameArray = rs.getString("username").split(",");
			for(int i=0;i<userNameArray.length;i++){
				userNameSet.add(Integer.valueOf(userNameArray[i]));
			}
			account.setUserIDsSet(userNameSet);
		}
		return account;
	}
}
