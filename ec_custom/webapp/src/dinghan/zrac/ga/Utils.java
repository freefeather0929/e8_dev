package dinghan.zrac.ga;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import weaver.conn.RecordSet;

public class Utils {
	
	//获取当前月第一天：
	public static void main(String[] args){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();    
	    c.add(Calendar.MONTH, 0);
	    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	    String first = format.format(c.getTime());
		System.out.println("===============first:"+first);  
	}
	  /**
     * 根据流程单号判断流程是否创建
     * @param docNo
     * @return
     */
    public boolean isHavaCreatFlow(String docNo){
     System.out.println(docNo);
   	 RecordSet rs=new RecordSet();
   	 String sql="select * from formtable_main_273 where approveprocessnumber ="+docNo;
        rs.execute(sql);
        if(rs.next()){
       	 return false;  //有记录，表示已经创建过流程
        }else{
       	 return true;   //此单号还没有创建流程
        }       	 
    }
    
    /** 
     * 获取现在时间 
     *  
     * @return返回短时间格式 yyyy-MM-dd 
     */  
    public static Date getNowDateShort() {  
        Date currentTime = new Date();  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
        String dateString = formatter.format(currentTime);  
        ParsePosition pos = new ParsePosition(8);  
        Date currentTime_2 = formatter.parse(dateString, pos);  
        return currentTime_2;  
    }  
    /**
     * 获得当前月的第一天
     * @return
     */
    public static String getThisMonthFirstDay(){
   	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 Calendar c = Calendar.getInstance();    
	     c.add(Calendar.MONTH, 0);
	     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	     String first = format.format(c.getTime());
		 return first;
    }
}
