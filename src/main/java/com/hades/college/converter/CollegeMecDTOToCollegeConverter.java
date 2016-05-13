package com.hades.college.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.hades.college.College;
import com.hades.college.CollegeGrade;
import com.hermes.college.CollegeMecDTO;
import com.hermes.college.CollegeMecGradeDTO;

@Component
public class CollegeMecDTOToCollegeConverter implements Converter<CollegeMecDTO, College>{

	@Override
	public College convert(CollegeMecDTO dto) {
		College college = new College(
						dto.getName(),
						dto.getInitials(),
						dto.getAddress(),
						dto.getPhone(),
						dto.getCnpj(),
						dto.getSite()
						);
		
		List<CollegeGrade> grades = new ArrayList<>();
		List<CollegeMecGradeDTO> gradeDtos = dto.getCollegeMecGradeDTO();
		
		for (CollegeMecGradeDTO gradeDto : gradeDtos) {
			CollegeGrade collegeGrade = new CollegeGrade(college, gradeDto.getGradeType(), gradeDto.getDate(), gradeDto.getValue());
			grades.add(collegeGrade);
		}
		
		college.setGrades(grades);
		
		return college;
	}

}
