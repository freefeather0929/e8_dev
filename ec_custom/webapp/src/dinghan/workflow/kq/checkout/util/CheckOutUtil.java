package dinghan.workflow.kq.checkout.util;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import org.apache.commons.logging.Log;

import dinghan.workflow.beans.PublicVariant;
import dinghan.workflow.kq.checkout.bean.CheckOutRecord;
import dinghan.workflow.kq.checkout.bean.CheckOutRecordSet;
import dinghan.workflow.kq.checkout.dao.impl.CheckOutImp;
import dinghan.workflow.kq.checkout.service.CheckOutService;
import dinghan.workflow.kq.checkout.service.impl.CheckOutServiceImpl;

/**
 * 打卡记录的工具类
 * <p>
 * 用于获取打卡记录集合，
 * @author zhangxiaoyu / 10593 - 2017-07-14
 */
public class CheckOutUtil {
	private static Log log = LogFactory.getLog(CheckOutUtil.class.getName());
	private CheckOutService checkOutSercie;
	public CheckOutUtil(){
		this.checkOutSercie = new CheckOutServiceImpl(new CheckOutImp());
	}
	
	/**
	 * 获取员工某一天的打卡记录集合
	 * <p> 
	 * 可以从获取的集合对象中获取当天打卡记录的集合以及首末次打卡的记录
	 * <p> 
	 * @param userWorkCode - 员工编号
	 * @param checkOutDate - 打卡的日期
	 * @param hasMobile - 是否获取移动考勤的标记
	 * @return
	 */
	public CheckOutRecordSet getPersonalCheckOutSetByDay(String userWorkCode, String checkOutDate, int hasMobile){
		CheckOutRecordSet checkOutRecordSet = new CheckOutRecordSet();
		List<CheckOutRecord> checkOutRecordList = checkOutSercie.queryCheckOutList(userWorkCode, checkOutDate, hasMobile);
		
		CheckOutRecord checkOutRecord_nextDate = getNextDateCheckOutRecord(userWorkCode, checkOutDate, hasMobile);
		if(checkOutRecord_nextDate != null){
			checkOutRecordList.add(checkOutRecord_nextDate);
		}
		if(checkOutRecordList.isEmpty()){
			checkOutRecordSet.setStartTime("");
			checkOutRecordSet.setEndTime("");
		}else{
			checkOutRecordSet.setStartTime(checkOutRecordList.get(0).getTime());
			checkOutRecordSet.setEndTime(checkOutRecordList.get(checkOutRecordList.size()-1).getTime());
		}
		checkOutRecordSet.setCheckOutRecordList(checkOutRecordList);
		return checkOutRecordSet;
	}
	
	/**
	 * 用于获取后一天的日期
	 * @param currentDate
	 * @return
	 */
	private String getNextDate(String currentDate){
		Date nextDate = null;
		String nextDateStr = null;
		try {
			nextDate = PublicVariant.StrToDate(currentDate, "yyyy-MM-dd");
			nextDate = PublicVariant.AdjustDateTime(nextDate, 0, 0, 1);
			nextDateStr = PublicVariant.DateToStr(nextDate, "yyyy-MM-dd");
		} catch (Exception e) {
			log.error("-----------获取后一天的日期时错误----------");
			log.error(e.getStackTrace());
			log.error("------------------------------------------");
			e.printStackTrace();
		}
		return nextDateStr;
	}
	
	/**
	 * 获取后一天打卡记录
	 * <p> 
	 * @param userWorkCode
	 * @param checkOutDate
	 * @param hasMobile
	 * @return
	 */
	private CheckOutRecord getNextDateCheckOutRecord(String userWorkCode, String checkOutDate, int hasMobile){
		CheckOutRecord checkOutRecord_NextDate = null;
		String nextDate = getNextDate(checkOutDate);
		//后一天的打卡记录
		List<CheckOutRecord> checkOutRecordList_NextDate  = checkOutSercie.queryCheckOutList(userWorkCode, nextDate, hasMobile);
		
		String forwordDayCheckOutNode = "05:00:00";
		
		if(!checkOutRecordList_NextDate.isEmpty()){
			if(checkOutRecordList_NextDate.get(0).getTime().compareTo(forwordDayCheckOutNode) < 0){
				checkOutRecord_NextDate = checkOutRecordList_NextDate.get(0);
				checkOutRecord_NextDate.setTime("23:59");
			}
		}
		return checkOutRecord_NextDate;
	}
}
