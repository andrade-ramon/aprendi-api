package com.qualfacul.hades.college.rank;

import java.util.List;

public class RankCalculatorResult {

	private Double negativeGrade;
	private Double positiveGrade;
	private Integer totalNumberOfGrades;
	private Double finalGrade;

	public Double getNegativeGrade() {
		return negativeGrade;
	}

	public Double getPositiveGrade() {
		return positiveGrade;
	}

	public Integer getTotalNumberOfGrades() {
		return totalNumberOfGrades;
	}

	public Double getFinalGrade() {
		return finalGrade;
	}

	public WithSize builder() {
		return new WithSize();
	}

	public class WithSize {
		public WithNegatives withSize(Integer total) {
			totalNumberOfGrades = total;
			return new WithNegatives();
		}
	}

	public class WithNegatives {
		public WithPositives withNegatives(List<Double> negatives) {

			Double sumOfNegativeGrades = negatives.stream().mapToDouble(grade -> 10 - grade).sum();

			Double negativePercent = negatives.isEmpty() ? 0 : ((double) negatives.size()) / totalNumberOfGrades;

			Double gradeWithoutPercent = negatives.isEmpty() ? 0 : (sumOfNegativeGrades / negatives.size()) * -1;

			negativeGrade = gradeWithoutPercent * negativePercent;
			return new WithPositives();
		}
	}

	public class WithPositives {
		public Builder withPositives(List<Double> positives) {
			Double sumOfPositiveGrades = positives.stream().mapToDouble(Double::doubleValue).sum();

			Double positivePercent = positives.isEmpty() ? 0 : ((double) positives.size()) / totalNumberOfGrades;

			Double gradeWithoutPercent = positives.isEmpty() ? 0 : sumOfPositiveGrades / positives.size();

			positiveGrade = gradeWithoutPercent * positivePercent;
			return new Builder();
		}
	}

	public class Builder {

		public RankCalculatorResult build() {
			RankCalculatorResult result = new RankCalculatorResult();
			result.negativeGrade = negativeGrade;
			result.positiveGrade = positiveGrade;
			result.totalNumberOfGrades = totalNumberOfGrades;
			result.finalGrade = negativeGrade + positiveGrade;

			return result;
		}
	}
}
