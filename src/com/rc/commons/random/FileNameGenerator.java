package com.rc.commons.random;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.rc.commons.Constants;

/**
 * <p>
 * 公共方法类
 * </p>
 * <p>
 * 随机生成文件名
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * @author Weiwenqi
 * @version 1.0
 * 
 */

public class FileNameGenerator {
	/**
	 * 随机数产生对象
	 */
	private static Random r = new Random();
	
	/**
	 * 随机产生一个文件名
	 * 
	 * @return - 随机生成的文件名
	 */
	public static String randomFileName() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < Integer.valueOf(Constants.TEMP_FILE_NAME_LENGTH)
				.intValue(); i++) {
			int t=r.nextInt(10);
			System.out.println(t);
			sb.append(t);
			
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		Set set=new HashSet();
		for(int i=0;i<1000000;i++){
			Random r = new Random(i);
			int aa = r.nextInt(99999999);
			System.out.println(aa);
			set.add(aa);
		}
		System.out.println(set.size());

//		long aa = (r.nextLong())%10000000;
//		System.out.println(aa);
	}
}