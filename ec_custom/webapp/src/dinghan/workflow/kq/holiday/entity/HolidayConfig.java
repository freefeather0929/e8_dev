package dinghan.workflow.kq.holiday.entity;

/**
 * 节假日配置信息
 * @author zhangxiaoyu / 10593 - 2017-10-23
 *
 */
public class HolidayConfig {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 人员字符串 由多个人员的id组成，以 逗号 相隔；
	 */
	private String person;
	/**
	 * 节日名称
	 */
	private int jrmc;	
	/**
	 * 加班系数
	 */
	private double overnum;
	/**
	 * 开始时间
	 */
	private String kssj;
	/**
	 * 结束时间
	 */
	private String jssj;
	/**
	 * 日期
	 */
	private String day;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public int getJrmc() {
		return jrmc;
	}

	public void setJrmc(int jrmc) {
		this.jrmc = jrmc;
	}

	public double getOvernum() {
		return overnum;
	}

	public void setOvernum(double overnum) {
		this.overnum = overnum;
	}

	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	
}
