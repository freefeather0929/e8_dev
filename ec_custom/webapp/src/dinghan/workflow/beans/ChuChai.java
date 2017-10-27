package dinghan.workflow.beans;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;

public class ChuChai {
	private static Log log = LogFactory.getLog(ChuChai.class.getName());
	
	private int id; // id
	private int requestId; // 请求id
	private int zjzg; // 直接主管
	private int proposer; // 申请人
	private int sjzg; // 上级主管
	private int stdept1; // 一级部门
	private int stdept2; // 二级部门
	private int stdept3; // 三级部门
	private String appnom; // 流水号1
	private String setdate; // 申请日期
	private String yjccsj; // 预计出差开始日期
	private String ccsj2; // 出差结束日期
	private String ccdd; // 出差地点
	private String txry; // 同行人员
	private String lxdh; // 联系电话
	private String ccsy; // 出差事由
	private String jhxcap; // 计划行程安排
	private String lsh2; // 流水号2
	private String sjkssj; // 实际开始日期
	private String sjjssj; // 实际结束日期
	private String jtxcap; // 具体行程安排
	private String bz; // 备 注
	private String fj; // 附 件
	private String ccsj1; // 预计出差开始时间
	private String ccsj3; // 预计出差结束时间
	private String kssj1; // 实际开始时间
	private String kssj3; // 实际结束时间
	private double ztfy; // 在途费用
	private double zsf; // 住宿费
	private double snjtf; // 市内交通费
	private double gwzf; // 公务杂费
	private double zdcf; // 招待餐费
	private double zdlp; // 招待礼品
	private double hjy; // 合计(元)
	private double zbqnzts; // 产品维护占用总天数（质保期内）
	private double fy1; // 费用（在途+住宿+市内交通）
	private double zbqwzts; // 产品维护占用总天数（质保期外）
	private double fy2; // 费用（在途+住宿+市内交通）
	private double fwxsyz; // 服务销售占用总天数
	private double fy3; // 费用（在途+住宿+市内交通）
	private double gcjfzy; // 工程交付占用总天数：
	private double fy4; // 费用（在途+住宿+市内交通）
	private double qtfy; // 其他费用
	private double yjccts; // 预计出差天数

	public ChuChai() {
		
	}

	/**
	 * 功能：根据requestid得到主表信息
	 * 
	 */
	public ChuChai(String requestid) {
		
		String sql = "select * from formtable_main_92 where requestid= " + requestid;
		try {
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			if (rs.next()) {
				this.appnom = rs.getString("appnom");
				this.bz = rs.getString("bz");
				this.ccdd = rs.getString("ccdd");
				this.ccsj1 = rs.getString("ccsj1");
				this.ccsj2 = rs.getString("ccsj2");
				this.ccsj3 = rs.getString("ccsj3");
				this.ccsy = rs.getString("ccsy");
				this.fj = rs.getString("fj");
				this.fwxsyz = rs.getDouble("fwxsyz");
				this.fy1 = rs.getDouble("fy1");
				this.fy2 = rs.getDouble("fy2");
				this.fy3 = rs.getDouble("fy3");
				this.fy4 = rs.getDouble("fy4");
				this.gcjfzy = rs.getDouble("gcjfzy");
				this.gwzf = rs.getDouble("gwzf");
				this.hjy = rs.getDouble("hjy");
				this.id = rs.getInt("id");
				this.jhxcap = rs.getString("jhxcap");
				this.jtxcap = rs.getString("jtxcap");
				this.kssj1 = rs.getString("kssj1");
				this.kssj3 = rs.getString("kssj3");
				this.lsh2 = rs.getString("lsh2");
				this.lxdh = rs.getString("lxdh");
				this.proposer = rs.getInt("proposer");
				this.qtfy = rs.getDouble("qtfy");
				this.requestId = rs.getInt("requestId");
				this.setdate = rs.getString("setdate");
				this.sjjssj = rs.getString("sjjssj");
				this.sjkssj = rs.getString("sjkssj");
				this.sjzg = rs.getInt("sjzg");
				this.snjtf = rs.getDouble("snjtf");
				this.stdept1 = rs.getInt("stdept1");
				this.stdept2 = rs.getInt("stdept2");
				this.stdept3 = rs.getInt("stdept3");
				this.txry = rs.getString("txry");
				this.yjccsj = rs.getString("yjccsj");
				this.yjccts = rs.getDouble("yjccts");
				this.zbqnzts = rs.getDouble("zbqnzts");
				this.zbqwzts = rs.getDouble("zbqwzts");
				this.zdcf = rs.getDouble("zdcf");
				this.zdlp = rs.getDouble("zdlp");
				this.zjzg = rs.getInt("zjzg");
				this.zsf = rs.getDouble("zsf");
				this.ztfy = rs.getDouble("ztfy");
			}
		} catch (Exception e) {
			log.error("---- 根据requestid得到主表信息错误 ----");
			log.error(e.getStackTrace());
			log.error("--------");
			e.printStackTrace();
			
		}
	}

