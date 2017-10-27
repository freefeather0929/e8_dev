package dinghan.workflow.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;

public class Abnormal {
	private static Log log = LogFactory.getLog(Abnormal.class.getName());

	private int id;// id
	private int requestId;// 请求id
	private int proposer;// 申请人
	private int yclx;// 异常类型
	private String sqrq;// 申请日期
	private String ycrq;// 异常日期
	private String time1;// 打卡时间1
	private String time2;// 打卡时间2
	private String time3;// 打卡时间3
	private String time4;// 打开时间4
	private String fj;// 附件
	private int hdzt;// 核定状态

	/**
	 * 通过userid 和日期 type类型 得到考勤异常对象 type 0:忘打卡；1：手工考勤；2：外出公干 当小于0时，得到所有的对象
	 * 
	 * @param userid
	 * @param date
	 */
	public Abnormal(int userid, String date, int type) {
		String sql = "select * from formtable_main_106 where proposer = " + userid + " and ycrq= '" + date + "' ";
		if (type >= 0) {
			sql += " and type=" + type;
		}
		try {
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			if (rs.next()) {
				this.fj = rs.getString("fj");
				this.id = rs.getInt("id");
				this.proposer = rs.getInt("proposer");
				this.requestId = rs.getInt("requestId");
				this.sqrq = rs.getString("sqrq");
				this.time1 = rs.getString("time1");
				this.time2 = rs.getString("time2");
				this.time3 = rs.getString("time3");
				this.time4 = rs.getString("time4");
				this.yclx = rs.getInt("yclx");
				this.ycrq = rs.getString("ycrq");
				this.hdzt = rs.getInt("hdzt");
			}
		} catch (Exception e) {
			log.error("得到异常信息出错：" + e);
		}
	}

	/**
	 * 根据requestid得到异常表的信息
	 * 
	 * @param requestid
	 */
	public Abnormal(String requestid) {
		String sql = "select * from formtable_main_106 where requestid = " + requestid;
		try {
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			if (rs.next()) {
				this.fj = rs.getString("fj");
				this.id = rs.getInt("id");
				this.proposer = rs.getInt("proposer");
				this.requestId = rs.getInt("requestId");
				this.sqrq = rs.getString("sqrq");
				this.time1 = rs.getString("time1");
				this.time2 = rs.getString("time2");
				this.time3 = rs.getString("time3");
				this.time4 = rs.getString("time4");
				this.yclx = rs.getInt("yclx");
				this.ycrq = rs.getString("ycrq");
				this.hdzt = rs.getInt("hdzt");
			}
		} catch (Exception e) {
			log.error("获取出差主表信息:" + e);
		}
	}

	/**
	 * 考勤员复核时，修改核定状态
	 * 
	 * @param requestId
	 * @param type
	 * @throws Exception
	 */
	public static void update(int requestId, int type) throws Exception {
		try {
			String sql = "update formtable_main_106 set hdzt=" + type;
			sql += " where requestId=" + requestId;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error("更新考勤异常出错：" + e);
			throw e;
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getProposer() {
		return proposer;
	}

	public void setProposer(int proposer) {
		this.proposer = proposer;
	}

	public int getYclx() {
		return yclx;
	}

	public void setYclx(int yclx) {
		this.yclx = yclx;
	}

	public String getSqrq() {
		return sqrq;
	}

	public void setSqrq(String sqrq) {
		this.sqrq = sqrq;
	}

	public String getYcrq() {
		return ycrq;
	}

	public void setYcrq(String ycrq) {
		this.ycrq = ycrq;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public String getTime3() {
		return time3;
	}

	public void setTime3(String time3) {
		this.time3 = time3;
	}

	public String getTime4() {
		return time4;
	}

	public void setTime4(String time4) {
		this.time4 = time4;
	}

	public String getFj() {
		return fj;
	}

	public void setFj(String fj) {
		this.fj = fj;
	}

	public int getHdzt() {
		return hdzt;
	}

	public void setHdzt(int hdzt) {
		this.hdzt = hdzt;
	}

	public Abnormal() {

	}
}
