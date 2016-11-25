package com.qualfacul.hades.college;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.RepositoryDefinition;

import com.qualfacul.hades.course.Course;

@RepositoryDefinition(domainClass = CollegeAddress.class, idClass = Long.class)
public interface CollegeAddressRepository {	
	
	Optional<CollegeAddress> findByAddressAndCep(String address, String cep);

	List<CollegeAddress> findAllByCollegeId(Long id);

	Optional<CollegeAddress> save(CollegeAddress collegeAddress);

	Optional<CollegeAddress> findByIdAndCollegeId(Long addressId, Long collegeId);
	
	Page<CollegeAddress> findByCoursesInOrderByCollegeName(List<Course> courses, Pageable pageable);
}
