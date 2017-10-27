package dinghan.workflow.beans;

import java.util.Properties;

//import javax.activation.DataHandler;
//import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import dinghan.workflow.service.kqservice.MyAuthenticator;
//import sun.misc.BASE64Encoder;

public class KQMail {
	
	private String host = null;	//服务器信息：IP地址或smtp.dinghantech.com
	private String port = "25"; //SMTP端口号：默认25
	private boolean validate = false; //是否需要登陆验证，默认不需要
	private String[] recipients = null;  //收件人地址
	private String[] recipientsName=null; //收件人名称
	private String subject   = null;  //邮件主题 
	private String content   = null;  //邮件内容 
	private String contentType  = null;  //邮件内容格式(text或html) 
	private String filePath  = null;  //附件路径 ，目前只提供一个附件，必须是服务器上的文件
	private String from=null; //发件人地址
	private String fromName=null;//发件人姓名
	private String fromPwd=null; //发件人邮箱密码
	private MyAuthenticator auth = null; //验证信息
	
	//发送不带附件的邮件(包括文本格式和html两种格式) 
	public void sendMail() throws MessagingException,Exception{
		 
		 if(this.recipients!=null && this.recipients.length>0){
			 Properties props = new Properties(); 
			 //设置邮件服务器地址，连接超时时限等信息
			 props.put("mail.smtp.host", this.host);
		     props.put("mail.smtp.port", this.port);
		     props.put("mail.smtp.auth", this.validate); 
		     props.put("mail.smtp.connectiontimeout", "30000"); //连接超时：10s
		     //props.put("mail.smtp.timeout", "30000");   //发送超时：30s
		       
		     //创建缺省的session对象 
		     if(this.validate){
		    	 this.auth = new MyAuthenticator(this.from, this.fromPwd);
		     }
		     
		     Session session = Session.getDefaultInstance(props, this.auth); 
		     //创建message对象 
		     Message msg = new MimeMessage(session); 
		     //发件人
		     InternetAddress addressFrom = new InternetAddress(this.from,this.fromName); 
		     msg.setFrom(addressFrom);   
		     //收件人
		     InternetAddress[] addressTo = new InternetAddress[this.recipients.length]; 
		     for (int i = 0; i < this.recipients.length; i++){ 
		         addressTo[i] = new InternetAddress(this.recipients[i],this.recipientsName[i]); 
		     } 
		     msg.setRecipients(Message.RecipientType.TO, addressTo); 
		     //设置邮件标题，中文编码
		     //this.subject = MimeUtility.encodeText(new String(this.subject.getBytes(), "GB2312"), "GB2312", "B");
		     msg.setSubject(this.subject);
		     //设置邮件内容,区分文本格式和HTML格式 
		     if (this.contentType == null || this.contentType.equals("text")) { 
		    	 msg.setText(this.content); 
		     } else if (this.contentType.equals("html")) { 
		    	 //设置正文内容 
		    	 Multipart mmp = new MimeMultipart();//新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个)
		    	 BodyPart mbp1 = new MimeBodyPart(); //新建一个存放信件内容的BodyPart对象 
		    	 mbp1.setContent(this.content, "text/html;charset=utf-8"); //给BodyPart对象设置内容和格式/编码方式
		    	 mmp.addBodyPart(mbp1); 
		    	 
		    	 /*if (this.filePath!=null && this.filePath!="") {
					//设置邮件附件 
					BodyPart mbp2 = new MimeBodyPart();
					FileDataSource fileDataSource = new FileDataSource(this.filePath);
					if(fileDataSource.getFile().exists()){
						mbp2.setDataHandler(new DataHandler(fileDataSource));
						BASE64Encoder enc = new BASE64Encoder();
						mbp2.setFileName("=?GB2312?B?"+enc.encode(fileDataSource.getName().getBytes())+"?=");
						mmp.addBodyPart(mbp2);
					}else{
						System.out.println("文件不存在："+this.filePath);
					}
				}*/
		    	 
				msg.setContent(mmp);	//把mmp作为消息对象的内容 
		     } 
		     
		     //设置邮件发送时间 
		     msg.setSentDate(new java.util.Date()); 
		     //调用抽象类的静态方法send()发送邮件 
		     Transport.send(msg); 
		 }  
	}
	
	//＝＝＝＝set\get方法区＝＝＝＝＝＝＝＝＝＝＝
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public String[] getRecipients() {
		return recipients;
	}

	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}

	public String[] getRecipientsName() {
		return recipientsName;
	}

	public void setRecipientsName(String[] recipientsName) {
		this.recipientsName = recipientsName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromPwd() {
		return fromPwd;
	}

	public void setFromPwd(String fromPwd) {
		this.fromPwd = fromPwd;
	}
	public MyAuthenticator getAuth() {
		return auth;
	}
	public void setAuth(MyAuthenticator auth) {
		this.auth = auth;
	}
}
