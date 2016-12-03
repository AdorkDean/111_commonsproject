package com.rc.commons.bdb.vo;

import java.io.Serializable;

/**  
 * @Title: TestObject.java
 * @Description: 
 * @author yinbinhome@163.com  
 * @date 2011-5-17 上午12:37:48
 * @version V1.0  
 */

public class TestObject implements Serializable {
	private String name="测试对象";
	private String desc="这是一个测试对象";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
