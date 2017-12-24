package dinghan.zrac.ga.wfbuild.erp2oa;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import dinghan.common.util.CalendarUtil;
import dinghan.common.wfbuilder.WorkFlowCreator;
import dinghan.workflow.kq.util.DepartmentInfoUtil;
import dinghan.zrac.ga.ConstantUtils;
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
	private static final String WORKFLOW_TYPE_ID = "183";            //正式系统
	private static final String FORM_NAME = "formtable_main_215";    //正式系统
//	private static final String WORKFLOW_TYPE_ID = "236";            //测试系统 
//	private static final String FORM_NAME = "formtable_main_273";    //测试系统   

	private ERPBill ZRLoanAppBill = new ZRLoanAppBill();   
	
	private DepartmentInfoUtil departmentUitl = new DepartmentInfoUtil();
	
	private WorkflowService workflowService = new WorkflowServiceImpl(); //工作流   webservice 内部使用创建流程的调用方式
	private WorkflowBaseInfo workflowBaseInfo = new WorkflowBaseInfo();  	//工作流基础信息
	private WorkflowRequestInfo workflowRequestInfo = new WorkflowRequestInfo();		//工作流请求信息
	
	private String loanDocNo = null;     //借款单单号  --ERP系统
	private String loanCreatedOn =null;  //借款单创建时间  --ERP系统
	private String docStatus =null;      //借款单状态 --ERP系统
	
	private int creatorId ;	//流程创建人id  -- 对应 借款单中的借款人
	private String creatorName =null;//流程创建人名称 -- 对应 借款单中的借款人姓名
	private String createdcode =null;//创建人(工号)  --  ERP创建人(工号)
	
	private String loanPersonCode=null;//借款人工号 ----ERP系统
	private String loanPerson=null;//借款人姓名  --ERP系统 
	private String project=null;//项目  --ERP系统
	private String expenseItem=null;//费用项目 --ERP系统
	private String loanDate=null;//借款日期
	private String expectPayDate=null;//预计还款日期
	private String loanUse=null;//用途 
	
	private int departmentid; //借款人所在行政部门ID - OA系统
	private String departmengName =null;//借款人所在行政部门名称 - OA系统
	
	private String _1st_departmentid; //借款人所在一级部门 ID- OA系统
	
	private Map<String,String> parameters = new HashMap<String, String>();
	
	private String tableHaveCreated_flag ="0"; //0:未来创建   1：已创建

	private int jobtitle ;//付款人工作岗位id  -- OA系统   
	private String jobtitlename	;//职位描述  -- OA系统
	
	private String  cause=null;//影响数据同步的原因 
	
	private Log log = LogFactory.getLog(ZRLoanAppWFBuilder.class.getName());
	ConstantUtils constantUtils =new ConstantUtils();
	
	@Override
	public String createWorkflow() {
		String requestId = "-1";
		if(this.loanDocNo != null){
			String respones = ZRLoanAppBill.queryBillInfo(loanDocNo);
			log.info("查找单个借款单号成功==============");  
			JSONObject json = JSONObject.fromObject(respones);
			if("0".equals(json.getString("error"))){   
				initCreatorInfo(json.get("LoanPersonCode").toString());	//获取借款单中的借款人信息
				if(this.creatorId != -1){
					log.info("creatorId===="+this.creatorId); 
					//主表字段
					WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[19]; //字段信息 
					//借款单号
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
				   if(json.get("LoanDate").toString().isEmpty()){    
					   addCouse(constantUtils.failurecause_1d);      
				   }  				   
				   wrti[2]=new WorkflowRequestTableField();
				   wrti[2].setFieldName("borrowdate");
				   wrti[2].setFieldValue(json.get("LoanDate").toString());
				   wrti[2].setView(true);
				   wrti[2].setEdit(true);
				   this.loanDate =json.get("LoanDate").toString(); 
				   
				   //借款组织
				   wrti[3]=new WorkflowRequestTableField();
				   wrti[3].setFieldName("borroworganization");
				   wrti[3].setFieldValue(json.get("LoanOrg").toString());
				   wrti[3].setView(true);
				   wrti[3].setEdit(true);
				   
				   //费用项目
				   if(json.get("ExpenseItem").toString().isEmpty()){    
					   addCouse(constantUtils.failurecause_1b);      
				   }  
				   wrti[4]=new WorkflowRequestTableField();
				   wrti[4].setFieldName("costproject");
				   wrti[4].setFieldValue(json.get("ExpenseItem").toString());
				   wrti[4].setView(true);
				   wrti[4].setEdit(true);
				   this.expenseItem=json.get("ExpenseItem").toString();
				   
				   //用途
				   //费用项目
				   if(json.get("LoanUse").toString().isEmpty()){    
					   addCouse(constantUtils.failurecause_1f);      
				   }  				   
				   wrti[5]=new WorkflowRequestTableField();
				   wrti[5].setFieldName("application");
				   wrti[5].setFieldValue(json.get("LoanUse").toString());
				   wrti[5].setView(true);
				   wrti[5].setEdit(true);
				   this.loanUse = json.get("LoanUse").toString(); 
				   
				   //预计还款日期
				   if(json.get("ExpectPayDate").toString().isEmpty()){    
					   addCouse(constantUtils.failurecause_1e);        
				   }  					   
				   wrti[6]=new WorkflowRequestTableField();
				   wrti[6].setFieldName("planrepaymentdate");
				   wrti[6].setFieldValue(json.get("ExpectPayDate").toString());
				   wrti[6].setView(true);
				   wrti[6].setEdit(true);
				   this.expectPayDate=json.get("ExpectPayDate").toString();
				   
				   //借款部门
				   wrti[7]=new WorkflowRequestTableField();
				   wrti[7].setFieldName("borrowdepartment");
				   wrti[7].setFieldValue(json.get("LoanDept").toString());
				   wrti[7].setView(true);
				   wrti[7].setEdit(true);

				   //借款人组织
				   wrti[8]=new WorkflowRequestTableField();
				   wrti[8].setFieldName("borrowerorganization");
				   wrti[8].setFieldValue(json.get("LoanPersonOrg").toString());
				   wrti[8].setView(true);
				   wrti[8].setEdit(true);
				   //借款人工号
				   wrti[9]=new WorkflowRequestTableField();
				   wrti[9].setFieldName("borrowernumber");
				   wrti[9].setFieldValue(json.get("LoanPersonCode").toString());
				   wrti[9].setView(true);
				   wrti[9].setEdit(true);
				   //借款币种
				   wrti[10]=new WorkflowRequestTableField();
				   wrti[10].setFieldName("loancurrent");
				   wrti[10].setFieldValue(json.get("LoanCurrency").toString());
				   wrti[10].setView(true);
				   wrti[10].setEdit(true);
				   //借款金额
				   wrti[11]=new WorkflowRequestTableField();
				   wrti[11].setFieldName("loanamount");
				   wrti[11].setFieldValue(json.get("LoanMoney").toString());
				   wrti[11].setView(true);
				   wrti[11].setEdit(true);
				   //状态
				   wrti[12]=new WorkflowRequestTableField();
				   wrti[12].setFieldName("status");
				   wrti[12].setFieldValue(json.get("DocStatus").toString());
				   wrti[12].setView(true); 
				   wrti[12].setEdit(true); 
				   //创建人
				   wrti[13]=new WorkflowRequestTableField();
				   wrti[13].setFieldName("founder");
				   wrti[13].setFieldValue(json.get("CreatedBy").toString());
				   wrti[13].setView(true);
				   wrti[13].setEdit(true);
				   //创建时间
				   wrti[14]=new WorkflowRequestTableField();
				   wrti[14].setFieldName("createdate");
				   wrti[14].setFieldValue(json.get("CreatedOn").toString());
				   wrti[14].setView(true);
				   wrti[14].setEdit(true);
				   
				   //项目
				   if(json.get("Project").toString().isEmpty()){    
					   addCouse(constantUtils.failurecause_1b);      
				   }   
				   wrti[15]=new WorkflowRequestTableField();
				   wrti[15].setFieldName("projectname");
				   wrti[15].setFieldValue(json.get("Project").toString());
				   wrti[15].setView(true);
				   wrti[15].setEdit(true);  
				   this.project = json.get("Project").toString();
				 
				   //借款人姓名  				   
				   wrti[16]=new WorkflowRequestTableField();
				   wrti[16].setFieldName("borrowername");
				   wrti[16].setFieldValue(this.creatorId+"");
				   wrti[16].setView(true);
				   wrti[16].setEdit(true);
					log.info("借款人姓名借款人姓名");
				   //借款人一级部门
				   wrti[17]=new WorkflowRequestTableField();
				   wrti[17].setFieldName("borrowerfirstdepartment");
				   //wrti[18].setFieldValue(firstDeptId);
				   wrti[17].setFieldValue(this._1st_departmentid+"");
				   wrti[17].setView(true);
				   wrti[17].setEdit(true);
				   //借款人行政部门
				   wrti[18]=new WorkflowRequestTableField();
				   wrti[18].setFieldName("borroweradministration");
				  // wrti[19].setFieldValue(borrowerInfo.getBorrowerAdministration());
				   wrti[18].setFieldValue(this.departmentid+"");
				   wrti[18].setView(true);
				   wrti[18].setEdit(true);
					log.info("借款人行政部门借款人行政部门");
				   WorkflowRequestTableRecord[] wrtri=new WorkflowRequestTableRecord[1];
			       wrtri[0]=new WorkflowRequestTableRecord();
			       wrtri[0].setWorkflowRequestTableFields(wrti);
			       WorkflowMainTableInfo wmi=new WorkflowMainTableInfo();
			       wmi.setRequestRecords(wrtri);
					log.info("wrtriwrtri");			       
			       //添加工作流id 
			       //WorkflowBaseInfo wbi= new WorkflowBaseInfo();
			       this.workflowBaseInfo.setWorkflowId(WORKFLOW_TYPE_ID);		//workflowid 流程接口演示流程2016==38
			       this.workflowRequestInfo.setCreatorId(this.creatorId+"");		//创建人id
			       this.workflowRequestInfo.setRequestLevel("0");	//0 正常，1重要，2紧急
			       this.workflowRequestInfo.setRequestName(WORKFLOW_NAME + json.getString("LoanPerson") + "-" + CalendarUtil.getCurDate());	//流程标题
			       this.workflowRequestInfo.setWorkflowMainTableInfo(wmi);	//添加主字段数据
			       this.workflowRequestInfo.setWorkflowBaseInfo(workflowBaseInfo);
			       if(!"".equals(this.loanPersonCode)&&!"".equals(this.project)&&!"".equals(this.expenseItem)&&!"".equals(this.loanDate)&&!"".equals(expectPayDate)&&!"".equals(loanUse))
			       {
			    	   requestId = this.workflowService.doCreateWorkflowRequest(this.workflowRequestInfo, this.creatorId); 
			       }else{
			    	   insertFailDataToLoanCronTable(requestId,this.cause);
			       }
			     //人员信息未在OA中获取或者ERP中的人员工号和OA中的工号不一致到 写入到异构系统单据创建流程错误记录表中
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
	this.cause=null;
	String requestID_String=null;  
	RecordSet rs = new RecordSet();      
	if (!this.parameters.isEmpty()) {   
//		ZRLoanAppBill zRLoanAppBill = new ZRLoanAppBill();     
		String respones = ZRLoanAppBill.queryAllBillInfo(this.parameters); 
		JSONObject json = JSONObject.fromObject(respones);  
		JSONArray jsonArray = json.getJSONArray("list");
		if (jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject job = jsonArray.getJSONObject(i);
				this.docStatus =job.get("DocStatus").toString();
				if(constantUtils.docStatus_approval.equals(this.docStatus)){   //只对"核准中"的单据进行处理
				this.loanDocNo = job.get("DocNo").toString(); 
				this.loanCreatedOn =job.get("CreatedOn").toString();
				this.createdcode=job.get("CreatedCode").toString();
				this.loanPersonCode=job.get("LoanPersonCode").toString(); 
				this.loanPerson=job.get("LoanPerson").toString();  
				if (this.checkNotCreated(this.loanDocNo) == true) {   //检测是否已同步，没同步上个月数据的则进行同步操作
					try { 
						requestID_String = this.createWorkflow(); 
						 int requestId_int =Integer.parseInt(requestID_String);  
				       		if(requestId_int <0){         								//检测是否存在同步失败的单据
				       			   if(this.checkNotCreatedInLoanCronJobTable(this.loanDocNo)==true){  // 检测"借款单定时失败信息记录表"是否已经记录过同步失败数据，否则记录数据
					       				try{
					       					insertFailDataToLoanCronTable(requestID_String,this.cause); //同步单据失败时，记录数据   
					       			}
					       			 catch (Exception e){
					       				 e.printStackTrace();
					       			 }
						num++;
				       	 }
					  }
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				else{
					if(this.checkNotCreatedInLoanCronJobTable(this.loanDocNo)==false){  //检测此数据已经有同步过，则取检测"借款单定时失败信息记录表"是否已经记录过该数据，有则删除
						this.deletehadCreatedInLoanCronJobTable(this.loanDocNo);
					}
				}
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
		if(rs.getCounts()>0){
		while(rs.next()){
			this.creatorId = rs.getInt("id");
			this.creatorName = rs.getString("creatorName");
			this.departmentid = rs.getInt("departmentId");
			this.departmengName = rs.getString("departmengName");
			this.jobtitle =rs.getInt("jobTitle");   
			this.jobtitlename = rs.getString("jobtitlename");
		}
		JSONObject json = JSONObject.fromObject(
			departmentUitl.getDepartmentContruction(this.creatorId)
		);
		this._1st_departmentid =  json.getString("level1_id");
		}else{
		  addCouse(constantUtils.failurecause_1a);
		}
	}	
	
	@Override
	public boolean hasCreated(String erpBillDocNo,String oaBillDocNo) {                                             
		String sql = "select f.id as id ,f.requestId as requestId,f.approveprocessnumber as approveprocessnumber ,w.lastoperatedate as lastoperatedate from " + FORM_NAME + " f ,workflow_requestbase w where f.borrowordernumber = '" + erpBillDocNo + "' and f.requestId = w.requestId "; 
		RecordSet rs = new RecordSet();  
		rs.executeSql(sql);     
		if(rs.getCounts() > 0){   
			while(rs.next()){
			if( "".equals(rs.getString("lastoperatedate")) ){    
				return true; //已存在数据但不属于退回的单据  
			         }
			 else  {  
				 if( !("".equals(oaBillDocNo)) ) {
					 return false; //已存在数据且是在被退回单据上重新获取U9数据
				 }
			  return true ;   //已存在数据且是 另外新建单据获取U9数据操作
			  }
			}
		}
		return false;//没有数据  
	}
	

	@Override
	public boolean checkNotCreated(String erpBillDocNo) { 
		String sql = "select f.id as id,f.requestId as requestId "
				+ " from " + FORM_NAME + " f "
				+ " where f.borrowordernumber = '" + erpBillDocNo + "'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		if(rs.getCounts() > 0){
			return false ;  
		}   
		return true;
	}	
	
	@Override
	public boolean checkNotCreatedInLoanCronJobTable(String erpBillDocNo) { 
		String sql = "select l.id as id,l.requestId as requestId "
				+ " from loanBillCronJob l "
				+ " where l.docno = '" + erpBillDocNo + "'";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		if(rs.getCounts() > 0){
			   
			return false ;  
		}   
		return true;
	}

	
	public String addCouse(String failureCouse){
		this.cause =this.cause +failureCouse;
		return this.cause;
	}
	
	public void insertFailDataToLoanCronTable(String requestId_String, String cause){
		       String insert_sql="";
			   String failurecause="";
			   RecordSet rs = new RecordSet();        
			   int requestId_int =Integer.parseInt(requestId_String);
			   if(requestId_int==-1 ){failurecause =constantUtils.failurecause_1;}
			   if(requestId_int==-2 ){failurecause =constantUtils.failurecause_2;}  
			   if(requestId_int==-3 ){failurecause =constantUtils.failurecause_3;}  
			   if(requestId_int==-4 ){failurecause =constantUtils.failurecause_4;}  			   
			   if(requestId_int==-5 ){failurecause =constantUtils.failurecause_5;}  		   
			   if(requestId_int==-6 ){failurecause =constantUtils.failurecause_6;}  			   
			   if(requestId_int==-7 ){failurecause =constantUtils.failurecause_7;}  
			   if(requestId_int==-8 ){failurecause =constantUtils.failurecause_8;} 
			   
			   insert_sql ="  insert into loanBillCronJob (requestid,requestname,docno,createdon,creatorid,createname,createdcode,loanpersoncode,loanperson,departmentid,departmenname,failurecause,ishandle) "+
			                   " values ("
			                   + requestId_int+",'"
			                   + this.workflowRequestInfo.getRequestName()+"','"
			                   + this.loanDocNo+"','"
			                   + this.loanCreatedOn+"',"
			                   + this.creatorId +",'"
			                   + this.creatorName+"','"
			                   + this.createdcode+"','"
			                   + this.loanPersonCode+"','"
			                   + this.loanPerson+"',"
			                   + this.departmentid+",'"
			                   + this.departmengName+"','"
			                   + failurecause+"','"
			                   + constantUtils.Ishandle_zero
			                   + "') ";
			
		  try {
			rs.executeSql(insert_sql);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}
	
	/**
	 * @title   删除记录过同步失败数据
	 * @author  hsf
	 * @date    2017年11月29日
	 * @param   
	 * @return  boolean  
	 */
	@Override
	public boolean deletehadCreatedInLoanCronJobTable(String erpBillDocNo) {
		String sql = "delete from loanBillCronJob   where docno = '" + erpBillDocNo + "'";
		RecordSet rs = new RecordSet();
		try {
			rs.executeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
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



	/** 
	 * @title  
	 * @author hsf
	 * @date   2017年12月23日
	 * @param  
	 * @return 
	 */
	@Override
	public boolean hasCreated(String erpBillDocNo) {
		// TODO Auto-generated method stub
		return false;
	}


	
}
