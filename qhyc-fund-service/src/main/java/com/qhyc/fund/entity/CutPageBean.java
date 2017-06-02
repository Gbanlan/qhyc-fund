package com.qhyc.fund.entity;

import java.util.List;

public class CutPageBean<T> {
	/**
	 * 集合
	 */
	private List<T> list;
	/**
	 * 记录数
	 */
	private int count;
	/**
	 * 总页数
	 */
	private int totalPage;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}
