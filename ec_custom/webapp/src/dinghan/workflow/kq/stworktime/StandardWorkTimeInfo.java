package dinghan.workflow.kq.stworktime;

/**
 * 标准工作时间信息
 * @author zhangxiaoyu / 10593 - 2017-11-08
 *
 */
public class StandardWorkTimeInfo {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 标准工作开始时间-结束时间 文本
	 */
	private String ststartendtime;
	/**
	 * 标准工作开始时间
	 */
	private String stworkstarttime;
	/**
	 * 标准工作结束时间
	 */
	private String stworkendtime;
	/**
	 * 午休开始时间
	 */
	private String streststarttime;
	/**
	 * 午休结束时间
	 */
	private String strestendtime;
	/**
	 * 午休小时数
	 */
	private double noonresthour;
	/**
	 * 是否有效
	 */
	private int isvalid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStstartendtime() {
		return ststartendtime;
	}

	public void setStstartendtime(String ststartendtime) {
		this.ststartendtime = ststartendtime;
	}

	public String getStworkendtime() {
		return stworkendtime;
	}

	public void setStworkendtime(String stworkendtime) {
		this.stworkendtime = stworkendtime;
	}

	public String getStreststarttime() {
		return streststarttime;
	}

	public void setStreststarttime(String streststarttime) {
		this.streststarttime = streststarttime;
	}

	public String getStrestendtime() {
		return strestendtime;
	}

	public void setStrestendtime(String strestendtime) {
		this.strestendtime = strestendtime;
	}

	public double getNoonresthour() {
		return noonresthour;
	}

	public void setNoonresthour(double noonresthour) {
		this.noonresthour = noonresthour;
	}

	public int getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(int isvalid) {
		this.isvalid = isvalid;
	}

	public String getStworkstarttime() {
		return stworkstarttime;
	}

	public void setStworkstarttime(String stworkstarttime) {
		this.stworkstarttime = stworkstarttime;
	}
	
}
