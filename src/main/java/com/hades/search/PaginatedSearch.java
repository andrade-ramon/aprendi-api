package com.hades.search;

import java.util.List;

public class PaginatedSearch<T> {

	private Integer count;
	private Integer page;
	private Integer pageSize;
	private Integer totalPages;
	private List<T> searchResult;
	
	public PaginatedSearch(List<T> searchResult, Integer count, Integer page, Integer pageSize) {
		this.searchResult = searchResult;
		this.count = count;
		this.page = page;
		this.pageSize = pageSize;
		this.totalPages = count / pageSize;
	}

	public List<T> getSearchResult() {
		return searchResult;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getCount() {
		return count;
	}
	
	public Integer getTotalPages() {
		return totalPages;
	}
}
