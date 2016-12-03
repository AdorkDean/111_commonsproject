package com.rc.commons.lucene;

import java.util.List;

/**  
 * @Title: IndexObject.java
 * @Description: 
 * @author yinbinhome@163.com  
 * @date 2011-11-1 下午04:51:14
 * @version V1.0  
 */

public class IndexObject {
	private String totalpages;
	private String page;
	private List<IndexModel> models;
	public String getTotalpages() {
		return totalpages;
	}
	public void setTotalpages(String totalpages) {
		this.totalpages = totalpages;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public List<IndexModel> getModels() {
		return models;
	}
	public void setModels(List<IndexModel> models) {
		this.models = models;
	}

	
}
