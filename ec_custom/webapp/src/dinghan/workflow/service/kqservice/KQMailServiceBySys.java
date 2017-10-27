package dinghan.workflow.service.kqservice;

import java.util.HashMap;
import java.util.Map;
import weaver.conn.RecordSet;
import weaver.email.EmailWorkRunnable;

/**
 * 功能：使用OA系统去群发邮件接口发送考勤汇总邮件
 * @author zhangxiaoyu
 */
public class KQMailServiceBySys {
	private String tip;	//提示信息
	private String userCode;	//工号
	private String monthStr;	//月份（.etc 2017-03）
	
	Map<String,String> mailInfoMap = new HashMap<String, String>();
	
	public KQMailServiceBySys(){
		
	}
	
	public void executeMail(){
		
		createKQCollectMailInfo(this.userCode,this.monthStr);
		//KQMail mail = new KQMail();
		//System.out.println("mail from address ："+this.mailInfoMap.get("mailto"));
		//System.out.println("mail from User Name ："+this.mailInfoMap.get("mailtoName"));
		
		String subject = this.mailInfoMap.get("subject");
		String content = "<br/>"+this.tip +"<br/><br/>"+ this.mailInfoMap.get("content");
		
		new Thread(new EmailWorkRunnable(this.mailInfoMap.get("mailto")+",",subject , content)).start();
	}
	
