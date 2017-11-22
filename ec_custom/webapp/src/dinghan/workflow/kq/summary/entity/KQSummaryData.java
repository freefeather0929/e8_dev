package dinghan.workflow.kq.summary.entity;
/**
 * 
 * 考勤汇总数据
 * @author zhangxiaoyu / 10593 - 2017-11-21
 * 
 */
public class KQSummaryData {
	/**
	 * ID
	 */
	private int id; 
	/**
	 * 
	 */
	//private int requestId; 
	/**
	 * 工号
	 */
	private String gh; 
	/**
	 * 员工用户ID
	 */
	private int xm; 
	/**
	 * 一级部门
	 */
	private String yjbm; 
	/**
	 * 二级部门
	 */
	private String ejbm; 
	/**
	 * 任职状态
	 */
	private String rzzt;
	/**
	 * 应出勤天数
	 */
	private double ycqts; 
	/**
	 * 加班工时
	 */
	private double jbgs; 
	/**
	 * 加班转调休
	 */
	private double jbztx; 
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
	 * 婚假
	 */
	private double hj; 
	/**
	 * 丧假
	 */
	private double sangj; 
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
	 * 哺乳假
	 */
	private double brj; 
	/**
	 * 旷工工时
	 */
	private double kg; 
	/**
	 * 忘打卡次数
	 */
	private double wdk; 
	/**
	 * 迟到次数
	 */
	private double cd; 
	/**
	 * 早退次数
	 */
	private double zt; 
	/**
	 * 剩余年休假工时
	 */
	private double synx;
	/**
	 * 剩余调休假工时
	 */
	private double sytx; 
	/**
	 * 考勤日期 ：创建考勤汇总的日期
	 */
	private String kqrq; 
	/**
	 * 考勤类别
	 */
	private int kqlb; 
	/**
	 * 汇总月份
	 */
	private String hzyf;
	/**
	 * 迟到总分钟数
	 */
	private double cdTime; 
	/**
	 * 早退总分钟数
	 */
	private double ztTime; 
	/**
	 * 表单建模模块ID
	 */
	private int formmodeid; 
	/**
	 * 表单建模数据创建者用户ID
	 */
	private int modedatacreater;
	/**
	 * 表单建模数据创建者用户类别
	 */
	private int modedatacreatertype; 
	/**
	 * 表单建模数据创建日期
	 */
	private String modedatacreatedate; 
	/**
	 * 表单建模数据创建时间
	 */
	private String modedatacreatetime; 
	/**
	 * 所属账套
	 */
	private int ssgs; 
	/**
	 * 岗位ID
	 */
	private int gw; 
	/**
	 * 婚检假
	 */
	private double hjj;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 获取工号
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
	 * 获取员工用户ID
	 * @return
	 */
	public int getXm() {
		return xm;
	}
	/**
	 * 设置员工用户ID
	 * @param xm
	 */
	public void setXm(int xm) {
		this.xm = xm;
	}
	/**
	 * 获取一级部门
	 * @return
	 */
	public String getYjbm() {
		return yjbm;
	}
	/**
	 * 设置一级部门
	 * @param yjbm
	 */
	public void setYjbm(String yjbm) {
		this.yjbm = yjbm;
	}
	/**
	 * 获取二级部门
	 * @return
	 */
	public String getEjbm() {
		return ejbm;
	}
	/**
	 * 设置二级部门
	 * @param ejbm
	 */
	public void setEjbm(String ejbm) {
		this.ejbm = ejbm;
	}
	/**
	 * 获取任职状态
	 * @return
	 */
	public String getRzzt() {
		return rzzt;
	}
	/**
	 * 设置任职状态
	 * @param rzzt
	 */
	public void setRzzt(String rzzt) {
		this.rzzt = rzzt;
	}
	/**
	 * 获取应出勤天数
	 * @return
	 */
	public double getYcqts() {
		return ycqts;
	}
	/**
	 * 设置应出勤天数
	 * @param ycqts
	 */
	public void setYcqts(double ycqts) {
		this.ycqts = ycqts;
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
	 * @param jbgs
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
	 * @param jbztx
	 */
	public void setJbztx(double jbztx) {
		this.jbztx = jbztx;
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
	 * 获取婚嫁工时
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
	 * 获取旷工工时
	 * @return
	 */
	public double getKg() {
		return kg;
	}
	/**
	 * 设置旷工工时
	 * @param kg
	 */
	public void setKg(double kg) {
		this.kg = kg;
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
	 * @param wdk
	 */
	public void setWdk(double wdk) {
		this.wdk = wdk;
	}
	/**
	 * 获取迟到次数
	 * @return
	 */
	public double getCd() {
		return cd;
	}
	/**
	 * 设置迟到次数
	 * @param cd
	 */
	public void setCd(double cd) {
		this.cd = cd;
	}
	/**
	 * 获取早退次数
	 * @return
	 */
	public double getZt() {
		return zt;
	}
	/**
	 * 设置早退次数
	 * @param zt
	 */
	public void setZt(double zt) {
		this.zt = zt;
	}
	/**
	 * 获取剩余年休假工时
	 * @return
	 */
	public double getSynx() {
		return synx;
	}
	/**
	 * 设置剩余年休假工时
	 * @param synx
	 */
	public void setSynx(double synx) {
		this.synx = synx;
	}
	/**
	 * 获取剩余调休假工时
	 * @return
	 */
	public double getSytx() {
		return sytx;
	}
	/**
	 * 设置剩余调休假工时
	 * @param sytx
	 */
	public void setSytx(double sytx) {
		this.sytx = sytx;
	}
	/**
	 * 获取考勤日期 - 创建汇总日期
	 * @return
	 */
	public String getKqrq() {
		return kqrq;
	}
	/**
	 * 设置考勤日期 - 创建汇总日期
	 * @param kqrq
	 */
	public void setKqrq(String kqrq) {
		this.kqrq = kqrq;
	}
	/**
	 * 获取考勤类别
	 * @return
	 */
	public int getKqlb() {
		return kqlb;
	}
	/**
	 * 设置考勤类别
	 * @param kqlb
	 */
	public void setKqlb(int kqlb) {
		this.kqlb = kqlb;
	}
	/**
	 * 获取汇总月份
	 * @return
	 */
	public String getHzyf() {
		return hzyf;
	}
	/**
	 * 设置汇总月份
	 * @param hzyf
	 */
	public void setHzyf(String hzyf) {
		this.hzyf = hzyf;
	}
	/**
	 * 获取总迟到分钟数
	 * @return
	 */
	public double getCdTime() {
		return cdTime;
	}
	/**
	 * 设置总迟到分钟数
	 * @param cdTime
	 */
	public void setCdTime(double cdTime) {
		this.cdTime = cdTime;
	}
	/**
	 * 获取总早退分钟数
	 * @return
	 */
	public double getZtTime() {
		return ztTime;
	}
	/**
	 * 设置总早退分钟数
	 * @param ztTime
	 */
	public void setZtTime(double ztTime) {
		this.ztTime = ztTime;
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
	/**
	 * 获取所属账套ID
	 * @return
	 */
	public int getSsgs() {
		return ssgs;
	}
	/**
	 * 设置所属账套ID
	 * @param ssgs
	 */
	public void setSsgs(int ssgs) {
		this.ssgs = ssgs;
	}
	/**
	 * 获取岗位ID
	 * @return
	 */
	public int getGw() {
		return gw;
	}
	/**
	 * 设置岗位ID
	 * @param gw
	 */
	public void setGw(int gw) {
		this.gw = gw;
	}
	/**
	 * 获取婚检假工时
	 * @return
	 */
	public double getHjj() {
		return hjj;
	}
	/**
	 * 设置婚检假工时
	 * @param hjj
	 */
	public void setHjj(double hjj) {
		this.hjj = hjj;
	}
	
	
}
