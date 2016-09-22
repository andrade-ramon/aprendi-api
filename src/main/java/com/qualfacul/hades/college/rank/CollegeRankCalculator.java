package com.qualfacul.hades.college.rank;

import java.util.List;

import com.qualfacul.hades.college.CollegeGrade;

public interface CollegeRankCalculator {

	RankCalculatorResult calculate(List<CollegeGrade> grades);
	
}
