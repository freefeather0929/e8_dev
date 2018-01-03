package dinghan.zrac.ga;

import org.apache.taglibs.standard.lang.jpath.adapter.Convert;

import net.sf.json.JSONObject;
import weaver.conn.RecordSet;
import weaver.soa.workflow.request.RequestInfo;
import weaver.soa.workflow.request.RequestService;
import weaver.workflow.webservices.WorkflowBaseInfo;
import weaver.workflow.webservices.WorkflowMainTableInfo;
import weaver.workflow.webservices.WorkflowRequestInfo;
import weaver.workflow.webservices.WorkflowRequestTableField;
import weaver.workflow.webservices.WorkflowRequestTableRecord;
import weaver.workflow.webservices.WorkflowService;
import weaver.workflow.webservices.WorkflowServiceImpl;
public class CreateWorkflow {
   
	public static void createRequest(JSONObject json)throws Exception{
	   //主表字段
	   WorkflowRequestTableField[] wrti = new WorkflowRequestTableField[22]; //字段信息
	   //审批流程单号
	   wrti[0]=new WorkflowRequestTableField();
	   wrti[0].setFieldName("approveprocessnumber"); 
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
	   wrti[13].setFieldValue("核准中");
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
	   
	   //调用获取借款人信息方法
	  // BorrowerInfo borrowerInfo=new BorrowerInfo();
	   //borrowerInfo=queryBorrowerInfo(json.get("DocNo").toString());
	   
	   //借款人姓名
	   wrti[17]=new WorkflowRequestTableField();
	   wrti[17].setFieldName("borrowername");
	   //wrti[17].setFieldValue(borrowerInfo.getBorrowerName());
	   wrti[17].setFieldValue("测试");
	   wrti[17].setView(true);
	   wrti[17].setEdit(true);
	   
	   //调用获取一级部门id方法
	  // String firstDeptId=Convert.toString(queryFirstdept(borrowerInfo.getBorrowerAdministration()));
	   
	   //借款人一级部门
	   wrti[18]=new WorkflowRequestTableField();
	   wrti[18].setFieldName("borrowerfirstdepartment");
	   //wrti[18].setFieldValue(firstDeptId);
	   wrti[18].setFieldValue("4");
	   wrti[18].setView(true);
	   wrti[18].setEdit(true);
	   //借款人行政部门
	   wrti[19]=new WorkflowRequestTableField();
	   wrti[19].setFieldName("borroweradministration");
	  // wrti[19].setFieldValue(borrowerInfo.getBorrowerAdministration());
	   wrti[19].setFieldValue("3");
	   wrti[19].setView(true);
	   wrti[19].setEdit(true);
	   //银行卡类型
	   wrti[20]=new WorkflowRequestTableField();
	   wrti[20].setFieldName("bankcardtype");
	  // wrti[20].setFieldValue(json.get("DocNo").toString());
	   wrti[20].setFieldValue("建行");
	   wrti[20].setView(true);
	   wrti[20].setEdit(true);
	  //银行卡号
	   wrti[21]=new WorkflowRequestTableField();
	   wrti[21].setFieldName("bankcardnumber");
	   //wrti[21].setFieldValue(json.get("DocNo").toString());
	   wrti[21].setFieldValue("123");
	   wrti[21].setView(true);
	   wrti[21].setEdit(true);
	   
	   WorkflowRequestTableRecord[] wrtri=new WorkflowRequestTableRecord[1];
       wrtri[0]=new WorkflowRequestTableRecord();
       wrtri[0].setWorkflowRequestTableFields(wrti);
       WorkflowMainTableInfo wmi=new WorkflowMainTableInfo();
       wmi.setRequestRecords(wrtri);
       
       
       //添加工作流id
       WorkflowBaseInfo wbi=new WorkflowBaseInfo();
       wbi.setWorkflowId("236");//workflowid 流程接口演示流程2016==38
       WorkflowRequestInfo wri = new WorkflowRequestInfo();//流程基本信息
       //wri.setCreatorId(borrowerInfo.getBorrowerId());//创建人id
       wri.setCreatorId("776");//创建人id
       wri.setRequestLevel("2");//0 正常，1重要，2紧急
       wri.setRequestName("流程测试");//流程标题
       wri.setWorkflowMainTableInfo(wmi);//添加主字段数据
       wri.setWorkflowBaseInfo(wbi);
     
       
       WorkflowServiceImpl wfs=new WorkflowServiceImpl();
       String rid=wfs.doCreateWorkflowRequest(wri, 776);     
	}
	
	  /***
     * 根据借款人工号从人力资源基本信息表查询借款人基本信息
     * @param borrowerNumber
     * @return
     */
    public static BorrowerInfo queryBorrowerInfo(String borrowerNumber){
           BorrowerInfo info=new BorrowerInfo();   	 
           String sql = "select id,lastname,departmentid from HrmResource where workcode ="+ borrowerNumber;               
           RecordSet rs=new RecordSet();
           rs.execute(sql);
           while(rs.next()){
        	  info.setBorrowerId(rs.getString("id")); // 获得指定字段的数据
        	  info.setBorrowerName(rs.getString("lastname"));
        	  info.setBorrowerAdministration(rs.getString("departmentid"));
           }         	
    	return info;
    }
 
    /***
     * 根据部门id返回借款人一级部门id
     * @param borrowerNumber
     * @return
     */
    public static int queryFirstdept(String deptId){
    	RecordSet recordSet=new RecordSet();
    	int tlevel = 0;
		int deptLelel = 0;
		int supDeptId = 0;
		String supDeptMark;
		String sql = "select top 1 id,departmentmark,departmentname,subcompanyid1,supdepid,tlevel from HrmDepartment where id ="+ deptId;
		recordSet.executeSql(sql);
		if(recordSet.first()){
			tlevel = recordSet.getInt("tlevel");
			supDeptId = recordSet.getInt("id");
			supDeptMark = recordSet.getString("departmentmark");
		}
		/* out.println("tlevel :: "+tlevel);
		out.println("supDeptId :: "+supDeptId);
		out.println("supDeptMark :: "+supDeptMark); */
		RecordSet recordSet2=new RecordSet();
		if(tlevel>2){
			int i = tlevel-2;
			while(i>-1){
				sql = "select top 1 id,departmentmark,departmentname,subcompanyid1,supdepid,tlevel from HrmDepartment where id ="+ deptId;
				recordSet2.executeSql(sql);
				if(recordSet2.first()){
					supDeptMark = recordSet2.getString("departmentmark");	
					supDeptId = recordSet2.getInt("id");	
					deptId = String.valueOf(recordSet2.getInt("supdepid"));	
				}
				//out.println("deptId :: "+(i)+" :: "+supDeptMark);
				i--;
			}
		}  
		return supDeptId;
    }
}
