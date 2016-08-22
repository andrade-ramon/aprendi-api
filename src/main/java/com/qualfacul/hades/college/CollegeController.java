package com.qualfacul.hades.college;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.exceptions.CollegeNotFoundException;


@RestController
public class CollegeController {

	@Autowired
	private CollegeRepository collegeRepository;	
	@Autowired
	private CollegeAddressRepository collegeAddressRepository;
	@Autowired
	private CollegeAddressToDTOConverter addressConverter;
	@Autowired
	private CollegeToCollegeDTOConverter collegeConverter;
	
	@PublicEndpoint
	@Get(value = "/colleges/{id}", responseStatus = CREATED)
	public CollegeDTO collegeSearch(@PathVariable Long id){
		return collegeRepository.findById(id)
								.map(college -> collegeConverter.convert(college))
								.orElseThrow(CollegeNotFoundException::new);
	}
	
	@PublicEndpoint
	@Get(value = "/colleges/{collegeId}/addresses/{addressId}")
	public CollegeAddressDTO collegeAddressSearch(@PathVariable Long addressId){
		return collegeAddressRepository.findById(addressId)
								.map(collegeAddress -> addressConverter.convert(collegeAddress))
								.orElseThrow(CollegeAddressNotFoundException::new);
	}
	
	@PublicEndpoint
	@Get(value = "/colleges/{collegeId}/addresses")
	public List<CollegeAddressDTO> collegeAddressesSearch(@PathVariable Long collegeId){
		return collegeAddressRepository.findAllByCollegeId(collegeId).stream()
				.map(collegeAddress -> addressConverter.convert(collegeAddress))
				.collect(Collectors.toList());
	}
}
