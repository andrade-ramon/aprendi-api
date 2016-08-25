package com.qualfacul.hades.college;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class CollegeAddressToDTOConverter implements Converter<CollegeAddress, CollegeAddressDTO>{

	@Override
	public CollegeAddressDTO convert(CollegeAddress from) {
		CollegeAddressDTO collegeAddressDTO = new CollegeAddressDTO();
		collegeAddressDTO.setId(from.getId());
		collegeAddressDTO.setName(from.getName());
		collegeAddressDTO.setAddress(from.getAddress());
		collegeAddressDTO.setCep(from.getCep());
		collegeAddressDTO.setNumber(from.getNumber());
		collegeAddressDTO.setNeighborhood(from.getNeighborhood());
		collegeAddressDTO.setCity(from.getCity());
		collegeAddressDTO.setState(from.getState());
		return collegeAddressDTO;
	}
	
}
