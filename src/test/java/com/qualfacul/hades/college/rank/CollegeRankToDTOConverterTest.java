package com.qualfacul.hades.college.rank;

import static com.qualfacul.hades.college.rank.CollegeRankType.GENERAL_RANK;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.qualfacul.hades.college.College;

public class CollegeRankToDTOConverterTest {

	@Test
	public void shouldConvertWithAllFields() {
		CollegeRankToDTOConverter converter = new CollegeRankToDTOConverter();
		College college = new College();
		college.setId(123l);
		college.setName("test");
		CollegeRank rank = new CollegeRank(college, 2.3, GENERAL_RANK, 3);
		
		CollegeRankDTO converted = converter.convert(rank);
		
		assertEquals(college.getId(), converted.getCollegeId());
		assertEquals(college.getName(), converted.getCollegeName());
		assertEquals(rank.getGrade(), converted.getGrade());
		assertEquals(rank.getCreatedAt(), converted.getLastUpdate());
		assertEquals(rank.getGradesQuantity(), converted.getTotalGrades());
	}

}
