package dinghan.workflow.kq.summary.gen;

import java.util.List;

import dinghan.workflow.kq.dailydetail.entity.KQDetailData;
import dinghan.workflow.kq.dailydetail.service.KQDetialService;
import dinghan.workflow.kq.dailydetail.service.impl.KQDetialServiceImpl;
import dinghan.workflow.kq.holiday.dao.HolidayConfigDao;
import dinghan.workflow.kq.holiday.dao.impl.HolidayConfigDaoImpl;
import dinghan.workflow.kq.summary.entity.KQSummaryData;
import dinghan.workflow.kq.userinfo.entity.UserInfo;
import weaver.conn.RecordSet;

/**
 * 中车考勤汇总生成
 * @author zhangxiaoyu / 10593 - 2017-11-22
 * 
 */
public class ZRKQSummaryDataGen implements KQSummaryDataGen {

	private KQDetialService kqDetailService = new KQDetialServiceImpl();
	private List<KQDetailData> kqDetailDataList = null;
	private HolidayConfigDao holidayDao = new HolidayConfigDaoImpl();
	private String startDateForCount;
	private String endDateForCount;
	private double effectDays = 0;
	
	private int userId;
	
	@Override
	public KQSummaryData createSummaryData(UserInfo userInfo, String kqMonth) {
		KQSummaryData kqSummaryData = null;
		this.startDateForCount = kqMonth + "-01";
		this.endDateForCount = kqMonth += "-31";
		this.userId = userInfo.getName();
		
		kqDetailDataList = kqDetailService.queryListByUserAndDate(userId, startDateForCount, endDateForCount);
		
		if(kqDetailDataList != null){
			/*
			 * 赋值人员信息
			 */
			kqSummaryData = new KQSummaryData();	
			kqSummaryData.setGh(userInfo.getCode());
			kqSummaryData.setXm(userId);
			kqSummaryData.setYjbm(userInfo.getDeptOneNameText());
			kqSummaryData.setEjbm(userInfo.getDeptTwoNameText());
			kqSummaryData.setKqlb(userInfo.getKaoQinType());
			kqSummaryData.setRzzt(userInfo.getInCompany()==0?"在职":"离职");
			kqSummaryData.setSsgs(userInfo.getCompany());
			if(this.getJobTitle(userId)>-1){
				kqSummaryData.setGw(this.getJobTitle(userId));
			}
			//请假小时数归零
			kqSummaryData.setSj(0);
			kqSummaryData.setBj(0);
			kqSummaryData.setTxj(0);
			kqSummaryData.setNxj(0);
			kqSummaryData.setHj(0);
			kqSummaryData.setCj(0);
			kqSummaryData.setCjj(0);
			kqSummaryData.setPcj(0);
			kqSummaryData.setLcj(0);
			kqSummaryData.setSangj(0);
			kqSummaryData.setJsj(0);
			kqSummaryData.setJyj(0);
			kqSummaryData.setTrgs(0);
			kqSummaryData.setGrgs(0);
			kqSummaryData.setHjj(0);
			kqSummaryData.setBrj(0);
			
			for(KQDetailData dtData : this.kqDetailDataList){
				
				kqSummaryData = soluteQingJiaData(kqSummaryData,dtData);	//处理请假数据
				
			}
			
			
		}
		
		
		return kqSummaryData;
	}
	
	/**
	 * 计算应出勤天数
	 * @return
	 */
	private double countEffectDays(UserInfo userInfo){
		
		this.effectDays = 0;
		
		
		
		return effectDays;
	}
	
	/**
	 * 处理请假数据
	 * 
	 */
	private KQSummaryData soluteQingJiaData(KQSummaryData kqSummaryData, KQDetailData dtData){
		KQSummaryData _kqSummaryData = kqSummaryData;
		/**
		 * 各类假期归零
		 */
		//for(KQDetailData dtData : this.kqDetailDataList){
			
			_kqSummaryData.setSj(_kqSummaryData.getSj() + dtData.getSj());
			_kqSummaryData.setBj(_kqSummaryData.getBj() + dtData.getBj());
			_kqSummaryData.setTxj(_kqSummaryData.getTxj() + dtData.getTxj());
			_kqSummaryData.setNxj(_kqSummaryData.getNxj() + dtData.getNxj());
			_kqSummaryData.setHj(_kqSummaryData.getHj() + dtData.getHj());
			_kqSummaryData.setCj(_kqSummaryData.getCj() + dtData.getCj());
			_kqSummaryData.setCjj(_kqSummaryData.getCjj() + dtData.getCjj());
			_kqSummaryData.setPcj(_kqSummaryData.getPcj() + dtData.getPcj());
			_kqSummaryData.setLcj(_kqSummaryData.getLcj() + dtData.getLcj());
			_kqSummaryData.setSangj(_kqSummaryData.getSangj() + dtData.getSangj());
			_kqSummaryData.setJsj(_kqSummaryData.getJsj() + dtData.getJsj());
			_kqSummaryData.setJyj(_kqSummaryData.getJyj() + dtData.getJyj());
			_kqSummaryData.setTrgs(_kqSummaryData.getTrgs() + dtData.getTrgs());
			_kqSummaryData.setGrgs(_kqSummaryData.getGrgs() + dtData.getGrgs());
			_kqSummaryData.setHjj(_kqSummaryData.getHjj() + dtData.getHjj());
			_kqSummaryData.setBrj(_kqSummaryData.getBrj() + dtData.getBrj());
			//}
		
		return _kqSummaryData;
	}

	/**
	 * 获取岗位名称
	 * @param jobTitleID - 岗位ID
	 * @return
	 */
	private int getJobTitle(int userId){
		int jobTitleID = -1;
		String slq = "select jobtitle from HrmJobTitles id = " + userId;
		RecordSet rs = new RecordSet();
		rs.executeSql(slq);
		while(rs.next()){
			jobTitleID = rs.getInt("jobtitle");
		}
		return jobTitleID;
	}
}
