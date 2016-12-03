package com.rc.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * @version 1.0
 */
public class CopyFile {
  public CopyFile() {
  }

  /**
   * copy file
   * @param file1 String
   * @param file2 String
   * @return boolean
   */
  public boolean copy(String file1, String file2) {
    try {
      File file_in = new java.io.File(file1);
      File file_out = new java.io.File(file2);
      FileInputStream in1 = new FileInputStream(file_in);
      FileOutputStream out1 = new FileOutputStream(file_out);
      byte[] bytes = new byte[1024];
      int c;
      while ( (c = in1.read(bytes)) != -1) {
        out1.write(bytes, 0, c);
      }

      in1.close();
      out1.close();
      return true;
    }
    catch (Exception e) {
          e.printStackTrace();
          return false;
        }
  }

  /**
   * 检测目录是否存在，如果不存在，则创建
   * @param picPath String
   * @return boolean
   */
  public boolean createPath(String sPath) {
//    System.out.print("*******picPath=" + sPath);
    String msg = "";
    try {
      File dir = new java.io.File(sPath);
      if (dir == null) {
        msg = "错误原因:＜BR＞对不起，不能创建空目录！";
        return false;
      }
      if (dir.isFile()) {
        msg = "错误原因:＜BR＞已有同名文件＜B＞" + dir.getAbsolutePath() + "＜/B＞存在。";
        return false;
      }
      if (!dir.exists()) {
        boolean result = dir.mkdirs();
        if (result == false) {
          msg = "错误原因:＜BR＞目录＜b＞" + dir.getAbsolutePath() + "＜/B＞创建失败，原因不明！";
          return false;
        }
        return true;
      }
      else {
        return true;
      }

    }
    catch (Exception e) {
      return (false);
    }
  }

  public static void main(String[] args) {
    CopyFile aa = new CopyFile();
    boolean s=aa.createPath ("C:\\tuyy1\\");
  }

}
