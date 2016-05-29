package com.hades.college.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.hades.college.College;
import com.qualfacul.hermes.college.CollegeMecDTO;
import com.qualfacul.hermes.college.CollegeMecGradeDTO;

@Component
public class CollegeToCollegeMecDTOConverter implements Converter<College, CollegeMecDTO>{

	@Override
	public CollegeMecDTO convert(College college) {
		CollegeMecDTO dto = new CollegeMecDTO();
		dto.setId(college.getId());
		dto.setName(college.getName());
		dto.setAddress(college.getAddress());
		dto.setCnpj(college.getCnpj());
		dto.setInitials(college.getInitials());
		dto.setPhone(college.getPhone());
		dto.setSite(college.getSite());
		
		college.getGrades().forEach(grade -> {
			CollegeMecGradeDTO gradeDto = new CollegeMecGradeDTO();
			gradeDto.setValue(grade.getValue());
			gradeDto.setDate(grade.getDate());
			gradeDto.setGradeOrigin(grade.getGradeOrigin());
			dto.addCollegeMecGradeDTO(gradeDto);
		});
		
		return dto;
	}

}
