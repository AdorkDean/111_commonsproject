package com.rc.commons.security;

import java.security.MessageDigest;

/**
 * <p>公共方法类</p>
 * <p>md5算法实现</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 *
 */

public class SecurityUtil  {

  /**
   * md5加密算法
   * @param value 欲使用md5算法加密的字符串
   * @return String 已经使用md5算法加密后的字符串
   */
  public static String MD5(String value) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(value.getBytes("UTF8"));
      byte s[] = md.digest();
      String result = "";
      for (int i = 0; i < s.length; i++) {
        result += Integer.toHexString( (0x000000FF & s[i]) | 0xFFFFFF00).
            substring(6);
      }
      return result;
    } catch (Exception e) {
      return null;
    }
  }
  
  /**
   * 测试程序
   * @param args String[]
   */
  public static void main(String[] args){
    SecurityUtil m = new SecurityUtil();
    System.out.println(SecurityUtil.MD5("abc").equals("900150983cd24fb0d6963f7d28e17f72"))  ;
  }
}
