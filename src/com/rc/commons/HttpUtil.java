package com.rc.commons;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class HttpUtil {
	/**
	 * 获得客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.isNotEmpty(ip)) {
			if (ip.length() > 45) {
				return ip.substring(0, 45);
			} else {
				return ip;
			}
		}
		return ip;
	}
	
	/**
	 * 获得客户端操作系统和浏览器类型
	 * 
	 * @param request
	 * @return
	 */
	public static int[] getAgent(HttpServletRequest request) {
		String strHeader = request.getHeader("User-Agent");
		String strSystem = null;
		String strExplorer = null;
		int[] arr = new int[2];

		// 判断客户端操作系统类型
		if (strHeader.indexOf("Windows NT 5.0") >= 0) {
			strSystem = "Windows 2000";
			arr[0] = 1;
		} else if (strHeader.indexOf("Windows NT 5.1") >= 0) {
			strSystem = "Windows XP";
			arr[0] = 2;
		} else if (strHeader.indexOf("Windows NT 5.2") >= 0) {
			strSystem = "Windows .NET/Windows 2003";
			arr[0] = 3;
		} else {
			arr[0] = 0;
		}
		
		// 判断客户端浏览器类型
		if (strHeader.indexOf("MSIE") >= 0) {
			strExplorer = strHeader.substring(strHeader.indexOf("MSIE"),
					strHeader.indexOf(";", strHeader.indexOf("MSIE")));
			arr[1] = 1;
		} else if (strHeader.indexOf("Firefox") >= 0) {
			strExplorer = strHeader.substring(strHeader.indexOf("Firefox"),
					strHeader.length());
			arr[1] = 5;
		} else {
			arr[1] = 0;
		}
		return arr;
	}
}
