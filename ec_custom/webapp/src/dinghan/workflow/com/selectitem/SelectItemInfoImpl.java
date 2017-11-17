package dinghan.workflow.com.selectitem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weaver.conn.RecordSet;

public class SelectItemInfoImpl implements SelectItemInfo{
	
	private static Log log = LogFactory.getLog(SelectItemInfoImpl.class.getName());
	
	@Override
	public SelectItemInfoBean getSelectItemInfo(String billId,String fieldName, int selectValue) {
		SelectItemInfoBean seletItemInfoBean = null;
		String sql = "select top 1 s.fieldid,w.fieldname,s.selectvalue,s.selectname from"
					+ " "+ SysBillFieldForm +" as w, "+ SysSelectItemForm +" as s"
						+ " where w.id = s.fieldid"
							+ " and w.billid = '" + billId + "'"
								+ " and w.fieldname = '"+fieldName+"'"
									+ " and s.selectvalue = '" + selectValue+ "'";
		log.error("获取 选择框 信息  sql :: " + sql);
		RecordSet rs = new RecordSet();
		rs.executeSql(sql);
		
		while(rs.next()){
			seletItemInfoBean = new SelectItemInfoBean();
			seletItemInfoBean.setFieldId(rs.getInt("fieldid"));
			seletItemInfoBean.setFieldName(rs.getString("fieldname"));
			seletItemInfoBean.setSelectValue(rs.getInt("selectvalue"));
			seletItemInfoBean.setSelectName(rs.getString("selectname"));
		}
		
		return seletItemInfoBean;
	}

}
