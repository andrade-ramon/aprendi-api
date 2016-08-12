package com.qualfacul.hades.course;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = CourseGrade.class, idClass = Long.class)
public interface CourseGradeRepository {
	
	CourseGrade save(CourseGrade courseGrade);
}
