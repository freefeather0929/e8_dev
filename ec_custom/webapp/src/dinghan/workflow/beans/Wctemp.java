package dinghan.workflow.beans;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;

public class Wctemp {
	private static Log log = LogFactory.getLog(Wctemp.class.getName());

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		Wctemp.log = log;
	}

	private int id;// id
	private int requestId;// 请求id
	private int hrmid;// 姓名
	private int kqr;// 考勤员
	private String flowno;// 流水号
	private String hrmno;// 工号
	private String wcrq;// 外出日期
	private String xq;// 星期
	private String hdkssj;// 核定开始时间
	private String hdjssj;// 核定结束时间
	private String dataid;// 数据id
	private String mainid;// 主流程id

	/**
	 * 功能：插入表3信息
	 * 
	 */
	public void insert() throws Exception {
		try {
			String sql = "INSERT INTO uf__wc_temp (hrmid,kqr,flowno,hrmno,wcrq,xq,hdkssj,hdjssj,dataid,mainid)";
			sql += " VALUES  (";
			sql += "'" + this.hrmid + "',";
			sql += "'" + this.kqr + "',";
			sql += "'" + this.flowno + "',";
			sql += "'" + this.hrmno + "',";
			sql += "'" + this.wcrq + "',";
			sql += "'" + this.xq + "',";
			sql += "'" + this.hdkssj + "',";
			sql += "'" + this.hdjssj + "',";
			sql += "'" + this.dataid + "',";
			sql += "'" + this.mainid + "'";
			sql += ")";
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error("插入外出中间表：" + e);
			throw e;
		}
	}

	/**
	 * 功能：根据主表id删除中间表信息
	 * 
	 */
	public static void delete(int mainid, String wcrq) throws Exception {
		try {
			String sql = "delete from uf__wc_temp where wcrq='" + wcrq
					+ "' and mainid=" + mainid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error("删除请假中间表信息：" + e);
			throw e;
		}
	}

	/*
	 * 通过用户id，外出日期得到外出list（已核定）
	 * 
	 * @return List<Wctemp>
	 */
	public static ArrayList<Wctemp> queryList(int hrmid, String wcrq) {
		ArrayList<Wctemp> aWC = new ArrayList<Wctemp>();
		try {
			String sql = "SELECT * FROM uf__wc_temp Where hrmid='" + hrmid
					+ "' and wcrq='" + wcrq + "'";

			RecordSet rs = new RecordSet();
			rs.executeSql(sql);

			while (rs.next()) {
				Wctemp wctemp = new Wctemp();
				wctemp.setHdkssj(rs.getString("hdkssj"));
				wctemp.setHdjssj(rs.getString("hdjssj"));
				aWC.add(wctemp);
			}
		} catch (Exception e) {
			log.error("得到外出中间表信息出错：" + e.getStackTrace());
			e.printStackTrace();
		}
		return aWC;
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

	public int getHrmid() {
		return hrmid;
	}

	public void setHrmid(int hrmid) {
		this.hrmid = hrmid;
	}

	public int getKqr() {
		return kqr;
	}

	public void setKqr(int kqr) {
		this.kqr = kqr;
	}

	public String getFlowno() {
		return flowno;
	}

	public void setFlowno(String flowno) {
		this.flowno = flowno;
	}

	public String getHrmno() {
		return hrmno;
	}

	public void setHrmno(String hrmno) {
		this.hrmno = hrmno;
	}

	public String getWcrq() {
		return wcrq;
	}

	public void setWcrq(String wcrq) {
		this.wcrq = wcrq;
	}

	public String getXq() {
		return xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}

	public String getHdkssj() {
		return hdkssj;
	}

	public void setHdkssj(String hdkssj) {
		this.hdkssj = hdkssj;
	}

	public String getHdjssj() {
		return hdjssj;
	}

	public void setHdjssj(String hdjssj) {
		this.hdjssj = hdjssj;
	}

	public String getDataid() {
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

	public String getMainid() {
		return mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid;
	}
}
