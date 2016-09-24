package com.qualfacul.hades.college.rank;

import static com.qualfacul.hades.college.CollegeGradeCalculator.MAX_NEGATIVE_GRADE;
import static java.lang.Double.valueOf;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class RankCalculatorResultTest {
	
	@Test
	public void shouldReturnMaxGradeWhenThereIsOnlyMaxGrades() {
		List<Double> negatives = emptyList();
		List<Double> positives = Arrays.asList(10d, 10d, 10d, 10d, 10d);
		
		RankCalculatorResult result = new RankCalculatorResult()
				.builder()
				.withSize(positives.size() + negatives.size())
				.withNegatives(negatives)
				.withPositives(positives)
				.build();
		
		assertEquals(result.getFinalGrade(), valueOf(10));
	}
	
	@Test
	public void shouldReturnPositiveGradeWhenThereIsOnlyPositiveGrades() {
		List<Double> negatives = emptyList();
		List<Double> positives = Arrays.asList(6d, 7d, 7d, 6d, 5d);
		
		RankCalculatorResult result = new RankCalculatorResult()
				.builder()
				.withSize(positives.size() + negatives.size())
				.withNegatives(negatives)
				.withPositives(positives)
				.build();
		
		boolean isAPositiveGrade = result.getFinalGrade() > MAX_NEGATIVE_GRADE; 
		assertTrue(isAPositiveGrade);
	}
	
	@Test
	public void shouldCalculatePositiveAndNegativeGrades() {
		List<Double> negatives = Arrays.asList(1d, 2d, 3d);
		List<Double> positives = Arrays.asList(6d, 7d, 7d, 6d, 5d);
		
		RankCalculatorResult calculatorResult = new RankCalculatorResult();
		calculatorResult.builder()
				.withSize(positives.size() + negatives.size())
				.withNegatives(negatives)
				.withPositives(positives);
		
		assertEquals(calculatorResult.getNegativeGrade(), valueOf(-3d));
		assertEquals(calculatorResult.getPositiveGrade(), valueOf(3.875));
	}
	
}