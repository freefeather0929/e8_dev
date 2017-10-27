package dinghan.workflow.kq.kqdt.entity;


/**
 * 考勤汇总持久化类
 * @author zhangxiaoyu / 10593
 *
 */
public class QKMonthCollect {
	
	private int id;
	private String gh; //工号
	private int xm; //姓名
	private String yjbm; //一级部门
	private String ejbm;
	
	private String rzzt; //在职
	private double ycqts; //应出勤天数
	private double jbgs; //加班工时
	private double jbztx; //加班转调休
	
	private double sj; 	//事假
	private double bj;	
	private double nxj;
	private double txj;
	private double hj;
	private double sangj;
	private double cj;  
	private double pcj;	
	private double cjj;	//产检假
	private double lcj;	
	private double jyj;	
	private double jsj;	//计生假
	private double grgs;	
	private double trgs;	
	private double brj;
	private double wdk;	//忘打卡
	private double cd;	//迟到
	private double zt;  //早退
	private double synx; //剩余年休
	private double sytx; //剩余调休
	private String kqrq; //考勤日期
	private int kqlb;  //考勤类别
	private String hzyf; //汇总月份
	private double cdTime; //迟到分钟
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public int getXm() {
		return xm;
	}
	public void setXm(int xm) {
		this.xm = xm;
	}
	public String getYjbm() {
		return yjbm;
	}
	public void setYjbm(String yjbm) {
		this.yjbm = yjbm;
	}
	public String getEjbm() {
		return ejbm;
	}
	public void setEjbm(String ejbm) {
		this.ejbm = ejbm;
	}
	public String getRzzt() {
		return rzzt;
	}
	public void setRzzt(String rzzt) {
		this.rzzt = rzzt;
	}
	public double getYcqts() {
		return ycqts;
	}
	public void setYcqts(double ycqts) {
		this.ycqts = ycqts;
	}
	public double getJbgs() {
		return jbgs;
	}
	public void setJbgs(double jbgs) {
		this.jbgs = jbgs;
	}
	public double getJbztx() {
		return jbztx;
	}
	public void setJbztx(double jbztx) {
		this.jbztx = jbztx;
	}
	public double getSj() {
		return sj;
	}
	public void setSj(double sj) {
		this.sj = sj;
	}
	public double getBj() {
		return bj;
	}
	public void setBj(double bj) {
		this.bj = bj;
	}
	public double getNxj() {
		return nxj;
	}
	public void setNxj(double nxj) {
		this.nxj = nxj;
	}
	public double getTxj() {
		return txj;
	}
	public void setTxj(double txj) {
		this.txj = txj;
	}
	public double getHj() {
		return hj;
	}
	public void setHj(double hj) {
		this.hj = hj;
	}
	public double getSangj() {
		return sangj;
	}
	public void setSangj(double sangj) {
		this.sangj = sangj;
	}
	public double getCj() {
		return cj;
	}
	public void setCj(double cj) {
		this.cj = cj;
	}
	public double getPcj() {
		return pcj;
	}
	public void setPcj(double pcj) {
		this.pcj = pcj;
	}
	public double getCjj() {
		return cjj;
	}
	public void setCjj(double cjj) {
		this.cjj = cjj;
	}
	public double getLcj() {
		return lcj;
	}
	public void setLcj(double lcj) {
		this.lcj = lcj;
	}
	public double getJyj() {
		return jyj;
	}
	public void setJyj(double jyj) {
		this.jyj = jyj;
	}
	public double getJsj() {
		return jsj;
	}
	public void setJsj(double jsj) {
		this.jsj = jsj;
	}
	public double getGrgs() {
		return grgs;
	}
	public void setGrgs(double grgs) {
		this.grgs = grgs;
	}
	public double getTrgs() {
		return trgs;
	}
	public void setTrgs(double trgs) {
		this.trgs = trgs;
	}
	public double getBrj() {
		return brj;
	}
	public void setBrj(double brj) {
		this.brj = brj;
	}
	public double getWdk() {
		return wdk;
	}
	public void setWdk(double wdk) {
		this.wdk = wdk;
	}
	public double getCd() {
		return cd;
	}
	public void setCd(double cd) {
		this.cd = cd;
	}
	public double getZt() {
		return zt;
	}
	public void setZt(double zt) {
		this.zt = zt;
	}
	public double getSynx() {
		return synx;
	}
	public void setSynx(double synx) {
		this.synx = synx;
	}
	public double getSytx() {
		return sytx;
	}
	public void setSytx(double sytx) {
		this.sytx = sytx;
	}
	public String getKqrq() {
		return kqrq;
	}
	public void setKqrq(String kqrq) {
		this.kqrq = kqrq;
	}
	public int getKqlb() {
		return kqlb;
	}
	public void setKqlb(int kqlb) {
		this.kqlb = kqlb;
	}
	public String getHzyf() {
		return hzyf;
	}
	public void setHzyf(String hzyf) {
		this.hzyf = hzyf;
	}
	public double getCdTime() {
		return cdTime;
	}
	public void setCdTime(double cdTime) {
		this.cdTime = cdTime;
	}
	
}
