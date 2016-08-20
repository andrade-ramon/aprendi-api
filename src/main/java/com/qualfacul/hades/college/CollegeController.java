package com.qualfacul.hades.college;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.PublicEndpoint;


@RestController
public class CollegeController {
	@Autowired
	private CollegeRepository collegeRepository;
	
	@Autowired
	private CollegeAddressRepository collegeAddressRepository;
	@Autowired
	private CollegeAddressToDTOConverter addressConverter;
	
	@PublicEndpoint
	@Get(value = "/colleges/{id}", responseStatus = CREATED)
	public CollegeDTO collegeSearch(@PathVariable Long id){
		Optional<College> optionalCollege = collegeRepository.findById(id);
		if (optionalCollege.isPresent()){
			College college = optionalCollege.get();
			CollegeToCollegeDTOConverter converter = new CollegeToCollegeDTOConverter();
			CollegeDTO collegeDTO = converter.convert(college);
			return collegeDTO;
		}
		return null;
	}
	
	@PublicEndpoint
	@Get(value = "/colleges/{collegeId}/addresses/{id}")
	public CollegeAddressDTO collegeAddressSearch(@PathVariable Long id){
		Optional<CollegeAddress> optionalCollegeAddress = collegeAddressRepository.findById(id);
		if (optionalCollegeAddress.isPresent()){
			CollegeAddress collegeAddress = optionalCollegeAddress.get();
			CollegeAddressToDTOConverter collegeAddressConverter = new CollegeAddressToDTOConverter();
			CollegeAddressDTO collegeAddressDTO = collegeAddressConverter.convert(collegeAddress);
			return collegeAddressDTO;
		}
		return null;
	}
	
	@PublicEndpoint
	@Get(value = "/colleges/{collegeId}/addresses")
	public List<CollegeAddressDTO> collegeAddressesSearch(@PathVariable Long collegeId){
		return collegeAddressRepository.findByCollegeId(collegeId)
				.stream()
				.map(collegeAddress -> {
					return addressConverter.convert(collegeAddress);
				})
				.collect(Collectors.toList());
	}
}
