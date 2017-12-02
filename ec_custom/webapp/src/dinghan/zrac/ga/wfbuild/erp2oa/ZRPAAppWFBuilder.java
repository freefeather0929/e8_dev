package dinghan.zrac.ga.wfbuild.erp2oa;

import java.util.HashMap;
import java.util.Map;

import dinghan.common.util.CalendarUtil;
import dinghan.common.wfbuilder.WorkFlowCreator;
import dinghan.workflow.kq.util.DepartmentInfoUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowDetailTableInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;

/**
 * 中车付款单申请流程构建者
 * @author hsf
 * 2017-11-21  
 *
 */
public class ZRPAAppWFBuilder extends WorkFlowCreator{
	
	private static final String WORKFLOW_NAME = "付款申请";
	private static final String WORKFLOW_TYPE_ID = "216";
	private static final String FORM_NAME = "formtable_main_258";  

	private ZRPAAppBill zRPAAppBill = new ZRPAAppBill();  
	
	private DepartmentInfoUtil departmentUitl = new DepartmentInfoUtil();
	
	private WorkflowService workflowService = new WorkflowServiceImpl(); //工作流   webservice 内部使用创建流程的调用方式
	private WorkflowBaseInfo workflowBaseInfo = new WorkflowBaseInfo();  	//工作流基础信息
	private WorkflowRequestInfo workflowRequestInfo = new WorkflowRequestInfo();		//工作流请求信息
	
	private String ReiDocNo = null;
	
	private int creatorId;	//流程创建人  -- 对应 付款单中的付款人
	
	private String departmentid; //付款人所在行政部门ID - OA系统
	
	private String _1st_departmentid; //付款人所在一级部门 ID- OA系统
	
	private int jobtitle ;//付款人工作岗位id- OA系统
	
	private Map<String,String> parameters = new HashMap<String, String>();
	
