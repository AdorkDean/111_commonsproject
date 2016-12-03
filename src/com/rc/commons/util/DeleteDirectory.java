package com.rc.commons.util;

import java.io.File;


/**  
 * @Title: DeleteDirectory.java
 * @Description: 空目录很好删，非空目录就需要采用递归的方法去删
 * @author yinbinhome@163.com  
 * @date 2011-11-3 上午11:37:44
 * @version V1.0  
 */
public class DeleteDirectory {
	/**
	 * Deletes the directory passed in.
	 * 
	 * @param dir
	 *            Directory to be deleted
	 */
	private static void doDeleteEmptyDir(String dir) {

		boolean success = (new File(dir)).delete();

		if (success) {
			System.out.println("Successfully deleted empty directory: " + dir);
		} else {
			System.out.println("Failed to delete empty directory: " + dir);
		}

	}

	
	/**
	 * 用递归方式清空目录
	 * @param dir
	 * @return
	 */
	public static boolean clearDir(File dir) {
		System.out.println("清空目录："+dir);

		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so now it can be smoked
		return true;
	}

	/**
	 * 删除目录
	 * @param dir
	 * @return
	 */
	public static boolean deleteDir(File dir){
		boolean f=clearDir(dir);
		return dir.delete();
	}
	/**
	 * Sole entry point to the class and application.
	 * 
	 * @param args
	 *            Array of String arguments.
	 */
	public static void main(String[] args) {

//		doDeleteEmptyDir("d:/test/keyword2/");

		String newDir2 = "d:/test/keyword2/";
		boolean success = clearDir(new File(newDir2));
		if (success) {
			System.out.println("Successfully deleted populated directory: " + newDir2);
		} else {
			System.out.println("Failed to delete populated directory: " + newDir2);
		}

	}

}