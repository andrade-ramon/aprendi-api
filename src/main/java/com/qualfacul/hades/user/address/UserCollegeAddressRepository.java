package com.qualfacul.hades.user.address;

import java.util.List;

import org.springframework.data.repository.RepositoryDefinition;

import com.qualfacul.hades.college.CollegeAddress;

@RepositoryDefinition(domainClass = UserCollegeAddress.class, idClass = UserCollegeAddressId.class)
public interface UserCollegeAddressRepository {

	List<UserCollegeAddress> findByIdCollegeAddress(CollegeAddress collegeAddress);
}
