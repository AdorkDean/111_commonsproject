package com.rc.commons.mail;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.rc.commons.freemarker.UtilStatic;


/**  
* @Title: Mail.java
* @Description: 
* @author yinbinhome@163.com
* @date 2012-1-5 下午01:02:59
* @version V1.0  
*/
public class Mail {
	private String host;
	private String username;
	private String showname;
	private String password;
	private String charset;
	private String fromaddress;
	private String templatepath;
	
	public Mail() {
		super();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getShowname() {
		return showname;
	}

	public void setShowname(String showname) {
		this.showname = showname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getFromaddress() {
		return fromaddress;
	}

	public void setFromaddress(String fromaddress) {
		this.fromaddress = fromaddress;
	}

	public String getTemplatepath() {
		return templatepath;
	}

	public void setTemplatepath(String templatepath) {
		this.templatepath = templatepath;
	}

	/**
	 * @param to   收件人地址
	 * @param subject  主题
	 * @param contentMap 模板变量
	 * @param templateName 模板名字
	 * 模板都在项目目录下\WebRoot\WEB-INF\T\ 里面，根据自己实际应用放一个自己的模板
	 * @return 成功返回true,失败返回false
	 */
	public boolean send(String to, String subject, Map<Object,Object> contentMap,String templateName) {
		HtmlEmail email = new HtmlEmail();
		try {
			email.setTLS(true);
			email.setHostName(host);
			email.setAuthentication(username, password);
			email.addTo(to);
			System.out.println(showname);
			email.setFrom(fromaddress,new String(showname.getBytes("utf-8"),charset),charset); // 发送方邮件地址和显示名字
			email.setSubject(subject); // 标题
			email.setCharset(charset); // 设置Charset
			String content=UtilStatic.doTemplateToString(contentMap,templatepath,templateName);//生成邮件内容
			email.setHtmlMsg(content); // 内容
			email.send();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param to   收件人地址
	 * @param subject  主题
	 * @param contentMap 模板变量
	 * @param templateName 模板名字
	 * 模板都在项目目录下\WebRoot\WEB-INF\T\ 里面，根据自己实际应用放一个自己的模板
	 * 此方法使用的templatepath为相对路径
	 * @return 成功返回true,失败返回false
	 */
	public boolean sendByPath(String to, String subject, Map<Object,Object> contentMap,String templateName) {
		HtmlEmail email = new HtmlEmail();
		String path=this.getClass().getResource("/").getFile().toString().replace("/WEB-INF/classes", "")+templatepath;
		System.out.println("path=="+path);
		try {
			email.setTLS(true);
			email.setHostName(host);
			email.setAuthentication(username, password);
			email.addTo(to);
			System.out.println(showname);
			email.setFrom(fromaddress,new String(showname.getBytes("iso8859-1"),charset),charset); // 发送方邮件地址和显示名字
			email.setSubject(subject); // 标题
			email.setCharset(charset); // 设置Charset
			String content=UtilStatic.doTemplateToString(contentMap,path,templateName);//生成邮件内容
			email.setHtmlMsg(content); // 内容
			email.send();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}	

	public static void main(String[] args) throws EmailException, UnsupportedEncodingException {
		Mail m=new Mail();
		m.setCharset("utf-8");
		m.setFromaddress("help@gemside.com");
		m.setHost("smtp.ym.163.com");
		m.setPassword("123321gemsides");
		m.setUsername("help@gemside.com");
		m.setShowname("admin");
		//m.setTemplatepath("d:/tmp/");
		m.send("1093665844@qq.com", "hello", new HashMap(), "aa.ftl");
	}

}
