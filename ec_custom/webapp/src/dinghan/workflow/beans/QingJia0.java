package dinghan.workflow.beans;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;

/**
 * 请假主表实体类
 * @author sunxiaoguang 
 * 
 */
public class QingJia0 {
	private Log log = LogFactory.getLog(QingJia0.class.getName());

	private int id; //主表id
	private int requestId; //请求id
	private String appnom; //流水号
	private int proposer; //申请人
	private String setdate; //申请日期
	private int stdept1; //一级部门
	private int stdept2; //二级部门
	private int bzgzsj; //标准工作时间
	private String lxdh; //联系电话
	private int zw; //职务
	private String yjqjts; //预计请假天数
	private int zjzg; //直接主管
	private int sjzg; //上级主管
	private int zgfz; //主管副总
	private int rlzyzj; //人力资源总监
	private String hdyj; //核定意见
	private String gh; //工号
	
	private List<QingJia> qingjia_dt3_List;
	private List<QingJia1> qingjia_dt1_List;
	
	public QingJia0() {
		
	}

	//通过requestid得到主表信息
	public QingJia0(String requestid) {
		try {
			String sql = "select * from formtable_main_89 where requestid = " + requestid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			if (rs.next()) {
				this.appnom = rs.getString("appnom");
				this.bzgzsj = rs.getInt("bzgzsj");
				this.gh = rs.getString("gh");
				this.hdyj = rs.getString("hdyj");
				this.id = rs.getInt("id");
				this.lxdh = rs.getString("lxdh");
				this.proposer = rs.getInt("proposer");
				this.requestId = rs.getInt("requestId");
				this.rlzyzj = rs.getInt("rlzyzj");
				this.setdate = rs.getString("setdate");
				this.sjzg = rs.getInt("sjzg");
				this.stdept1 = rs.getInt("stdept1");
				this.stdept2 = rs.getInt("stdept2");
				this.yjqjts = rs.getString("yjqjts");
				this.zgfz = rs.getInt("zgfz");
				this.zjzg = rs.getInt("zjzg");
				this.zw = rs.getInt("zw");
			}
		} catch (Exception e) {
			log.error("requestid取主表信息：" + e);
		}
	}

	/**
	 * 构造方法，通过主表ID获取主表对象
	 * @param mainid
	 */
	public QingJia0(int mainid) {
		try {
			String sql = "select * from formtable_main_89 where id= " + mainid;
			RecordSet rs = new RecordSet();
			rs.executeSql(sql);
			if (rs.next()) {
				this.appnom = rs.getString("appnom");	//申请单号
				this.bzgzsj = rs.getInt("bzgzsj");	//标准工作时间
				this.gh = rs.getString("gh");	//工号
				this.hdyj = rs.getString("hdyj");	//核定意见
				this.id = rs.getInt("id");
				this.lxdh = rs.getString("lxdh");
				this.proposer = rs.getInt("proposer");
				this.requestId = rs.getInt("requestId");
				this.rlzyzj = rs.getInt("rlzyzj");
				this.setdate = rs.getString("setdate");
				this.sjzg = rs.getInt("sjzg");
				this.stdept1 = rs.getInt("stdept1");
				this.stdept2 = rs.getInt("stdept2");
				this.yjqjts = rs.getString("yjqjts");
				this.zgfz = rs.getInt("zgfz");
				this.zjzg = rs.getInt("zjzg");	
				this.zw = rs.getInt("zw");	//职务
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

	public String getAppnom() {
		return appnom;
	}

	public void setAppnom(String appnom) {
		this.appnom = appnom;
	}

	public int getProposer() {
		return proposer;
	}

	public void setProposer(int proposer) {
		this.proposer = proposer;
	}

	public String getSetdate() {
		return setdate;
	}

	public void setSetdate(String setdate) {
		this.setdate = setdate;
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

	public int getBzgzsj() {
		return bzgzsj;
	}

	public void setBzgzsj(int bzgzsj) {
		this.bzgzsj = bzgzsj;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public int getZw() {
		return zw;
	}

	public void setZw(int zw) {
		this.zw = zw;
	}

	public String getYjqjts() {
		return yjqjts;
	}

	public void setYjqjts(String yjqjts) {
		this.yjqjts = yjqjts;
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

	public int getZgfz() {
		return zgfz;
	}

	public void setZgfz(int zgfz) {
		this.zgfz = zgfz;
	}

	public int getRlzyzj() {
		return rlzyzj;
	}

	public void setRlzyzj(int rlzyzj) {
		this.rlzyzj = rlzyzj;
	}

	public String getHdyj() {
		return hdyj;
	}

	public void setHdyj(String hdyj) {
		this.hdyj = hdyj;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}
}
