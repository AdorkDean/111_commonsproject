package com.rc.commons.validate;

public class ValidatorCommons implements Validator {
	
	private static final long serialVersionUID = 2356229622700795348L;
	
	// 手机号验证
	public static boolean checkMobile(String str) {
		String regEx = "^\\d{11}+$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(regEx);
		java.util.regex.Matcher m = p.matcher(str.trim());
		boolean result = m.find();
		return result;
	}
	
	// 电话号码验证
	public static boolean checkPhone(String str) {
		String regEx = "^\\d{2,4}\\-\\[0-9- ]{6,15}+$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(regEx);
		java.util.regex.Matcher m = p.matcher(str.trim());
		boolean result = m.find();
		return result;
	}
	
	// 邮编验证
	public static boolean checkCode(String str) {
		String regEx = "^\\d{6}+$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(regEx);
		java.util.regex.Matcher m = p.matcher(str.trim());
		boolean result = m.find();
		return result;
	}
	
	// email验证
	public static boolean checkEmail(String str) {
		String regEx = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(regEx);
		java.util.regex.Matcher m = p.matcher(str.trim());
		boolean result = m.find();
		return result;
	}
	
	// 地址编码验证
	public static boolean checkAddressCode(String str) {
		String regEx = "^\\d{9}+$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(regEx);
		java.util.regex.Matcher m = p.matcher(str.trim());
		boolean result = m.find();
		return result;
	}
	
	public String validate(Object value) {
		return null;
	}
	
	public void init() {
	}
}
