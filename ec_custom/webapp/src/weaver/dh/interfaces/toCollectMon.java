package weaver.dh.interfaces;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;
import weaver.formmode.setup.ModeRightInfo;
import weaver.general.Util;
import weaver.interfaces.schedule.BaseCronJob;

import com.weaver.formmodel.util.DateHelper;

import dinghan.common.util.CalendarUtil;
import dinghan.workflow.service.QingJiaService;

public class toCollectMon extends BaseCronJob{
	
	private Log log = LogFactory.getLog(toCollectMon.class.getName());
	
	private String resourceid="";
	private String flag="0";
	private int OperatorId;
	//private String kqjSource = "kqj";
	private String currentDay = DateHelper.getCurrentDate();
	private String currentTime = DateHelper.getCurrentTime();
	private String toCollectMon = DateHelper.getCurrentMonth();
	//private Map<String,String> DkMap = new HashMap<String,String>();	//临时code
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	HashMap<String,String> cus_Map = new HashMap<String,String>();
	HashMap<String,String> hz_Map = new HashMap<String,String>();
	HashMap<String,String> Map = new HashMap<String,String>();
	HashMap<String,String> jiaBan_Map = new HashMap<String,String>();
	
	RecordSet rsA = new RecordSet();
	toCollect co  = new  toCollect();
	
	/**
	 * 初始化并执行汇总操作
	 * @param zt 
	 * @param hrid
	 * @param dayB
	 */
	public void getJump(String zt,String hrid,String dayB,int opreatorId){
		
		flag = "1";
		String[] hid  = new String[]{"0"}; 
		if(!"".equals(Util.null2String(hrid))){
			hid = hrid.split(",");
		}
        for(int n=0;n<hid.length;n++){
        	if(!"".equals(Util.null2String(hid[n]))){
        		resourceid +=hid[n]+",";
        	}
		}
		String getPeople = "select name from uf_hr_userinfo1 where Company = '"+zt+"'";
		RecordSet r = new RecordSet();
		r.executeSql(getPeople);
		while(r.next()){
			String name = Util.null2String(r.getString("name"));
			if(!"".equals(name)){
				resourceid +=name+",";
			}
		}
		if(resourceid.endsWith(",")){
			resourceid = resourceid.substring(0, resourceid.length()-1);
		}
		this.toCollectMon = dayB;
		this.OperatorId = opreatorId;
		this.execute();
	}
	
