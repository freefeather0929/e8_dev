package dinghan.workflow.kq.dailydetail.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.kq.dailydetail.entity.KQDetailData;
import dinghan.workflow.kq.dailydetail.gen.ZRKQDetailDataGen;
import dinghan.workflow.kq.dailydetail.service.KQDetialService;
import dinghan.workflow.kq.dailydetail.service.impl.KQDetialServiceImpl;
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
 * 中车考勤明细处理
 * @author zhangxiaoyu / 10593 - 2017-11-21
 * 
 */
public class ZRDetailDataHandler implements KQDetailDataHandle {
	private Log log = LogFactory.getLog(ZRDetailDataHandler.class.getName());
	private UserInfoVerify userInfoVerify = new UserInfoVerify();
	private UserInfoDao userInfoDao = new UserInfoDaoImpl();
	//private 
	private ZRKQDetailDataGen zrKQDetailDataGen;
	private String userVerifyMessage;
	private JSONArray uvJAarray = new JSONArray();
	private JSONObject uvJson = null;
	private KQDetialService kqDetailService = new KQDetialServiceImpl();
	
	public ZRDetailDataHandler(int dataCreatorID){
		this.zrKQDetailDataGen = new ZRKQDetailDataGen(dataCreatorID);
	}
	
	@Override
	public int updateKQDetailDataForUser(String userCode, String startDate, String endDate) {
		int i = 0;
		this.uvJson = new JSONObject();
		String tmpDate = startDate;
		uvJson.put("usercode", userCode);
		UserInfo userInfo = this.verifyUserInfo(userCode);
		List<KQDetailData> kqDetailList_updated = new ArrayList<KQDetailData>();
		//if("".equals(this.userVerifyMessage))
		
		if(userInfo!=null){
			String joinDate = userInfo.getJoinDate();
			String leaveDate = userInfo.getLeaveDate();
			while(tmpDate.compareTo(endDate)<=0){
				//log.error("生成明细 - 日期 :: " + tmpDate);
				//对入离职日期做判断
				if(tmpDate.compareTo(joinDate)<0){
					continue;
				}
				if(!"".equals(leaveDate) && tmpDate.compareTo(joinDate)>0){
					continue;
				}
				
				KQDetailData origindetailData = kqDetailService.queryByUserAndDate(userInfo.getName(), tmpDate);
				KQDetailData detailData_neo = this.zrKQDetailDataGen.createDetailData(userInfo, tmpDate);
				if(origindetailData == null){
					kqDetailService.add(detailData_neo);
				}else{
					detailData_neo.setId(origindetailData.getId());
					kqDetailService.update(detailData_neo);
				}
				detailData_neo = kqDetailService.queryByUserAndDate(userInfo.getName(), tmpDate);
				kqDetailList_updated.add(detailData_neo);
				i++;
				tmpDate = CalendarUtil.moveDate(tmpDate, 0, 0, 1);
				//log.error("下一天的日期 ::" + tmpDate);
			}
			uvJson.put("updated", i);
		}else{
			uvJson.put("message",this.userVerifyMessage);
		}
		
		for(KQDetailData data : kqDetailList_updated){
			//重建权限
			ModeRightInfo modeRightInfo = new ModeRightInfo();
			modeRightInfo.rebuildModeDataShareByEdit(data.getModedatacreater(), data.getFormmodeid(), data.getId());
		}
		
		uvJAarray.add(uvJson);
		return i;
	}

	@Override
	public int updateKQDetailDataForAccount(int accountId, String startDate, String endDate) {
		List<UserInfo> userInfoList = userInfoDao.queryUserListByAccount(accountId);
		int i = 0;
		if(userInfoList != null){
			for(UserInfo userInfo : userInfoList){
				i += this.updateKQDetailDataForUser(userInfo.getCode(), startDate, endDate);
			}
		}
		return i;
	}

	@Override
	public int updateKQDetailDataForUserArray(Integer[] userIDs, String startDate, String endDate) {
		int i = 0;
		String workcode = "";
		for(int userId : userIDs){
			workcode = this.getUserWorkCode(userId);
			i += this.updateKQDetailDataForUser(workcode, startDate, endDate);
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

	public JSONArray getUvJAarray() {
		return uvJAarray;
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
}
