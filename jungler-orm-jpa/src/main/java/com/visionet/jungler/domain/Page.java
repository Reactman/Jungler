/**
 * Copyright (c) 2016. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.domain;

import java.util.List;

public class Page {

	private long total;//总行数
	private int pageSize = 10;//每页显示的数据记录
	private int curPage;//当前页
	private List rows;//储存查询的结果集

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurPage() {
		return this.curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
}
