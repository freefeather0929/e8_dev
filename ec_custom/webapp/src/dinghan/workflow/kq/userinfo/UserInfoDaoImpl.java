package dinghan.workflow.kq.userinfo;

import java.util.ArrayList;
import java.util.List;

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
		UserInfo userInfo = null;
		String _code = code;
		if(!"".equals(_code)){
			String sql = "select top 1 * from " +UserInfoFormName + " where Code = '" + _code +"'";
			
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			
			while(rs.next()){
				userInfo = new UserInfo();
				userInfo.setCode(_code);
				userInfo.setName(rs.getInt("Name"));
				userInfo.setNameCN(rs.getString("NameCN"));
				userInfo.setStartWorkTime(Util.null2String(rs.getString("StartWorkTime")));
				userInfo.setEndWorkTime(Util.null2String(rs.getString("EndWorkTime")));
				userInfo.setJoinDate(Util.null2String(rs.getString("JoinDate")));
				userInfo.setLeaveDate(Util.null2String(rs.getString("LeaveDate")));
				userInfo.setMail(rs.getString("Mail"));
				userInfo.setMobileAtten(rs.getInt("MobileAtten"));	//是否允许移动打卡
				userInfo.setInCompany(rs.getInt("InCompany"));	//在职状态
				userInfo.setAmStartWorkTime(Util.null2String(rs.getString("AmStartWorkTime")));
				userInfo.setPmEndWorkTime(Util.null2String(rs.getString("PmEndWorkTime")));
				userInfo.setKaoQinType(rs.getInt("KaoQinType"));
				userInfo.setCompany(rs.getInt("Company"));
				userInfo.setSYNianXiuJia(rs.getDouble("SYNianXiuJia"));
				userInfo.setSYTiaoXiuJia(rs.getDouble("SYTiaoXiuJia"));
				userInfo.setRest(rs.getDouble("rest"));
				userInfo.setAllowovertime(rs.getInt("allowovertime"));	//是否允许加班
				userInfo.setOtinweekday(rs.getInt("otinweekday"));	//允许在工作日加班
				userInfo.setDeptOneNameText( Util.null2String(rs.getString("DeptOneNameText")) );
				userInfo.setDeptTwoNameText( Util.null2String(rs.getString("DeptTwoNameText")) );
				userInfo.setDeptThreeNameText( Util.null2String(rs.getString("DeptThreeNameText")) );
			}
		}
		return userInfo;
	}

	@Override
	public List<UserInfo> queryUserListByAccount(int accountID) {
		List<UserInfo> userInfoList = null;
		UserInfo userInfo = null;
		String sql = "select Code from " +UserInfoFormName + " where Company = '" + accountID +"'";
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			userInfoList = new ArrayList<UserInfo>();
		}
		
		while(rs.next()){
			userInfo = this.queryByCode(Util.null2String(rs.getString("Code")));
			userInfoList.add(userInfo);
		}
		return userInfoList;
	}

}
