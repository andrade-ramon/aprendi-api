package com.qualfacul.hades.course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.college.CollegeAddress;
import com.qualfacul.hades.college.CollegeAddressRepository;
import com.qualfacul.hades.converter.ListConverter;
import com.qualfacul.hades.exceptions.CourseNotFoundException;
import com.qualfacul.hades.search.PaginatedResult;
import com.qualfacul.hades.search.SearchQuery;

@RestController
public class CourseController {
	private static final int MAX_RESULTS_PER_PAGE = 10;

	private static final float COURSE_THRESHOLD = 0.4f;

	@Autowired
	private SearchQuery<Course, CourseDTO> courseSearch;
	@Autowired
	private CourseToDTOConverter courseConverter;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CollegeAddressRepository collegeAddressRepository;
	@Autowired
	private CollegeAddressToCourseCollegesDTOConverter collegeAddressConverter;

	@PublicEndpoint
	@Get("/courses/search/{query}")
	public PaginatedResult<CourseDTO> list(@PathVariable String query, @RequestParam(required = false) Integer page) {
		ListConverter<Course, CourseDTO> listConverter = new ListConverter<>(courseConverter);

		PaginatedResult<CourseDTO> dtos = courseSearch.builder()
										.forEntity(Course.class)
										.withThreshold(COURSE_THRESHOLD)
										.matching(query)
										.forPage(page)
										.withListConverter(listConverter).build();

		return dtos;
	}

	@PublicEndpoint
	@Get("/courses/{courseId}/colleges")
	public PaginatedResult<CourseCollegesDTO> getCollegesFor(@PathVariable Long courseId,
			@RequestParam(required = false) Integer page) {
		if (page == null || page < 1) {
			page = 1;
		}

		Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
		List<CourseCollegesDTO> dtos = new ArrayList<>();

		Pageable pageable = new PageRequest(page - 1, MAX_RESULTS_PER_PAGE);

		Page<CollegeAddress> colleges = collegeAddressRepository
				.findByCoursesInOrderByCollegeName(Arrays.asList(course), pageable);

		colleges.getContent().stream().forEach(collegeAddress -> {
			CourseCollegesDTO dto = collegeAddressConverter.convert(collegeAddress);
			dtos.add(dto);
		});
		
		int totalOfElements = (int) colleges.getTotalElements();
		PaginatedResult<CourseCollegesDTO> result = new PaginatedResult<>(dtos, page, totalOfElements, MAX_RESULTS_PER_PAGE);

		return result;
	}
}
