package com.qualfacul.hades.college;

import static com.qualfacul.hades.search.SearchQuery.MAX_RESULTS_PER_PAGE;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.converter.ListConverter;
import com.qualfacul.hades.exceptions.CollegeNotFoundException;
import com.qualfacul.hades.search.PaginatedSearch;
import com.qualfacul.hades.search.SearchQuery;


@RestController
public class CollegeController {
	
	private static final float COLLEGE_THRESHOLD = 0.4f;
	
	@Autowired
	private SearchQuery<College> collegeSearch;
	@Autowired
	private CollegeRepository collegeRepository;	
	@Autowired
	private CollegeAddressRepository collegeAddressRepository;
	@Autowired
	private CollegeAddressToDTOConverter addressConverter;
	@Autowired
	private CollegeToCollegeDTOConverter collegeConverter;
	
	@PublicEndpoint
	@Get("/colleges/{id}")
	public CollegeDTO show(@PathVariable Long id){
		return collegeRepository.findById(id)
								.map(college -> collegeConverter.convert(college))
								.orElseThrow(CollegeNotFoundException::new);
	}
	
	@PublicEndpoint
	@Get("/colleges/{collegeId}/addresses/{addressId}")
	public CollegeAddressDTO showAddress(@PathVariable Long collegeId, @PathVariable Long addressId){
		return collegeAddressRepository.findByIdAndCollegeId(addressId, collegeId)
								.map(collegeAddress -> addressConverter.convert(collegeAddress))
								.orElseThrow(CollegeAddressNotFoundException::new);
	}
	
	@PublicEndpoint
	@Get("/colleges/{collegeId}/addresses")
	public List<CollegeAddressDTO> collegeAddressesSearch(@PathVariable Long collegeId){
		return collegeAddressRepository.findAllByCollegeId(collegeId).stream()
				.map(collegeAddress -> addressConverter.convert(collegeAddress))
				.collect(Collectors.toList());
	}
	
	@PublicEndpoint
	@Get("/search/{query}")
	public PaginatedSearch<CollegeDTO> list(@PathVariable String query, @RequestParam(required = false) Integer page) {
		
		List<College> colleges = collegeSearch
			.builder()
			.forEntity(College.class)
			.withThreshold(COLLEGE_THRESHOLD)
			.matching(query)
			.forPage(page)
			.build();
		
		ListConverter<College, CollegeDTO> listConverter = new ListConverter<>(collegeConverter);
		List<CollegeDTO> dtos = listConverter.convert(colleges);
		
		return new PaginatedSearch<CollegeDTO>(dtos, page, MAX_RESULTS_PER_PAGE);
	}
}
