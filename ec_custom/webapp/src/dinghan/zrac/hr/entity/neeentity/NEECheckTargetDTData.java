package dinghan.zrac.hr.entity.neeentity;

/**
 * 新员工培养目标明细
 * @author zhangxiaoyu/10593 - 2017-10-10
 * 
 */
public class NEECheckTargetDTData {

	protected int id;
	
	/**
	 * 主表ID
	 */
	protected int mainid;
	
	/**
	 * 考核目标
	 */
	protected String checkgoal;
	/**
	 * 考核目标分解工作内容
	 */
	protected String breakdownwork;
	/**
	 * 考核依据
	 */
	protected String checkbasis;
	/**
	 * 考核达成情况
	 */
	protected String checkfinishstate;
	/**
	 * 备注
	 */
	protected String remark;

	public int getId() {
		
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCheckgoal() {
		return checkgoal;
	}

	public void setCheckgoal(String checkgoal) {
		this.checkgoal = checkgoal;
	}

	public String getBreakdownwork() {
		return breakdownwork;
	}

	public void setBreakdownwork(String breakdownwork) {
		this.breakdownwork = breakdownwork;
	}

	public String getCheckbasis() {
		return checkbasis;
	}

	public void setCheckbasis(String checkbasis) {
		this.checkbasis = checkbasis;
	}

	public String getCheckfinishstate() {
		return checkfinishstate;
	}

	public void setCheckfinishstate(String checkfinishstate) {
		this.checkfinishstate = checkfinishstate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getMainid() {
		return mainid;
	}

	public void setMainid(int mainid) {
		this.mainid = mainid;
	}

}
