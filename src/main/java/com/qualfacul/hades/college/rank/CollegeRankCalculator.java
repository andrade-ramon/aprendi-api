package com.qualfacul.hades.college.rank;

import java.util.List;

import com.qualfacul.hades.college.CollegeGrade;

interface CollegeRankCalculator {

	RankCalculatorResult calculate(List<CollegeGrade> grades);
	
}
