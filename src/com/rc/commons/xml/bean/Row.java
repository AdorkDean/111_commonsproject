package com.rc.commons.xml.bean;

import java.util.List;

/**  
 * @Title: Row.java
 * @Description: 
 * @author yinbinhome@163.com  
 * @date 2012-11-17 下午12:51:29
 * @version V1.0  
 */

public class Row {
	private List rowcells;
	private String Index;
	
	public List getRowcells() {
		return rowcells;
	}
	public void setRowcells(List rowcells) {
		this.rowcells = rowcells;
	}
	public String getIndex() {
		return Index;
	}
	public void setIndex(String index) {
		Index = index;
	}
	
}
