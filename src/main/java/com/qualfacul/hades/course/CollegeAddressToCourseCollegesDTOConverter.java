package com.qualfacul.hades.course;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.college.CollegeAddress;

@WebComponent
public class CollegeAddressToCourseCollegesDTOConverter implements Converter<CollegeAddress, CourseCollegesDTO>{

	@Override
	public CourseCollegesDTO convert(CollegeAddress collegeAddress) {
		CourseCollegesDTO dto = new CourseCollegesDTO();
		dto.setId(collegeAddress.getCollege().getId());
		dto.setName(collegeAddress.getCollege().getName());
		dto.setInitials(collegeAddress.getCollege().getInitials());
		dto.setAddress(collegeAddress.getAddress());
		dto.setState(collegeAddress.getState());
		dto.setCity(collegeAddress.getCity());
		
		return dto;
	}

}
