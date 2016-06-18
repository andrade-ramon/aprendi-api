package com.hades.college.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.hades.college.College;
import com.hades.college.CollegeGrade;
import com.hades.college.CollegeMec;
import com.qualfacul.hermes.college.CollegeMecDTO;

@Component
public class CollegeMecDTOToCollegeConverter implements Converter<CollegeMecDTO, College>{

	@Override
	public College convert(CollegeMecDTO collegeMecDTO) {
		CollegeMec collegeMec = new CollegeMec(collegeMecDTO.getMecId());
		
		College college = new College.Builder()
						.withName(collegeMecDTO.getName())
						.withInitials(collegeMecDTO.getInitials())
						.withAddress(collegeMecDTO.getAddress())
						.withPhone(collegeMecDTO.getPhone())
						.withCnpj(collegeMecDTO.getCnpj())
						.withSite(collegeMecDTO.getSite())
						.withCollegeMec(collegeMec)
						.build();
		
		List<CollegeGrade> grades = new ArrayList<>();
		
		collegeMecDTO.getCollegeMecGradeDTO().forEach(gradeDto -> {
			CollegeGrade collegeGrade = new CollegeGrade(college, gradeDto.getGradeOrigin(), gradeDto.getDate(), gradeDto.getValue());
			grades.add(collegeGrade);
		});
		
		college.setGrades(grades);
		
		return college;
	}

}
