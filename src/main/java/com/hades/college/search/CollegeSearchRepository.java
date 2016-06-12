package com.hades.college.search;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hades.search.PaginatedSearch;
import com.hades.search.SearchQuery;
import com.qualfacul.hades.college.College;

@Repository
@Transactional
public class CollegeSearchRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	public PaginatedSearch<College> listByQyery(String match, Integer...page) {
		Integer pageNum = page.length != 0 && page[0] != null  ? page[0] : 1; 
		
		PaginatedSearch<College> paginatedSearch = new SearchQuery<College>()
			.withManager(manager)
			.forEntity(College.class)
			.withThreshold(0.3f)
			.matching(match)
			.forPage(pageNum)
			.build();
		
		return paginatedSearch;
	}

}
