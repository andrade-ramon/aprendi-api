package com.qualfacul.hades.search;

import static org.apache.commons.lang3.StringUtils.lowerCase;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qualfacul.hades.converter.ListConverter;

@Repository
@Transactional
public class SearchQuery<F, T> {

	public static final int MAX_RESULTS_PER_PAGE = 10;
	private final static Logger LOGGER = LoggerFactory.getLogger(SearchQuery.class);

	@PersistenceContext
	private EntityManager manager;

	private String match;
	private Class<?> clazz;
	private Set<String> fieldNames;
	private Float threshold;
	private Integer currentPage;
	private ListConverter<F, T> listConverter;

	public SearchQueryBuilder builder() {
		fieldNames = new HashSet<>();
		return new SearchQueryBuilder();
	}

	public class SearchQueryBuilder {

		public SearchQueryWithThreshold forEntity(Class<?> c) {
			clazz = c;
			try {
				searchInSubClasses(clazz, false);
			} catch (ClassNotFoundException e) {
				LOGGER.warn("Cannot index all fields of Class: {} ", clazz);
			}
			
			return new SearchQueryWithThreshold();
		}
		
		private void searchInSubClasses(Class<?> c, boolean isSuperClass) throws ClassNotFoundException{
			for (Field field : c.getDeclaredFields()) {
				if (field.isAnnotationPresent(org.hibernate.search.annotations.Field.class)) {
					String value = isSuperClass ? lowerCase(c.getSimpleName()) + "." + field.getName() : field.getName();
					fieldNames.add(value);
				} else if (field.isAnnotationPresent(IndexedEmbedded.class)) {
					Class<?> clazz = Class.forName(field.getGenericType().getTypeName());
					searchInSubClasses(clazz, true);
				}
			}
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

		public SearchQueryListConverter forPage(Integer pageNum) {
			currentPage = pageNum != null && pageNum > 0 ? pageNum : 1;
			return new SearchQueryListConverter();
		}
	}

	public class SearchQueryListConverter {
		
		public SearchQueryFactory withListConverter(ListConverter<F, T> converter) {
			listConverter = converter;
			return new SearchQueryFactory();
		}
	}
	
	
	public class SearchQueryFactory {
		
		@SuppressWarnings("unchecked")
		public PaginatedResult<T> build() {
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
			
			List<T> results = listConverter.convert(query.list());
			
			return new PaginatedResult<T>(results, currentPage, query.getResultSize(), MAX_RESULTS_PER_PAGE);
		}
	}

}
