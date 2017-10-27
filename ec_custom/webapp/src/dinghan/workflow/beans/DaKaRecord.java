package dinghan.workflow.beans;

import java.util.ArrayList;
import java.util.Date;

import weaver.conn.RecordSet;

public class DaKaRecord {
	private String code;
	private String date;
	private String firsttime;
	private String lasttime;
	
	private ArrayList<String> dk_list; // 记录全天打卡记录

	public DaKaRecord() {
		
	}

	/**
	 * 功能：根据工号/姓名、日期获取指定日期的首末次打卡信息(考勤机)
	 * 
	 * @param code
	 * @param date
	 * @throws Exception
	 */
	public DaKaRecord(String code, String date) throws Exception {
		
		try {
			Date endDate = PublicVariant.StrToDate(date, "yyyy-MM-dd");
			endDate = PublicVariant.AdjustDateTime(endDate, 0, 0, 1);
			String sql = "SELECT * FROM vwCheckTime  WHERE Code='" + code
					+ "' AND Date BETWEEN '" + date + "' AND '";
			sql += PublicVariant.DateToStr(endDate, "yyyy-MM-dd") + "' order BY Date,Time ";

			RecordSet rs = new RecordSet();
			rs.executeSqlWithDataSource(sql, "kqj");
			// 最多只有两条记录
			String stuH = "02:00:00";
			ArrayList<String> list = new ArrayList<String>();
			while (rs.next()) {

				this.code = rs.getString("Code");
				this.date = date;
				if (date.equalsIgnoreCase(rs.getString("Date"))) {
					String time = rs.getString("Time");
					time = time.substring(0, 5);
					list.add(time);

				} else {
					// 虽然当天没有打卡，但仍要检测第二天是否有打卡行为
					if (rs.getString("Time").trim().compareTo(stuH) < 0) {
						list.add("23:59");
					}
				}
			}
			
			// 考勤记录去除空值
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).equals("")|| list.get(i).equalsIgnoreCase("null")) {
					list.remove(i);
					i--;
				}
			}
			
			this.dk_list = list;
			if (!list.isEmpty()) {

				this.firsttime = list.get(0);
				this.lasttime = list.get(list.size() - 1);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFirsttime() {
		return firsttime;
	}

	public void setFirsttime(String firsttime) {
		this.firsttime = firsttime;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public ArrayList<String> getDk_list() {
		return dk_list;
	}

	public void setDk_list(ArrayList<String> dk_list) {
		this.dk_list = dk_list;
	}
}
