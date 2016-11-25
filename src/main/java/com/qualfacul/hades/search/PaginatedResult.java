package com.qualfacul.hades.search;

import java.util.Collection;

public class PaginatedResult<T> {

	private Integer page;
	private Integer currentPageSize;
	private Integer totalResults;
	private Integer totalPages;
	private Collection<T> result;

	public PaginatedResult(Collection<T> result, int page, int totalResults, int maxResultsPerPage) {
		this.page = page;
		this.currentPageSize = result.size();
		this.result = result;
		this.totalResults = totalResults;

		int totalPages = (int) (totalResults / maxResultsPerPage);
		if (totalResults % maxResultsPerPage == 0) {
			this.totalPages = totalPages;
		} else {
			this.totalPages = totalPages + 1;
		}
	}

	public Integer getPage() {
		return page;
	}

	public Integer getCurrentPageSize() {
		return currentPageSize;
	}

	public Integer getTotalResults() {
		return totalResults;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public Collection<T> getResult() {
		return result;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setCurrentPageSize(Integer currentPageSize) {
		this.currentPageSize = currentPageSize;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public void setResult(Collection<T> result) {
		this.result = result;
	}
}
