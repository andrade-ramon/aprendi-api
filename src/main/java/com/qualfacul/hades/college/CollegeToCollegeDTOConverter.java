package com.qualfacul.hades.college;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.user.address.UserCollegeAddressRepository;

@WebComponent
public class CollegeToCollegeDTOConverter implements Converter<College, CollegeDTO>{
	
	private UserCollegeAddressRepository userCollegeAddressRepository;

	@Autowired
	public CollegeToCollegeDTOConverter(UserCollegeAddressRepository userCollegeAddressRepository) {
		this.userCollegeAddressRepository = userCollegeAddressRepository;
	}

	@Override
	public CollegeDTO convert(College from) {
		CollegeDTO dto = new CollegeDTO();
		dto.setId(from.getId());
		dto.setName(from.getName());
		dto.setInitials(from.getInitials());
		dto.setPhone(from.getPhone());
		dto.setCnpj(from.getCnpj());
		dto.setSite(from.getSite());
		
		Integer collegesCount = from.getAddresses().stream()
				.map(collegeAddress -> collegeAddress.getCourses().size())
				.mapToInt(i -> i.intValue()).sum();
		
		Integer studentsCount = from.getAddresses().stream()
			.map(collegeAddress -> userCollegeAddressRepository.findByIdCollegeAddress(collegeAddress).size())
			.mapToInt(i -> i.intValue()).sum();
		
		dto.setCoursesCount(collegesCount);
		dto.setStudentsCount(studentsCount);
		
		return dto;
	}

}
