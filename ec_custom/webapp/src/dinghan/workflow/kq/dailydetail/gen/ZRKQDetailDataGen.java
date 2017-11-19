package dinghan.workflow.kq.dailydetail.gen;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.kq.checkout.bean.CheckOutRecord;
import dinghan.workflow.kq.checkout.util.CheckOutUtil;
import dinghan.workflow.kq.dailydetail.entity.KQDetailData;
import dinghan.workflow.kq.kqdt.service.ZRChuChaiCheckDTService;
import dinghan.workflow.kq.kqdt.service.ZRJiaBanCheckDTService;
import dinghan.workflow.kq.kqdt.service.ZRQingJiaCheckDTService;
import dinghan.workflow.kq.kqdt.service.ZRWaiChuCheckDTService;
import dinghan.workflow.kq.kqdt.service.impl.ZRChuChaiCheckDTServiceImpl;
import dinghan.workflow.kq.kqdt.service.impl.ZRJiaBanCheckDTServiceImpl;
import dinghan.workflow.kq.kqdt.service.impl.ZRQingJiaCheckDTServiceImpl;
import dinghan.workflow.kq.kqdt.service.impl.ZRWaiChuCheckDTServiceImpl;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
import weaver.conn.RecordSet;
import weaver.general.Util;
import weaver.hrm.User;

public class ZRKQDetailDataGen implements KQDetailDataGen{
	private ZRQingJiaCheckDTService zrQingJiaCheckDTService = new ZRQingJiaCheckDTServiceImpl();
	private ZRJiaBanCheckDTService zrJiaBanCheckDTService = new ZRJiaBanCheckDTServiceImpl();
	private ZRChuChaiCheckDTService zrChuChaiCheckDTService = new ZRChuChaiCheckDTServiceImpl();
	private ZRWaiChuCheckDTService zrWaiChuCheckDTService = new ZRWaiChuCheckDTServiceImpl();
	//private UserInfoDao userInfoDao = new UserInfoDaoImpl();
	private CheckOutUtil checkOutUtil = new CheckOutUtil();
	
	private List<String> dakaRecordList = null;
	
	public ZRKQDetailDataGen(){
		checkOutUtil.setForwordDayCheckOutNode("02:00:00");
	}
	
	@Override
	public KQDetailData createDetailData(UserInfo userInfo, String kqDate){
		UserInfo _userInfo = userInfo;
		String _kqDate = kqDate;
		KQDetailData kqDetailData = new KQDetailData();
		dakaRecordList = this.initCheckOutRecord(_userInfo, _kqDate);
		
		
		
		return kqDetailData;
	}
	
	/**
	 * 初始化打卡信息
	 * 	-- 步骤
	 * 		1. 获取打卡信息
	 * 		2. 获取手工考勤
	 * @param workcode
	 * @param dakaDate
	 * @return
	 */
	private List<String> initCheckOutRecord(UserInfo userInfo, String dakaDate){
		List<String> _dakaRecordList = new ArrayList<String>();
		
		List<CheckOutRecord> checkOutRecordList = 
				checkOutUtil.getPersonalCheckOutSetByDay(userInfo.getCode(), dakaDate, userInfo.getMobileAtten()).getCheckOutRecordList();
		
		for(CheckOutRecord record : checkOutRecordList){
			_dakaRecordList.add(record.getTime());
		}
		
		return _dakaRecordList;
	}
	
	/**
	 * 考勤明细的用户信息赋值
	 * @param userInfo
	 * @param kqDetailData
	 * @return
	 */
	private KQDetailData updateValue_UserInfo(UserInfo userInfo,KQDetailData kqDetailData){
		KQDetailData _kqDetailData = kqDetailData;
		UserInfo _userInfo = userInfo;
		User user = new User(_userInfo.getName());
		_kqDetailData.setXm(_userInfo.getName());
		_kqDetailData.setGh(_userInfo.getCode());
		_kqDetailData.setKqlb_n(_userInfo.getKaoQinType());
		_kqDetailData.setZt(_userInfo.getInCompany()>0?"离职":"在职");
		_kqDetailData.setGw(Integer.valueOf(user.getJobtitle()));
		_kqDetailData.setYjbm_n(_userInfo.getDeptOneNameText());
		_kqDetailData.setEjbm_n(_userInfo.getDeptTwoNameText());
		_kqDetailData.setSsgs(_userInfo.getCompany());
		
		return _kqDetailData;
		
	}
	
	/**
	 * 获取用户工号
	 * @param userId
	 * @return
	 */
	private int getUserId(String workcode){
		int userId = -1;
		
		String sql = "select id from HrmResource where workcode = " + workcode;
		
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			workcode = Util.null2String(rs.getString("id"));
		}
		
		return userId;
	}
}
