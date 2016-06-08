package com.hades.college.search;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.PermitEndpoint;
import com.qualfacul.hades.college.College;

@RestController
public class CollegeSearchController {
	
	private CollegeSearchRepository searchRepository;
	
	@Autowired
	public CollegeSearchController(CollegeSearchRepository searchRepository) {
		this.searchRepository = searchRepository;
	}

	@PermitEndpoint
	@Get("/colleges")
	public List<College> list(@NotBlank @RequestParam("q") String query) {
		
		List<College> colleges = searchRepository.listByQyery(query);
		
		return colleges;
	}
}
