package com.qualfacul.hades.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = CollegePost.class, idClass = Long.class)
public interface CollegePostRepository {

	Optional<CollegePost> findById(Long id);

	List<CollegePost> findAllByCollegeId(Long collegeId);
	
	Optional<CollegePost> findByIdAndDeleted(Long id, boolean deleted);
	
	CollegePost save(CollegePost collegePost);
	
}
