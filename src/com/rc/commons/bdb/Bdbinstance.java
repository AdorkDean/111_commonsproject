package com.rc.commons.bdb;

import java.io.File;
import java.util.List;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

/**
 * @Title: Bdbinstance.java
 * @Description: DBD 数据库工具
 * @author yinbinhome@163.com
 * @date 2011-4-12 上午09:25:07
 * @version V1.0
 */

public class Bdbinstance {
	private Environment myDbEnvironment = null;
	private Database myDatabase = null;
	private Database myClassDatabase = null;
	private String DBDpath="";
	
	
	
	
	
	public Bdbinstance(String dpath) {
		super();
		DBDpath = dpath;
	}

	public Bdbinstance() {

	}
	
	public String getDBDpath() {
		return DBDpath;
	}

	public void setDBDpath(String dpath) {
		DBDpath = dpath;
	}

	public Environment getMyDbEnvironment() {
		return myDbEnvironment;
	}

	public void setMyDbEnvironment(Environment myDbEnvironment) {
		this.myDbEnvironment = myDbEnvironment;
	}

	public Database getMyDatabase() {
		return myDatabase;
	}

	public void setMyDatabase(Database myDatabase) {
		this.myDatabase = myDatabase;
	}

	public Database getMyClassDatabase() {
		return myClassDatabase;
	}

	public void setMyClassDatabase(Database myClassDatabase) {
		this.myClassDatabase = myClassDatabase;
	}

	

	

	/**
	 * 打开环境
	 */
	public void openBDBEnv() {
		System.out.println("打开BerkeleyDB环境......");
		try {
			EnvironmentConfig envConfig = new EnvironmentConfig();
			// 如果不存在则创建一个
			envConfig.setAllowCreate(true);
			
			// 以只读方式打开，默认为false
			envConfig.setReadOnly(false);
			// 事务支持,如果为true，则表示当前环境支持事务处理，默认为false，不支持事务处理。
			envConfig.setTransactional(true);
			// 设置当前环境能够使用的RAM占整个JVM内存的百分比
			envConfig.setCachePercent(20);
			// 设置当前环境能够使用的最大RAM。单位BYTE
			envConfig.setCacheSize(1024 * 1024 * 50);
			// 当提交事务的时候是否把缓存中的内容同步到磁盘中去。true 表示不同步，也就是说不写磁盘
//			 envConfig.setTxnNoSync(false);
			// 当提交事务的时候，是否把缓冲的log写到磁盘上 true 表示不同步，也就是说不写磁盘
//			 envConfig.setTxnWriteNoSync(false);
			setMyDbEnvironment(new Environment(new File(DBDpath), envConfig));
		} catch (DatabaseException dbe) {
			// 错误处理
			System.out.println("DBD数据不存在");
			dbe.printStackTrace();
		}

	}

