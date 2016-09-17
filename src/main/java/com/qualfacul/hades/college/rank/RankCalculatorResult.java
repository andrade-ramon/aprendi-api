package com.qualfacul.hades.college.rank;

import java.util.List;

public class RankCalculatorResult {

	private Double negativeGrade;
	private Double positiveGrade;
	private Integer totalGrades;
	private Double finalGrade;
	
	public Integer getTotalGrades() {
		return totalGrades;
	}
	
	public Double getFinalGrade() {
		return finalGrade;
	}
	
	public WithSize builder() {
		return new WithSize();
	}
	
	public class WithSize {

		public WithNegatives withSize(Integer total) {
			totalGrades = total;
			return new WithNegatives();
		}
	}

	public class WithNegatives {
		public WithPositives withNegatives(List<Double> negatives) {
			
			Double sumOfNegativeGrades = negatives.stream().mapToDouble(grade -> 10 - grade).sum();
			
			Integer negativePercent = negatives.isEmpty() ? 0 : negatives.size() / totalGrades; 

			Double gradeWithoutPercent = negatives.isEmpty() ? 0 : (sumOfNegativeGrades / negatives.size()) * -1;

			negativeGrade = gradeWithoutPercent * negativePercent;
			return new WithPositives();
		}
	}

	public class WithPositives {
		public Builder withPositives(List<Double> positives) {
			Double sumOfPositiveGrades = positives.stream().mapToDouble(Double::doubleValue).sum();
			
			Integer positivePercent = positives.isEmpty() ? 0 : positives.size() / totalGrades; 

			Double gradeWithoutPercent = positives.isEmpty() ? 0 : sumOfPositiveGrades / positives.size();

			positiveGrade = gradeWithoutPercent * positivePercent;
			return new Builder();
		}
	}
	
	public class Builder {

		public RankCalculatorResult build () {
			RankCalculatorResult result = new RankCalculatorResult();
			result.negativeGrade = negativeGrade;
			result.positiveGrade = positiveGrade;
			result.totalGrades = totalGrades;
			result.finalGrade = negativeGrade + positiveGrade;
			
			return result;
		}
	}
}
