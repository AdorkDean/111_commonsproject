package com.rc.commons.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

/**
 * <p>
 * 公共方法类
 * </p>
 * <p>
 * 使用Singleton模式构造连接数据库的工厂方法，完成与数据库的连接，返回连接对象
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * @author Weiwenqi
 * @version 1.0
 */

public class ConnectionFactory {
	private Map dataSourceHome;
	private static ConnectionFactory dataSourceFactory; // 必须是static的,否则不能形成有效的工厂模式

	/**
	 * 构造器，产生工厂实例并给数据源dataSourceHome赋初值，该属性用来保存数据源
	 * 
	 * @throws Exception
	 */
	private ConnectionFactory() throws Exception {
		this.dataSourceHome = Collections.synchronizedMap(new HashMap());
	}

	/**
	 * 该方法返回一个工厂的实例
	 * 
	 * @return ConnectionFactory
	 * @throws Exception
	 */
	public static ConnectionFactory getFactory() throws Exception {
		try {
			if (ConnectionFactory.dataSourceFactory == null) {
				ConnectionFactory.dataSourceFactory = new ConnectionFactory(); // 生成工厂的实例并赋给属性dataSourceFactory
			}
		} catch (NamingException e) {
			throw new Exception(e);
		}

		return ConnectionFactory.dataSourceFactory;
	}

	/**
	 * 采用JDBC方式进行连接,适用于本机调试
	 * 
	 * @param url
	 *            oracle数据库连接串
	 * @param username
	 *            用户
	 * @param password
	 *            密码
	 * @exception Exception
	 *                异常抛出
	 * @return Connection 取得的连接
	 */
	public Connection getConnection(String url, String username, String password)
			throws Exception { // 绝不能是static
		Connection conn = null;
		String URL = url;
		// 采用JDBC方式连接Oracle数据库
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(URL, username, password);
		} catch (Exception e) {
			System.out.println("Error at Connection getConnection():"
					+ e.getMessage());
		} finally {
			return conn;
		}
	}

	/**
	 * 释放数据库资源
	 * 
	 * @param conn
	 *            数据库连接
	 * @param stmt
	 *            Statement
	 * @param rst
	 *            结果集
	 */
	public static void closeConnection(Connection conn, Statement stmt,
			ResultSet rst) {
		if (rst != null) {
			try {
				rst.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	/**
	 * 测试连接
	 * 
	 * @param args
	 *            参数
	 */
	public static void main(String[] args) {
		try {
			ConnectionFactory.getFactory().getConnection(
					"jdbc:oracle:thin:@192.168.3.115:1521:eb2b", "weiwenqi",
					"weiwenqi");
		} catch (Exception e) {
			System.out.println("Error :" + e.getMessage());
		}
	}
}
