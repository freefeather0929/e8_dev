package dinghan.workflow.kq.checkout.bean;
/**
 * 
 * 单条打卡记录
 * @author zhangxiaoyu / 10593 - 2017-07-14
 *	
 */
public class CheckOutRecord {
	
	/**
	 * 员工工号
	 */
	private String code;
	/**
	 * 打卡日期
	 */
	private String date;
	/**
	 * 打卡时间
	 */
	private String time;
	/**
	 * 员工姓名
	 */
	private String name;
	/**
	 * 打卡类 ：0 -- 考勤机打卡； 1 -- 移动打卡
	 */
	private int signtype;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSigntype() {
		return signtype;
	}
	public void setSigntype(int signtype) {
		this.signtype = signtype;
	}
	
}
