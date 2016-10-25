package com.qualfacul.hades.college;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.util.LocalDateUtils;

@WebComponent
public class CollegeGradeToStudentCollegeGradeDTOConverter implements Converter<CollegeGrade, StudentCollegeGradeDTO> {

	@Override
	public StudentCollegeGradeDTO convert(CollegeGrade from) {
		StudentCollegeGradeDTO dto = new StudentCollegeGradeDTO();
		dto.setCollegeId(from.getCollege().getId());
		dto.setStudentName(from.getUser().getName());
		dto.setValue(from.getValue());
		dto.setOrigin(from.getGradeOrigin());
		dto.setDate(LocalDateUtils.from(from.getDate()));
		
		return dto;
	}
}