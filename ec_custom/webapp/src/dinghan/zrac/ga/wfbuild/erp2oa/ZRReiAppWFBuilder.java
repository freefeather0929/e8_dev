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
 * 中车报销单申请流程构建者
 * @author hsf
 * 2017-11-7  
 *
 */
public class ZRReiAppWFBuilder extends WorkFlowCreator{
	
	private static final String WORKFLOW_NAME = "费用报销申请";
	private static final String WORKFLOW_TYPE_ID = "213";
	private static final String FORM_NAME = "formtable_main_254";  

	private ERPReiBill ZRReiAppBill = new ZRReiAppBill();  
	
	private DepartmentInfoUtil departmentUitl = new DepartmentInfoUtil();
	
	private WorkflowService workflowService = new WorkflowServiceImpl(); //工作流   webservice 内部使用创建流程的调用方式
	private WorkflowBaseInfo workflowBaseInfo = new WorkflowBaseInfo();  	//工作流基础信息
	private WorkflowRequestInfo workflowRequestInfo = new WorkflowRequestInfo();		//工作流请求信息
	
	private String ReiDocNo = null;
	
	private int creatorId;	//流程创建人  -- 对应 报销单中的报销人
	
	private String departmentid; //报销人所在行政部门ID - OA系统
	
	private String _1st_departmentid; //报销人所在一级部门 ID- OA系统
	
	private int jobtitle ;//报销人工作岗位id- OA系统
	
	private Map<String,String> parameters = new HashMap<String, String>();
	
