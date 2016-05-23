package com.hades.college;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.springframework.http.HttpStatus.CREATED;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.Get;
import com.hades.annotation.InternalEndpoint;
import com.hades.annotation.Post;
import com.hades.college.converter.CollegeMecDTOToCollegeConverter;
import com.hades.college.converter.CollegeToCollegeMecDTOConverter;
import com.hermes.college.CollegeMecDTO;

@RestController
public class CollegeController {

	private CollegeMecDTOToCollegeConverter dtoToCollegeConverter;
	private CollegeToCollegeMecDTOConverter collegeToDtoConverter;
	
	private CollegeRepository repository;
	
	@Autowired
	public CollegeController(CollegeMecDTOToCollegeConverter dtoToCollegeConverter,CollegeToCollegeMecDTOConverter collegeToDtoConverter, CollegeRepository repository) {
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
	
	@Get("/api/{version}/colleges")
	@InternalEndpoint
	public List<CollegeMecDTO> getAll() {
		List<CollegeMecDTO> collegeDtos = new ArrayList<>();
		
		repository.findAll().forEach(college -> {
			collegeDtos.add(collegeToDtoConverter.convert(college));
		});
		
		return collegeDtos;
	}
}
