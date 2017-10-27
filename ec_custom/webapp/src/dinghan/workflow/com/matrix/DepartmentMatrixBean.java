package dinghan.workflow.com.matrix;

/**
 * 部门矩阵信息
 * @author zhangxiaoyu / 10593 - 2017-10-21
 *
 */
public class DepartmentMatrixBean {
	
	private int id;
	private int deptsigner; 	//部门权签人
	private int compsigner; 	//公司权签人
	private int ceo; 	//集团总裁
	
	private int exceo; //分公司执行总裁或分公司总裁
	
	private int accountant;  //财务会计
	
	private int secretary;	//部门秘书
	
	private int salesacc;	//销售会计
	
	private int cashier;	//出纳
	
	private int attendofficer; //考勤员
	
	private int mattendofficer; //计量制考勤员
	
	private int subcmpsmgr; //分公司总经理-技术市场部
	
	private int exvicepresident; //主管副总裁
	
	/**
	 * 获取ID
	 * @return
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 获取部门权签人ID
	 * @return
	 */
	public int getDeptsigner() {
		return deptsigner;
	}

	public void setDeptsigner(int deptsigner) {
		this.deptsigner = deptsigner;
	}
	
	/**
	 * 获取公司权签人ID
	 * @return
	 */
	public int getCompsigner() {
		return compsigner;
	}

	public void setCompsigner(int compsigner) {
		this.compsigner = compsigner;
	}
	
	/**
	 * 获取集团总裁ID
	 * @return
	 */
	public int getCeo() {
		return ceo;
	}

	public void setCeo(int ceo) {
		this.ceo = ceo;
	}
	
	/**
	 * 获取执行总裁/分公司总裁ID
	 * @return
	 */
	public int getExceo() {
		return exceo;
	}

	public void setExceo(int exceo) {
		this.exceo = exceo;
	}
	
	/**
	 * 获取财务会计ID
	 * @return
	 */
	public int getAccountant() {
		return accountant;
	}

	public void setAccountant(int accountant) {
		this.accountant = accountant;
	}
	
	/**
	 * 获取部门秘书ID
	 * @return
	 */
	public int getSecretary() {
		return secretary;
	}

	public void setSecretary(int secretary) {
		this.secretary = secretary;
	}

	/**
	 * 获取销售会计ID
	 * @return
	 */
	public int getSalesacc() {
		return salesacc;
	}
	
	public void setSalesacc(int salesacc) {
		this.salesacc = salesacc;
	}

	/**
	 * 获取出纳ID
	 * @return
	 */
	public int getCashier() {
		return cashier;
	}
	
	
	public void setCashier(int cashier) {
		this.cashier = cashier;
	}

	/**
	 * 获取考勤员ID
	 * @return
	 */
	public int getAttendofficer() {
		return attendofficer;
	}

	public void setAttendofficer(int attendofficer) {
		this.attendofficer = attendofficer;
	}
	
	/**
	 * 获取计量值考勤员ID
	 * @return
	 */
	public int getMattendofficer() {
		return mattendofficer;
	}

	public void setMattendofficer(int mattendofficer) {
		this.mattendofficer = mattendofficer;
	}

	/**
	 * 获取分公司总经理ID
	 * @return
	 */
	public int getSubcmpsmgr() {
		return subcmpsmgr;
	}

	public void setSubcmpsmgr(int subcmpsmgr) {
		this.subcmpsmgr = subcmpsmgr;
	}

	/**
	 * 获取主管副总裁ID
	 * @return
	 */
	public int getExvicepresident() {
		return exvicepresident;
	}

	public void setExvicepresident(int exvicepresident) {
		this.exvicepresident = exvicepresident;
	}
	
	
}
