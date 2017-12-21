package dinghan.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONUtil {

	/**
	 * 处理json字符串的特殊符号
	 * - *注意 先处理字符串 ，再将字符串 接入 json
	 * @param s
	 * @return
	 */
	public static String stringToJson(String s) {    
        StringBuffer sb = new StringBuffer ();     
        for (int i=0; i<s.length(); i++) {     
      
            char c = s.charAt(i);     
            switch (c) {     
            case '\"':     
                sb.append("\\\"");     
                break;     
//            case '\\':   //如果不处理单引号，可以释放此段代码，若结合下面的方法处理单引号就必须注释掉该段代码
//                sb.append("\\\\");     
//                break;     
            case '/':     
                sb.append("\\/");     
                break;     
            case '\b':      //退格
                sb.append("\\b");     
                break;     
            case '\f':      //走纸换页
                sb.append("\\f");     
                break;     
            case '\n':     
                sb.append("\\n"); //换行    
                break;     
            case '\r':      //回车
                sb.append("\\r");     
                break;     
            case '\t':      //横向跳格
                sb.append("\\t");     
                break;     
            default:     
                sb.append(c);    
            }}
        return sb.toString();     
     }
	 /**
	  * 字符串替换
	  * @param target
	  * @param pattern
	  * @param replacer
	  * @return
	  */
	 public static String replaceAll(String target,String pattern, String replacer){
		 String result = "";
		 Pattern p = Pattern.compile(pattern);
		 
		 Matcher m = p.matcher(target);
		 
		 result = m.replaceAll(replacer);
		 
		 return result;
	 }
	
}
