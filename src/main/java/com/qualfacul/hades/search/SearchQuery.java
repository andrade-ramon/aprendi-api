package com.qualfacul.hades.search;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SearchQuery<T> {

	private static final int MAX_RESULTS_PER_PAGE = 40;

	@PersistenceContext
	private EntityManager manager;

	private String match;
	private Class<?> clazz;
	private Set<String> fieldNames = new HashSet<>();
	private Float threshold;
	private Integer currentPage;

	public SearchQueryBuilder builder() {
		return new SearchQueryBuilder();
	}

	public class SearchQueryBuilder {

		public SearchQueryWithThreshold forEntity(Class<?> c) {
			clazz = c;
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(org.hibernate.search.annotations.Field.class)) {
					fieldNames.add(field.getName());
				}
			}
			return new SearchQueryWithThreshold();
		}
	}

	public class SearchQueryWithThreshold {

		public SearchQueryWithMatcher withThreshold(Float t) {
			threshold = t;
			return new SearchQueryWithMatcher();
		}
	}

	public class SearchQueryWithMatcher {

		public SearchQueryPagination matching(String m) {
			match = m;
			return new SearchQueryPagination();
		}
	}

	public class SearchQueryPagination {

		public SearchQueryFactory forPage(Integer pageNum) {
			currentPage = pageNum != null && pageNum > 0 ? pageNum : 1;
			return new SearchQueryFactory();
		}
	}

	public class SearchQueryFactory {

		@SuppressWarnings("unchecked")
		public PaginatedSearch<T> build() {
			Session session = manager.unwrap(Session.class);
			FullTextSession fullTextSession = Search.getFullTextSession(session);

			QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
													   .buildQueryBuilder()
													   .forEntity(clazz)
													   .get();
			
			Query luceneQuery = queryBuilder.keyword()
											.fuzzy()
											.withThreshold(threshold)
											.onFields(fieldNames.toArray(new String[fieldNames.size()]))
											.matching(match)
											.createQuery();

			FullTextQuery query = fullTextSession.createFullTextQuery(luceneQuery, clazz);
			query.setMaxResults(MAX_RESULTS_PER_PAGE);
			query.setFirstResult((currentPage - 1) * MAX_RESULTS_PER_PAGE);

			PaginatedSearch<T> paginatedSearch = new PaginatedSearch<>(query.list(), query.getResultSize(), currentPage, MAX_RESULTS_PER_PAGE);

			return paginatedSearch;
		}
	}

}
