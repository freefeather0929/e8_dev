package dinghan.workflow.beans;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;

public class Cctemp {
	private static Log log = LogFactory.getLog(Cctemp.class.getName());

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		Cctemp.log = log;
	}

	private int id;// id
	private int requestId;// 请求id
	private int hrmid;// 姓名
	private int kqr;// 考勤员
	private String flowno;// 流水单号
	private String hrmno;// 工号
	private String ccrq;// 出差日期
	private String hdkssj;// 核定开始时间
	private String hdjssj;// 核定结束时间
	private String dataid;// 数据id
	private String xq;// 星期
	private String mainid;// 主流程id

	/**
	 * 功能：插入表3信息
	 * 
	 */
	public void insert() throws Exception {
		try {
			String sql = "INSERT INTO uf__cc_temp (hrmid,kqr,flowno,hrmno,ccrq,hdkssj,hdjssj,dataid,xq,mainid)";
			sql += " VALUES  (";
			sql += "'" + this.hrmid + "',";
			sql += "'" + this.kqr + "',";
			sql += "'" + this.flowno + "',";
			sql += "'" + this.hrmno + "',";
			sql += "'" + this.ccrq + "',";
			sql += "'" + this.hdkssj + "',";
			sql += "'" + this.hdjssj + "',";
			sql += "'" + this.dataid + "',";
			sql += "'" + this.xq + "',";
			sql += "'" + this.mainid + "'";
			sql += ")";
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("插入出差中间表：" + e);
			throw e;
		}
	}

	/**
	 * 功能：根据主表id删除中间表信息
	 * 
	 */
	public static void delete(int mainid, String ccrq) throws Exception {
		try {
			String sql = "delete from uf__cc_temp where ccrq='" + ccrq
					+ "' and mainid=" + mainid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("删除出差中间表信息：" + e);
			throw e;
		}
	}

	/**
	 * 功能：根据主表id删除中间表信息
	 * 
	 */
	public static void delete(int mainid) throws Exception {
		try {
			String sql = "delete from uf__cc_temp where  mainid=" + mainid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("删除出差中间表信息：" + e);
			throw e;
		}
	}

	/*
	 * 通过用户id，出差日期得到外出list（已核定）
	 * 
	 * @return List<JiaBan1>
	 */

	@Override
	public String toString() {
		return "Cctemp [id=" + id + ", requestId=" + requestId + ", hrmid="
				+ hrmid + ", kqr=" + kqr + ", flowno=" + flowno + ", hrmno="
				+ hrmno + ", ccrq=" + ccrq + ", hdkssj=" + hdkssj + ", hdjssj="
				+ hdjssj + ", dataid=" + dataid + ", xq=" + xq + ", mainid="
				+ mainid + "]";
	}
	
	/**
	 * 获取出差中间表信息的集合
	 * @param hrmid - 人员的ID
	 * @param ccrq - 出差日期
	 * @return List<Cctemp> - 出差中间表的集合
	 */
	public static ArrayList<Cctemp> queryList(int hrmid, String ccrq) {
		ArrayList<Cctemp> aCC = new ArrayList<Cctemp>();
		try {
			String sql = "SELECT * FROM uf__cc_temp Where hrmid='" + hrmid + "' and ccrq='" + ccrq + "'";

			RecordSet rs = new RecordSet();
			rs.executeSql(sql);

			while (rs.next()) {
				Cctemp cctemp = new Cctemp();
				cctemp.setHdkssj(rs.getString("hdkssj"));
				cctemp.setHdjssj(rs.getString("hdjssj"));
				aCC.add(cctemp);
			}
		} catch (Exception e) {
			log.error("得到外出中间表信息出错：" + e.getStackTrace());
			e.printStackTrace();
		}
		return aCC;
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

	public String getCcrq() {
		return ccrq;
	}

	public void setCcrq(String ccrq) {
		this.ccrq = ccrq;
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

	public String getXq() {
		return xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}

	public String getMainid() {
		return mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid;
	}
}
