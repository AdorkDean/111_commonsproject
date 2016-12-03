package com.rc.commons.bdb.bdbpool;


import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

import com.rc.commons.bdb.Bdbinstance;
import com.rc.commons.bdb.vo.TestObject;

/**
 * @Title: ConnectionPool.java
 * @Description:
 * @author yinbinhome@163.com
 * @date 2011-5-16 下午09:40:09
 * @version V1.0
 */

public class ConnectionPool {
	private static ConnectionPool instance=null;
	
	private ConnectionParam param;

	private String testTable = "testObject"; // 测试连接是否可用的测试表名，默认没有测试表

	private Vector connections = null; // 存放连接池中数据库连接的向量 , 初始时为

	

	private ConnectionPool(ConnectionParam param) {
		super();
		this.param = param;
	}
	
	public synchronized static ConnectionPool getIntance(ConnectionParam param){
		if(instance==null){
			instance = new ConnectionPool(param);
			instance.createPool();			
		}
		return instance;
	}
	
	/**
	 * 创建一个数据库连接池，连接池中的可用连接的数量采用类成员 initialConnections 中设置的值
	 */

	public synchronized void createPool() {
		// 确保连接池没有创建
		// 如果连接池己经创建了，保存连接的向量 connections 不会为空
		if (connections != null) {
			return; // 如果己经创建，则返回
		}

		// Bdbinstance b = Bdbinstance.getInstance();
		// b.openBDBEnv();

		connections = new Vector();
		// 根据 initialConnections 中设置的值，创建连接。
		createConnections(this.param.getMinConnection());
		System.out.println(" 数据库连接池创建成功！ ");
	}

	/**
	 * 
	 * 创建由 numConnections 指定数目的数据库连接 , 并把这些连接 放入 connections 向量中
	 * 
	 * @param numConnections
	 *            要创建的数据库连接的数目
	 */

	private void createConnections(int numConnections) {

		// 循环创建指定数目的数据库连接
		for (int x = 0; x < numConnections; x++) {
			// 是否连接池中的数据库连接的数量己经达到最大？最大值由类成员 maxConnections,指出，如果 maxConnections
			// 为 0 或负数，表示连接数量没有限制。
			// 如果连接数己经达到最大，即退出。

			if (this.param.getMaxConnection() > 0 && this.connections.size() >= this.param.getMaxConnection()) {
				break;
			}

			// add a new PooledConnection object to connections vector
			// 增加一个连接到连接池中（向量 connections 中）

			try {
				Bdbinstance b = new Bdbinstance(param.getBdbPath());
				b.openBDBEnv();
				b.openDatabase();
				connections.addElement(new PooledConnection(b));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(" 创建数据库连接失败！ " + e.getMessage());
			}

			System.out.println(" 数据库连接己创建 ......");
		}

	}

	/**
	 * 
	 * 创建一个新的数据库连接并返回它
	 * 
	 * @return 返回一个新创建的数据库连接
	 */

	private Bdbinstance newConnection() throws SQLException {

		// 创建一个数据库连接
		Bdbinstance b = new Bdbinstance();
		b.openBDBEnv();
		b.openDatabase();

		// 如果这是第一次创建数据库连接，即检查数据库，获得此数据库允许支持的
		// 最大客户连接数目
		
		return b; // 返回创建的新的数据库连接
	}
	
	
	/**
	 * 
	 * 通过调用 getFreeConnection() 函数返回一个可用的数据库连接 ,
	 * 
	 * 如果当前没有可用的数据库连接，并且更多的数据库连接不能创
	 * 
	 * 建（如连接池大小的限制），此函数等待一会再尝试获取。
	 * 
	 * @return 返回一个可用的数据库连接对象
	 */

	public synchronized Bdbinstance getConnection(){

		// 确保连接池己被创建
		if (connections == null) {
			return null; // 连接池还没创建，则返回 null
		}

		Bdbinstance conn = getFreeConnection(); // 获得一个可用的数据库连接
		// 如果目前没有可以使用的连接，即所有的连接都在使用中

		while (conn == null) {
			// 等一会再试
			wait(250);
			conn = getFreeConnection(); // 重新再试，直到获得可用的连接，如果
			// getFreeConnection() 返回的为 null
			// 则表明创建一批连接后也不可获得可用连接
		}

		return conn;// 返回获得的可用的连接
	}
	
