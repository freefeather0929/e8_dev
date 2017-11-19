package dinghan.workflow.kq.dailydetail.entity;

/**
 * 考勤明细
 * @author  - zhangxiaoyu / 10593 - 2017-11-18
 * 
 */
public class KQDetailData {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 人员工号
	 */
	private String gh;
	/**
	 * 人员ID
	 */
	private int xm;
	/**
	 * 考勤日期
	 */
	private String kqrq;
	/**
	 * 首次打卡时间
	 */
	private String scdksj;
	/**
	 * 末次打卡时间
	 */
	private String mcdksj;
	/**
	 * 迟到分数
	 */
	private double cd;
	/**
	 * 早退分数
	 */
	private double zaot;
	/**
	 * 忘打卡次数
	 */
	private double wdk;
	
	/**
	 * 加班工时
	 */
	private double jbgs;
	
	/**
	 * 加班转调休工时
	 */
	private double jbztx;
	/**
	 * 矿工工时
	 */
	private double kg;
	/**
	 * 事假
	 */
	private double sj;
	/**
	 * 病假
	 */
	private double bj;
	/**
	 * 年休假
	 */
	private double nxj;
	/**
	 * 调休假
	 */
	private double txj;
	/**
	 * 婚嫁
	 */
	private double hj;
	/**
	 * 产假
	 */
	private double cj;
	/**
	 * 陪产假
	 */
	private double pcj;
	/**
	 * 产检假
	 */
	private double cjj;
	/**
	 * 哺乳假
	 */
	private double brj;
	/**
	 * 丧假
	 */
	private double sangj;
	/**
	 * 流产假
	 */
	private double lcj;
	/**
	 * 节育假
	 */
	private double jyj;
	/**
	 * 计生假
	 */
	private double jsj;
	/**
	 * 个人工伤假
	 */
	private double grgs;
	/**
	 * 他人工伤假
	 */
	private double trgs;
	
	/**
	 * 考勤类别
	 */
	private int kqlb_n;
	
