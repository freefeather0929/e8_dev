package dinghan.zrac.ga;
import weaver.interfaces.schedule.*;
import weaver.conn.RecordSet;
import weaver.workflow.request.MailAndMessage;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.apache.taglibs.standard.lang.jpath.adapter.Convert;
import java.util.HashMap;
import dinghan.zrac.ga.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LoanApplication extends BaseCronJob {
		
     public void execute(){
         Map<String,String> parameters=new HashMap<String,String>();  
  		//取当前日期
  		Utils utils=new Utils();
  		//String endDate=utils.getNowDateShort().toString();
  		//取得当前月的第一天
  		//String startDate= utils.getThisMonthFirstDay();
  		//拿到当前月份的第一天的日期
      	parameters.put("startDate", "2017-07-01");  
      	parameters.put("endDate", "2017-07-05"); 
  		//parameters.put("startDate", startDate); 
      	//parameters.put("endDate", endDate); 
      	parameters.put("org", "");  
      	parameters.put("creater", ""); 
      	//调用ERP的接口
          String result =HttpUtils.sendGet("http://10.10.66.246:8080/OA2ERP/U9/LoanBillListSvl", parameters);
          // 首先把字符串转成 JSONObject对象
          JSONObject json = JSONObject.fromObject(result);       
          //得到JSONArray对象
          JSONArray jsonArray =json.getJSONArray("list"); 
         // ArrayList list=new ArrayList(); //定义存放状态为核准中的单号的数组
          if(jsonArray.size()>0){      
          	  for(int i=0;i<jsonArray.size();i++){
          		// 遍历 jsonarray 数组，把每一个对象转成 json 对象
          	    JSONObject job = jsonArray.getJSONObject(i); 
          	    //取出审核状态，找到状态为核准中的对象
          	    //System.out.println(job.get("DocStatus"));
          	   // String docStatus=job.get("DocStatus").toString();  
          	    //if(docStatus.equals("核准中")){         
          	    	String docNo=job.get("DocNo").toString();
          	    	//判断此流程是否被创建
          	    	System.out.println(docNo);
          	    	//if(utils.isHavaCreatFlow(docNo)){    
          	    		//list.add(job.get("DocNo"));
          	    		try {        	    		
          					CreateWorkflow.createRequest(job);
          				} catch (Exception e) {
          					System.out.println(e);
          					e.printStackTrace();
          				}
          	    	//}           	    
          	    //}
          	 }
          }        	   	  
 		
     }  
}
