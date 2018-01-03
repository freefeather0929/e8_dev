package dinghan.common.outsys.midware;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import weaver.file.LogMan;
import weaver.general.GCONST;

/**
 * @title  读取配置文件InnerMidware.properties的参数
 * @author hsf
 * @date  2017年12月1日 
 * @return 
 */
public class InnerMidwareGetIP implements InnerMidware {
  public String PROTOCOL="";
  public String IP="";
  public String PORT="";
  public String PATH_TO_U9="";
  public String PROP_ROOT="";
  public Object  readProperty(){
	  String fname="InnerMidware";
	  Properties prop =new Properties();
	        File f;
	        PROP_ROOT = GCONST.getPropertyPath();
	        f = new File((new StringBuilder(String.valueOf(PROP_ROOT))).append(fname).append(".properties").toString());
	        if(!f.exists())
	            return null;
	        try
	        {
	                InputStream is = new BufferedInputStream(new FileInputStream(f));
	                prop.load(is);
	                Iterator<String> it=prop.stringPropertyNames().iterator();
	                while(it.hasNext()){
	                	                 String key=it.next();
	                	                 if("PROTOCOL".equals(key)){this.PROTOCOL=prop.getProperty(key);}
	                	                 if("IP".equals(key)){this.IP=prop.getProperty(key);}
	                	                 if("PORT".equals(key)){this.PORT=prop.getProperty(key);}
	                	                 if("PATH_TO_U9".equals(key)){this.PATH_TO_U9=prop.getProperty(key);}
	                	                 System.out.println(key+":"+prop.getProperty(key));
	                	             }  
	                is.close();
	            return null;
	        }
	        catch(Exception e)
	        {
	           e.printStackTrace();
	            return null;
	        }
	    }
	  
  }