	@Override 
	public String createWorkflow() { 
		String requestId = "-1";
		
		if(this.ReiDocNo != null){
			String respones = ZRReiAppBill.queryReiBillInfo(ReiDocNo);
			JSONObject json = JSONObject.fromObject(respones);//解析JSON字符串
			JSONArray  jsonArr_reiBillDetail1 ;//用来接收JSON对象里的数组1
			JSONArray  jsonArr_reiBillDetail2 ;//用来接收JSON对象里的数组2
			if(!("0".equals(json.getString("error")))){
				initCreatorInfo(json.get("ReimBurseByCode").toString());	//获取费用报销单中的报销人信息
				
				if(this.creatorId != -1){  
				     
					//主表字段
					WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[13]; //字段信息
				  
			        //报销组织
				    wrti[0]=new WorkflowRequestTableField();
					wrti[0].setFieldName("reiorg");  
					wrti[0].setFieldValue(json.get("ReimBurseOrg").toString());
					wrti[0].setView(true);
					wrti[0].setEdit(true);
					
			        //单号
				    wrti[1]=new WorkflowRequestTableField();
					wrti[1].setFieldName("reibillno");  
					wrti[1].setFieldValue(json.get("DocNo").toString());
					wrti[1].setView(true);
					wrti[1].setEdit(true);					
					
			        //单据类型
				    wrti[2]=new WorkflowRequestTableField();
					wrti[2].setFieldName("billtype");  
					wrti[2].setFieldValue(json.get("DocType").toString());
					wrti[2].setView(true);
					wrti[2].setEdit(true);	
					
					
			        //创建人
				    wrti[3]=new WorkflowRequestTableField();
					wrti[3].setFieldName("creater");  
					wrti[3].setFieldValue(json.get("CreatedBy").toString());
					wrti[3].setView(true);
					wrti[3].setEdit(true);						
					
			        //创建日期
			        wrti[4]=new WorkflowRequestTableField();
					wrti[4].setFieldName("creatdate");  
					wrti[4].setFieldValue(json.get("CreatedOn").toString());
					wrti[4].setView(true);
					wrti[4].setEdit(true);					
					
			        //报销日期
				    wrti[5]=new WorkflowRequestTableField();
					wrti[5].setFieldName("reidate");  
					wrti[5].setFieldValue(json.get("ReimburseDate").toString());
					wrti[5].setView(true);
					wrti[5].setEdit(true);							
	
			        //报销人部门
				    wrti[6]=new WorkflowRequestTableField();
					wrti[6].setFieldName("reiperdep");  
					wrti[6].setFieldValue(String.valueOf(this.departmentid));
					wrti[6].setView(true);
					wrti[6].setEdit(true);						
					
					
			        //报销人1
				    wrti[7]=new WorkflowRequestTableField();
					wrti[7].setFieldName("Reiper1");  
					wrti[7].setFieldValue(String.valueOf(this.creatorId));
					wrti[7].setView(true);
					wrti[7].setEdit(true);							
										
			        //报销人2
				    wrti[8]=new WorkflowRequestTableField();
					wrti[8].setFieldName("Reiper2");  
					wrti[8].setFieldValue(String.valueOf(this.creatorId));
					wrti[8].setView(true);
					wrti[8].setEdit(true);	
					
			        //报销人职务  
				    wrti[9]=new WorkflowRequestTableField();
					wrti[9].setFieldName("reiperpost");  
					wrti[9].setFieldValue(String.valueOf(String.valueOf(this.jobtitle)));
					wrti[9].setView(true);
					wrti[9].setEdit(true);						
					
					
			        //报销总金额
				    wrti[10]=new WorkflowRequestTableField();
					wrti[10].setFieldName("sumreimbursemoney");    
					wrti[10].setFieldValue(json.get("SumReimburseMoney").toString());
					wrti[10].setView(true);
					wrti[10].setEdit(true);	
					
			        //状态
				    wrti[11]=new WorkflowRequestTableField();
					wrti[11].setFieldName("docstatus");  
					wrti[11].setFieldValue(json.get("docstatus").toString());
					wrti[11].setView(true);
					wrti[11].setEdit(true);	
					
			        //报销人工号
				    wrti[12]=new WorkflowRequestTableField();
					wrti[12].setFieldName("reiperjobnum");  
					wrti[12].setFieldValue(json.get("ReimBurseByCode").toString());
					wrti[12].setView(true);
					wrti[12].setEdit(true);						
					
				   
				   WorkflowRequestTableRecord[] wrtri=new WorkflowRequestTableRecord[1];//主字段只有一行数据
			       wrtri[0]=new WorkflowRequestTableRecord();
			       wrtri[0].setWorkflowRequestTableFields(wrti);
			       WorkflowMainTableInfo wmi=new WorkflowMainTableInfo();
			       wmi.setRequestRecords(wrtri);
			       
					String detailrowsdata1 = json.get("list").toString();
					jsonArr_reiBillDetail1 =json.getJSONArray(detailrowsdata1);
					int detailrows1 = jsonArr_reiBillDetail1.size(); // 对页签1添加指定条数明细
					// 添加明细数据
					wrtri = new WorkflowRequestTableRecord[detailrows1];// 添加指定条数行明细数据
					for (int i = 0; i < detailrows1; i++) {
						JSONObject object = (JSONObject) jsonArr_reiBillDetail1.get(i);
						// 每行明细对应的字段
						wrti = new WorkflowRequestTableField[6]; // 字段信息
						
						//事由
						wrti[0] = new WorkflowRequestTableField();
						wrti[0].setFieldName("reason");
						wrti[0].setFieldValue(object.getString("Reason"));
						wrti[0].setView(true);// 字段是否可见
						wrti[0].setEdit(true);// 字段是否可编辑

						//费用项目
						wrti[1] = new WorkflowRequestTableField();
						wrti[1].setFieldName("expenseitem");
						wrti[1].setFieldValue(object.getString("ExpenseItem"));
						wrti[1].setView(true);// 字段是否可见
						wrti[1].setEdit(true);// 字段是否可编辑						
						
						
						//列支部门
						wrti[2] = new WorkflowRequestTableField();
						wrti[2].setFieldName("expensepaydept");
						wrti[2].setFieldValue(object.getString("ExpensePayDept"));
						wrti[2].setView(true);// 字段是否可见
						wrti[2].setEdit(true);// 字段是否可编辑	
						
						//列支人员
						wrti[3] = new WorkflowRequestTableField();
						wrti[3].setFieldName("expensepayby");
						wrti[3].setFieldValue(object.getString("ExpensePayBy"));
						wrti[3].setView(true);// 字段是否可见
						wrti[3].setEdit(true);// 字段是否可编辑	
						

						//项目
						wrti[4] = new WorkflowRequestTableField();
						wrti[4].setFieldName("project");
						wrti[4].setFieldValue(object.getString("Project"));
						wrti[4].setView(true);// 字段是否可见
						wrti[4].setEdit(true);// 字段是否可编辑							
						
						//报销金额
						wrti[5] = new WorkflowRequestTableField();
						wrti[5].setFieldName("reimburesemoney");
						wrti[5].setFieldValue(object.getString("ReimburseMoney"));
						wrti[5].setView(true);// 字段是否可见
						wrti[5].setEdit(true);// 字段是否可编辑							
						

						wrtri[i] = new WorkflowRequestTableRecord();
						wrtri[i].setWorkflowRequestTableFields(wrti);
					} 
					
					// 添加到明细表中
					WorkflowDetailTableInfo WorkflowDetailTableInfo[] = new WorkflowDetailTableInfo[2];// 指定明细表的个数，多个明细表指定多个，顺序按照明细的顺序
					WorkflowDetailTableInfo[0] = new WorkflowDetailTableInfo();
					WorkflowDetailTableInfo[0].setWorkflowRequestTableRecords(wrtri);
			       
			       
					String detailrowsdata2 = json.get("LoanList").toString();
					jsonArr_reiBillDetail2 =json.getJSONArray(detailrowsdata2);
					int detailrows2 = jsonArr_reiBillDetail1.size(); // 对页签2添加指定条数明细
					// 添加明细数据
					wrtri = new WorkflowRequestTableRecord[detailrows2];// 添加指定条数行明细数据
					for (int i = 0; i < detailrows2; i++) {
						JSONObject object = (JSONObject) jsonArr_reiBillDetail2.get(i);
						// 每行明细对应的字段
						wrti = new WorkflowRequestTableField[5]; // 字段信息
						
						//借款单
						wrti[0] = new WorkflowRequestTableField();
						wrti[0].setFieldName("punborbillno");
						wrti[0].setFieldValue(object.getString("DocNo")); 
						wrti[0].setView(true);// 字段是否可见
						wrti[0].setEdit(true);// 字段是否可编辑

						//冲销金额
						wrti[1] = new WorkflowRequestTableField();
						wrti[1].setFieldName("hadpayamo");
						wrti[1].setFieldValue(object.getString("LoanBalanceMoney"));
						wrti[1].setView(true);// 字段是否可见
						wrti[1].setEdit(true);// 字段是否可编辑						
						
						
						//税率
						wrti[2] = new WorkflowRequestTableField();
						wrti[2].setFieldName("taxrate");
						wrti[2].setFieldValue(object.getString("TaxRate"));
						wrti[2].setView(true);// 字段是否可见
						wrti[2].setEdit(true);// 字段是否可编辑	
						
						//未税金额
						wrti[3] = new WorkflowRequestTableField();
						wrti[3].setFieldName("nontaxamo");
						wrti[3].setFieldValue(object.getString("NonTaxMoney"));
						wrti[3].setView(true);// 字段是否可见
						wrti[3].setEdit(true);// 字段是否可编辑	
						

						//税额
						wrti[4] = new WorkflowRequestTableField();
						wrti[4].setFieldName("taxamo");
						wrti[4].setFieldValue(object.getString("TotalTax"));
						wrti[4].setView(true);// 字段是否可见
						wrti[4].setEdit(true);// 字段是否可编辑							
	 											
 
						wrtri[i] = new WorkflowRequestTableRecord();
						wrtri[i].setWorkflowRequestTableFields(wrti);
					} // 添加到明细表中
			       
					WorkflowDetailTableInfo[1] = new WorkflowDetailTableInfo();
					WorkflowDetailTableInfo[1].setWorkflowRequestTableRecords(wrtri);
			       
			       
			       
			       //添加工作流id
			       //WorkflowBaseInfo wbi= new WorkflowBaseInfo();
			       this.workflowBaseInfo.setWorkflowId(WORKFLOW_TYPE_ID);		//workflowid 流程接口演示流程2016==38
			       this.workflowRequestInfo.setCreatorId(this.creatorId+"");		//创建人id
			       this.workflowRequestInfo.setRequestLevel("0");	//0 正常，1重要，2紧急
			       this.workflowRequestInfo.setRequestName(WORKFLOW_NAME + json.getString("ReiPerson") + "-" + CalendarUtil.getCurDate());	//流程标题
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
			String respones = ZRReiAppBill.queryReiBillInfo(parameters);
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
	
	@Override
	public boolean hasCreated(String erpBillDocNo) { 
		String sql = "select id,requestId from " + FORM_NAME + " where reibillno = '" + erpBillDocNo + "'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		if(rs.getCounts() > 0){
			return true;
		}   
		return false;
	}
	
}
