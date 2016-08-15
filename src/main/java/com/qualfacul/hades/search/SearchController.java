package com.qualfacul.hades.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.college.College;

@RestController
public class SearchController {
	
	private static final float COLLEGE_THRESHOLD = 0.4f;
	private SearchQuery<College> collegeSearch; 
	
	@Autowired
	public SearchController(SearchQuery<College> collegeSearch) {
		this.collegeSearch = collegeSearch;
	}

	@PublicEndpoint
	@Get("/search/{query}")
	public PaginatedSearch<College> list(@PathVariable String query, @RequestParam(required = false) Integer page) {
		
		PaginatedSearch<College> paginatedSearch = collegeSearch
			.builder()
			.forEntity(College.class)
			.withThreshold(COLLEGE_THRESHOLD)
			.matching(query)
			.forPage(page)
			.build();
		
		return paginatedSearch;
	}
	
}
