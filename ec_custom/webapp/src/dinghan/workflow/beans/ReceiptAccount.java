/*   1:    */ package dinghan.workflow.beans;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ 
/*   5:    */ public class ReceiptAccount
/*   6:    */ {
/*   7:    */   private int requestId;
/*   8:    */   private String appno;
/*   9:    */   private int apppsn;
/*  10:    */   private String kpDate;
/*  11:    */   private int pjType;
/*  12:    */   private int kppsn;
/*  13:    */   private String hkDate;
/*  14:    */   private int contraType;
/*  15:    */   private String kpUnit;
/*  16:    */   private double kptotal;
/*  17:    */   private ArrayList<ReceiptList> receiptList;
/*  18:    */   
/*  19:    */   public int getRequestId()
/*  20:    */   {
/*  21: 32 */     return this.requestId;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setRequestId(int requestId)
/*  25:    */   {
/*  26: 36 */     this.requestId = requestId;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public String getAppno()
/*  30:    */   {
/*  31: 40 */     return this.appno;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setAppno(String appno)
/*  35:    */   {
/*  36: 44 */     this.appno = appno;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int getApppsn()
/*  40:    */   {
/*  41: 48 */     return this.apppsn;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setApppsn(int apppsn)
/*  45:    */   {
/*  46: 52 */     this.apppsn = apppsn;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getKpDate()
/*  50:    */   {
/*  51: 56 */     return this.kpDate;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setKpDate(String kpDate)
/*  55:    */   {
/*  56: 60 */     this.kpDate = kpDate;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getPjType()
/*  60:    */   {
/*  61: 64 */     return this.pjType;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setPjType(int pjType)
/*  65:    */   {
/*  66: 68 */     this.pjType = pjType;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getKppsn()
/*  70:    */   {
/*  71: 72 */     return this.kppsn;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setKppsn(int kppsn)
/*  75:    */   {
/*  76: 76 */     this.kppsn = kppsn;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public String getHkDate()
/*  80:    */   {
/*  81: 80 */     return this.hkDate;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setHkDate(String hkDate)
/*  85:    */   {
/*  86: 84 */     this.hkDate = hkDate;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getContraType()
/*  90:    */   {
/*  91: 88 */     return this.contraType;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setContraType(int contraType)
/*  95:    */   {
/*  96: 92 */     this.contraType = contraType;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getKpUnit()
/* 100:    */   {
/* 101: 96 */     return this.kpUnit;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setKpUnit(String kpUnit)
/* 105:    */   {
/* 106:100 */     this.kpUnit = kpUnit;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public double getKptotal()
/* 110:    */   {
/* 111:104 */     return this.kptotal;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setKptotal(double d)
/* 115:    */   {
/* 116:108 */     this.kptotal = d;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public ArrayList<ReceiptList> getReceiptList()
/* 120:    */   {
/* 121:112 */     return this.receiptList;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setReceiptList(ArrayList<ReceiptList> receiptList)
/* 125:    */   {
/* 126:116 */     this.receiptList = receiptList;
/* 127:    */   }
/* 128:    */ }


/* Location:           F:\oa_back\oacustom\custom_class\
 * Qualified Name:     dinghan.workflow.beans.ReceiptAccount
 * JD-Core Version:    0.7.0.1
 */