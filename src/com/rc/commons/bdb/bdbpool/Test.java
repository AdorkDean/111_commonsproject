package com.rc.commons.bdb.bdbpool;

import com.rc.commons.bdb.Bdbinstance;

/**  
 * @Title: Test.java
 * @Description: 
 * @author yinbinhome@163.com  
 * @date 2011-5-17 上午01:08:24
 * @version V1.0  
 */

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnectionParam param= new ConnectionParam();
		param.setBdbPath("D:/BDB/dbEnv");
		ConnectionPool p = ConnectionPool.getIntance(param);
		p.createPool();
		Bdbinstance b = p.getConnection();

//		for(int i=0;i<100;i++){
//			TestObject t=new TestObject();
//			t.setName("name-"+i);
//			b.addObject("t"+i, t);
//			System.out.println("name-"+i);
//			b.delData("t"+i);
////			提交事务
//		}	
		b.envSync();
		b.truncateDatebase();
		p.returnConnection(b);
		p.closeConnectionPool();
//		for(int i=0;i<100;i++){
//			TestObject t=new TestObject();
//			TestObject to=(TestObject)b.getObject("t"+i,t);
//			System.out.println("这是取出来的对象名字："+to.getName());
//		}
//		new Thread(new TestThread(p.getConnection())).start();
//		new Thread(new TestThread(p.getConnection())).start();
//		new Thread(new TestThread(p.getConnection())).start();
//		new Thread(new TestThread(p.getConnection())).start();
//		new Thread(new TestThread(p.getConnection())).start();
	}
}
