package com.rc.commons.validate;

/**
 * <p>公共方法类</p>
 * <p>校验手机号码</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 *
 */

public class ValidatePhoneNo implements Validator{
	
  public void init() {
  }
  /* 校验手机号码是否正确
   * @see com.ninetowns.b2b.commons.Validator#validate(int, int, java.lang.Object, java.util.List)
   */
  public String validate(Object value) {
          String strPhoneNo = (String) value;
          try {
            if (strPhoneNo == null || strPhoneNo.equals("")) {
                    return "手机号码不能为空。";
            }else if (strPhoneNo.trim().length() != 11) {
                    return "手机号码必须是11位。";
            }else if (!strPhoneNo.substring(0, 2).equals("13")) {
                    return "手机号码必须是以13开头的。";
            }
            try {
                    Long.parseLong(strPhoneNo);
                    return null;
            }
            catch (NumberFormatException ex) {
                    return "手机号码每一位必须是数字";
            }
          } catch (RuntimeException e) {
                  return "手机号码格式不正确！";
          }
  }
  
  public static boolean checkMobile(String str){
	  String regEx = "^\\d{11}+$";
	  java.util.regex.Pattern p = java.util.regex.Pattern.compile(regEx);
	  java.util.regex.Matcher m = p.matcher(str.trim());
	  boolean result = m.find();
	  return result;
  }
  
  public static boolean checkPhone(String str){
	  String regEx = "^\\d{2,3}\\-\\d{6,9}+$";
	  java.util.regex.Pattern p = java.util.regex.Pattern.compile(regEx);
	  java.util.regex.Matcher m = p.matcher(str.trim());
	  boolean result = m.find();
	  return result;
  }
  
  public static boolean checkCode(String str){
	  String regEx = "^\\d{6}+$";
	  java.util.regex.Pattern p = java.util.regex.Pattern.compile(regEx);
	  java.util.regex.Matcher m = p.matcher(str.trim());
	  boolean result = m.find();
	  return result;
  }
}
