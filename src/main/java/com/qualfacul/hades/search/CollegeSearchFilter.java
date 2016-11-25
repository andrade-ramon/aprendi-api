package com.qualfacul.hades.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.qualfacul.hades.college.CollegeDTO;

public class CollegeSearchFilter implements SearchFilter<CollegeDTO> {
	private String state;
	private Integer minMecGrade;

	public CollegeSearchFilter(String state, Integer minMecGrade) {
		this.state = state;
		this.minMecGrade = minMecGrade;
	}

	@Override
	public List<CollegeDTO> apply(List<CollegeDTO> listToFilter) {
		List<CollegeDTO> filteredList = new ArrayList<>();
		filteredList = listToFilter.stream().filter(item -> {
			boolean containsState = true;
			boolean contaisGrade = true;
			if (state != null) {
				System.out.println(">>>>>>>>>" + item.getStates());
				containsState = item.getStates().contains(state);
			}
			if (minMecGrade != null) {
				if (item.getMecGrade() == null) {
					contaisGrade = false;
	 			} else {
	 				contaisGrade = item.getMecGrade() >= minMecGrade;
	 			}
			}
			return containsState && contaisGrade;
		}).collect(Collectors.toList());

		return filteredList;
	}

}
