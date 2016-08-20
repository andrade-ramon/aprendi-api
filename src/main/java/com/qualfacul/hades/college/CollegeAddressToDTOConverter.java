package com.qualfacul.hades.college;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class CollegeAddressToDTOConverter implements Converter<CollegeAddress, CollegeAddressDTO>{

	@Override
	public CollegeAddressDTO convert(CollegeAddress source) {
		CollegeAddressDTO collegeAddressDTO = new CollegeAddressDTO();
		collegeAddressDTO.setId(source.getId());
		collegeAddressDTO.setAddress(source.getAddress());
		collegeAddressDTO.setCep(source.getCep());
		collegeAddressDTO.setNumber(source.getNumber());
		collegeAddressDTO.setNeighborhood(source.getNeighborhood());
		collegeAddressDTO.setCity(source.getCity());
		collegeAddressDTO.setState(source.getState());
		return collegeAddressDTO;
	}
	
}