	@Override
	public void execute() {
		getData();
		getHrmCusData();
		RecordSet hrm =  new RecordSet();
		RecordSet rsG =  new RecordSet();
		RecordSet rs =  new RecordSet();
		RecordSet rs_ResetRight = new RecordSet();
		
		ModeRightInfo modeRightInfo = new ModeRightInfo();
		
		//数据唯一性，某个人的某一天数据唯一
		//String sqlHrm = "select id,workcode from HrmResource where status in (1,2,3,5)";
		String sqlHrm = "select id,workcode,jobtitle from HrmResource where id in ("+resourceid+")";
		if("0".equals(flag)){
			sqlHrm = "select id,workcode,jobtitle from HrmResource where status in (0,1,2,3,5)";
		}
		
		String sqlAllCurMonthRecord = "";
		
		hrm.executeSql(sqlHrm);
		while(hrm.next()){
			String hid = Util.null2String(hrm.getString("id"));
			getJiaBan(hid);
			String workcode = Util.null2String(hrm.getString("workcode"));
			//String exists =  Util.null2String(hz_Map.get(hid));
			String getSql = "select * from uf_kqhzmx where kqrq like '%"+toCollectMon+"%' and xm = '"+hid+"'";
			rsG.executeSql(getSql);
			String excSql = "";
			//String jbxs = "";//加班系数
			String trgs = "0";//他人工伤
			String brj = "0";//哺乳假
			String wdk = "0";//忘打卡
			BigDecimal kg = new BigDecimal("0");//旷工
			String xm = hid;//姓名  id
			BigDecimal cdsj = new BigDecimal("0");//迟到总时间
			BigDecimal ztsj = new BigDecimal("0");//早退总时间
			String kqlb =  Util.null2String(cus_Map.get(hid+"_KQT"));//考勤类别
			String ssgs = Util.null2String(cus_Map.get(hid+"_COM"));//一级部门;//所属公司
			String yjbm =  Util.null2String(cus_Map.get(hid+"_DEPT1"));//一级部门
			String ejbm =  Util.null2String(cus_Map.get(hid+"_DEPT2"));//二级部门
			//String sjbm =  Util.null2String(cus_Map.get(hid+"_DEPT3"));//三级部门
			String zt =  Util.null2String(cus_Map.get(hid+"_RZZT"));//状态
			String join =  Util.null2String(cus_Map.get(hid+"_join"));//入职日期
			String leave =  Util.null2String(cus_Map.get(hid+"_leave"));//离职日期
			String synx =  Util.null2String(cus_Map.get(hid+"_synx"));//synx
			String sytx =  Util.null2String(cus_Map.get(hid+"_sytx"));//sytx
			BigDecimal jb_ztx = new BigDecimal(this.null2o(jiaBan_Map.get(hid+"_ztx")));
			BigDecimal jb_bztx = new BigDecimal(this.null2o(jiaBan_Map.get(hid+"_bztx")));
			String dstr = toCollectMon+"-01";
			//log.error("dstr :: " + dstr);
			String lastDate = getLastDateOfMon(dstr);
			float ycqts = DateHelper.getDaysBetween(lastDate,dstr,true);
			if(DateHelper.isWorkDay(DateHelper.parseDate(dstr))){
				ycqts = ycqts+1;
			}
			if(!"".equals(join)){
				String JoinMonth = join.substring(0, 7);
				if(toCollectMon.equals(JoinMonth)){
				   ycqts = DateHelper.getDaysBetween(lastDate,join,true);
				   if(DateHelper.isWorkDay(DateHelper.parseDate(join))){
						ycqts = ycqts+1;
					}
				}
			}if(!"".equals(leave)){
				String leaveMonth = leave.substring(0, 7);
				if(toCollectMon.equals(leaveMonth)){
					ycqts = DateHelper.getDaysBetween(leave,toCollectMon+"-01",true);
					if(DateHelper.isWorkDay(DateHelper.parseDate(toCollectMon+"-01"))){
						ycqts = ycqts+1;
					}
				}
			}
			
			if("0".equals(zt)){
				zt = "在职";
			}else{
				zt = "离职";
			}
			
			String gw = Util.null2String(hrm.getString("jobtitle"));//岗位
			BigDecimal jbgs =jb_bztx;//加班工时
			String sj = "0";//事假
			String bj = "0";//病假
			String nxj = "0";//年休假
			String txj = "0";//调休假
			String hj = "0";//婚假
			String sangj = "0";//丧假
			String cj = "0";//产假
			String cjj = "0";//产检假
			String lcj = "0";//流产假
			String pcj = "0";//陪产假
			String jyj = "0";//节育假
			String jsj = "0";//计生假
			String grgs = "0";//个人工伤
			int cdTims = 0;//迟到次数
			int if10 = 0;
			int zaoTims = 0;//早退次数
			while(rsG.next()){
				 trgs = new BigDecimal(this.null2o(rsG.getString("trgs"))).add(new BigDecimal(trgs))+"";;//他人工伤
				 brj =  new BigDecimal(this.null2o(rsG.getString("brj"))).add(new BigDecimal(brj))+"";;//哺乳假
				 wdk = new BigDecimal(this.null2o(rsG.getString("wdk"))).add(new BigDecimal(wdk))+"";//忘打卡
				 kg = new BigDecimal(this.null2o(rsG.getString("kg"))).add(kg);//旷工
				 BigDecimal cdTemp = new BigDecimal(this.null2o(rsG.getString("cd")));//迟到
				 cdsj = cdsj.add(cdTemp);//迟到时间和
				 if(cdTemp.doubleValue()<=10&&cdTemp.doubleValue()>0){
					 cdTims++;
					 if10++;
				 }else if(cdTemp.doubleValue()>10&&cdTemp.doubleValue()<=30){
					 cdTims++;
				 }else if(cdTemp.doubleValue()>30&&cdTemp.doubleValue()<=60){
					 cdTims = cdTims+2;
				 }else if(cdTemp.doubleValue()>60){
					  kg = cdTemp.divide(new BigDecimal(60)).add(kg);//迟到算在旷工上的
				 }
				 BigDecimal zaotTemp = new BigDecimal(this.null2o(rsG.getString("zaot")));//早退时间
				 ztsj = ztsj.add(zaotTemp);
				 if(zaotTemp.doubleValue()<=30&&zaotTemp.doubleValue()>0){
					 zaoTims++; 
				 }if(zaotTemp.doubleValue()>30&&zaotTemp.doubleValue()<=60){
					 zaoTims = zaoTims+2;
				 }if(zaotTemp.doubleValue()>60){
					 kg = zaotTemp.divide(new BigDecimal(60)).add(kg);//早退算在旷工上的
				 }
				 //jbgs = new BigDecimal(this.null2o(rsG.getString("jbgs"))).add(jbgs);//加班工时
				 sj = new BigDecimal(this.null2o(rsG.getString("sj"))).add(new BigDecimal(sj))+"";//事假
				 bj = new BigDecimal(this.null2o(rsG.getString("bj"))).add(new BigDecimal(bj))+"";//病假
				 nxj = new BigDecimal(this.null2o(rsG.getString("nxj"))).add(new BigDecimal(nxj))+"";//年休假
				 txj = new BigDecimal(this.null2o(rsG.getString("txj"))).add(new BigDecimal(txj))+"";//调休假
				 hj = new BigDecimal(this.null2o(rsG.getString("hj"))).add(new BigDecimal(hj))+"";//婚假
				 sangj = new BigDecimal(this.null2o(rsG.getString("sangj"))).add(new BigDecimal(sangj))+"";//丧假
				 cj = new BigDecimal(this.null2o(rsG.getString("cj"))).add(new BigDecimal(cj))+"";//产假
				 cjj = new BigDecimal(this.null2o(rsG.getString("cjj"))).add(new BigDecimal(cjj))+"";//产检假
				 lcj = new BigDecimal(this.null2o(rsG.getString("lcj"))).add(new BigDecimal(lcj))+"";//流产假
				 jyj = new BigDecimal(this.null2o(rsG.getString("jyj"))).add(new BigDecimal(jyj))+"";//节育假
				 jsj = new BigDecimal(this.null2o(rsG.getString("jsj"))).add(new BigDecimal(jsj))+"";;//计生假
				 grgs = new BigDecimal(this.null2o(rsG.getString("grgs"))).add(new BigDecimal(grgs))+"";//个人工伤
			}
			if(if10>=3){
				cdTims = cdTims -3;
			}else{	//增加判断 ，当月10分钟以内迟到，三级以内的次数不计入总数 -- 2017-03-05 by zhangxiaoyu
				cdTims = cdTims -if10;	
			}
			
			kg = kg.multiply(new BigDecimal("1"));
			int h1 = kg.intValue();
			double kg1=kg.doubleValue();
			if (kg1 < h1 + 0.25) {
				kg1 = h1;
			} else if (kg1 < h1 + 0.75) {
				kg1 = h1 + 0.5;
			} else {
				kg1 = h1 + 1;
			}
			
			Calendar calendar = Calendar.getInstance();
			try {
				calendar.setTime(sdf.parse(this.toCollectMon));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			//calendar.add(Calendar.MONTH, -1);
			//String curDate = CalendarUtil.getCurDate();
			int curMonth = Integer.parseInt(dstr.substring(5,7));
			//int curMonth = calendar.get(Calendar.MONTH) + 1;  
			
			//log.error("当前月份 :: "+ curMonth);
			String preMonthStr = CalendarUtil.moveDate(dstr, 0, -1, 0);
			//log.error("preMonthStr :: " + preMonthStr);
			int premonth = Integer.parseInt(preMonthStr.substring(5,7));		//获取前一个月份，传入计算剩余年休、调休方法
			int preYear = Integer.parseInt(preMonthStr.substring(0,4));
			QingJiaService qingjiaService = new QingJiaService();
			double lastMonthTiaoXiu = 0.00;
			double lastMonthNianXiu = 0.00;
			
			String sql = "";
			
			RecordSet recordSet = new RecordSet();
			try {
				
				lastMonthTiaoXiu = qingjiaService.countLastTiaoXiuorNianXiuHour(Integer.parseInt(hid), QingJiaService.TiaoXiu, "",preYear, curMonth);
				lastMonthNianXiu = qingjiaService.countLastTiaoXiuorNianXiuHour(Integer.parseInt(hid), QingJiaService.NianXiu, "",preYear, curMonth);
				log.error("lastMonthTiaoXiu :: " + lastMonthTiaoXiu + " -- lastMonthNianXiu :: " + lastMonthNianXiu);
				log.error("curMonth :: " + curMonth);
				if(curMonth == 12){
					
					double syNXHour = 0;
					int curYear = calendar.get(Calendar.YEAR);
					int joinYear = Integer.parseInt(join.substring(0, 4));
					int yearIn = curYear - joinYear;
					//double nianXiu_neo = 0;
					if(yearIn >= 20){
						syNXHour = 120;
					}else if(yearIn >= 10){
						syNXHour = 80;
					}else if(yearIn>0){
						syNXHour = 40;
					}else{	
						Calendar _curMonth = GregorianCalendar.getInstance();
						Calendar _joinMonth = GregorianCalendar.getInstance();
						//SimpleDateFormat sdf = ;
						try {
							_curMonth.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(curYear+"-"+curMonth+"-31"));
							_joinMonth.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(join));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
						long betwean = _curMonth.getTimeInMillis() - _joinMonth.getTimeInMillis();
						double daysForCount = (double)betwean/1000/60/60/24/365*5;
						daysForCount = new BigDecimal(daysForCount).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
						syNXHour = 8*daysForCount;
					}
					lastMonthTiaoXiu += lastMonthNianXiu;
					lastMonthNianXiu = syNXHour;
				}
				
				sql = "update uf_hr_userinfo1 set SYTiaoXiuJia = '"+lastMonthTiaoXiu+"',"
						+ "SYNianXiuJia = '"+lastMonthNianXiu+"' where Name = " + hid;
				
				recordSet.executeSql(sql);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			sytx = lastMonthTiaoXiu+"";
			synx = lastMonthNianXiu+"";
			
			//删除当前汇总记录
			String delMonSql = "delete from uf_kqhz where hzyf= '"+toCollectMon+"' and xm='"+xm+"'";
			
			RecordSet rsDelMon = new RecordSet();
			
			rsDelMon.executeSql(delMonSql);
			
			//if("".equals(exists)){//不存在
				excSql = "insert into uf_kqhz(jbztx,synx,sytx,ycqts,hzyf,kqrq,trgs,brj,gh,wdk,kg,xm,gw,cdTime,cd,kqlb,ztTime,zt,ssgs,yjbm,ejbm,rzzt," +
					"jbgs,sj,bj,nxj,txj,pcj,hj,sangj,cj,cjj,lcj,jyj,jsj,grgs," +
				"formmodeid,modedatacreater,modedatacreatedate,modedatacreatetime)"
					+ " values('"+jb_ztx+"','"+synx+"','"+sytx+"','"+ycqts+"','"+toCollectMon+"','"+currentDay+"'," +
						"'"+trgs+"','"+brj+"','"+workcode+"','"+wdk+"','"+kg1+"','"+xm+"','"+gw+"',"
								+ "'"+cdsj+"','"+cdTims+"','"+kqlb+"','"+ztsj+"','"+zaoTims+"','"+ssgs+"','"+yjbm+"'," +
							"'"+ejbm+"','"+zt+"','"+jbgs+"','"+sj+"','"+bj+"','"+nxj+"','"+txj+"','"+pcj+"',"+
								"'"+hj+"','"+sangj+"','"+cj+"','"+cjj+"','"+lcj+"','"+jyj+"','"+jsj+"','"+grgs+"'," +
									"'61',"+this.OperatorId+",'"+currentDay+"','"+currentTime+"')";
			/*}else{//存在
				excSql = "update uf_kqhz set trgs='"+trgs+"',brj='"+brj+"',wdk='"+wdk+"',kg='"+kg1+"',cd='"+cdTims+"',cdTime='"+cdsj+"',"+
							"kqlb='"+kqlb+"',ztTime='"+ztsj+"',zt='"+zaoTims+"',ssgs='"+ssgs+"',yjbm='"+yjbm+"',ejbm='"+ejbm+"',rzzt='"+zt+"'," +
								"gw='"+gw+"',jbgs='"+jbgs+"',sj='"+sj+"',bj='"+bj+"',nxj='"+nxj+"',pcj='"+pcj+"'," +
									"txj='"+txj+"',hj='"+hj+"',sangj='"+sangj+"',cj='"+cj+"',cjj='"+cjj+"',lcj='"+lcj+"',jyj='"+jyj+"',jsj='"+jsj+"'," +
										"grgs='"+grgs+"',ycqts='"+ycqts+"',synx='"+synx+"',sytx='"+sytx+"',jbztx='"+jb_ztx+"'" +
												" where hzyf= '"+toCollectMon+"' and xm='"+xm+"'";
			}*/
			rs.executeSql(excSql);
			//重置权限
			sqlAllCurMonthRecord = "select id from uf_kqhz where hzyf = '"+toCollectMon+"' and xm = '"+xm+"'";
			
			rs_ResetRight.executeSql(sqlAllCurMonthRecord);
			
			while(rs_ResetRight.next()){
				modeRightInfo = new ModeRightInfo();
				modeRightInfo.rebuildModeDataShareByEdit(OperatorId, 61, rs_ResetRight.getInt("id"));
			}
		}
	}
	
	/**
	 * 获取月的最后一天
	 * @param dstr
	 * @return
	 */
	private String getLastDateOfMon(String dstr) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		String lastdate = ""; 
		try {
			Date date = sdf.parse(dstr);
			lastdate = DateHelper.getLastDayOfMonthWeek(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lastdate;
	}
	
	/**
	 * 获取汇总信息
	 */
	public void getData(){
		String sql = "select gh,xm from uf_kqhz where hzyf = '"+toCollectMon+"'";
		rsA.executeSql(sql);
		while(rsA.next()){
			String code = Util.null2String(rsA.getString("gh"));
			String id = Util.null2String(rsA.getString("xm"));
			hz_Map.put(id, code);
		}
	}
	/**
	 * 
	 * @param str
	 * @return
	 */
	public String null2o(String str){
		String returnSt = str;
		if(str==null||"".equals(str)){
			returnSt = "0";
		}
		return returnSt;
	}
	/**
	 * 加班
	 */
	private void getJiaBan(String hrmid) {
		jiaBan_Map.clear();
		String sql = "select hrmno,jbrq,xq,hdgs,jbxs,sfztx from uf__jb_temp where jbrq like '%"+toCollectMon+"%' and hrmid = '"+hrmid+"'";
		rsA.executeSql(sql);
		BigDecimal ztx = new BigDecimal("0");
		BigDecimal bztx = new BigDecimal("0");
		while(rsA.next()){
			String hdgs = Util.null2o(rsA.getString("hdgs"));//核定工时
			//String jbxs = Util.null2o(rsA.getString("jbxs"));//加班系数
			String sfztx = Util.null2String(rsA.getString("sfztx"));//是否加班转调休
			if("0".equals(sfztx)){//是
				ztx = ztx.add(new BigDecimal(hdgs));
			}else if("1".equals(sfztx)){//否
				bztx = bztx.add(new BigDecimal(hdgs));
			}
		}
		jiaBan_Map.put(hrmid+"_ztx", ztx+"");
		jiaBan_Map.put(hrmid+"_bztx", bztx+"");
		
	}
	/**
	 * 人员基本信息
	 */
	private void getHrmCusData() {
		String sql = "select Name,StartWorkTime,EndWorkTime,AmStartWorkTime,PmEndWorkTime,KaoQinType,Company,DeptOneName,DeptTwoName,DeptThreeName,InCompany, " +
				"DeptOneNameText,DeptTwoNameText,DeptThreeNameText,JoinDate,LeaveDate,SYNianXiuJia,SYTiaoXiuJia from uf_hr_userinfo1";
		RecordSet cusRs = new RecordSet();
		cusRs.executeSql(sql);
		while(cusRs.next()){
			String id = Util.null2String(cusRs.getString("Name"));
			String StartWorkTime = Util.null2String(cusRs.getString("StartWorkTime"));
			String EndWorkTime = Util.null2String(cusRs.getString("EndWorkTime"));
			String amE = Util.null2String(cusRs.getString("AmStartWorkTime"));//上午下班时间
			String pmS = Util.null2String(cusRs.getString("PmEndWorkTime"));//下午上班时间
			String KaoQinType = Util.null2String(cusRs.getString("KaoQinType"));
			String Company = Util.null2String(cusRs.getString("Company"));//所属公司
			String DeptOneName = Util.null2String(cusRs.getString("DeptOneNameText"));//一级部门
			String DeptTwoName = Util.null2String(cusRs.getString("DeptTwoNameText"));//二级部门
			String DeptThreeName = Util.null2String(cusRs.getString("DeptThreeNameText"));//三级部门
			String InCompany = Util.null2String(cusRs.getString("InCompany"));//任职状态
			String JoinDate = Util.null2String(cusRs.getString("JoinDate"));//入职日期
			String LeaveDate = Util.null2String(cusRs.getString("LeaveDate"));//离职日期
			String SYNianXiuJia = Util.null2String(cusRs.getString("SYNianXiuJia"));//剩余年休
			String SYTiaoXiuJia = Util.null2String(cusRs.getString("SYTiaoXiuJia"));//剩余调休
			cus_Map.put(id+"_18", StartWorkTime);
			cus_Map.put(id+"_19", EndWorkTime);
			cus_Map.put(id+"_amE", amE);
			cus_Map.put(id+"_pmS", pmS);
			cus_Map.put(id+"_KQT", KaoQinType);
			cus_Map.put(id+"_COM", Company);
			cus_Map.put(id+"_DEPT1", DeptOneName);
			cus_Map.put(id+"_DEPT2", DeptTwoName);
			cus_Map.put(id+"_DEPT3", DeptThreeName);
			cus_Map.put(id+"_RZZT", InCompany);
			cus_Map.put(id+"_join", JoinDate);
			cus_Map.put(id+"_leave", LeaveDate);
			cus_Map.put(id+"_synx", SYNianXiuJia);
			cus_Map.put(id+"_sytx", SYTiaoXiuJia);
		}
	}
	
	/*public static void main(String args[]){ 
		System.out.println(DateHelper.getDaysBetween("2017-01-31","2017-01-01",true));
		System.out.println("11:11:00".compareTo(""));
		System.out.println("".compareTo("11:00:11"));
		System.out.println("13:00:00".compareTo("11:00:11"));
	}
	 */
}
