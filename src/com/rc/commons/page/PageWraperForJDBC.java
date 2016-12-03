/*
 * 创建日期 2005-12-14
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.rc.commons.page;

import java.sql.ResultSet;

/**
 * @author  
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class PageWraperForJDBC {
	
	private PageInfo pageInfo=null;
	private ResultSet result=null;

	/**
	 * @return
	 */
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	
	/**
	 * @param info
	 */
	public void setPageInfo(PageInfo info) {
		pageInfo = info;
	}

	

	/**
	 * @return
	 */
	public ResultSet getResult()
	{
		return result;
	}

	/**
	 * @param set
	 */
	public void setResult(ResultSet set)
	{
		result = set;
	}

}
