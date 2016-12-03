package com.rc.commons;

/**
 * <p>公共方法类</p>
 * <p>系统常量</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @author Weiwenqi
 * @version 1.0
 *
 */

public interface Constants {
  /**
   * 编码
   */
  public static String ENCODING = "encoding";
  /**
   * 编码值
   */
  public static String DEFAULT_ENCODING = "GBK";
  /**
   * 分页的每页记录数,缺省为每页10条记录
   */
  public static int PAGE_SIZE = 10;
  /**
   * 文件名的长度
   */
  public static String TEMP_FILE_NAME_LENGTH = "8";
  /**
   * 图形校验码在Session中的Key值
   */
  public static String VERIFY_CODE="verifycode";
  /**
   * 图形校验码长度
   */
  public static int VERIFY_CODE_LENGTH = 8;
  /**
   * 存放图片临时文件的文件夹名称
   */
  public static String TEMP_PHOTO_PATH = "/photo";
  /**
   * 存放上传文件的文件夹名称
   */
  public static String UPLOAD_PATH = "/upload";
  /**
   * 存放临时文件的文件夹名称
   */
  public static String TEMP_FILE_PATH = "/temp";
  /**
   * 上传文件的最大尺寸(字节)
   */
  public static long UpLoadMaxSize = 4194304;
  /**
   * 上传文件的缓冲区大小(字节)
   */
  public static int UpLoadThresholdSize = 4096;
  /**
   * 超级用户
   */
  public static String SUPER_USER = "admin";
  /**
   * 防止重复提交的key
   */
  public static String TOKEN_KEY="com.nt.b2b.token.key";
  /**
   * jdbc batch size
   */
  public static int JDBC_BATCH_SIZE = 50;
  /**
   * 网站服务邮箱
   */  
  public static String EMAIL_FROM = "service@b2bsources.com";
  /**
   * 网站名称
   */  
  public static String WEBSITES_NAME = "www.b2bsources.com";

}

