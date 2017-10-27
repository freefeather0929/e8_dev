package dinghan.workflow.beans;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;

public class WaiChu {
	private static Log log = LogFactory.getLog(WaiChu.class.getName());

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		WaiChu.log = log;
	}

	private int id; // id
	private int requestId; // 请求id
	private int proposer; // 申请人
	private int stdept1; // 一级部门
	private int stdept2; // 二级部门
	private int wcrq; // 外出时间
	private int stdept3; // 三级部门
	private String appnom; // 流水号
	private String setdate; // 申请日期
	private String lxdh; // 联系电话
	private String kssj; // 开始时间
	private String jssj; // 结束时间
	private String xq; // 星期
	private String zw1; // 职务
	private String ggsy; // 公干事由
	private int hdzt;// 核定状态

	/**
	 * 核定明细
	 * 
	 * @return
	 * @throws Exception
	 */
	public static void checkList(int id) throws Exception {
		log.error("外出明细核定：");
		String nowDate = PublicVariant.DateToStr(new Date(), "YYYY-MM-dd");
		String sql = "";
		if (id == 0) {
			sql = "SELECT * FROM formtable_main_95 WHERE wcrq<'" + nowDate;
			sql += "' and hdzt is null ORDER BY id";
		} else {
			sql = "SELECT * FROM formtable_main_95 WHERE id=" + id;
		}

		RecordSet wcrs = new RecordSet();
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		String userCode = "-1";
	    String sqlCode = "";
		
		while (rs.next()) {
			
			sqlCode = "select top 1 workcode from HrmResource where id = " + rs.getInt("userid");
			
			RecordSet rs2 = new RecordSet();
			
			rs2.executeSql(sqlCode);
			
			while(rs2.next()){
				userCode = rs2.getString("workcode");
			}
			
			UserInfo userInfo = new UserInfo(rs.getInt("proposer"),userCode);
			Wctemp wctemp = new Wctemp();
			wctemp.setFlowno(rs.getString("appnom"));
			wctemp.setHdjssj(rs.getString("jssj"));
			wctemp.setHdkssj(rs.getString("kssj"));
			wctemp.setHrmid(rs.getInt("proposer"));
			wctemp.setHrmno(userInfo.getCode());
			// wctemp.setKqr(kqr);
			wctemp.setMainid(String.valueOf(rs.getInputStream("id")));
			wctemp.setWcrq(rs.getString("wcrq"));
			wctemp.setXq(rs.getString("xq"));
			wctemp.setDataid(rs.getString("requestId"));
			wctemp.setMainid(String.valueOf(rs.getInt("id")));

			Wctemp.delete(rs.getInt("id"), rs.getString("wcrq"));// 删除中间表信息

			wctemp.insert();// 插入中间表信息

			String wcsql = "update formtable_main_95 set hdzt=1 where id="
					+ rs.getInt("id");
			wcrs.executeSql(wcsql);
		}
	}

	public int getWcrq() {
		return wcrq;
	}

	public void setWcrq(int wcrq) {
		this.wcrq = wcrq;
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

	public int getStdept1() {
		return stdept1;
	}

	public void setStdept1(int stdept1) {
		this.stdept1 = stdept1;
	}

	public int getStdept2() {
		return stdept2;
	}

	public void setStdept2(int stdept2) {
		this.stdept2 = stdept2;
	}

	public int getStdept3() {
		return stdept3;
	}

	public void setStdept3(int stdept3) {
		this.stdept3 = stdept3;
	}

	public String getAppnom() {
		return appnom;
	}

	public void setAppnom(String appnom) {
		this.appnom = appnom;
	}

	public String getSetdate() {
		return setdate;
	}

	public void setSetdate(String setdate) {
		this.setdate = setdate;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
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

	public String getXq() {
		return xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}

	public String getZw1() {
		return zw1;
	}

	public void setZw1(String zw1) {
		this.zw1 = zw1;
	}

	public String getGgsy() {
		return ggsy;
	}

	public void setGgsy(String ggsy) {
		this.ggsy = ggsy;
	}

	public int getHdzt() {
		return hdzt;
	}

	public void setHdzt(int hdzt) {
		this.hdzt = hdzt;
	}

}
