package com.qualfacul.hades.college.rank;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class CollegeRankToDTOConverter implements Converter<CollegeRank, CollegeRankDTO>{

	@Override
	public CollegeRankDTO convert(CollegeRank from) {
		CollegeRankDTO to = new CollegeRankDTO();
		to.setCollegeId(from.getCollege().getId());
		to.setCollegeName(from.getCollege().getName());
		to.setGrade(from.getGrade());
		to.setLastUpdate(from.getCreatedAt());
		to.setTotalGrades(from.getGradesQuantity());
		return to;
	}

}
