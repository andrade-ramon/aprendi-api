package com.hades.college;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.springframework.http.HttpStatus.CREATED;

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
import com.hades.exceptions.college.NoColegeFound;
import com.qualfacul.hermes.college.CollegeMecDTO;
import com.qualfacul.poseidon.course.CourseMecDTO;

@RestController
public class CollegeApiController {

	private CollegeMecDTOToCollegeConverter dtoToCollegeConverter;
	private CollegeToCollegeMecDTOConverter collegeToDtoConverter;
	
	private CollegeRepository repository;
	
	@Autowired
	public CollegeApiController(CollegeMecDTOToCollegeConverter dtoToCollegeConverter,CollegeToCollegeMecDTOConverter collegeToDtoConverter, CollegeRepository repository) {
		this.dtoToCollegeConverter = dtoToCollegeConverter;
		this.collegeToDtoConverter = collegeToDtoConverter;
		this.repository = repository;
	}

	@Post(value = "/api/{version}/colleges", responseStatus = CREATED)
	@InternalEndpoint
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
	
	@Get("/api/{version}/colleges/{collegeId}")
	@InternalEndpoint
	public CollegeMecDTO getById(@PathVariable("collegeId") Long collegeId) {
		Optional<College> optionalCollege = repository.findById(collegeId);
		College collegeFound = optionalCollege.orElseThrow(NoColegeFound::new);
		
		return collegeToDtoConverter.convert(collegeFound);
	}
	
	@Get("/api/{version}/colleges/count")
	@InternalEndpoint
	public Long count() {
		return repository.count();
	}
	
	@Post(value = "/api/{version}/colleges/{collegeId}/grades", responseStatus = CREATED)
	@InternalEndpoint
	public Long updateOrSaveGrade(@Valid @RequestBody CourseMecDTO dto) {
		Course courseToMerge = dtoToCourseConverter.convert(dto);

		Optional<College> optionalCollege = repository.findByCnpj(collegeToMerge.getCnpj());

		optionalCollege.ifPresent(college -> {
			if (equalsIgnoreCase(collegeToMerge.getCnpj(), college.getCnpj())) {
				collegeToMerge.setId(college.getId());
			}
		});

		repository.save(collegeToMerge);

		return collegeToMerge.getId();
		return null;
	}
}