	@Override 
	public String createWorkflow() { 
		String requestId = "-1";
		
		if(this.ReiDocNo != null){
			String respones = zRPAAppBill.queryPABillInfo(ReiDocNo);
			JSONObject json = JSONObject.fromObject(respones);//解析JSON字符串
			JSONArray  jsonArr_reiBillDetail1 ;//用来接收JSON对象里的数组1
			JSONArray  jsonArr_reiBillDetail2 ;//用来接收JSON对象里的数组2
			if(!("0".equals(json.getString("error")))){
				initCreatorInfoByName(json.get("CreatedBy").toString());	//获取费用付款单中的付款人信息
				
				if(this.creatorId != -1){  
				     
					//主表字段
					WorkflowRequestTableField[] wrti1 = new WorkflowRequestTableField[8]; //字段信息
				  
			        //单据类型
				    wrti1[0]=new WorkflowRequestTableField();
					wrti1[0].setFieldName("billtype");  
					wrti1[0].setFieldValue(json.get("DocType").toString());
					wrti1[0].setView(true);
					wrti1[0].setEdit(true);
					
			        //单号
				    wrti1[1]=new WorkflowRequestTableField();
					wrti1[1].setFieldName("payappbillnum");  
					wrti1[1].setFieldValue(json.get("DocNo").toString());
					wrti1[1].setView(true);
					wrti1[1].setEdit(true);					
					
			        //申请日期
				    wrti1[2]=new WorkflowRequestTableField();
					wrti1[2].setFieldName("appdate");  
					wrti1[2].setFieldValue(json.get("PayRFDate").toString());
					wrti1[2].setView(true);
					wrti1[2].setEdit(true);	
					
					
			        //创建人
				    wrti1[3]=new WorkflowRequestTableField();
					wrti1[3].setFieldName("creatername");  
					wrti1[3].setFieldValue(json.get("CreatedBy").toString());
					wrti1[3].setView(true);
					wrti1[3].setEdit(true);						
					
					
			        //部门
			        wrti1[4]=new WorkflowRequestTableField();
					wrti1[4].setFieldName("apppaydep");  
					wrti1[4].setFieldValue(json.get("Dept").toString());
					wrti1[4].setView(true);
					wrti1[4].setEdit(true);					
					
			        //预计付款日期
				    wrti1[5]=new WorkflowRequestTableField();
					wrti1[5].setFieldName("planpaydate");  
					wrti1[5].setFieldValue(json.get("ExpectPayDate").toString());
					wrti1[5].setView(true);
					wrti1[5].setEdit(true);							
	
			
					
						
					
				   
				   WorkflowRequestTableRecord[] wrtri1=new WorkflowRequestTableRecord[1];//主字段只有一行数据
			       wrtri1[0]=new WorkflowRequestTableRecord();
			      
			       
			       
					String detailrowsdata1 = json.get("list").toString();
					jsonArr_reiBillDetail1 =json.getJSONArray(detailrowsdata1);
					int detailrows1 = jsonArr_reiBillDetail1.size(); // 对页签1添加指定条数明细
					// 添加明细数据
					 WorkflowRequestTableRecord[]	wrtri2 = new WorkflowRequestTableRecord[detailrows1];// 添加指定条数行明细数据
					for (int i = 0; i < detailrows1; i++) {
						JSONObject object = (JSONObject) jsonArr_reiBillDetail1.get(i);
						// 每行明细对应的字段
						WorkflowRequestTableField[]	wrti2 = new WorkflowRequestTableField[16]; // 字段信息
						
						
						
						//行号
						wrti2[0] = new WorkflowRequestTableField();
						wrti2[0].setFieldName("linenumber");
						wrti2[0].setFieldValue(object.getString("LineNum"));
						wrti2[0].setView(true);// 字段是否可见
						wrti2[0].setEdit(true);// 字段是否可编辑
						

						//选单
						wrti2[1] = new WorkflowRequestTableField();
						wrti2[1].setFieldName("selectbill");
						wrti2[1].setFieldValue(object.getString("ChooseSrcBill"));
						wrti2[1].setView(true);// 字段是否可见
						wrti2[1].setEdit(true);// 字段是否可编辑
						
						//结算方式
						wrti2[2] = new WorkflowRequestTableField();
						wrti2[2].setFieldName("paytype");
						wrti2[2].setFieldValue(object.getString("SttlMethod"));
						wrti2[2].setView(true);// 字段是否可见
						wrti2[2].setEdit(true);// 字段是否可编辑						
						
						//币种
						wrti2[3] = new WorkflowRequestTableField();
						wrti2[3].setFieldName("currency");
						wrti2[3].setFieldValue(json.getString("ReqFundAC"));  //从表头获取 币种
						wrti2[3].setView(true);// 字段是否可见
						wrti2[3].setEdit(true);// 字段是否可编辑						
						
						//付款本币金额
						wrti2[4] = new WorkflowRequestTableField();
						wrti2[4].setFieldName("payamo");
						wrti2[4].setFieldValue(object.getString("AcmPayFCMoney"));  
						wrti2[4].setView(true);// 字段是否可见
						wrti2[4].setEdit(true);// 字段是否可编辑												
						
				        //请款用途   ------------主表
					    wrti1[6]=new WorkflowRequestTableField();
						wrti1[6].setFieldName("recunit");  
						wrti1[6].setFieldValue(String.valueOf(object.get("Supplier").toString()));
						wrti1[6].setView(true);
						wrti1[6].setEdit(true);		
						
						

						wrtri2[i] = new WorkflowRequestTableRecord();
						wrtri2[i].setWorkflowRequestTableFields(wrti2);
					} 
					
					
					
			        //请款用途
				    wrti1[7]=new WorkflowRequestTableField();
					wrti1[7].setFieldName("apppayuses");  
					wrti1[7].setFieldValue(String.valueOf(json.get("ReqFundUse").toString()));
					wrti1[7].setView(true);
					wrti1[7].setEdit(true);			
					
					
					// 添加到主表中
					   wrtri1[0].setWorkflowRequestTableFields(wrti1);
				       WorkflowMainTableInfo wmi=new WorkflowMainTableInfo();
				       wmi.setRequestRecords(wrtri1);
					
					
					
					// 添加到明细表中
					WorkflowDetailTableInfo WorkflowDetailTableInfo[] = new WorkflowDetailTableInfo[1];// 指定明细表的个数，多个明细表指定多个，顺序按照明细的顺序
					WorkflowDetailTableInfo[0] = new WorkflowDetailTableInfo();
					WorkflowDetailTableInfo[0].setWorkflowRequestTableRecords(wrtri2);
			       
			    
			       
			       
			       
			       //添加工作流id
			       //WorkflowBaseInfo wbi= new WorkflowBaseInfo();
			       this.workflowBaseInfo.setWorkflowId(WORKFLOW_TYPE_ID);		//workflowid 流程接口演示流程2016==38
			       this.workflowRequestInfo.setCreatorId(this.creatorId+"");		//创建人id
			       this.workflowRequestInfo.setRequestLevel("0");	//0 正常，1重要，2紧急
			       this.workflowRequestInfo.setRequestName(WORKFLOW_NAME + json.getString("CreatedBy") + "-" + CalendarUtil.getCurDate());	//流程标题
			       this.workflowRequestInfo.setWorkflowMainTableInfo(wmi);	//添加主字段数据
			       this.workflowRequestInfo.setWorkflowBaseInfo(workflowBaseInfo);
			       requestId = this.workflowService.doCreateWorkflowRequest(this.workflowRequestInfo, this.creatorId); 
				}else{
					//人员信息未在OA中获取或者ERP中的人员工号和OA中的工号不一致到 写入到异构系统单据创建流程错误记录表中
					//json.get("ReiPersonCode").toString()
				}
			}else{
				// ERP单据未找到
				// 返回错误信息
			}
		}
		return requestId;
	}
	
