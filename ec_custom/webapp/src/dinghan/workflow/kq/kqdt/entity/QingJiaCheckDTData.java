package dinghan.workflow.kq.kqdt.entity;

/**
 * 请假申请核定明细（明细3）
 * <p>
 * 是由请假申请明细（明细1）在申请人提交流程时通过自动生成核定明细的Action衍生出来的，
 * 核定过程由系统配置的定时核定程序执行，对当前日期之前所有未经过核定的明细表3记录进行核定。
 * 
 * 
 * @author zhangxiaoyu / 10593 - 20170731
 *
 */
public class QingJiaCheckDTData {
	/**
	 * ID 
	 */
	private int id; 
	/**
	 * 请假申请人
	 */
	private int userid; 
	/**
	 * 请假流程主表ID
	 */
	private int mainid; 
	/**
	 * 请假日期
	 */
	private String rq; 
	/**
	 * 请假类别
	 */
	private String qjlb; 
	/**
	 * 开始时间
	 */
	private String kssj; 
	/**
	 * 结束时间
	 */
	private String jssj; 
	/**
	 * 核定状态
	 */
	private int hdzt; 
	/**
	 * 星期
	 */
	private String xq;
	/**
	 * 核定开始时间
	 */
	private String hdkssj; 
	/**
	 * 核定结束时间
	 */
	private String hdjssj; 
	/**
	 * 打卡记录
	 */
	private String dkjl; 
	/**
	 * 流水号
	 */
	private String appnom; 
	/**
	 * 工号
	 */
	private String gh; 
	/**
	 * 核定工时
	 */
	private double hdgs; 
	/**
	 * 节点id
	 */
	private String jdid;
	/**
	 * 标准工作时间
	 */
	private String bzgzsj; 
	/**
	 * 考勤核定月份
	 */
	private String kqhdyf; 
	/**
	 * 流程审批结束日期
	 */
	private String lcspjsrq;
	/**
	 * 考勤员
	 */
	private int kqy; 
	/**
	 * 明细行id
	 */
	private int row_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getMainid() {
		return mainid;
	}
	public void setMainid(int mainid) {
		this.mainid = mainid;
	}
	public String getRq() {
		return rq;
	}
	public void setRq(String rq) {
		this.rq = rq;
	}
	public String getQjlb() {
		return qjlb;
	}
	public void setQjlb(String qjlb) {
		this.qjlb = qjlb;
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
	public int getHdzt() {
		return hdzt;
	}
	public void setHdzt(int hdzt) {
		this.hdzt = hdzt;
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
	public String getDkjl() {
		return dkjl;
	}
	public void setDkjl(String dkjl) {
		this.dkjl = dkjl;
	}
	public String getAppnom() {
		return appnom;
	}
	public void setAppnom(String appnom) {
		this.appnom = appnom;
	}
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public double getHdgs() {
		return hdgs;
	}
	public void setHdgs(double hdgs) {
		this.hdgs = hdgs;
	}
	public String getJdid() {
		return jdid;
	}
	public void setJdid(String jdid) {
		this.jdid = jdid;
	}
	public String getBzgzsj() {
		return bzgzsj;
	}
	public void setBzgzsj(String bzgzsj) {
		this.bzgzsj = bzgzsj;
	}
	public String getKqhdyf() {
		return kqhdyf;
	}
	public void setKqhdyf(String kqhdyf) {
		this.kqhdyf = kqhdyf;
	}
	public String getLcspjsrq() {
		return lcspjsrq;
	}
	public void setLcspjsrq(String lcspjsrq) {
		this.lcspjsrq = lcspjsrq;
	}
	public int getKqy() {
		return kqy;
	}
	public void setKqy(int kqy) {
		this.kqy = kqy;
	}
	public int getRow_id() {
		return row_id;
	}
	public void setRow_id(int row_id) {
		this.row_id = row_id;
	}	
}
