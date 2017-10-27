package dinghan.zrac.hr.entity.neeentity;

/**
 * 新员工培养目标
 * @author zhangxiaoyu/10593 - 2017-10-10
 * 
 */
public class NEECheckTargetDTData_Month extends NEECheckTargetDTData {

	/**
	 * 月度评价内容
	 */
	private String monthevaluation;
	/**
	 * 月度评定等级
	 */
	private int monthassess;
	/**
	 * 考核目标明细ID
	 * <p>每个月的考核目标明细表是由主管填写的考核目标明细复制而来。
	 * 复制内容包括考核目标，目标分解工作内容，考核依据和备注。
	 * 此ID即为考核目标明细表记录的ID，将此ID复制到各月的明细表中，用于对各月目标ID的更新。
	 */
	private int targetDataID;
	
	public String getMonthevaluation() {
		return monthevaluation;
	}
	public void setMonthevaluation(String monthevaluation) {
		this.monthevaluation = monthevaluation;
	}
	public int getMonthassess() {
		return monthassess;
	}
	public void setMonthassess(int monthassess) {
		this.monthassess = monthassess;
	}
	public int getTargetDataID() {
		return targetDataID;
	}
	public void setTargetDataID(int targetDataID) {
		this.targetDataID = targetDataID;
	}
	
}
