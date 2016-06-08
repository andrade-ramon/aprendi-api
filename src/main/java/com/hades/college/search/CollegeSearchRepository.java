package com.hades.college.search;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qualfacul.hades.college.College;

@Repository
@Transactional
public class CollegeSearchRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public List<College> listByQyery(String match) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(manager);
		
		QueryParser parser = new QueryParser(Version.LUCENE_32, "initials", new BrazilianAnalyzer(Version.LUCENE_32));
		
		/*QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
						 .buildQueryBuilder()
						 .forEntity(College.class)
						 .get();
		Query luceneQuery = queryBuilder.keyword()
					.onFields("name", "initials")
					.matching(match)
					.createQuery();*/
		Query luceneQuery;
		try {
			luceneQuery = parser.parse(match);
			FullTextQuery query = fullTextEntityManager.createFullTextQuery(luceneQuery);
			return query.getResultList();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
