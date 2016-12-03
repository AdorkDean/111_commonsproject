package com.rc.commons.freemarker;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: Util.java
 * @Description: 模板工具类
 * @author yinbinhome@163.com
 * @date 2011-6-6 下午01:42:35
 * @version V1.0
 */

public class UtilStatic {
	/**
	 * 
	 * @param root
	 *            页面需要的数据
	 * @param templatePath
	 * 			  模板的路径
	 * @param htmlTemplateName
	 *            模板的名字
	 * @param outPath
	 *            输出路径
	 * @return
	 */
	public static boolean doTemplatePage(Map<Object, Object> root, String templatePath,String htmlTemplateName, String outPath) {
		try {
			String content = FreeMarkerUtil.createTemplate(root, htmlTemplateName,templatePath);
			if(content.equals("-1")){
				return false;
			}else{
				FileUtil.createFile(content, outPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @param root
	 *            页面需要的数据
	 * @param templatePath
	 * 			  模板的路径
	 * @param htmlTemplateName
	 *            模板的名字
	 * @return 	返回内容（字符串--html,txt都可）
	 */	
	public static String doTemplateToString(Map<Object, Object> root, String templatePath,String htmlTemplateName) {
		String content="";
		try {
			content = FreeMarkerUtil.createTemplate(root, htmlTemplateName,templatePath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public static void main(String[] args) {
		Map root=new HashMap();
		root.put("name", "helloworld");
		UtilStatic.doTemplatePage(root,"D:/workspace/drug_task/WebRoot/WEB-INF/T", "t.ftl", "D:/temp/123/123/12.html");
	}
}