	/**
	 * 在职状态
	 */
	private String zt;
	/**
	 * 岗位ID
	 */
	private int gw;
	/**
	 * 一级部门
	 */
	private String yjbm_n;
	/**
	 * 二级部门
	 */
	private String ejbm_n;
	/**
	 * 所属账套ID
	 */
	private int ssgs;
	/**
	 * 建模模块ID
	 */
	private int formmodeid;
	/**
	 * 数据创建人员ID
	 */
	private int modedatacreater;
	/**
	 * 数据创建人员类别 
	 */
	private int modedatacreatertype;
	/**
	 * 数据创建日期
	 */
	private String modedatacreatedate;
	/**
	 * 数据创建时间
	 */
	private String modedatacreatetime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 获取人员工号
	 * @return
	 */
	public String getGh() {
		return gh;
	}
	/**
	 * 设置工号
	 * @param gh
	 */
	public void setGh(String gh) {
		this.gh = gh;
	}
	/**
	 * 获取人员姓名ID
	 * @return
	 */
	public int getXm() {
		return xm;
	}
	/**
	 * 设置工号
	 * @param gh
	 */
	public void setXm(int xm) {
		this.xm = xm;
	}
	/**
	 * 获取考勤日期
	 * @return
	 */
	public String getKqrq() {
		return kqrq;
	}
	/**
	 * 设置工号
	 * @param gh
	 */
	public void setKqrq(String kqrq) {
		this.kqrq = kqrq;
	}
	/**
	 * 获取首次打卡时间
	 * @return
	 */
	public String getScdksj() {
		return scdksj;
	}
	/**
	 * 设置首次打卡时间
	 * @param gh
	 */
	public void setScdksj(String scdksj) {
		this.scdksj = scdksj;
	}
	/**
	 * 获取首次末次时间
	 * @return
	 */
	public String getMcdksj() {
		return mcdksj;
	}
	/**
	 * 设置末次打卡时间
	 * @param gh
	 */
	public void setMcdksj(String mcdksj) {
		this.mcdksj = mcdksj;
	}
	/**
	 * 获取迟到分数
	 * @return
	 */
	public double getCd() {
		return cd;
	}
	/**
	 * 设置工号
	 * @param gh
	 */
	public void setCd(double cd) {
		this.cd = cd;
	}
	/**
	 * 获取早退分数
	 * @return
	 */
	public double getZaot() {
		return zaot;
	}
	/**
	 * 设置早退分数
	 * @param gh
	 */
	public void setZaot(double zaot) {
		this.zaot = zaot;
	}
	/**
	 * 获取忘打卡次数
	 * @return
	 */
	public double getWdk() {
		return wdk;
	}
	/**
	 * 设置忘打卡次数
	 * @param gh
	 */
	public void setWdk(double wdk) {
		this.wdk = wdk;
	}
	/**
	 * 获取加班工时
	 * @return
	 */
	public double getJbgs() {
		return jbgs;
	}
	/**
	 * 设置加班工时
	 * @param gh
	 */
	public void setJbgs(double jbgs) {
		this.jbgs = jbgs;
	}
	/**
	 * 获取加班转调休工时
	 * @return
	 */
	public double getJbztx() {
		return jbztx;
	}
	/**
	 * 设置加班转调休工时
	 * @param gh
	 */
	public void setJbztx(double jbztx) {
		this.jbztx = jbztx;
	}
	/**
	 * 获取旷工工时
	 * @return
	 */
	public double getKg() {
		return kg;
	}
	/**
	 * 设置旷工工时
	 * @param gh
	 */
	public void setKg(double kg) {
		this.kg = kg;
	}
	/**
	 * 获取事假工时
	 * @return
	 */
	public double getSj() {
		return sj;
	}
	/**
	 * 设置事假工时
	 * @param sj
	 */
	public void setSj(double sj) {
		this.sj = sj;
	}
	/**
	 * 获取病假工时
	 * @return
	 */
	public double getBj() {
		return bj;
	}
	/**
	 * 设置病假工时
	 * @param bj
	 */
	public void setBj(double bj) {
		this.bj = bj;
	}
	/**
	 * 获取年休假工时
	 * @return
	 */
	public double getNxj() {
		return nxj;
	}
	/**
	 * 设置年休假工时
	 * @param nxj
	 */
	public void setNxj(double nxj) {
		this.nxj = nxj;
	}
	/**
	 * 获取调休假工时
	 * @return
	 */
	public double getTxj() {
		return txj;
	}
	/**
	 * 设置调休假工时
	 * @param txj
	 */
	public void setTxj(double txj) {
		this.txj = txj;
	}
	/**
	 * 获取婚假工时
	 * @return
	 */
	public double getHj() {
		return hj;
	}
	/**
	 * 设置婚嫁工时
	 * @param hj
	 */
	public void setHj(double hj) {
		this.hj = hj;
	}
	/**
	 * 获取产假工时
	 * @return
	 */
	public double getCj() {
		return cj;
	}
	/**
	 * 设置产假工时
	 * @param cj
	 */
	public void setCj(double cj) {
		this.cj = cj;
	}
	/**
	 * 获取陪产假工时
	 * @return
	 */
	public double getPcj() {
		return pcj;
	}
	/**
	 * 设置陪产假工时
	 * @param pcj
	 */
	public void setPcj(double pcj) {
		this.pcj = pcj;
	}
	
	/**
	 * 获取产检假工时
	 * @return
	 */
	public double getCjj() {
		return cjj;
	}
	/**
	 * 设置产检假工时
	 * @param cjj
	 */
	public void setCjj(double cjj) {
		this.cjj = cjj;
	}
	/**
	 * 获取哺乳假工时
	 * @return
	 */
	public double getBrj() {
		return brj;
	}
	
	/**
	 * 设置哺乳假工时
	 * @param brj
	 */
	public void setBrj(double brj) {
		this.brj = brj;
	}
	
	/**
	 * 获取丧假工时
	 * @return
	 */
	public double getSangj() {
		return sangj;
	}
	/**
	 * 设置丧假工时
	 * @param sangj
	 */
	public void setSangj(double sangj) {
		this.sangj = sangj;
	}
	
