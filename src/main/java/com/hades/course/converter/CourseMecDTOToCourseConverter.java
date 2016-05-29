package com.hades.course.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.hades.course.Course;
import com.qualfacul.poseidon.course.CourseMecDTO;

@Component
public class CourseMecDTOToCourseConverter implements Converter<CourseMecDTO, Course> {

	@Override
	@SuppressWarnings("deprecation")
	public Course convert(CourseMecDTO from) {
		Course to = new Course();
		
		to.setName(from.getName());
		to.setModality(from.getModality());
		to.setType(from.getType());
		
		return to;
	}

}
