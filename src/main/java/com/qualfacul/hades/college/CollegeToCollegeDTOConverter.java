package com.qualfacul.hades.college;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.user.User;
import com.qualfacul.hades.user.address.UserCollegeAddressRepository;

@WebComponent
public class CollegeToCollegeDTOConverter implements Converter<College, CollegeDTO>{
	
	private UserCollegeAddressRepository userCollegeAddressRepository;
	private LoggedUserManager loggedUserManager;

	@Autowired
	public CollegeToCollegeDTOConverter(UserCollegeAddressRepository userCollegeAddressRepository,LoggedUserManager loggedUserManager) {
		this.userCollegeAddressRepository = userCollegeAddressRepository;
		this.loggedUserManager = loggedUserManager;
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
		
		Map<User, List<CollegeGrade>> gradesGroupByUser = from.getGrades().stream()
			.filter(grade -> grade.getGradeOrigin().isFromStudent())
			.collect(Collectors.groupingBy(CollegeGrade::getUser));
		
		dto.setCoursesCount(collegesCount);
		dto.setStudentsCount(studentsCount);
		dto.setRatingsCount(gradesGroupByUser.keySet().size());
		
		loggedUserManager.getStudent().ifPresent(user -> {
			dto.setAlreadyRated(gradesGroupByUser.containsKey(user));
		});
		
		return dto;
	}

}
