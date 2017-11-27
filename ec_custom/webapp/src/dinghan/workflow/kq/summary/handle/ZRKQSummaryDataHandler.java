package dinghan.workflow.kq.summary.handle;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.workflow.kq.summary.entity.KQSummaryData;
import dinghan.workflow.kq.summary.gen.KQSummaryDataGen;
import dinghan.workflow.kq.summary.gen.ZRKQSummaryDataGen;
import dinghan.workflow.kq.summary.service.KQSummaryDataService;
import dinghan.workflow.kq.summary.service.impl.KQSummaryDataServiceImpl;
import dinghan.workflow.kq.userinfo.UserInfoDao;
import dinghan.workflow.kq.userinfo.UserInfoDaoImpl;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
import dinghan.workflow.kq.userinfo.verify.UserInfoVerify;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.Util;
import weaver.hrm.User;
/**
 * 中车考勤汇总操作
 * @author zhangxiaoyu / 10593 - 2017-11-22
 * 
 */
public class ZRKQSummaryDataHandler implements KQSummaryDataHandle{
	private Log log = LogFactory.getLog(ZRKQSummaryDataHandler.class.getName());
	private UserInfoVerify userInfoVerify = new UserInfoVerify();
	private UserInfoDao userInfoDao = new UserInfoDaoImpl();
	
	private String userVerifyMessage;
	private JSONArray uvJAarray = new JSONArray();
	private JSONObject uvJson = null;
	
	private KQSummaryDataService kqSummaryDataService = new KQSummaryDataServiceImpl();
	private KQSummaryDataGen kqSummaryDataDen;
	
	public ZRKQSummaryDataHandler(int dataCreatorID){
		this.kqSummaryDataDen = new ZRKQSummaryDataGen(dataCreatorID);
	}
	
	@Override
	public int updateKQSummaryDataForUser(String userCode, String year, String month) {
		int i = 0;
		this.uvJson = new JSONObject();
		uvJson.put("usercode", userCode);
		uvJson.put("lastname", getUserLastName(userCode));
		String _kqMonth = year+"-"+month;
		
		UserInfo userInfo = this.verifyUserInfo(userCode);
		
		if(userInfo!=null){
			log.error("生成 " + userInfo.getNameCN() + "，"+ _kqMonth + " 考勤汇总");
			KQSummaryData kqSummaryData = kqSummaryDataDen.createSummaryData(userInfo, _kqMonth);
			if(kqSummaryData != null){
				KQSummaryData originSummaryData = kqSummaryDataService.queryByUserIDAndMonth(userInfo.getName(), _kqMonth);
				if(originSummaryData == null){
					kqSummaryDataService.add(kqSummaryData);
				}else{
					//log.error("更新 考勤 汇总");
					kqSummaryData.setId(originSummaryData.getId());
					kqSummaryDataService.update(kqSummaryData);
				}
				i += 1;
				
				kqSummaryData = kqSummaryDataService.queryByUserIDAndMonth(userInfo.getName(), _kqMonth);
				if(kqSummaryData!=null){
					ModeRightInfo modeRightInfo = new ModeRightInfo();
					modeRightInfo.rebuildModeDataShareByEdit(kqSummaryData.getModedatacreater(), kqSummaryData.getFormmodeid(), kqSummaryData.getId());
				}
			}
			uvJson.put("updated", i);
		}else{
			uvJson.put("message",this.userVerifyMessage);
		}
		uvJAarray.add(uvJson);
		return i;
	}
	
	@Override
	public int updateKQSummaryDataForAccount(int accountId, String year, String month) {
		int i = 0;
		List<UserInfo> userInfoList = userInfoDao.queryUserListByAccount(accountId);
		
		if(userInfoList!=null){
			for(UserInfo userInfo : userInfoList){
				i += this.updateKQSummaryDataForUser(userInfo.getCode(), year, month);
			}
		}
		
		return i;
	}

	@Override
	public int updateKQSummaryDataForUserArray(Integer[] userIDs, String year, String month) {
		int i = 0;
		String workcode = "";
		for(int userId : userIDs){
			workcode = this.getUserWorkCode(userId);
			i += this.updateKQSummaryDataForUser(workcode, year, month);
		}
		return i;
	}

	@Override
	public UserInfo verifyUserInfo(String userCode) {
		//JSONObject verifyJson = new JSONObject();
		String message = "";
		UserInfo userInfo = userInfoDao.queryByCode(userCode);
		log.error("userCode :: userInfo ? null " + (userInfo == null));
		if(userInfo==null){
			message = "考勤系统中该人员信息不存在";
			this.userVerifyMessage = message;
			return null;
		}else{
			if(userInfo.getName()<0){
				User sysUser = userInfoVerify.hasSysUserInfo(userInfo);
				if(sysUser!=null){
					userInfo.setName(sysUser.getUID());
				}else{
					message = "人员信息未在OA系统中注册";
					return null;
				}
			}
			if(!userInfoVerify.hasJoinDate(userInfo)){
				message = "入职日期为空";
			}
			if(userInfo.getInCompany() == 1){
				if(!userInfoVerify.hasLeaveDate(userInfo)){
					if(!"".equals(message)){
						message += "，";
					}
					message+="离职日期为空";
				}
			}
			if("".equals(userInfo.getAmStartWorkTime())){
				if(!"".equals(message)){
					message += "，";
				}
				message+="午休开始时间为空";
			}
			if("".equals(userInfo.getPmEndWorkTime())){
				if(!"".equals(message)){
					message += "，";
				}
				message+="午休结束时间为空";
			}
			if("".equals(userInfo.getStartWorkTime())){
				if(!"".equals(message)){
					message += "，";
				}
				message+="标准上班班时间为空";
			}
			if("".equals(userInfo.getEndWorkTime())){
				if(!"".equals(message)){
					message += "，";
				}
				message+="标准下班班时间为空";
			}
			if(userInfo.getRest() < 0){
				if(!"".equals(message)){
					message += "，";
				}
				message+="标准午休小时数为空";
			}
			if(!"".equals(message)){
				this.userVerifyMessage = message;
				return null;
			}
		}
		return userInfo;
	}

	
	/**
	 * 获取用户工号
	 * @param userId
	 * @return
	 */
	private String getUserWorkCode(int userID){
		String workcode = "";
		
		String sql = "select workcode from HrmResource where id = " + userID;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			workcode = Util.null2String(rs.getString("workcode"));
		}
		
		return workcode;
	} 

	/**
	 * 获取用户姓名
	 * @param userId
	 * @return
	 */
	private String getUserLastName(String workcode){
		String lastname = "";
		String sql = "select lastname from HrmResource where workcode = '" + workcode + "'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			lastname = Util.null2String(rs.getString("lastname"));
		}
		return lastname;
	}
	
	public JSONArray getUvJAarray() {
		return uvJAarray;
	}

}
