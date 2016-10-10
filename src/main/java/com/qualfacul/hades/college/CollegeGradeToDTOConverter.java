package com.qualfacul.hades.college;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class CollegeGradeToDTOConverter implements Converter<CollegeGrade, CollegeGradeDTO> {

	@Override
	public CollegeGradeDTO convert(CollegeGrade from) {
		CollegeGradeDTO dto = new CollegeGradeDTO();
		dto.setCollegeId(from.getCollege().getId());
		dto.setUserId(from.getUser().getId());
		dto.setOrigin(from.getGradeOrigin());
		dto.setValue(from.getValue());
		dto.setDate(from.getDate());
		
		return dto;
	}
}