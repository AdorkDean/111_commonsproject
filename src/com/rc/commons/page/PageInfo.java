package com.rc.commons.page;


public class PageInfo {
	private int page=1;
	private int pageSize=20;
	private int count;
	private int pages;
	private int prePage;
	private int nextPage;
	private int start; //当页开始记录
	private int end; //当页结束记录
	private int startPage; //当前开始页码
	private int endPage; //当前结束页码
  
	public int getCount() {
		return count;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPages() {
		return pages;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public int getPage() {
		return page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getEnd() {
		return end;
	}

	public int getStart() {
		return start;
	}

	public PageInfo() {
	}
	/**
	 * @return
	 */
	public int getEndPage() {
		return endPage;
	}

	/**
	 * @return
	 */
	public int getStartPage() {
		return startPage;
	}

	/**
	 * @param i
	 */
	public void setEndPage(int i) {
		endPage = i;
	}

	/**
	 * @param i
	 */
	public void setStartPage(int i) {
		startPage = i;
	}

}
