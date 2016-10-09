package com.qualfacul.hades.college.rank;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

import com.qualfacul.hades.college.College;

@RepositoryDefinition(domainClass = CollegeRank.class, idClass = Long.class)
public interface CollegeRankRepository {
	
	void save(CollegeRank collegeRanking);
	
	Optional<CollegeRank> findByCollegeAndRankType(College college, CollegeRankType rankType);

	List<CollegeRank> findByRankTypeAndGradesQuantityGreaterThanOrderByGradeDesc(CollegeRankType rankType, int gradesQuantity);
}
