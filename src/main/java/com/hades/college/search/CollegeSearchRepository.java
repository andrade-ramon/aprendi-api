package com.hades.college.search;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hades.search.SearchQuery;
import com.qualfacul.hades.college.College;

@Repository
@Transactional
public class CollegeSearchRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public List<College> listByQyery(String match) {
		
		return (List<College>) SearchQuery.Builder()
						.withManager(manager)
						.withThreshold(0.3f)
						.matching(match)
						.forEntity(College.class)
						.build();
	}

}
