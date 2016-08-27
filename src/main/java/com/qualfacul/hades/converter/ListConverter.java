package com.qualfacul.hades.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;

public class ListConverter<F, T> implements Converter<List<? extends F>, List<T>>{
	
	private final Converter<F, T> converter;

	public ListConverter(Converter<F, T> converter) {
		this.converter = converter;
	}

	@Override
	public List<T> convert(List<? extends F> from) {
		if (from == null) {
			return null;
		}
		return from.stream()
					.map(converter::convert)
					.filter(converted -> converted != null)
					.collect(Collectors.toList());
	}

}
