package com.qualfacul.hades.course;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Course.class, idClass = Long.class)
public interface CourseRepository {

	Optional<Course> findByNameAndDegreeAndModality(String name, CourseDegree degree, CourseModality modality);
	
	Course save(Course course);
	
	List<Course> findAll();

}