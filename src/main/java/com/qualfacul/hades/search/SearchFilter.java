package com.qualfacul.hades.search;

import java.util.List;

public interface SearchFilter<T> {
	
	public List<T> apply(List<T> listToFilter);
}
