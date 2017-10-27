package dinghan.workflow.kq.checkout.bean;

import java.util.List;

/**
 * 打卡记录集合
 * 获取某人、某天的所有打卡记录，并获取最早和最晚的记录
 * @author zhangxiaoyu / 10593 - 2017-07-13
 */
public class CheckOutRecordSet {
	
	/**
	 * 打卡记录集合
	 */
	private List<CheckOutRecord> checkOutRecordList;
	
	/**
	 * 最早打卡记录
	 */
	private String startTime;
	
	/**
	 * 最晚打卡记录
	 */
	private String endTime;
	
	/**
	 * 获取打卡记录的集合
	 * @return
	 */
	public List<CheckOutRecord> getCheckOutRecordList() {
		return checkOutRecordList;
	}
	public void setCheckOutRecordList(List<CheckOutRecord> checkOutRecordList) {
		this.checkOutRecordList = checkOutRecordList;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
