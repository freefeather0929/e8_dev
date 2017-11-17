package dinghan.workflow.beans;

import java.util.ArrayList;

/**
 * 发票台账信息实体类
 * @author zhangxiaoyu / 10593 
 * 
 * 修改：
 * 1. 增加 申请人 邮箱、电话、是否签收标识 属性 -- 2017-10-31 by zhangxiaoyu / 10593
 * 
 */
public class ReceiptAccount
{
  /**
   * 流程requestId
   */
  private int requestId;
  /**
   * 流程流水号
   */
  private String appno;
  /**
   * 申请人
   */
  private int apppsn;
  /**
   * 开票日期
   */
  private String kpDate;
  /**
   * 票据类型
   */
  private int pjType;
  /**
   * 开票人
   */
  private int kppsn;
  /**
   * 回款承诺日期
   */
  private String hkDate;
  /**
   * 合同类型
   */
  private int contraType;
  /**
   * 开具单位
   */
  private String kpUnit;
  /**
   * 开票金额合计
   */
  private double kptotal;
  
  /**
   * 申请人邮箱
   */
  private String apppsnmail;
  
  /**
   * 申请人电话
   */
  private String apppsnmobile;
  
  /**
   * 签收单返回标识
   */
  private int issigned;
  
  /**
   * 开票明细List
   */
  private ArrayList<ReceiptList> receiptList;
  
  public int getRequestId()
  {
    return this.requestId;
  }
  
  public void setRequestId(int requestId)
  {
    this.requestId = requestId;
  }
  
  public String getAppno()
  {
    return this.appno;
  }
  
  public void setAppno(String appno)
  {
    this.appno = appno;
  }
  
  public int getApppsn()
  {
    return this.apppsn;
  }
  
  public void setApppsn(int apppsn)
  {
    this.apppsn = apppsn;
  }
  
  public String getKpDate()
  {
    return this.kpDate;
  }
  
  public void setKpDate(String kpDate)
  {
    this.kpDate = kpDate;
  }
  
  public int getPjType()
  {
    return this.pjType;
  }
  
  public void setPjType(int pjType)
  {
    this.pjType = pjType;
  }
  
  public int getKppsn()
  {
    return this.kppsn;
  }
  
  public void setKppsn(int kppsn)
  {
    this.kppsn = kppsn;
  }
  
  public String getHkDate()
  {
    return this.hkDate;
  }
  
  public void setHkDate(String hkDate)
  {
    this.hkDate = hkDate;
  }
  
  public int getContraType()
  {
    return this.contraType;
  }
  
  public void setContraType(int contraType)
  {
    this.contraType = contraType;
  }
  
  public String getKpUnit()
  {
    return this.kpUnit;
  }
  
  public void setKpUnit(String kpUnit)
   {
     this.kpUnit = kpUnit;
   }
   
   public double getKptotal()
   {
     return this.kptotal;
   }
   
   public void setKptotal(double d)
   {
     this.kptotal = d;
   }
   
   public ArrayList<ReceiptList> getReceiptList()
   {
     return this.receiptList;
   }
   
   public void setReceiptList(ArrayList<ReceiptList> receiptList)
   {
     this.receiptList = receiptList;
   }

	public String getApppsnmail() {
		return apppsnmail;
	}
	
	public void setApppsnmail(String apppsnmail) {
		this.apppsnmail = apppsnmail;
	}
	
	public String getApppsnmobile() {
		return apppsnmobile;
	}
	
	public void setApppsnmobile(String apppsnmobile) {
		this.apppsnmobile = apppsnmobile;
	}

	public int getIssigned() {
		return issigned;
	}

	public void setIssigned(int issigned) {
		this.issigned = issigned;
	}
   
   
 }





