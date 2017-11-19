package dinghan.workflow.kq.userinfo;

import dinghan.workflow.kq.userinfo.entity.UserInfo;
import weaver.conn.RecordSet;
import weaver.general.Util;
/**
 * 人员信息DAO实现类
 * @author zhangxiaoyu / 10593 - 20171023
 * 
 */
public class UserInfoDaoImpl implements UserInfoDao {

	@Override
	public UserInfo queryByCode(String code) {
		
		String _code = code;
		
		UserInfo userInfo = null;
		
		String sql = "select top 1 * from " +UserInfoFormName + " where Code = '" + _code +"'";
		
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			userInfo = new UserInfo();
			userInfo.setCode(_code);
			userInfo.setName(rs.getInt("Name"));
			userInfo.setNameCN(rs.getString("NameCN"));
			userInfo.setStartWorkTime(rs.getString("StartWorkTime"));
			userInfo.setEndWorkTime(rs.getString("EndWorkTime"));
			userInfo.setJoinDate(rs.getString("JoinDate"));
			userInfo.setLeaveDate("LeaveDate");
			userInfo.setMail(rs.getString("Mail"));
			userInfo.setMobileAtten(rs.getInt("MobileAtten"));	//是否允许移动打卡
			userInfo.setInCompany(rs.getInt("InCompany"));	//在职状态
			userInfo.setAmStartWorkTime(rs.getString("AmStartWorkTime"));
			userInfo.setPmEndWorkTime(rs.getString("PmEndWorkTime"));
			userInfo.setKaoQinType(rs.getInt("KaoQinType"));
			userInfo.setCompany(rs.getInt("Company"));
			userInfo.setSYNianXiuJia(rs.getDouble("SYNianXiuJia"));
			userInfo.setSYTiaoXiuJia(rs.getDouble("SYTiaoXiuJia"));
			userInfo.setRest(rs.getDouble("rest"));
			userInfo.setAllowovertime(rs.getInt("allowovertime"));	//是否允许加班
			userInfo.setDeptOneNameText( Util.null2String(rs.getString("DeptOneNameText")) );
			userInfo.setDeptTwoNameText( Util.null2String(rs.getString("DeptTwoNameText")) );
			userInfo.setDeptThreeNameText( Util.null2String(rs.getString("DeptThreeNameText")) );
		}
		
		return userInfo;
	}

}