	/**
	 * 
	 * 本函数从连接池向量 connections 中返回一个可用的的数据库连接，如果
	 * 
	 * 当前没有可用的数据库连接，本函数则根据 incrementalConnections 设置
	 * 
	 * 的值创建几个数据库连接，并放入连接池中。
	 * 
	 * 如果创建后，所有的连接仍都在使用中，则返回 null
	 * 
	 * @return 返回一个可用的数据库连接
	 */

	private Bdbinstance getFreeConnection()  {

		// 从连接池中获得一个可用的数据库连接
		Bdbinstance conn = findFreeConnection();
		if (conn == null) {
			// 如果目前连接池中没有可用的连接
			// 创建一些连接
			createConnections(this.param.getIncrementalConnections());
			// 重新从池中查找是否有可用连接
			conn = findFreeConnection();
			if (conn == null) {
				// 如果创建连接后仍获得不到可用的连接，则返回 null
				return null;
			}
		}
		return conn;

	}
	
	
	/**
	 * 
	 * 查找连接池中所有的连接，查找一个可用的数据库连接，
	 * 
	 * 如果没有可用的连接，返回 null
	 * 
	 * @return 返回一个可用的数据库连接
	 */

	private Bdbinstance findFreeConnection() {

		Bdbinstance conn = null;
		PooledConnection pConn = null;
		// 获得连接池向量中所有的对象
		Enumeration enumerate = connections.elements();
		// 遍历所有的对象，看是否有可用的连接
		while (enumerate.hasMoreElements()) {
			pConn = (PooledConnection) enumerate.nextElement();
			if (!pConn.isBusy()) {
				// 如果此对象不忙，则获得它的数据库连接并把它设为忙
				conn = pConn.getConnection();
				pConn.setBusy(true);
				// 测试此连接是否可用
				if (!testConnection(conn)) {
					// 如果此连接不可再用了，则创建一个新的连接，
					// 并替换此不可用的连接对象，如果创建失败，返回 null
					try {
						conn = newConnection();
					} catch (SQLException e) {
						System.out.println(" 创建数据库连接失败！ " + e.getMessage());
						return null;
					}
					pConn.setConnection(conn);
				}
				break; // 己经找到一个可用的连接，退出
			}
		}

		return conn;// 返回找到到的可用连接

	}
	
	
	/**
	 * 
	 * 测试一个连接是否可用，如果不可用，关掉它并返回 false
	 * 
	 * 否则可用返回 true
	 * 
	 * 
	 * 
	 * @param conn
	 *            需要测试的数据库连接
	 * 
	 * @return 返回 true 表示此连接可用， false 表示不可用
	 */

	private boolean testConnection(Bdbinstance conn) {

		try {

			// 判断测试表是否存在
			if (testTable.equals("")) {
				TestObject t=new TestObject();
				conn.addObject("testObject", t);
				conn.getObject("testObject", t);
			}else{
				TestObject t=new TestObject();
				conn.addObject(testTable, t);
				conn.getObject(testTable, t);
			}

		} catch (Exception e) {
			// 上面抛出异常，此连接己不可用，关闭它，并返回 false;
			closeConnection(conn);
			return false;
		}
		// 连接可用，返回 true
		return true;

	}
	
	
	/**
	 * 
	 * 此函数返回一个数据库连接到连接池中，并把此连接置为空闲。
	 * 
	 * 所有使用连接池获得的数据库连接均应在不使用此连接时返回它。
	 * 
	 * @param 需返回到连接池中的连接对象
	 */

