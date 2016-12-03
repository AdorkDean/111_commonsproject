package com.rc.commons.log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author  
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class SysLogger {
//	String className = "WebPortalLogger";
	public final static String SPLIT_STR = " ,";

	//	 static Logger logger = Logger.getLogger(WebPortalLogger.class);
	static Logger logger = Logger.getLogger("");
	static String propertiesFileName = "log4jFilelogger.properties";

	static {
		try {
			String log4JPropertyFile = "";
			Properties props = new Properties();
			InputStream in =
			SysLogger.class.getResourceAsStream("/" + propertiesFileName);
			props.load(in);

			PropertyConfigurator.configure(props);
		} catch (FileNotFoundException fnfe) {
			System.out.println(
				"FileLogger.java - FileNotFoundException : " + fnfe.toString());
		} catch (IOException ioe) {
			System.out.println(
				"FileLogger.java - IOException : " + ioe.toString());
		} catch (Exception e) {
			System.out.println("FileLogger.java - Exception : " + e.toString());
			e.printStackTrace();
		}
	}

	public static void debug(Object msg) {
		logger.debug(msg);
	}

	public static void info(Object msg) {
		logger.info(msg);
	}

	public static void warn(Object msg) {
		logger.warn(msg);
	}

	public static void error(Object msg) {
		logger.error(msg);
	}

	public static void fatal(Object msg) {
		logger.fatal(msg);
	}

	public static void printStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		String beginStackTrace =
			"   ==== Begin Stack Trace for the Exception  ==== \n";
		String endStackTrace =
			"\n =============  End of Stack Trace  =========== \n";
		String stackTrace = "";

		int level = logger.getEffectiveLevel().toInt();

		try {
			e.printStackTrace(pw);
			stackTrace = beginStackTrace + sw.toString() + endStackTrace;

			switch (level) {
				case Level.DEBUG_INT :
					debug(stackTrace);
					break;
				case Level.INFO_INT :
					info(stackTrace);
					break;
				case Level.ERROR_INT :
					error(stackTrace);
					break;
				case Level.FATAL_INT :
					fatal(stackTrace);
					break;
				case Level.ALL_INT :
					debug(stackTrace);
					break;
				default :
					debug(stackTrace);
					break;
			}
			sw.close();
			pw.close();
		} catch (Exception exc) {
			debug(exc.toString());
		}
	}

	public static void main(String args[]) {
		//		DOMConfigurator.configure("log4jconfig.xml");

		//书写示例：
		logger.debug("Here is some DEBUG,调试信息");

		//在做重要操作时，删除后台用户、删除会员、删除产品、删除商机
		logger.info("Here is some INFO，信息提示");

		logger.warn("Here is some WARN，警告信息");

		//注：自己在写日志时，需要把下面的FileLogger.class替换成自己的“�嗝�.class”
		logger.error(
		SysLogger.class
				+ ".方法名"
				+ SysLogger.SPLIT_STR
				+ "接收到的参数"
				+ SysLogger.SPLIT_STR
				+ "具体错误信息");

		logger.fatal(SysLogger.class);
	}

}
