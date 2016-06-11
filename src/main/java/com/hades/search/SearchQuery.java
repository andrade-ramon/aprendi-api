package com.hades.search;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

public class SearchQuery {

	Session session;
	String searchMatch;
	Class<?> searchClazz;
	Set<String> fieldNames = new HashSet<>();
	float searchThreshold;

	public SearchQuery.Threshold withManager(EntityManager manager) {
		this.session = manager.unwrap(Session.class);
		return new Threshold(); 
	}
	public class Threshold {

		public Matcher withThreshold(float threshold) {
			searchThreshold = threshold;
			return new Matcher();
		}
	}

	public class Matcher {
		
		public SearchQuery.Entities matching(String matchString) {
			searchMatch = matchString;
			return new Entities();
		}
	}
	
	public class Entities {

		public SearchQuery.Builder forEntity(Class<?> clazz) {
			searchClazz = clazz;
			Class<?> c = clazz;
		    while (c != null) {
		        for (Field field : c.getDeclaredFields()) {
		            if (field.isAnnotationPresent(org.hibernate.search.annotations.Field.class)) {
		               fieldNames.add(field.getName()); 
		            }
		        }
		        c = c.getSuperclass();
		    }
			return new Builder();
		}
		
	}
	public class Builder {


		public List<?> build() {
			FullTextSession fullTextSession = Search.getFullTextSession(session);
			
			QueryBuilder queryBuilder = fullTextSession
							.getSearchFactory()
							.buildQueryBuilder()
							.forEntity(searchClazz)
							.get();
			Query luceneQuery = queryBuilder.keyword()
							.fuzzy()
							.withThreshold(searchThreshold)
							.onFields(fieldNames.toArray(new String[fieldNames.size()]))
							.matching(searchMatch)
							.createQuery();
			
			FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery, searchClazz);
			
			return query.list();
		}
		
	}
	public static SearchQuery Builder() {
		return new SearchQuery();
	}
}
