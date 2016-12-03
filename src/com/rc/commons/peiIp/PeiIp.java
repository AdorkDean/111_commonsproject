package com.rc.commons.peiIp;

public class PeiIp {

	/**
	 * @Title: main
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param args 
	 */
	public static void main(String[] args) {
		String str ="1";
		System.out.println(getAsIP("2",3,"0"));

	}
	/**
	 * 此方法可以做类似修改成IP地址的那种工具
	 * 传入一个str，如果不够N位，那么前面差几位，就补几个字符串m(m的长度是1)
	 * 如果传入的str是空的话，那么返回N位的m;
	 * */
	public static String getAsIP(String str,int n ,String m){
		if(str!=null && str.trim().length()>0){
			int length = str.length();
			int ss = n-length;
			for(int i=0;i<ss;i++){
				str = m+str;
			}
			return str;
		}else{
			String ss="";
			for(int i=0;i<n;i++){
				ss=m+ss;
			}			
			return ss;
		}
		
	}
}
