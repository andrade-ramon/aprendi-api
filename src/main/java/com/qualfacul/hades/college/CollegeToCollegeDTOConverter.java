package com.qualfacul.hades.college;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class CollegeToCollegeDTOConverter implements Converter<College, CollegeDTO>{

	@Override
	public CollegeDTO convert(College from) {
		CollegeDTO collegeDTO = new CollegeDTO();
		collegeDTO.setId(from.getId());
		collegeDTO.setName(from.getName());
		collegeDTO.setInitials(from.getInitials());
		collegeDTO.setPhone(from.getPhone());
		collegeDTO.setCnpj(from.getCnpj());
		collegeDTO.setSite(from.getSite());
		
		Integer collegesCount = from.getCollegeAdresses().stream()
				.map(collegeAddress -> collegeAddress.getCourseGrades().size())
				.mapToInt(i -> i.intValue()).sum();
		
		
		collegeDTO.setCoursesCount(collegesCount);
		return collegeDTO;
	}

}
