package com.example.mywork.util;

public class Page {
	/**
	 * 		// 总条数
	 */
	public int count = Integer.MAX_VALUE;
	/**
	 * 		// 页码
	 */
	public int pageNo = 1;
	/**
	 * 		//每页条数
	 */
	public int pageItemSize = 24;//3;
	/**
	 * 		页数
	 */
	public int pageSize = 1;// 页数
	
	public boolean isLastPage ()
	{
		return pageNo >= pageSize;
	}
	
	public void nextPage ()
	{
		pageNo += pageItemSize;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageItemSize() {
		return pageItemSize;
	}
	public void setPageItemSize(int pageItemSize) {
		this.pageItemSize = pageItemSize;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
