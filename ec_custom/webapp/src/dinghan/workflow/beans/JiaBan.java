package dinghan.workflow.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;

public class JiaBan {
	private static Log log = LogFactory.getLog(JiaBan.class.getName());

	private int id; // id
	private int requestId; // 请求id
	private int proposer; // 申请人
	private int stdept2; // 二级部门
	private int stdept1; // 一级部门
	private int jbgzdd; // 加班工作地点
	private int zjzg; // 直接主管
	private int sjzg; // 上级主管
	private int bzgzsj; // 标准工作时间
	private int kqhdyj; // 考勤核定意见
	private String setdate;// 申请日期
	private String jbsy; // 加班事由
	private String kqlx; // 申请人考勤类型
	private String sqdh; // 申请单号

	// 通过requestid得到主表信息
	public JiaBan(String requestid) {
		try {
			String sql = "select * from formtable_main_94 where requestid= " + requestid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			if (rs.next()) {
				this.kqhdyj = rs.getInt("kqhdyj");
				this.jbgzdd = rs.getInt("jbgzdd");
				this.zjzg = rs.getInt("zjzg");
				this.id = rs.getInt("id");
				this.bzgzsj = rs.getInt("bzgzsj");
				this.proposer = rs.getInt("proposer");
				this.requestId = rs.getInt("requestId");
				this.sqdh = rs.getString("sqdh");
				this.setdate = rs.getString("setdate");
				this.sjzg = rs.getInt("sjzg");
				this.stdept1 = rs.getInt("stdept1");
				this.stdept2 = rs.getInt("stdept2");
				this.jbsy = rs.getString("jbsy");
				this.kqlx = rs.getString("kqlx");
			}
		} catch (Exception e) {
			log.error("requestid取主表信息：" + e);
		}
	}

	// 通过mainid得到主表信息
	public JiaBan(int mainid) {
		try {
			String sql = "select * from formtable_main_94 where id= " + mainid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			if (rs.next()) {
				this.kqhdyj = rs.getInt("kqhdyj");
				this.jbgzdd = rs.getInt("jbgzdd");
				this.zjzg = rs.getInt("zjzg");
				this.id = rs.getInt("id");
				this.bzgzsj = rs.getInt("bzgzsj");
				this.proposer = rs.getInt("proposer");
				this.requestId = rs.getInt("requestId");
				this.sqdh = rs.getString("sqdh");
				this.setdate = rs.getString("setdate");
				this.sjzg = rs.getInt("sjzg");
				this.stdept1 = rs.getInt("stdept1");
				this.stdept2 = rs.getInt("stdept2");
				this.jbsy = rs.getString("jbsy");
				this.kqlx = rs.getString("kqlx");
			}
		} catch (Exception e) {
			log.error("requestid取主表信息：" + e);
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

	public int getStdept2() {
		return stdept2;
	}

	public void setStdept2(int stdept2) {
		this.stdept2 = stdept2;
	}

	public int getStdept1() {
		return stdept1;
	}

	public void setStdept1(int stdept1) {
		this.stdept1 = stdept1;
	}

	public int getJbgzdd() {
		return jbgzdd;
	}

	public void setJbgzdd(int jbgzdd) {
		this.jbgzdd = jbgzdd;
	}

	public int getZjzg() {
		return zjzg;
	}

	public void setZjzg(int zjzg) {
		this.zjzg = zjzg;
	}

	public int getSjzg() {
		return sjzg;
	}

	public void setSjzg(int sjzg) {
		this.sjzg = sjzg;
	}

	public int getBzgzsj() {
		return bzgzsj;
	}

	public void setBzgzsj(int bzgzsj) {
		this.bzgzsj = bzgzsj;
	}

	public int getKqhdyj() {
		return kqhdyj;
	}

	public void setKqhdyj(int kqhdyj) {
		this.kqhdyj = kqhdyj;
	}

	public String getSetdate() {
		return setdate;
	}

	public void setSetdate(String setdate) {
		this.setdate = setdate;
	}

	public String getJbsy() {
		return jbsy;
	}

	public void setJbsy(String jbsy) {
		this.jbsy = jbsy;
	}

	public String getKqlx() {
		return kqlx;
	}

	public void setKqlx(String kqlx) {
		this.kqlx = kqlx;
	}

	public String getSqdh() {
		return sqdh;
	}

	public void setSqdh(String sqdh) {
		this.sqdh = sqdh;
	}

	public String getZw() {
		return zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}

	private String zw;

}
