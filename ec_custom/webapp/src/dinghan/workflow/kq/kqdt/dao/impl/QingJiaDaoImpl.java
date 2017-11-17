package dinghan.workflow.kq.kqdt.dao.impl;

import java.util.ArrayList;
import java.util.List;

import dinghan.workflow.beans.QingJia;
import dinghan.workflow.kq.kqdt.dao.QingJiaCheckDTDao;
import weaver.conn.RecordSet;

public class QingJiaDaoImpl extends KQDetailBaseImpl<QingJia> implements QingJiaCheckDTDao{
	public static final String Qingjia_DT3_FormName = "formtable_main_89_dt3";
	@Override
	public boolean add(QingJia qingjia_dt3) {
		String sql = "INSERT INTO "+Qingjia_DT3_FormName+" (userid,mainid,rq,qjlb,kssj,jssj,hdzt,xq,hdkssj,hdjssj,dkjl,appnom,gh,hdgs,jdid,bzgzsj,kqhdyf,lcspjsrq,kqy,row_id)"
		 + " values("
		 	+ "'" + qingjia_dt3.getUserid() + "',"
		 		+ "'" + qingjia_dt3.getMainid() + "',"
		 			+ "'" + qingjia_dt3.getRq() + "',"
		 				+ "'" + qingjia_dt3.getQjlb() + "',"
		 					+ "'" + qingjia_dt3.getKssj() + "',"
		 						+ "'" + qingjia_dt3.getJssj() + "',"
		 							+ "'" + qingjia_dt3.getHdzt() + "',"
		 								+ "'" + qingjia_dt3.getXq() + "',"
		 									+ "'" + qingjia_dt3.getHdkssj() + "',"
		 										+ "'" + qingjia_dt3.getHdjssj() + "',"
		 											+ "'" + qingjia_dt3.getDkjl() + "',"
		 												+ "'" + qingjia_dt3.getAppnom() + "',"
		 													+ "'" + qingjia_dt3.getGh() + "',"
		 														+ "'" + qingjia_dt3.getHdgs() + "',"
		 															+ "'" + qingjia_dt3.getJdid() + "',"
		 																+ "'" + qingjia_dt3.getBzgzsj() + "',"
		 																	+ "'" + qingjia_dt3.getKqhdyf() + "',"
		 																		+ "'" + qingjia_dt3.getLcspjsrq() + "',"
		 																			+ "'" + qingjia_dt3.getKqy() + "',"
		 																				+ "'" + qingjia_dt3.getRow_id() + "'"
		 + ")";
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		return false;
	}

	@Override
	public boolean deleteById(int id) {
		String sql = "delete from "+ Qingjia_DT3_FormName +" where id = " + id;
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public boolean update(QingJia qingjia_dt3) {
		String sql = "UPDATE SET " + Qingjia_DT3_FormName + " "
				+ "mainid = "+ qingjia_dt3.getMainid()+","
					+"rq = '"+qingjia_dt3.getRq()+"',"
							+ "kssj = '"+qingjia_dt3.getKssj()+"',"
			 
			 + "jssj = '"+qingjia_dt3.getJssj()+"',"
			 	+ "qjlb = '"+qingjia_dt3.getQjlb()+"',"
					 + "hdkssj = '"+qingjia_dt3.getHdkssj()+"',"
							 + "hdjssj = '"+qingjia_dt3.getHdjssj()+"',"
									 + "hdgs = '"+qingjia_dt3.getHdgs()+"',"
											 + "dkjl = '"+qingjia_dt3.getDkjl()+"',"
													 + "userid = '"+qingjia_dt3.getUserid()+"',"
															 + "appnom = '"+qingjia_dt3.getAppnom()+"',"
																	 + "gh = '"+qingjia_dt3.getGh()+"',"
																			 + "jdid = '"+qingjia_dt3.getJdid()+"',"
																					 + "bzgzsj = '"+qingjia_dt3.getBzgzsj()+"',"
																							 + "kqhdyf = '"+qingjia_dt3.getKqhdyf()+"',"
																									 + "lcspjsrq = '"+qingjia_dt3.getLcspjsrq()+"',"
																											 + "hdzt = '"+qingjia_dt3.getHdzt()+"',"
																													 + "kqy = '"+qingjia_dt3.getKqy()+"',"
																															 + "xq = '"+qingjia_dt3.getXq()+"',"
																																	 + "row_id = '"+qingjia_dt3.getRow_id()+"'"
			+ "WHERE "
				+ "id = '"+ qingjia_dt3.getId() +"'";
		RecordSet rs = new RecordSet();
		return rs.executeSql(sql);
	}

	@Override
	public QingJia queryById(int id) {
		RecordSet rs = new RecordSet();
		String sql = "select top 1 * from "+ Qingjia_DT3_FormName + "where id = " + id;
		
		rs.executeSql(sql);
		
		QingJia qingjia_dt3 = new QingJia();
		
		while(rs.next()){
			qingjia_dt3.setAppnom(rs.getString("appnom"));
			qingjia_dt3.setBzgzsj(rs.getString("bzgzsj"));
			qingjia_dt3.setDkjl(rs.getString("dkjl"));
			qingjia_dt3.setGh(rs.getString("gh"));
			qingjia_dt3.setHdgs(rs.getDouble("hdgs"));
			qingjia_dt3.setHdjssj(rs.getString("hdjssj"));
			qingjia_dt3.setHdkssj(rs.getString("hdkssj"));
			qingjia_dt3.setHdzt(rs.getInt("hdzt"));
			qingjia_dt3.setId(rs.getInt("id"));
			qingjia_dt3.setJdid(rs.getString("jdid"));
			qingjia_dt3.setJssj(rs.getString("jssj"));
			qingjia_dt3.setKqhdyf(rs.getString("kqhdyf"));
			qingjia_dt3.setKqy(rs.getInt("kqy"));
			qingjia_dt3.setKssj(rs.getString("kssj"));
			qingjia_dt3.setLcspjsrq(rs.getString("lcspjsrq"));
			qingjia_dt3.setMainid(rs.getInt("mainid"));
			qingjia_dt3.setQjlb(rs.getString("qjlb"));
			qingjia_dt3.setRow_id(rs.getInt("row_id"));
			qingjia_dt3.setRq(rs.getString("rq"));
			qingjia_dt3.setUserid(rs.getInt("userid"));
			qingjia_dt3.setXq(rs.getString("xq"));
		}
		
		return qingjia_dt3;
	}

	@Override
	public List<QingJia> queryListByMainID(int mainID) {
		
		List<QingJia> qingjia_dt3_List = new ArrayList<QingJia>();
		
		String sql = "select * from " + Qingjia_DT3_FormName + " where mainid = " + mainID;
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			QingJia qingjia_dt3 = this.queryById(rs.getInt("id"));
			qingjia_dt3_List.add(qingjia_dt3);
		}
		
		return qingjia_dt3_List;
	}

	@Override
	public List<QingJia> queryListBySEDate(String startDate, String endDate) {
		List<QingJia> qingjia_dt3_List = new ArrayList<QingJia>();
		
		String sql = "select * from " + Qingjia_DT3_FormName + " where rq between " + startDate + " and " + endDate + " order by rq";
		RecordSet rs = new RecordSet();
		
		rs.executeSql(sql);
		
		while(rs.next()){
			QingJia qingjia_dt3 = this.queryById(rs.getInt("id"));
			qingjia_dt3_List.add(qingjia_dt3);
		}
		
		return qingjia_dt3_List;
	}

}
