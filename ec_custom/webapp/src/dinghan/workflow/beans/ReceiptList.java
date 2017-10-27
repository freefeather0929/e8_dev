package dinghan.workflow.beans;

/**
 * 发票明细
 * @author zhangxiaoyu / 10593 
 *
 */
public class ReceiptList
{
  private int mainId;
  private String contraNo;	//合同号
  private double contraMoney;	//合同金额
  private String code;	//发票编号
  private double money;		//发票金额
  private int statue;	//票据状态   0 -- 有效 :: 1 -- 无效
  private String mark;	
  
  public int getMainId()
  {
    return this.mainId;
  }
  
  public void setMainId(int mainId)
  {
    this.mainId = mainId;
  }
  
  public String getContraNo()
  {
    return this.contraNo;
  }
  
  public void setContraNo(String contraNo)
  {
    this.contraNo = contraNo;
  }
  
  public double getContraMoney()
  {
    return this.contraMoney;
  }
  
  public void setContraMoney(double contraMoney)
  {
    this.contraMoney = contraMoney;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public double getMoney()
  {
    return this.money;
  }
  
  public void setMoney(double money)
  {
    this.money = money;
  }
  
  public int getStatue()
  {
    return this.statue;
  }
  
  public void setStatue(int i)
  {
    this.statue = i;
  }
  
  public String getMark()
  {
    return this.mark;
  }
  
  public void setMark(String mark)
  {
    this.mark = mark;
  }
}
