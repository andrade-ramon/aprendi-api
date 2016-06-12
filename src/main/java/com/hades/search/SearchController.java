package com.hades.search;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hades.college.search.CollegeSearchRepository;
import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.PermitEndpoint;
import com.qualfacul.hades.college.College;

@RestController
public class SearchController {
	
	private CollegeSearchRepository searchRepository;
	
	@Autowired
	public SearchController(CollegeSearchRepository searchRepository) {
		this.searchRepository = searchRepository;
	}

	@PermitEndpoint
	@Get("/search/{query}")
	public PaginatedSearch<College> list(@NotBlank @PathVariable String query, @RequestParam(required = false) Integer page) {
		return searchRepository.listByQyery(query, page);
	}
}