	/**
	 * 功能：创建考勤汇总邮件信息
	 * @param userCode -- 员工工号
	 * @param month -- 月份[格式： yyyy-MM ]
	 * @return mailInfoMap -- 邮箱信息：keys[ "mailto" : 接收人邮箱地址，"mailtoName" : 接收人姓名 ，"content" ：邮件内容（html），"subject" : 邮件标题   ]
	 */
	private void createKQCollectMailInfo(String userCode,String month){
		
		RecordSet hrrs = new RecordSet();
		
		String _userCode = userCode;
		String _month = month;
		
		String mailto = "";
		String mailtoName = "";
		
		String Sql = "select top 1 h.Code,h.NameCN,h.Mail,h.Company,h.KaoQinType,h.DeptOneNameText,h.DeptTwoNameText,h.DeptThreeNameText,h.InCompany,h.Post,"
				+ "k.hzyf,k.ycqts,k.jbgs,k.jbztx,k.sj,k.bj,k.hj,k.sangj,k.cj,k.cjj,k.lcj,k.jyj,k.jsj,k.grgs,k.kg,k.trgs,k.wdk,k.cd,k.zt,k.brj,k.nxj,k.txj,k.synx,k.sytx"
				+ " from uf_kqhz k,uf_hr_userinfo1 h where h.Code = k.gh and h.Code = '" +_userCode+ "' and k.hzyf = '"+ _month +"' order by ";
		
		hrrs.executeSql(Sql);

		mailInfoMap.clear();

		String mailContent = "<table align=\"center\" border=\"1\" cellpadding=\"5\" style=\"width:600px;border-collapse:collapse;font-size:12px;\">";
		
		while(hrrs.next()){
			mailto = hrrs.getString("Mail");
			
			mailtoName = hrrs.getString("NameCN");
			
			mailContent += "<tr>";	//标题
			mailContent += "<td colspan=\"4\" style=\"background-color: #1e90ff\">"
									+"<span style=\"display: block; border-width: 0;font-size:14px;font-weight:bold;text-align:center;color:#ffffff\">月考勤汇总统计表（系统邮件，请勿回复！）</span>"
								+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">工号：</td>";
			mailContent += "<td style=\"width:30%;\">"+hrrs.getString("Code")+"</td>";
			mailContent += "<td style=\"width:20%;\">姓名：</td>";
			mailContent += "<td style=\"width:30%;\">"+hrrs.getString("NameCN")+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">所属帐套：</td>";
			mailContent += "<td style=\"width:30%;\">"+getAccountName(hrrs.getString("Company"))+"</td>";
			mailContent += "<td style=\"width:20%;\">考勤类型：</td>";
			mailContent += "<td style=\"width:30%;\">"+this.getKaoQinType(Integer.parseInt(hrrs.getString("KaoQinType")))+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">任职状态：</td>";
			
			if("0".equals(hrrs.getString("InCompany"))){
				mailContent += "<td style=\"width:30%;\">在职</td>";
			}else{
				mailContent += "<td style=\"width:30%;\">离职</td>";
			}
			
			mailContent += "<td style=\"width:20%;\">岗位：</td>";
			mailContent += "<td style=\"width:30%;\">"+hrrs.getString("Post")+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">考勤月份：</td>";
			mailContent += "<td style=\"width:30%;\">"+hrrs.getString("hzyf")+"</td>";
			mailContent += "<td style=\"width:20%;\">应出勤天数：</td>";
			mailContent += "<td style=\"width:30%;\">"+hrrs.getDouble("ycqts")+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">加班工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+ (hrrs.getDouble("jbgs")<0?0.00:hrrs.getDouble("jbgs"))  +"</td>";
			mailContent += "<td style=\"width:20%;\">加班转调休：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("jbztx")<0?0.00:hrrs.getDouble("jbztx"))+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">事假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("sj")<0?0.00:hrrs.getDouble("sj"))+"</td>";
			mailContent += "<td style=\"width:20%;\">病假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("bj")<0?0.00:hrrs.getDouble("bj"))+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">婚假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("hj")<0?0.00:hrrs.getDouble("hj"))+"</td>";
			mailContent += "<td style=\"width:20%;\">丧假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("sangj")<0?0.00:hrrs.getDouble("sangj"))+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">产假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("cj")<0?0.00:hrrs.getDouble("cj"))+"</td>";
			mailContent += "<td style=\"width:20%;\">产检假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("cjj")<0?0.00:hrrs.getDouble("cjj"))+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">流产假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("lcj")<0?0.00:hrrs.getDouble("lcj"))+"</td>";
			mailContent += "<td style=\"width:20%;\">节育假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("jyj")<0?0.00:hrrs.getDouble("jyj"))+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">计划生育假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("jsj")<0?0.00:hrrs.getDouble("jsj"))+"</td>";
			mailContent += "<td style=\"width:20%;\">个人工伤假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("grgs")<0?0.00:hrrs.getDouble("grgs"))+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">旷工工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("kg")<0?0.00:hrrs.getDouble("kg"))+"</td>";
			mailContent += "<td style=\"width:20%;\">他人工伤假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("trgs")<0?0.00:hrrs.getDouble("trgs"))+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">忘打卡次数：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("wdk")<0?0.00:hrrs.getDouble("wdk"))+"</td>";
			mailContent += "<td style=\"width:20%;\">迟到次数：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("cd")<0?0.00:hrrs.getDouble("cd"))+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">早退次数：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("zt")<0?0.00:hrrs.getDouble("zt"))+"</td>";
			mailContent += "<td style=\"width:20%;\">哺乳假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("brj")<0?0.00:hrrs.getDouble("brj"))+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">年休假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("nxj")<0?0.00:hrrs.getDouble("nxj"))+"</td>";
			mailContent += "<td style=\"width:20%;\">调休假工时：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("txj")<0?0.00:hrrs.getDouble("txj"))+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td style=\"width:20%;\">剩余年休假：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("synx")<0?0.00:hrrs.getDouble("synx"))+"</td>";
			mailContent += "<td style=\"width:20%;\">剩余调休假：</td>";
			mailContent += "<td style=\"width:30%;\">"+(hrrs.getDouble("sytx")<0?0.00:hrrs.getDouble("sytx"))+"</td>";
			mailContent += "</tr>";
			
			mailContent += "<tr>";
			mailContent += "<td colspan=\"4\" style=\"text-align:center\">";
			mailContent += "<a target=\"_blank\" style=\"color:#ff0000\" href=\"http://oa.dinghantech.com/login/Login.jsp?gopage=/formmode/search/CustomSearchBySimple.jsp?customid=79\">";
			mailContent += "点击查看详细考勤明细</a>";
			mailContent += "</td>";
			mailContent += "</tr>";
			
		}
		
		mailContent += "</table>";
		
		this.mailInfoMap.put("mailto", mailto);
		this.mailInfoMap.put("mailtoName", mailtoName);
		this.mailInfoMap.put("content", mailContent);
		this.mailInfoMap.put("subject", _month + "月度考勤汇总，请核对！如果异常，请及时反馈给考勤员。");

	}
	
	private String getAccountName(String id){
		RecordSet rs = new RecordSet();
		String sql = "select company from uf_hr_company where id = " + id;
		rs.executeSql(sql);
		String companyName = "";
		while(rs.next()){
			companyName = rs.getString("company");
		}
		return companyName;
	}
	
	private String getKaoQinType(int id){
		
		String type = "";
		switch(id){
			case 0 :
				type = "非弹性制";
				break;
			case 1 :
				type = "弹性制";
				break;
			case 2 :
				type = "免打卡";
				break;	
			case 3 :
				type = "长期驻外";
				break;
		}
		
		return type;
		
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getMonthStr() {
		return monthStr;
	}

	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}
	
}
