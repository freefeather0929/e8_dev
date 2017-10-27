package dinghan.workflow.action;

import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import weaver.conn.RecordSet;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;
import weaver.workflow.request.RequestManager;

/**
 * 发货申请欠料补发Action
 * <p>
 * 用于改变欠料流程的欠料明细状态
 * @author zhangxiaoyu / 10593
 *
 */
public class ChangeReDelStatueAction
   implements Action
 {
   private RequestManager requestManager;
   private Log log = LogFactory.getLog(ChangeReDelStatueAction.class.getName());
   
   public String execute(RequestInfo request)
   {
     this.requestManager = request.getRequestManager();
     String requestid = request.getRequestid();
     String workflowId = request.getWorkflowid();
     this.log.error("workflowId = " + workflowId);
     int formid = this.requestManager.getFormid();
     changeReDelStatue(requestid, formid);
     return "1";
   }
   
   public void changeReDelStatue(String requestid, int formid)
   {
     //this.log.error("开始执行欠料状态变更Action:请求ID -- " + requestid);
     ArrayList<String> redelListIds = new ArrayList();
     ArrayList<String> markList = new ArrayList();
     RecordSet rs = new RecordSet();
     RecordSet rs1 = new RecordSet();
     RecordSet rs2 = new RecordSet();
     String sql = "select top 1 id,qlflowlink from formtable_main_99 where requestId = " + requestid;
     //this.log.error(sql);
     rs.executeSql(sql);
     int wfId = -1;
     String qlWfId = "";
     while (rs.next())
     {
       wfId = rs.getInt("id");
       qlWfId = rs.getString("qlflowlink").trim();
     }
     //this.log.error("查询到流程主表记录的ID为：" + wfId);
     //this.log.error("查询到欠发流程主表记录的ID为：" + qlWfId);
     if (qlWfId.trim() != "")
     {
       sql = "select owndtbtn,mark from formtable_main_99_dt1 where mainid = " + wfId;
       //this.log.error("开始执行查询：" + sql);
       rs1.executeSql(sql);
       String qlListRowId = "";
       String mark = "";
       while (rs1.next())
       {
         qlListRowId = rs1.getString("owndtbtn").trim();
         mark = rs1.getString("mark");
         this.log.error("查询到欠料明细行的id为：" + qlListRowId);
         redelListIds.add(qlListRowId);
         markList.add(mark);
       }
       for (int i = 0; i < redelListIds.size(); i++)
       {
         sql = "update formtable_main_99_dt1 SET ownsoluted = '1',mark='" + (String)markList.get(i) + ";<br/>欠料已补发',redelwf=" + wfId + " where id= " + qlListRowId;
         this.log.error("开始执行更新：" + sql);
         if (rs2.executeSql(sql)) {
           this.log.error("第 " + (i + 1) + " 条明细数据更新完毕");
         }
       }
       sql = "select count(id) from formtable_main_99_dt1 where isown = '1' and ownsoluted <> '1' and mainid = " + qlWfId;
       rs2.executeSql(sql);
       int qldtnum = 0;
       while (rs2.next()) {
         qldtnum = rs2.getInt(1);
       }
       if (qldtnum == 0)
       {
         //this.log.error("所有欠料明细都已经处理完成，现在更新主表欠料处理结果字段....");
         sql = "update formtable_main_99 set redeled = '1' where id = " + qlWfId;
         rs2.executeSql(sql);
       }
       //this.log.error("接口执行完成....");
     }
   }
 }





