 package dinghan.workflow.beans;
 
 /**
  * 晚餐补助实体化类
  * @author zhangxiaoyu / 10593 - 2017-04-28
  *
  */
 public class WanCanBuZhu
 {
   private String wfMainId;
   private String Date;
   private String WeekDay;
   private String DakaRecord;
   private double menoy;
   private String Mark;
   
   public String getWfMainId()
   {
     return this.wfMainId;
   }
   
   public void setWfMainId(String wfMainId)
   {
     this.wfMainId = wfMainId;
   }
   
   public String getDate()
   {
     return this.Date;
   }
   
   public void setDate(String date)
   {
     this.Date = date;
   }
   
   public String getWeekDay()
   {
     return this.WeekDay;
   }
   
   public void setWeekDay(String weekDay)
   {
     this.WeekDay = weekDay;
   }
   
   public String getDakaRecord()
   {
     return this.DakaRecord;
   }
   
   public void setDakaRecord(String dakaRecord)
   {
     this.DakaRecord = dakaRecord;
   }
   
   public double getMenoy()
   {
     return this.menoy;
   }
   
   public void setMenoy(double menoy)
   {
     this.menoy = menoy;
   }
   
   public String getMark()
   {
     return this.Mark;
   }
   
   public void setMark(String mark)
   {
     this.Mark = mark;
   }
 }