	/**
	 * 打开/创建数据库
	 */
	public void openDatabase() {
		try {
			DatabaseConfig dbConfig = new DatabaseConfig();
			dbConfig.setAllowCreate(true);
			// 如果是true的话，则当不存在此数据库的时候创建一个。
			// dbConfig.setBtreeComparator(true);
			// 设置用于Btree比较的比较器，通常是用来排序
			// dbConfig.setDuplicateComparator() ;
			// 设置用来比较一个key有两个不同值的时候的大小比较器。
//			 dbConfig.setSortedDuplicates(true);
			// 设置一个key是否允许存储多个值，true代表允许，默认false.
			// dbConfig.setExclusiveCreate(true);
			// 以独占的方式打开，也就是说同一个时间只能有一实例打开这个database。
			//dbConfig.setReadOnly(false);
			// 以只读方式打开database,默认是false.
			// dbConfig.setTransactional(true);
			// 如果设置为true,则支持事务处理，默认是false，不支持事务。
			
			// 存储一般类型数据库
			setMyDatabase(getMyDbEnvironment().openDatabase(null, "basicDatabase", dbConfig));
			
			// 存储对象的数据库
			setMyClassDatabase(getMyDbEnvironment().openDatabase(null, "ClassDatabase", dbConfig));
			System.out.println("打开BerkeleyDB 数据库.....");
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 关闭数据库，关闭环境
	 */
	public void shutdown() {
		Database myDatabase_t = getMyDatabase();
		Database myClassDatabase_t = getMyClassDatabase();
		Environment myDbEnvironment_t = getMyDbEnvironment();
		try {
			if (myDatabase_t != null) {
				myDatabase_t.close();
			}
			if (myClassDatabase_t != null) {
				myClassDatabase_t.close();
			}
			if (myDbEnvironment_t != null) {
				myDbEnvironment_t.cleanLog();// 在关闭环境前清理下日志
				myDbEnvironment_t.close();
			}
			System.out.println("关闭BerkeleyDB......");
		} catch (DatabaseException dbe) {
			System.out.println("关闭BDB数据库失败");
		}
	}

	/**
	 * 清理日志
	 */
	public void cleanLog() {
		Environment myDbEnvironment_t = getMyDbEnvironment();
		try {
			if (myDbEnvironment_t != null) {
				myDbEnvironment_t.cleanLog(); // 在关闭环境前清理下日志
				myDbEnvironment_t.close();
			}
		} catch (DatabaseException dbe) {
			// Exception handling goes here
		}
	}

	/**
	 * 取数据库名
	 * 
	 * @return
	 */
	public String getBDBname() {
		// 取得数据库的名称
		return myDatabase.getDatabaseName() == null ? "" : myDatabase.getDatabaseName();
	}

	/**
	 * 取当前环境
	 * 
	 * @return
	 */
	public Environment getEnv() {
		return myDatabase.getEnvironment();
	}

	/**
	 * 删除当前环境中指定的数据库
	 */
	public void delDatabase() {
		String dbName = myDatabase.getDatabaseName();
		myDatabase.close();
		myDbEnvironment.removeDatabase(null, dbName);
	}

	/**
	 * 给当前环境下的数据库改名
	 * 
	 * @param newName
	 */
	public void renameDatabase(String newName) {
		String oldName = myDatabase.getDatabaseName();
		myDatabase.close();
		myDbEnvironment.renameDatabase(null, oldName, newName);
	}

	/**
	 * 清空database内的所有数据，返回清空了多少条记录
	 * 
	 * @return
	 */
	public Long truncateDatebase() {
		String oldName = myDatabase.getDatabaseName();
		String oldName2=myClassDatabase.getDatabaseName();
		myDatabase.close();
		myClassDatabase.close();
		Long numDiscarded = getEnv().truncateDatabase(null, oldName, true);
		Long numDiscarded2 = getEnv().truncateDatabase(null, oldName2, true);
		System.out.println("basicDatabase库一共删除了 " + numDiscarded + " 条记录 从数据库 " + oldName);
		System.out.println("ClassDatabase库一共删除了 " + numDiscarded2 + " 条记录 从数据库 " + oldName);
		return numDiscarded;
	}

	/**
	 * 返回当前环境下的数据库列表
	 * 
	 * @return
	 */
	public List getDBlist() {
		List myDbNames = myDbEnvironment.getDatabaseNames();
		for (int i = 0; i < myDbNames.size(); i++) {
			System.out.println("Database Name: " + (String) myDbNames.get(i));
		}
		return myDbNames;
	}

	/**
	 * 存储基本数据类型
	 * @param aKey
	 * @param aData
	 */
	public void addData(String aKey,String aData) {
		try {
			// 设置key/value,注意DatabaseEntry内使用的是bytes数组
			DatabaseEntry theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry(aData.getBytes("UTF-8"));
			myDatabase.put(null, theKey, theData);
		} catch (Exception e) {
			// 错误处理
			System.out.println("报错");
		}
	}

	/**
	 * 获取基本类型数据
	 * @param aKey
	 * @return
	 */
	public String getData(String aKey) {
		String foundData = "";
		try {
			DatabaseEntry theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry();

			if (myDatabase.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				foundData = new String(retData, "UTF-8");
				System.out.println("For key: '" + aKey + "' found data: '" + foundData + "'.");
			} else {
				System.out.println("No record found for key '" + aKey + "'.");
			}
		} catch (Exception e) {
			// Exception handling goes here
		}

		return foundData;
	}

	/**
	 * 删除基本类型数据
	 */
	public boolean delData(String aKey) {
		try {
			DatabaseEntry theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
			OperationStatus aa = myDatabase.delete(null, theKey);
			if(aa==OperationStatus.SUCCESS){
				return true;
			}else{
				return false;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("删除失败");
			return false;
		}
	}

	/**
	 * 提交事务,把数据同步到磁盘
	 */
	public void envSync() {
		getEnv().sync();
	}



	/**
	 * 存储对象
	 * 
	 * @param key
	 * @param obj
	 * @throws Exception
	 */
	public void addObject(String key, Object obj) {
		try {
			StoredClassCatalog classCatalog = new StoredClassCatalog(getMyClassDatabase());
			EntryBinding dataBinding = new SerialBinding(classCatalog, Object.class);
			DatabaseEntry theKey = new DatabaseEntry(key.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry();
			dataBinding.objectToEntry(obj, theData);
			getMyDatabase().put(null, theKey, theData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("保存异常");
			e.printStackTrace();
		}
	}

	public Object getObject(String aKey,Object obj) {
		try {
			// 实例化catalog
			StoredClassCatalog classCatalog = new StoredClassCatalog(getMyClassDatabase());
			// 创建绑定对象
			EntryBinding dataBinding = new SerialBinding(classCatalog, Object.class);
			DatabaseEntry theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry();
			if(getMyDatabase().get(null, theKey, theData, LockMode.DEFAULT)==OperationStatus.SUCCESS){
				// 根据存储的类信息还原数据
				return dataBinding.entryToObject(theData);	
			}else{
				System.out.println("No record found for key '" + aKey + "'.");
				return null;
			}
					
		} catch (Exception e) {
			// Exception handling goes here
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void main(String[] args) {
		Bdbinstance b=new Bdbinstance("D:/BDB/dbEnv");
		b.openBDBEnv();
		b.openDatabase();
		String aa=b.getData("1@2011042900000001");
		System.out.println(aa);
		b.shutdown();
	}
}
