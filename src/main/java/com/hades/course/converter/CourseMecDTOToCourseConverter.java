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

import com.hades.college.College;
import com.hades.course.Course;
import com.hades.course.CourseGrade;
import com.qualfacul.poseidon.course.CourseMecDTO;

@Component
public class CourseMecDTOToCourseConverter implements Converter<CourseMecDTO, Course> {
	@Override
	@SuppressWarnings("deprecation")
	public Course convert(CourseMecDTO courseMecDTO) {
		Course course = new Course();
		
		course.setName(courseMecDTO.getName());
		if (equalsIgnoreCase(courseMecDTO.getModality() , "Presencial")){
			course.setModality(PRESENTIAL);
		} else {
			course.setModality(DISTANCE);
		}
		
		if (equalsIgnoreCase(courseMecDTO.getType() , "Tecnológico")){
			course.setDegree(TECHNOLOGIST);
		} else if (equalsIgnoreCase(courseMecDTO.getType() , "Bacharelado")) {
			course.setDegree(BACHELOR);
		} else if (equalsIgnoreCase(courseMecDTO.getType() , "Licenciatura")) {
			course.setDegree(GRADUATION);
		} else {
			course.setDegree(SEQUENTIAL);
		}
		
		courseMecDTO.getCourseMecGradeDTOs().forEach(gradeDTO -> {
			CourseGrade grade = new CourseGrade();
			
			grade.setValue(gradeDTO.getValue());
			grade.setGradeOrigin(gradeDTO.getGradeOrigin());
			grade.setCourse(course);
			grade.setCollege(new College(gradeDTO.getCollegeId()));
			
			course.addGrade(grade);
		});
		
		return course;
	}

}