	/**
	 * 功能：根据requestid得到主表信息
	 */
	public ChuChai(int mainid) {
		
		String sql = "select * from formtable_main_92 where id=" + mainid;
		try {
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			if (rs.next()) {
				this.requestId = rs.getInt("requestId");
				this.proposer = rs.getInt("proposer");
				this.appnom = rs.getString("appnom");
				this.lsh2 = rs.getString("lsh2");
			}
		} catch (Exception e) {
			log.error("---- 获取出差主表信息 ----");
			log.error(e.getStackTrace());
			log.error("--------");
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能：根据用户id，开始日期，结束日期 得到出差明细列表
	 * 
	 */
	public static ArrayList<ChuChai> queryList(String userid, String ksrq, String jsrq, String requestid){
		ArrayList<ChuChai> ccList = new ArrayList<ChuChai>();
		try {
			String sql = "select * from formtable_main_92 where (proposer='";
			sql += userid + "' AND yjccsj BETWEEN '" + ksrq + "' and '";
			sql += jsrq + "' or  proposer='" + userid;
			sql += "' AND ccsj2 BETWEEN '" + ksrq + "' and '" + jsrq + "'";
			sql += " or proposer='" + userid + "' AND yjccsj<='" + ksrq;
			sql += "' AND ccsj2>='" + jsrq + "') and appnom!='' and requestId!='" + requestid + "'";
			
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			while (rs.next()) {
				ChuChai occ = new ChuChai();
				occ.setYjccsj(rs.getString("yjccsj"));// 开始日期
				occ.setCcsj2(rs.getString("ccsj2"));// 结束日期
				occ.setCcsj1(rs.getString("ccsj1"));// 开始时间
				occ.setCcsj3(rs.getString("ccsj3"));// 结束时间

				occ.setSjkssj(rs.getString("sjkssj"));// 实际开始日期
				occ.setSjjssj(rs.getString("sjjssj"));// 实际结束日期
				occ.setKssj1(rs.getString("kssj1"));// 实际开始时间
				occ.setKssj3(rs.getString("kssj3"));// 实际结束时间
				ccList.add(occ);
			}
		} catch (Exception e) {
			log.error("---- 根据用户id，开始日期，结束日期 得到出差明细列表错误  ----");
			log.error(e.getStackTrace());
			log.error("--------");
			e.printStackTrace();
		}
		return ccList;
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

	public int getZjzg() {
		return zjzg;
	}

	public void setZjzg(int zjzg) {
		this.zjzg = zjzg;
	}

	public int getProposer() {
		return proposer;
	}

	public void setProposer(int proposer) {
		this.proposer = proposer;
	}

	public int getSjzg() {
		return sjzg;
	}

	public void setSjzg(int sjzg) {
		this.sjzg = sjzg;
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

	public String getYjccsj() {
		return yjccsj;
	}

	public void setYjccsj(String yjccsj) {
		this.yjccsj = yjccsj;
	}

	public String getCcsj2() {
		return ccsj2;
	}

	public void setCcsj2(String ccsj2) {
		this.ccsj2 = ccsj2;
	}

	public String getCcdd() {
		return ccdd;
	}

	public void setCcdd(String ccdd) {
		this.ccdd = ccdd;
	}

	public String getTxry() {
		return txry;
	}

	public void setTxry(String txry) {
		this.txry = txry;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getCcsy() {
		return ccsy;
	}

	public void setCcsy(String ccsy) {
		this.ccsy = ccsy;
	}

	public String getJhxcap() {
		return jhxcap;
	}

	public void setJhxcap(String jhxcap) {
		this.jhxcap = jhxcap;
	}

	public String getLsh2() {
		return lsh2;
	}

	public void setLsh2(String lsh2) {
		this.lsh2 = lsh2;
	}

	public String getSjkssj() {
		return sjkssj;
	}

	public void setSjkssj(String sjkssj) {
		this.sjkssj = sjkssj;
	}

	public String getSjjssj() {
		return sjjssj;
	}

	public void setSjjssj(String sjjssj) {
		this.sjjssj = sjjssj;
	}

	public String getJtxcap() {
		return jtxcap;
	}

	public void setJtxcap(String jtxcap) {
		this.jtxcap = jtxcap;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getFj() {
		return fj;
	}

	public void setFj(String fj) {
		this.fj = fj;
	}

	public String getCcsj1() {
		return ccsj1;
	}

	public void setCcsj1(String ccsj1) {
		this.ccsj1 = ccsj1;
	}

	public String getCcsj3() {
		return ccsj3;
	}

	public void setCcsj3(String ccsj3) {
		this.ccsj3 = ccsj3;
	}

	public String getKssj1() {
		return kssj1;
	}

	public void setKssj1(String kssj1) {
		this.kssj1 = kssj1;
	}

	public String getKssj3() {
		return kssj3;
	}

	public void setKssj3(String kssj3) {
		this.kssj3 = kssj3;
	}

	public double getZtfy() {
		return ztfy;
	}

	public void setZtfy(double ztfy) {
		this.ztfy = ztfy;
	}

	public double getZsf() {
		return zsf;
	}

	public void setZsf(double zsf) {
		this.zsf = zsf;
	}

	public double getSnjtf() {
		return snjtf;
	}

	public void setSnjtf(double snjtf) {
		this.snjtf = snjtf;
	}

	public double getGwzf() {
		return gwzf;
	}

	public void setGwzf(double gwzf) {
		this.gwzf = gwzf;
	}

	public double getZdcf() {
		return zdcf;
	}

	public void setZdcf(double zdcf) {
		this.zdcf = zdcf;
	}

	public double getZdlp() {
		return zdlp;
	}

	public void setZdlp(double zdlp) {
		this.zdlp = zdlp;
	}

	public double getHjy() {
		return hjy;
	}

	public void setHjy(double hjy) {
		this.hjy = hjy;
	}

	public double getZbqnzts() {
		return zbqnzts;
	}

	public void setZbqnzts(double zbqnzts) {
		this.zbqnzts = zbqnzts;
	}

	public double getFy1() {
		return fy1;
	}

	public void setFy1(double fy1) {
		this.fy1 = fy1;
	}

	public double getZbqwzts() {
		return zbqwzts;
	}

	public void setZbqwzts(double zbqwzts) {
		this.zbqwzts = zbqwzts;
	}

	public double getFy2() {
		return fy2;
	}

	public void setFy2(double fy2) {
		this.fy2 = fy2;
	}

	public double getFwxsyz() {
		return fwxsyz;
	}

	public void setFwxsyz(double fwxsyz) {
		this.fwxsyz = fwxsyz;
	}

	public double getFy3() {
		return fy3;
	}

	public void setFy3(double fy3) {
		this.fy3 = fy3;
	}

	public double getGcjfzy() {
		return gcjfzy;
	}

	public void setGcjfzy(double gcjfzy) {
		this.gcjfzy = gcjfzy;
	}

	public double getFy4() {
		return fy4;
	}

	public void setFy4(double fy4) {
		this.fy4 = fy4;
	}

	public double getQtfy() {
		return qtfy;
	}

	public void setQtfy(double qtfy) {
		this.qtfy = qtfy;
	}

	public double getYjccts() {
		return yjccts;
	}

	public void setYjccts(double yjccts) {
		this.yjccts = yjccts;
	}

}
