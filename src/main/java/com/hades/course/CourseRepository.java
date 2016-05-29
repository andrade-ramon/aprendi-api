package com.hades.course;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Course.class, idClass = Long.class)
public interface CourseRepository {

	Optional<Course> findByNameAndDegreeAndModality(String name, CourseDegree degree, CourseModality modality);

}
