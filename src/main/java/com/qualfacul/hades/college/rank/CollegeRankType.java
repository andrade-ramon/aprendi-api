package com.qualfacul.hades.college.rank;

public enum CollegeRankType {
	GENERAL_RANK {
		@Override
		public CollegeRankCalculator getRankingCalcultor() {
			return new GeneralCollegeRankCalculator();
		}
	},
	INFRA_RANK {
		@Override
		public CollegeRankCalculator getRankingCalcultor() {
			return new InfraCollegeRankCalculator();
		}
	},
	PRICE_RANK {
		@Override
		public CollegeRankCalculator getRankingCalcultor() {
			return new PriceCollegeRankCalculator();
		}
	},
	TEACH_RANK {
		@Override
		public CollegeRankCalculator getRankingCalcultor() {
			return new TechCollegeRankCalculator();
		}
	};

	public abstract CollegeRankCalculator getRankingCalcultor();
}
