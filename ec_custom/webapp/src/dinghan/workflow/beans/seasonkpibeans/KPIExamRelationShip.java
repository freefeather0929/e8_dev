package dinghan.workflow.beans.seasonkpibeans;

/**
 * 季度KPI考核关系
 * @author zhangxiaoyu / 10593 - 2017-06-09
 * 
 */
public class KPIExamRelationShip {
	
	private int id;
	
	/**
	 * 被考核人员工的OA用户ID( Configure RelationShip Employee)
	 */
	private int cfgREmpl; 
	
	/**
	 * 考核年份[格式：yyyy]
	 */
	private int cfgYear;
	
	/**
	 * 考核季度 每年一季度 对应数字 0，四季度对应数字3
	 */
	private int season;
	
	/**
	 * 考核责任者的OA用户ID ( Examine Person )
	 */
	private int examPsn;
	
	/**
	 * 考核复核者的OA用户ID ( Review Person )
	 */
	private int reviewPsn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCfgREmpl() {
		return cfgREmpl;
	}

	public void setCfgREmpl(int cfgREmpl) {
		this.cfgREmpl = cfgREmpl;
	}

	public int getCfgYear() {
		return cfgYear;
	}

	public void setCfgYear(int cfgYear) {
		this.cfgYear = cfgYear;
	}

	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public int getExamPsn() {
		return examPsn;
	}

	public void setExamPsn(int examPsn) {
		this.examPsn = examPsn;
	}

	public int getReviewPsn() {
		return reviewPsn;
	}

	public void setReviewPsn(int reviewPsn) {
		this.reviewPsn = reviewPsn;
	}
	
}
