package com.qualfacul.hades.college.rank;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.RepositoryDefinition;

import com.qualfacul.hades.college.College;

@RepositoryDefinition(domainClass = CollegeRank.class, idClass = Long.class)
public interface CollegeRankRepository {

	void save(CollegeRank collegeRanking);

	Optional<CollegeRank> findByCollegeAndRankType(College college, CollegeRankType rankType);

	Page<CollegeRank> findByRankTypeAndGradesQuantityGreaterThanOrderByGradeDesc(CollegeRankType rankType,
			int gradesQuantity, Pageable request);
}
