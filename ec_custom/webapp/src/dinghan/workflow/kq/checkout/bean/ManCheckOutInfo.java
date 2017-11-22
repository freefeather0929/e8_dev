package dinghan.workflow.kq.checkout.bean;
/**
 * 手工考勤
 * @author zhangxiaoyu / 10593 - 2017-11-19
 *
 */
public class ManCheckOutInfo {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 工号
	 */
	private String workcode;
	/**
	 * 用户ID
	 */
	private int userid;
	/**
	 * 打卡日期
	 */
	private String dakadate;
	/**
	 * 打卡时间
	 */
	private String dakatime;
	/**
	 * 打卡类型 0 - 手工考勤 ； 1- 忘打卡
	 */
	private int dakatype;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWorkcode() {
		return workcode;
	}

	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getDakadate() {
		return dakadate;
	}

	public void setDakadate(String dakadate) {
		this.dakadate = dakadate;
	}
	

	public String getDakatime() {
		return dakatime;
	}

	public void setDakatime(String dakatime) {
		this.dakatime = dakatime;
	}

	public int getDakatype() {
		return dakatype;
	}

	public void setDakatype(int dakatype) {
		this.dakatype = dakatype;
	}
	
}