	/**
	 * 获取流产假工时
	 * @return
	 */
	public double getLcj() {
		return lcj;
	}
	/**
	 * 设置流产假工时
	 * @param lcj
	 */
	public void setLcj(double lcj) {
		this.lcj = lcj;
	}
	/**
	 * 获取节育假工时
	 * @return
	 */
	public double getJyj() {
		return jyj;
	}
	
	/**
	 * 设置节育假工时
	 * @param jyj
	 */
	public void setJyj(double jyj) {
		this.jyj = jyj;
	}
	/**
	 * 获取计生假工时
	 * @return
	 */
	public double getJsj() {
		return jsj;
	}
	
	/**
	 * 设置计生假工时
	 * @param jsj
	 */
	public void setJsj(double jsj) {
		this.jsj = jsj;
	}
	
	/**
	 * 获取个人工伤假工时
	 * @return
	 */
	public double getGrgs() {
		return grgs;
	}
	
	/**
	 * 设置个人工伤假工时
	 * @param grgs
	 */
	public void setGrgs(double grgs) {
		this.grgs = grgs;
	}
	/**
	 * 获取他人工伤假工时
	 * @return
	 */
	public double getTrgs() {
		return trgs;
	}
	
	/**
	 * 设置他人工伤假工时
	 * @param trgs
	 */
	public void setTrgs(double trgs) {
		this.trgs = trgs;
	}
	
	/**
	 * 获取考勤类别
	 * @return
	 */
	public int getKqlb_n() {
		return kqlb_n;
	}
	
	/**
	 * 设置考勤类别
	 * @param kqlb_n
	 */
	public void setKqlb_n(int kqlb_n) {
		this.kqlb_n = kqlb_n;
	}
	
	/**
	 * 获取在职状态
	 * @return
	 */
	public String getZt() {
		return zt;
	}
	
	/**
	 * 设置在职状态
	 * @param zt
	 */
	public void setZt(String zt) {
		this.zt = zt;
	}
	
	/**
	 * 获取岗位名称
	 * @return
	 */
	public int getGw() {
		return gw;
	}
	/**
	 * 设置岗位名称
	 * @param gw
	 */
	public void setGw(int gw) {
		this.gw = gw;
	}
	
	/**
	 * 获取一级部门
	 * @return
	 */
	public String getYjbm_n() {
		return yjbm_n;
	}
	/**
	 * 设置一级部门
	 * @param yjbm_n
	 */
	public void setYjbm_n(String yjbm_n) {
		this.yjbm_n = yjbm_n;
	}
	
	/**
	 * 获取二级部门
	 * @return
	 */
	public String getEjbm_n() {
		return ejbm_n;
	}
	/**
	 * 设置二级部门
	 * @param ejbm_n
	 */
	public void setEjbm_n(String ejbm_n) {
		this.ejbm_n = ejbm_n;
	}
	
	/**
	 * 获取所属账套
	 * @return
	 */
	public int getSsgs() {
		return ssgs;
	}
	/**
	 * 设置所属账套
	 * @param sgs
	 */
	public void setSsgs(int ssgs) {
		this.ssgs = ssgs;
	}
	public int getFormmodeid() {
		return formmodeid;
	}
	
	public void setFormmodeid(int formmodeid) {
		this.formmodeid = formmodeid;
	}
	public int getModedatacreater() {
		return modedatacreater;
	}
	public void setModedatacreater(int modedatacreater) {
		this.modedatacreater = modedatacreater;
	}
	public int getModedatacreatertype() {
		return modedatacreatertype;
	}
	public void setModedatacreatertype(int modedatacreatertype) {
		this.modedatacreatertype = modedatacreatertype;
	}
	public String getModedatacreatedate() {
		return modedatacreatedate;
	}
	public void setModedatacreatedate(String modedatacreatedate) {
		this.modedatacreatedate = modedatacreatedate;
	}
	public String getModedatacreatetime() {
		return modedatacreatetime;
	}
	public void setModedatacreatetime(String modedatacreatetime) {
		this.modedatacreatetime = modedatacreatetime;
	}
	
	
}