	public void returnConnection(Bdbinstance conn) {

		// 确保连接池存在，如果连接没有创建（不存在），直接返回

		if (connections == null) {
			System.out.println(" 连接池不存在，无法返回此连接到连接池中 !");
			return;
		}

		PooledConnection pConn = null;
		Enumeration enumerate = connections.elements();
		// 遍历连接池中的所有连接，找到这个要返回的连接对象
		while (enumerate.hasMoreElements()) {
			pConn = (PooledConnection) enumerate.nextElement();
			// 先找到连接池中的要返回的连接对象
			if (conn == pConn.getConnection()) {
				// 找到了 , 设置此连接为空闲状态
				pConn.setBusy(false);
				break;
			}

		}

	}
	
	
	/**
	 * 
	 * 刷新连接池中所有的连接对象
	 * 
	 * 
	 */

	public synchronized void refreshConnections() throws SQLException {

		// 确保连接池己创新存在
		if (connections == null) {
			System.out.println(" 连接池不存在，无法刷新 !");
			return;
		}

		PooledConnection pConn = null;
		Enumeration enumerate = connections.elements();
		while (enumerate.hasMoreElements()) {
			// 获得一个连接对象
			pConn = (PooledConnection) enumerate.nextElement();
			// 如果对象忙则等 5 秒 ,5 秒后直接刷新
			if (pConn.isBusy()) {
				wait(5000); // 等 5 秒
			}

			// 关闭此连接，用一个新的连接代替它。
			closeConnection(pConn.getConnection());
			pConn.setConnection(newConnection());
			pConn.setBusy(false);
		}

	}
	
	/**
	 * 
	 * 关闭连接池中所有的连接，并清空连接池。
	 */

	public synchronized void closeConnectionPool(){

		// 确保连接池存在，如果不存在，返回
		if (connections == null) {
			System.out.println(" 连接池不存在，无法关闭 !");
			return;
		}
		PooledConnection pConn = null;
		Enumeration enumerate = connections.elements();
		while (enumerate.hasMoreElements()) {
			pConn = (PooledConnection) enumerate.nextElement();
			// 如果忙，等 5 秒
			if (pConn.isBusy()) {
				wait(5000); // 等 5 秒
			}
			// 5 秒后直接关闭它
			closeConnection(pConn.getConnection());
			// 从连接池向量中删除它
			connections.removeElement(pConn);
		}

		// 置连接池为空
		connections = null;
	}
	
	
	
	/**
	 * 
	 * 关闭一个数据库连接
	 * 
	 * @param 需要关闭的数据库连接
	 */

	private void closeConnection(Bdbinstance conn) {
		try {
			conn.shutdown();
		} catch (Exception e) {
			System.out.println(" 关闭数据库连接出错： " + e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * 使程序等待给定的毫秒数
	 * 
	 * @param 给定的毫秒数
	 */

	private void wait(int mSeconds) {
		try {
			Thread.sleep(mSeconds);
		} catch (InterruptedException e) {
		}
	}
	
	
	
	
	

	public ConnectionParam getParam() {
		return param;
	}

	public void setParam(ConnectionParam param) {
		this.param = param;
	}

	public String getTestTable() {
		return testTable;
	}

	public void setTestTable(String testTable) {
		this.testTable = testTable;
	}

	public Vector getConnections() {
		return connections;
	}

	public void setConnections(Vector connections) {
		this.connections = connections;
	}

	/**
	 * 
	 * 内部使用的用于保存连接池中连接对象的类 此类中有两个成员，一个是数据库的连接，另一个是指示此连接是否 正在使用的标志。
	 */
	class PooledConnection {

		Bdbinstance connection = null;// 数据库连接

		boolean busy = false; // 此连接是否正在使用的标志，默认没有正在使用

		// 构造函数，根据一个 Connection 构告一个 PooledConnection 对象
		public PooledConnection(Bdbinstance connection) {
			this.connection = connection;
		}

		// 返回此对象中的连接
		public Bdbinstance getConnection() {
			return connection;
		}

		// 设置此对象的，连接
		public void setConnection(Bdbinstance connection) {
			this.connection = connection;
		}

		// 获得对象连接是否忙
		public boolean isBusy() {
			return busy;
		}

		// 设置对象的连接正在忙
		public void setBusy(boolean busy) {
			this.busy = busy;
		}

	}

}
