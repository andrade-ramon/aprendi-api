package com.hades.search;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

public class SearchQuery<T> {

	private static final int MAX_RESULTS_PER_PAGE = 40;
	Session session;
	String match;
	Class<?> clazz;
	Set<String> fieldNames;
	float threshold;
	Integer page;

	public SearchQuery<T>.Entities withManager(EntityManager manager) {
		this.session = manager.unwrap(Session.class);
		return new Entities(); 
	}
	public class Entities {
		public SearchQuery<T>.Threshold forEntity(Class<?> c) {
			fieldNames = new HashSet<>();
			clazz = c;
	        for (Field field : clazz.getDeclaredFields()) {
	            if (field.isAnnotationPresent(org.hibernate.search.annotations.Field.class)) {
	               fieldNames.add(field.getName()); 
	            }
	        }
	        return new Threshold();
		}
	}
	public class Threshold {

		public Matcher withThreshold(float t) {
			threshold = t;
			return new Matcher();
		}
	}

	public class Matcher {
		
		public SearchQuery<T>.Pagination matching(String m) {
			match = m;
			return new Pagination();
		}
	}
	
	public class Pagination {
		
		public SearchQuery<T>.Factory forPage(Integer pageNum) {
			page = pageNum != 0 ? pageNum : 1;
			return new Factory();
		}
	}
	
	public class Factory {

		@SuppressWarnings("unchecked")
		public PaginatedSearch<T> build() {
			FullTextSession fullTextSession = Search.getFullTextSession(session);
			
			QueryBuilder queryBuilder = fullTextSession
							.getSearchFactory()
							.buildQueryBuilder()
							.forEntity(clazz)
							.get();
			Query luceneQuery = queryBuilder
							.keyword()
							.fuzzy()
							.withThreshold(threshold)
							.onFields(fieldNames.toArray(new String[fieldNames.size()]))
							.matching(match)
							.createQuery();
			
			FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery, clazz);
			query.setMaxResults(MAX_RESULTS_PER_PAGE);
			query.setFirstResult((page-1) * MAX_RESULTS_PER_PAGE);
			
			PaginatedSearch<T> paginatedSearch = new PaginatedSearch<>(
								query.list(),
								query.getResultSize(),
								page,
								MAX_RESULTS_PER_PAGE
							);
			
			return paginatedSearch;
		}
	}
	
}
