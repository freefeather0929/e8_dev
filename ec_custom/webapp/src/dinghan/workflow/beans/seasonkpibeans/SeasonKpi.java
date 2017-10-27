package dinghan.workflow.beans.seasonkpibeans;

/**
 * 季度KPI项目
 * @author zhangxiaoyu / 10593
 * 
 */
public class SeasonKpi
{
  private int dtId;		//季度KPI明细表记录ID
  private int mainId;	//季度主表记录ID
  private String kpiTarget;		//kpi目标
  private String standard; 		//kpi评价标准
  private int weight;		//权重
  
  public int getDtId()
  {
    return this.dtId;
  }
  
  public void setDtId(int dtId)
  {
    this.dtId = dtId;
  }
  
  public int getMainId()
  {
    return this.mainId;
  }
  
  public void setMainId(int mainId)
  {
    this.mainId = mainId;
  }
  
  public String getKpitarget()
  {
    return this.kpiTarget;
  }
  
  public void setKpitarget(String kpiTarget)
  {
    this.kpiTarget = kpiTarget;
  }
  
  public String getStandard()
  {
    return this.standard;
  }
  
  public void setStandard(String standard)
  {
    this.standard = standard;
  }
  
  public int getWeight()
  {
    return this.weight;
  }
  
  public void setWeight(int weight)
  {
    this.weight = weight;
  }
  
}
