package com.qualfacul.hades.course;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

import com.qualfacul.hades.college.CollegeAddress;

@RepositoryDefinition(domainClass = CourseGrade.class, idClass = Long.class)
public interface CourseGradeRepository {

	CourseGrade save(CourseGrade courseGrade);

	Optional<CourseGrade> findByCollegeAddressAndCourseAndGradeOrigin(CollegeAddress collegeAddress,
			Course course, CourseGradeOrigin gradeOrigin);
}
