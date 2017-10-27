package dinghan.workflow.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;

public class QJtemp {
	private static Log log = LogFactory.getLog(QJtemp.class.getName());

	private int id;	// id
	private int hrmid;	// 人员id
	private int requestId;	// 请求id
	private int qjlx;	// 请假类型
	private int kqr;	// 考勤员
	private String flowno;	// 流水单号
	private String hrmno;	// 工号
	private String qjrq;	// 请假日期
	private String xq;	// 星期
	private String hdkssj;	// 核定开始时间
	private String hdjssj;	// 核定结束时间
	private String hdgs;	// 核定工时
	private String hdyf;	// 核定月份
	private String dataid;	// 数据id
	private String mainid;	// 主流程id
	private int wf_dt3_id;	// 请假明细3id
	private int isabnormal;  //是否来自异常补录流程

	/**
	 * 功能：插入请假中间表信息
	 * 
	 */
	public void insert() throws Exception {
		try {
			String sql = "INSERT INTO uf__qj_temp (hrmid,qjlx,kqr,flowno,hrmno,qjrq,xq,hdkssj,hdjssj,hdgs,hdyf,dataid,mainid,isabnormal,wf_dt3_id)";
			sql += " VALUES (";
			sql += "'" + this.hrmid + "',";
			sql += "'" + this.qjlx + "',";
			sql += "'" + this.kqr + "',";
			sql += "'" + this.flowno + "',";
			sql += "'" + this.hrmno + "',";
			sql += "'" + this.qjrq + "',";
			sql += "'" + this.xq + "',";
			sql += "'" + this.hdkssj + "',";
			sql += "'" + this.hdjssj + "',";
			sql += "'" + this.hdgs + "',";
			sql += "'" + this.hdyf + "',";
			sql += "'" + this.dataid + "',";
			sql += "'" + this.mainid + "',";
			sql += "'" + this.isabnormal + "',";
			sql += "'" + this.wf_dt3_id + "'";
			sql += ")";
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
		} catch (Exception e) {
			log.error("插入请假中间表：" + e);
			throw e;
		}
	}

	/**
	 * 根据主表id删除中间表信息
	 * 
	 */
	public static void delete(int mainid) throws Exception {
		try {
	      String sql = "delete from uf__qj_temp where mainid = '" + mainid + "'";
	      RecordSet rs = new RecordSet();
	      rs.executeSql(sql);
	    } catch (Exception e) {
	      log.error("删除请假中间表信息：" + e);
	      throw e;
	    }
	}
	
	/**
	 * 根据主表id删除中间表信息
	 * @param mainid - 主表Id
	 * @param qjrq - 请假日期
	 * @param qjlx - 请假类型
	 */
	public static void delete(int mainid,String qjrq,int qjlx) throws Exception {
		try {
	      String sql = "delete from uf__qj_temp where mainid = '" + mainid + "' and ";
	      RecordSet rs = new RecordSet();
	      rs.executeSql(sql);
	    } catch (Exception e) {
	      log.error("删除请假中间表信息：" + e);
	      throw e;
	    }
	}
	
	/**
	 * 删除来自考勤异常补录的请假中间表
	 */
	public static void deleteJQTempFromAbnormal(int mainid){
		
		try{
			String sql = "delete from uf__qj_temp where mainid = '" + mainid + "'"
					+ " and isabnormal = '1'";
		    RecordSet rs = new RecordSet();
		    rs.executeSql(sql);
		}catch(Exception e) {
			e.getStackTrace();
		}
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHrmid() {
		return hrmid;
	}

	public void setHrmid(int hrmid) {
		this.hrmid = hrmid;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getQjlx() {
		return qjlx;
	}

	public void setQjlx(int qjlx) {
		this.qjlx = qjlx;
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

	public String getQjrq() {
		return qjrq;
	}

	public void setQjrq(String qjrq) {
		this.qjrq = qjrq;
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

	public String getHdgs() {
		return hdgs;
	}

	public void setHdgs(String hdgs) {
		this.hdgs = hdgs;
	}

	public String getHdyf() {
		return hdyf;
	}

	public void setHdyf(String hdyf) {
		this.hdyf = hdyf;
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

	public int getIsabnormal() {
		return isabnormal;
	}

	public void setIsabnormal(int isabnormal) {
		this.isabnormal = isabnormal;
	}

	public int getWf_dt3_id() {
		return wf_dt3_id;
	}

	public void setWf_dt3_id(int wf_dt3_id) {
		this.wf_dt3_id = wf_dt3_id;
	}
}
