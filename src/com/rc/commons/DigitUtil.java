package com.rc.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DigitUtil {
	public DigitUtil() {
	}
	
	/**
	 * 判断是否位数字，非小数
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean isDigit(String str) {
		boolean result = true;

		char[] c = str.toCharArray();

		for (int i = 0; i < c.length; i++) {
			char ch = c[i];
			if (!Character.isDigit(ch)) {
				return false;
			}
		}
		return result;
	}

	/**
	 * 判断字符串是否为小数
	 * 
	 * @param str
	 *            String
	 * @param num
	 *            int 小数位数
	 * @return boolean
	 */
	public static boolean isDecimalFraction(String str, int num) {

		int pointNum = 0;
		int realNum = 0;
		char[] c = str.toCharArray();
		if (c.length == 0) {
			return false;
		}
		if (Character.toString(c[0]).equals(".")
				|| Character.toString(c[c.length - 1]).equals(".")) {
			return false;
		}
		if (str.length() > 1 && str.startsWith("0")) {
			if (!Character.toString(c[1]).equals(".")) {
				return false;
			}

		}
		for (int i = 0; i < c.length; i++) {
			char ch = c[i];
			if (Character.toString(ch).equals(".")) {
				pointNum++;
				if (pointNum > 1) {
					return false;
				}

			} else if (!Character.isDigit(ch)) {
				return false;
			} else if (pointNum == 1) {
				realNum++;
				if (realNum > num) {
					return false;
				}
			}

		}

		return true;

	}

	/**
	 * 判断字符串是否为小数,小数位数不限
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean isDecimalFraction(String str) {
		return isDecimalFraction(str, Integer.MAX_VALUE);
	}

	/**
	 * 判断字符串是否为小数,格式不要求标准
	 * 
	 * @param str
	 *            String
	 * @param num
	 *            int
	 * @return boolean
	 */
	public static boolean isUnformalDecimalFraction(String str, int num) {

		int pointNum = 0;
		int realNum = 0;
		char[] c = str.toCharArray();
		if (c.length == 0) {
			return false;
		}
		// if (Character.toString(c[0]).equals(".") ||
		// Character.toString(c[c.length - 1]).equals(".")) {
		// return false;
		// }
		// if (str.length() > 1 && str.startsWith("0")) {
		// if (!Character.toString(c[1]).equals(".")) {
		// return false;
		// }
		// }
		for (int i = 0; i < c.length; i++) {
			char ch = c[i];
			if (Character.toString(ch).equals(".")) {
				pointNum++;
				if (pointNum > 1) {
					return false;
				}

			} else if (!Character.isDigit(ch)) {
				return false;
			} else if (pointNum == 1) {
				realNum++;
				if (realNum > num) {
					return false;
				}
			}

		}

		return true;

	}

	/**
	 * 判断字符串是否为小数,格式不要求标准,小数位数不限
	 * 
	 * @param str
	 *            String
	 * @return boolean
	 */
	public static boolean isUnformalDecimalFraction(String str) {

		return isUnformalDecimalFraction(str, Integer.MAX_VALUE);

	}
	/**
	 * 
	*    
	* 方法描述： 验证是否是数字，包含小数 
	* 创建人：hexiaoliang  
	* 创建时间：2010-3-25 下午05:29:39    
	* @param str
	* @return
	* @version   1.0
	*
	 */
	public static boolean isNumeric(String str){
		String pattern = "[0-9]+(.[0-9]+)?";  
		Pattern p = Pattern.compile(pattern);  
		Matcher m = p.matcher(str);  
		
		boolean flag =true;	
		if(str.indexOf(".")==-1&&"0".equals(String.valueOf(str.charAt(0))))
			flag=false;
		
		return  m.matches()&&flag; 
	} 
	public static void main(String[] args) {
		String str = "10012";
		System.out.println(DigitUtil.isNumeric(str));
	}

}
