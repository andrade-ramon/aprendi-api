package com.qualfacul.hades.college.rank;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class CollegeRankToDTOConverter implements Converter<CollegeRank, CollegeRankDTO>{

	@Override
	public CollegeRankDTO convert(CollegeRank source) {
		CollegeRankDTO collegeRankDTO = new CollegeRankDTO();
		collegeRankDTO.setCollegeId(source.getCollege().getId());
		collegeRankDTO.setCollegeName(source.getCollege().getName());
		collegeRankDTO.setGrade(source.getGrade());
		collegeRankDTO.setLastUpdate(source.getCreatedAt());
		collegeRankDTO.setTotalGrades(source.getGradesQuantity());
		return collegeRankDTO;
	}

}
