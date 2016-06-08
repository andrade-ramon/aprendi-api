package com.hades.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
class SearchIndexInitializer implements ApplicationListener<ApplicationReadyEvent> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchIndexInitializer.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			LOGGER.error("Error on building the search index", e);
		}
	}

}
