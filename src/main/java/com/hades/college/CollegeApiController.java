package com.hades.college;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.Get;
import com.hades.annotation.InternalEndpoint;
import com.hades.annotation.Post;
import com.hades.college.converter.CollegeMecDTOToCollegeConverter;
import com.hades.college.converter.CollegeToCollegeMecDTOConverter;
import com.hades.course.Course;
import com.hades.course.converter.CourseMecDTOToCourseConverter;
import com.hades.exceptions.college.NoColegeFound;
import com.qualfacul.hermes.college.CollegeMecDTO;
import com.qualfacul.poseidon.course.CourseMecDTO;

@RestController
public class CollegeApiController {

	private CollegeMecDTOToCollegeConverter dtoToCollegeConverter;
	private CollegeToCollegeMecDTOConverter collegeToDtoConverter;
	private CourseMecDTOToCourseConverter dtoToCourseConverter;

	private CollegeRepository repository;

	@Autowired
	public CollegeApiController(CollegeMecDTOToCollegeConverter dtoToCollegeConverter, CollegeToCollegeMecDTOConverter collegeToDtoConverter,
								CourseMecDTOToCourseConverter dtoToCourseConverter, CollegeRepository repository) {
		this.dtoToCollegeConverter = dtoToCollegeConverter;
		this.collegeToDtoConverter = collegeToDtoConverter;
		this.dtoToCourseConverter = dtoToCourseConverter;
		this.repository = repository;
	}

	@InternalEndpoint
	@Post(value = "/api/{version}/colleges", responseStatus = CREATED)
	public Long updateOrSave(@Valid @RequestBody CollegeMecDTO dto) {
		College collegeToMerge = dtoToCollegeConverter.convert(dto);

		Optional<College> optionalCollege = repository.findByCnpj(collegeToMerge.getCnpj());

		optionalCollege.ifPresent(college -> {
			if (equalsIgnoreCase(collegeToMerge.getCnpj(), college.getCnpj())) {
				collegeToMerge.setId(college.getId());
			}
		});

		repository.save(collegeToMerge);

		return collegeToMerge.getId();
	}

	@InternalEndpoint
	@Get("/api/{version}/colleges/{collegeId}")
	public CollegeMecDTO getById(@PathVariable("collegeId") Long collegeId) {
		Optional<College> optionalCollege = repository.findById(collegeId);
		College collegeFound = optionalCollege.orElseThrow(NoColegeFound::new);

		return collegeToDtoConverter.convert(collegeFound);
	}

	@InternalEndpoint
	@Get("/api/{version}/colleges/count")
	public Long count() {
		return repository.count();
	}

	@InternalEndpoint
	@Post(value = "/api/{version}/colleges/{collegeId}/grades", responseStatus = CREATED)
	public Long updateOrSaveGrade(@PathVariable Long collegeId, @Valid @RequestBody CourseMecDTO dto) {
		Course courseToMerge = dtoToCourseConverter.convert(dto);

		repository.findById(collegeId).ifPresent(college -> {
			
			List<Course> collegeCourses = college.getCourses();
			
			if (!collegeCourses.contains(college)) {
				collegeCourses.add(courseToMerge);
			} else {
				collegeCourses.stream()
							  .filter(course -> course.equals(courseToMerge))
							  .map(course -> courseToMerge);
			}

			repository.save(college);
		});
		
		return courseToMerge.getId();
	}
}
