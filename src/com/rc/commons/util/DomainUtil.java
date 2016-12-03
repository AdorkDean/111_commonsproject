package com.rc.commons.util;

/**
 * @Title: DomainUtil.java
 * @Description:域名相关工具类，根据二级域名取网站全路径域名，例如根据“www”取回全路径域名为“www.xxxx.com”
 * @author yinbinhome@163.com
 * @date 2013-3-22 上午10:45:44
 * @version V1.0
 */

public class DomainUtil {
	private static DomainUtil instance = null;

	private DomainUtil() {

	}

	public synchronized static DomainUtil getInstance() {
		if (instance == null) {
			instance = new DomainUtil();
		}
		return instance;
	}
	
	/**
	 * 说明:不能直接调用，应先调用单例实例化方法getInstance再调用此方法
	 * @param domain 二级域名
	 * @return 返回域名，不带有http
	 */
	public static String getDomain(String domain) {
		String name = InfoUtil.getInstance().getInfo("domain", "domain."+domain);
		return name;
	}
	
	/**
	 * 说明:不能直接调用，应先调用单例实例化方法getInstance再调用此方法
	 * @param domain 二级域名
	 * @return 返回带有http://的域名URL
	 */
	public static String getDomain4Url(String domain) {
		String prefix = InfoUtil.getInstance().getInfo("domain", "domain.prefix");
		String name = InfoUtil.getInstance().getInfo("domain", "domain."+domain);
		name = prefix + name;
		return name;
	}
	
	
	/**
	 * 说明:不能直接调用，应先调用单例实例化方法getInstance再调用此方法
	 * @param domain 二级域名
	 * @return 返回服务器相对应的二级域名的据对路径s
	 */
	public static String getPath(String domain) {
		String path = InfoUtil.getInstance().getInfo("domain", "path."+domain);
		return path;
	}
}
