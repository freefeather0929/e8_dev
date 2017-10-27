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
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;

public class ZRLoanAppWFBuilder extends WorkFlowCreator{
	
	private static final String WORKFLOW_NAME = "借款申请";
	private static final String WORKFLOW_TYPE_ID = "183";
	private static final String FORM_NAME = "formtable_main_215";

	private ERPBill ZRLoanAppBill = new ZRLoanAppBill();
	
	private DepartmentInfoUtil departmentUitl = new DepartmentInfoUtil();
	
	private WorkflowService workflowService = new WorkflowServiceImpl(); //工作流   webservice 内部使用创建流程的调用方式
	private WorkflowBaseInfo workflowBaseInfo = new WorkflowBaseInfo();  	//工作流基础信息
	private WorkflowRequestInfo workflowRequestInfo = new WorkflowRequestInfo();		//工作流请求信息
	
	private String loanDocNo = null;
	
	private int creatorId;	//流程创建人  -- 对应 借款单中的借款人
	
	private String departmentid; //借款人所在行政部门ID - OA系统
	
	private String _1st_departmentid; //借款人所在一级部门 ID- OA系统
	
	private Map<String,String> parameters = new HashMap<String, String>();
	
	@Override
	public String createWorkflow() {
		String requestId = "-1";
		if(this.loanDocNo != null){
			String respones = ZRLoanAppBill.queryBillInfo(loanDocNo);
			JSONObject json = JSONObject.fromObject(respones);
			if(!("0".equals(json.getString("error")))){
				initCreatorInfo(json.get("LoanPersonCode").toString());	//获取借款单中的借款人信息
				
				if(this.creatorId != -1){
				
					//主表字段
					WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[19]; //字段信息
					//审批流程单号
				   wrti[0]=new WorkflowRequestTableField();
				   wrti[0].setFieldName("borrowordernumber");
				   wrti[0].setFieldValue(json.get("DocNo").toString());
				   wrti[0].setView(true);
				   wrti[0].setEdit(true);
				   //单据类型
				   wrti[1]=new WorkflowRequestTableField();
				   wrti[1].setFieldName("documenttype");
				   wrti[1].setFieldValue(json.get("DocType").toString());
				   wrti[1].setView(true);
				   wrti[1].setEdit(true);
				   
				   //借款日期
				   wrti[2]=new WorkflowRequestTableField();
				   wrti[2].setFieldName("borrowdate");
				   wrti[2].setFieldValue(json.get("LoanDate").toString());
				   wrti[2].setView(true);
				   wrti[2].setEdit(true);
				   //System.out.println(json.get("LoanDate").toString());
				   //借款组织
				   wrti[3]=new WorkflowRequestTableField();
				   wrti[3].setFieldName("borroworganization");
				   wrti[3].setFieldValue(json.get("LoanOrg").toString());
				   wrti[3].setView(true);
				   wrti[3].setEdit(true);
				   //费用项目
				   wrti[4]=new WorkflowRequestTableField();
				   wrti[4].setFieldName("costproject");
				   wrti[4].setFieldValue(json.get("ExpenseItem").toString());
				   wrti[4].setView(true);
				   wrti[4].setEdit(true);
				   //用途
				   wrti[5]=new WorkflowRequestTableField();
				   wrti[5].setFieldName("application");
				   wrti[5].setFieldValue(json.get("LoanUse").toString());
				   wrti[5].setView(true);
				   wrti[5].setEdit(true);
				   //借款单号
				   wrti[6]=new WorkflowRequestTableField();
				   wrti[6].setFieldName("borrowordernumber");
				   wrti[6].setFieldValue(json.get("ID").toString());
				   wrti[6].setView(true);
				   wrti[6].setEdit(true);
				   //预计还款日期
				   wrti[7]=new WorkflowRequestTableField();
				   wrti[7].setFieldName("planrepaymentdate");
				   wrti[7].setFieldValue(json.get("ExpectPayDate").toString());
				   wrti[7].setView(true);
				   wrti[7].setEdit(true);
				   //借款部门
				   wrti[8]=new WorkflowRequestTableField();
				   wrti[8].setFieldName("borrowdepartment");
				   wrti[8].setFieldValue(json.get("LoanDept").toString());
				   wrti[8].setView(true);
				   wrti[8].setEdit(true);
				   //借款人组织
				   wrti[9]=new WorkflowRequestTableField();
				   wrti[9].setFieldName("borrowerorganization");
				   wrti[9].setFieldValue(json.get("LoanPersonOrg").toString());
				   wrti[9].setView(true);
				   wrti[9].setEdit(true);
				   //借款人工号
				   wrti[10]=new WorkflowRequestTableField();
				   wrti[10].setFieldName("borrowernumber");
				   wrti[10].setFieldValue(json.get("LoanPersonCode").toString());
				   wrti[10].setView(true);
				   wrti[10].setEdit(true);
				   //借款币种
				   wrti[11]=new WorkflowRequestTableField();
				   wrti[11].setFieldName("loancurrent");
				   wrti[11].setFieldValue(json.get("LoanCurrency").toString());
				   wrti[11].setView(true);
				   wrti[11].setEdit(true);
				   //借款金额
				   wrti[12]=new WorkflowRequestTableField();
				   wrti[12].setFieldName("loanamount");
				   wrti[12].setFieldValue(json.get("LoanMoney").toString());
				   wrti[12].setView(true);
				   wrti[12].setEdit(true);
				   //状态
				   wrti[13]=new WorkflowRequestTableField();
				   wrti[13].setFieldName("status");
				   //wrti[13].setFieldValue(json.get("DocStatus").toString());
				   wrti[13].setFieldValue(json.getString("DocStatus"));
				   wrti[13].setView(true);
				   wrti[13].setEdit(true);
				   //创建人
				   wrti[14]=new WorkflowRequestTableField();
				   wrti[14].setFieldName("founder");
				   wrti[14].setFieldValue(json.get("CreatedBy").toString());
				   wrti[14].setView(true);
				   wrti[14].setEdit(true);
				   //创建时间
				   wrti[15]=new WorkflowRequestTableField();
				   wrti[15].setFieldName("createdate");
				   wrti[15].setFieldValue(json.get("CreatedOn").toString());
				   wrti[15].setView(true);
				   wrti[15].setEdit(true);
				   //项目
				   wrti[16]=new WorkflowRequestTableField();
				   wrti[16].setFieldName("projectname");
				   wrti[16].setFieldValue(json.get("Project").toString());
				   wrti[16].setView(true);
				   wrti[16].setEdit(true);
				   
				   //借款人姓名
				   wrti[17]=new WorkflowRequestTableField();
				   wrti[17].setFieldName("borrowername");
				   wrti[17].setFieldValue(this.creatorId+"");
				   wrti[17].setView(true);
				   wrti[17].setEdit(true);
				   
				   //借款人一级部门
				   wrti[18]=new WorkflowRequestTableField();
				   wrti[18].setFieldName("borrowerfirstdepartment");
				   //wrti[18].setFieldValue(firstDeptId);
				   wrti[18].setFieldValue(this._1st_departmentid+"");
				   wrti[18].setView(true);
				   wrti[18].setEdit(true);
				   //借款人行政部门
				   wrti[19]=new WorkflowRequestTableField();
				   wrti[19].setFieldName("borroweradministration");
				  // wrti[19].setFieldValue(borrowerInfo.getBorrowerAdministration());
				   wrti[19].setFieldValue(this.departmentid+"");
				   wrti[19].setView(true);
				   wrti[19].setEdit(true);
				   
				   WorkflowRequestTableRecord[] wrtri=new WorkflowRequestTableRecord[1];
			       wrtri[0]=new WorkflowRequestTableRecord();
			       wrtri[0].setWorkflowRequestTableFields(wrti);
			       WorkflowMainTableInfo wmi=new WorkflowMainTableInfo();
			       wmi.setRequestRecords(wrtri);
			       
			       //添加工作流id
			       //WorkflowBaseInfo wbi= new WorkflowBaseInfo();
			       this.workflowBaseInfo.setWorkflowId(WORKFLOW_TYPE_ID);		//workflowid 流程接口演示流程2016==38
			       this.workflowRequestInfo.setCreatorId(this.creatorId+"");		//创建人id
			       this.workflowRequestInfo.setRequestLevel("0");	//0 正常，1重要，2紧急
			       this.workflowRequestInfo.setRequestName(WORKFLOW_NAME + json.getString("LoanPerson") + "-" + CalendarUtil.getCurDate());	//流程标题
			       this.workflowRequestInfo.setWorkflowMainTableInfo(wmi);	//添加主字段数据
			       this.workflowRequestInfo.setWorkflowBaseInfo(workflowBaseInfo);
			       requestId = this.workflowService.doCreateWorkflowRequest(this.workflowRequestInfo, this.creatorId); 
				}else{
					//人员信息未在OA中获取或者ERP中的人员工号和OA中的工号不一致到 写入到异构系统单据创建流程错误记录表中
					//json.get("LoanPersonCode").toString()
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
			String respones = ZRLoanAppBill.queryAllBillInfo(parameters);
			JSONObject json = JSONObject.fromObject(respones);
			JSONArray jsonArray =json.getJSONArray("list");
			
			if(jsonArray.size()>0){      
				for(int i=0;i<jsonArray.size();i++){
					JSONObject job = jsonArray.getJSONObject(i); 
					this.loanDocNo = job.get("DocNo").toString();
					if(hasCreated(this.loanDocNo) == true){
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
		String sql = "select id,deparmentid from Hrmresourse where workcode = '" + workcode + "'";
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		while(rs.next()){
			this.creatorId = rs.getInt("id");
			this.departmentid = rs.getInt("deparmentid")+"";
		}
		JSONObject json = JSONObject.fromObject(
			departmentUitl.getDepartmentContruction(this.creatorId)
		);
		this._1st_departmentid =  json.getString("level1_id");
	}
	
	@Override
	public boolean hasCreated(String erpBillDocNo) {
		String sql = "select id,requestId,approveprocessnumber from " + FORM_NAME + " where borrowordernumber = '" + erpBillDocNo + "'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		if(rs.getCounts() > 0){
			return true;
		}
		
		return false;
	}
	

	public ERPBill getZRLoanAppBill() {
		return ZRLoanAppBill;
	}

	public void setZRLoanAppBill(ERPBill zRLoanAppBill) {
		ZRLoanAppBill = zRLoanAppBill;
	}

	public String getLoanDocNo() {
		return loanDocNo;
	}

	public void setLoanDocNo(String loanDocNo) {
		this.loanDocNo = loanDocNo;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	
	
}
