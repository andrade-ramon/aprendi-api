package com.qualfacul.hades.course;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class CourseToDTOConverter implements Converter<Course, CourseDTO> {

	@Override
	public CourseDTO convert(Course from) {
		CourseDTO dto = new CourseDTO();
		dto.setId(from.getId());
		dto.setName(from.getName());
		dto.setModality(from.getModality());
		dto.setDegree(from.getDegree());
		
		from.getCourseGrades().forEach(grade -> {
			CourseGradeDTO gradeDTO = new CourseGradeDTO();
			gradeDTO.setValue(grade.getValue());
			gradeDTO.setOrigin(grade.getGradeOrigin());
		});
		return dto;
	}

}
