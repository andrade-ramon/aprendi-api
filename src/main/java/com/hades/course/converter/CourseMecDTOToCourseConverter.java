package com.hades.course.converter;

import static com.hades.course.CourseDegree.BACHELOR;
import static com.hades.course.CourseDegree.GRADUATION;
import static com.hades.course.CourseDegree.SEQUENTIAL;
import static com.hades.course.CourseDegree.TECHNOLOGIST;
import static com.hades.course.CourseModality.DISTANCE;
import static com.hades.course.CourseModality.PRESENTIAL;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

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
		if (equalsIgnoreCase(from.getModality() , "Presencial")){
			to.setModality(PRESENTIAL);
		} else {
			to.setModality(DISTANCE);
		}
		
		if (equalsIgnoreCase(from.getType() , "Tecnológico")){
			to.setDegree(TECHNOLOGIST);
		} else if (equalsIgnoreCase(from.getType() , "Bacharelado")) {
			to.setDegree(BACHELOR);
		} else if (equalsIgnoreCase(from.getType() , "Licenciatura")) {
			to.setDegree(GRADUATION);
		} else {
			to.setDegree(SEQUENTIAL);
		}
		
		return to;
	}

}
