package com.qualfacul.hades.college;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class CollegeGradeToDTOConverter implements Converter<CollegeGrade, CollegeGradeDTO>{

	@Override
	public CollegeGradeDTO convert(CollegeGrade from) {
		CollegeGradeDTO to = new CollegeGradeDTO();
		to.setValue(from.getValue());
		return to;
	}

}
