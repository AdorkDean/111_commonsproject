package com.rc.commons.validate;

/**
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * @author Weiwenqi
 * @version 1.0
 */

public class ValidateEmail implements Validator {
	public void init() {
	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean checkEmail(String str) {
		String regEx = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(regEx);
		java.util.regex.Matcher m = p.matcher(str.trim());
		boolean result = m.find();
		return result;
	}

	public String validate(Object value) {
		return null;
	}
}