	@Override
	public int createMultiWorkflow() {
		int num = 0;
		String requstID;
		if(!parameters.isEmpty()){
			String respones = zRPAAppBill.queryPAAllBillInfo(parameters);
			JSONObject json = JSONObject.fromObject(respones);
			JSONArray jsonArray =json.getJSONArray("list");
			
			if(jsonArray.size()>0){      
				for(int i=0;i<jsonArray.size();i++){
					JSONObject job = jsonArray.getJSONObject(i); 
					this.ReiDocNo = job.get("DocNo").toString();
					if(hasCreated(this.ReiDocNo) == true){
						continue;
					}
					//判断此流程是否被创建
					try {
						requstID = this.createWorkflow();
						num ++;
					} catch (Exception e) {
						e.printStackTrace();
					}
			   }
			}
		}
		return num;
	}
	
	/**
	 * 获取人员ID
	 * @param workcode 
	 */
	private void initCreatorInfo(String workcode){
		this.creatorId = -1;
		String sql ="select h.id as id,h.loginId as loginId,h.lastName as lastName,h.jobTitle as jobTitle,h.seclevel as seclevel,h.departmentId as departmentId,h.subcompanyid1 as subcompanyid1,j.jobtitlename as jobtitlename,d.departmentname as departmentname "
	              +" from HrmResource h,HrmJobTitles j,HrmDepartment d"
	              +" where h.workcode='"+workcode+"'"
	              +" and h.jobTitle = j.id  "
	              + " and h.departmentId =d.id ";
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		while(rs.next()){
			this.creatorId = rs.getInt("id");
			this.departmentid = rs.getInt("departmentId")+"";
			this.jobtitle =rs.getInt("jobTitle");   
		}
		JSONObject json = JSONObject.fromObject(
			departmentUitl.getDepartmentContruction(this.creatorId)
		);
		this._1st_departmentid =  json.getString("level1_id");
	}

	private void initCreatorInfoByName(String name){
		this.creatorId = -1;
		String sql ="select h.id as id,h.loginId as loginId,h.lastName as lastName,h.jobTitle as jobTitle,h.seclevel as seclevel,h.departmentId as departmentId,h.subcompanyid1 as subcompanyid1,j.jobtitlename as jobtitlename,d.departmentname as departmentname "
	              +" from HrmResource h,HrmJobTitles j,HrmDepartment d"
	              +" where h.lastname='"+name+"'"
	              +" and h.jobTitle = j.id  "
	              + " and h.departmentId =d.id ";
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		while(rs.next()){
			this.creatorId = rs.getInt("id");
			this.departmentid = rs.getInt("departmentId")+"";
			this.jobtitle =rs.getInt("jobTitle");   
		}
		JSONObject json = JSONObject.fromObject(
			departmentUitl.getDepartmentContruction(this.creatorId)
		);
		this._1st_departmentid =  json.getString("level1_id");
	}
	
	
	
	
	
	@Override
	public boolean hasCreated(String erpBillDocNo) { 
		String sql = "select f.id as id,f.requestId as requestId,w.Lastoperatedate as Lastoperatedate from " + FORM_NAME + " f ,workflow_requestbase w where f.reibillno = '" + erpBillDocNo + "' and f.requestId = w.requestId ";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		if(rs.getCounts() > 0){
			if( null == rs.getDate("Lastoperatedate") ){   
				return true; //有数据但不属于退回的单据
			}   
			return false ;   //有数据但属于退回的单据
		}   
		return false;
	}
}
