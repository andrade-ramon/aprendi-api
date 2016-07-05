package com.qualfacul.hades.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO {

	private List<FieldErrorDTO> errors = new ArrayList<>();

	public void addFieldError(String path, String message) {
		FieldErrorDTO error = new FieldErrorDTO(path, message);
		errors.add(error);
	}

	public List<FieldErrorDTO> getFieldErrors() {
		return errors;
	}

	public void setFieldErrors(List<FieldErrorDTO> errors) {
		this.errors = errors;
	}

}