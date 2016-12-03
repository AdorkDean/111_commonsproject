package com.rc.commons.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerUtil {

	/**
	 * freemarker 生成静态页
	 * @param root 页面需要的参数，一个Map
	 * @param htmlTemplateName 模板的名字
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String  createTemplate(Map<Object,Object> root,String htmlTemplateName,String templatePath) {
		Configuration freemarkerCfg = new Configuration();
		String path = FreeMarkerUtil.class.getResource("").toString();
		
		System.out.println("----------------path:"+path+"        os name:"+System.getProperty("os.name"));
		
		
		try {
			String osName = System.getProperty("os.name").toLowerCase();
			
			if(osName.startsWith("windows")){
				System.out.println((path.split("/WEB-INF")[0]+templatePath).replace("file:/", ""));
				freemarkerCfg.setDirectoryForTemplateLoading(new File((path.split("/WEB-INF")[0]+templatePath).replace("file:/", "")));
			}else{
				System.out.println((path.split("/WEB-INF")[0]+templatePath).replace("file:", ""));
				freemarkerCfg.setDirectoryForTemplateLoading(new File((path.split("/WEB-INF")[0]+templatePath).replace("file:", "")));
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("模板目录不存在！");
			return "-1";
		}

		freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");
		Template template;
		Locale.setDefault(Locale.ENGLISH);
		StringWriter writer = new StringWriter();
		try {
			template = freemarkerCfg.getTemplate(htmlTemplateName);
			template.setEncoding("UTF-8");
			template.process(root, writer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	public static void main(String[] args) {
		Map root=new HashMap();
		root.put("name", "helloworld");
		
		String dd=FreeMarkerUtil.createTemplate(root, "t.ftl", "D:/workspace/drug_task/WebRoot/WEB-INF/T");
		System.out.println(dd);
	}

}