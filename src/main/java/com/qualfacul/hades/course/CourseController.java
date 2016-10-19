package com.qualfacul.hades.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.converter.ListConverter;
import com.qualfacul.hades.search.PaginatedResult;
import com.qualfacul.hades.search.SearchQuery;

@RestController
public class CourseController {
	private static final float COURSE_THRESHOLD = 0.4f;
	
	@Autowired
	private SearchQuery<Course, CourseDTO> courseSearch;
	@Autowired
	private CourseToDTOConverter courseConverter;
	
	@PublicEndpoint
	@Get("/courses/search/{query}")
	public PaginatedResult<CourseDTO> list(@PathVariable String query, @RequestParam(required = false) Integer page) {		
		ListConverter<Course, CourseDTO> listConverter = new ListConverter<>(courseConverter);
		
		PaginatedResult<CourseDTO> dtos = courseSearch
				.builder()
				.forEntity(Course.class)
				.withThreshold(COURSE_THRESHOLD)
				.matching(query)
				.forPage(page)
				.withListConverter(listConverter)
				.build();
			
		return dtos;
		
	}
}
