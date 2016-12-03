package com.rc.commons.bdb.bdbpool;

import com.rc.commons.bdb.Bdbinstance;
import com.rc.commons.bdb.vo.TestObject;

/**  
 * @Title: TestThread.java
 * @Description: 
 * @author yinbinhome@163.com  
 * @date 2011-5-17 上午01:18:47
 * @version V1.0  
 */

public class TestThread implements Runnable {
	private Bdbinstance b=null;
	
	public TestThread(Bdbinstance b) {
		super();
		this.b = b;
	}

	public Bdbinstance getB() {
		return b;
	}

	public void setB(Bdbinstance b) {
		this.b = b;
	}

	
	public void run() {
		System.out.println("toString="+this.toString());
		// TODO Auto-generated method stub
		TestObject t=new TestObject();
//		for(int i=0;i<100;i++){
//			TestObject tt=(TestObject)b.get("t"+i, t);
//			System.out.println(tt.getName());
//		}
		TestObject tt=(TestObject)getB().getObject("t"+99, t);
		System.out.println(tt.getName());
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
