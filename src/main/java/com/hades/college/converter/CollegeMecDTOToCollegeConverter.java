package com.hades.college.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.hades.college.College;
import com.hades.college.CollegeGrade;
import com.hermes.college.CollegeMecDTO;

@Component
public class CollegeMecDTOToCollegeConverter implements Converter<CollegeMecDTO, College>{

	@Override
	public College convert(CollegeMecDTO dto) {
		
		College college = new College.Builder()
						.withName(dto.getName())
						.withInitials(dto.getInitials())
						.withAddress(dto.getAddress())
						.withPhone(dto.getPhone())
						.withCnpj(dto.getCnpj())
						.withSite(dto.getSite())
						.build();
		
		List<CollegeGrade> grades = new ArrayList<>();
		
		dto.getCollegeMecGradeDTO().forEach(gradeDto -> {
			CollegeGrade collegeGrade = new CollegeGrade(college, gradeDto.getGradeType(), gradeDto.getDate(), gradeDto.getValue());
			grades.add(collegeGrade);
		});
		
		college.setGrades(grades);
		
		return college;
	}

}
