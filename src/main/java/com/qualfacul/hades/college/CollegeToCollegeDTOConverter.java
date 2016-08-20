package com.qualfacul.hades.college;

import org.springframework.core.convert.converter.Converter;

public class CollegeToCollegeDTOConverter implements Converter<College, CollegeDTO>{

	@Override
	public CollegeDTO convert(College source) {
		CollegeDTO collegeDTO = new CollegeDTO();
		collegeDTO.setId(source.getId());
		collegeDTO.setMecId(source.getMecId());
		collegeDTO.setName(source.getName());
		collegeDTO.setInitials(source.getInitials());
		collegeDTO.setPhone(source.getPhone());
		collegeDTO.setCnpj(source.getCnpj());
		collegeDTO.setSite(source.getSite());
		return collegeDTO;
	}

}
