package com.qualfacul.hades.course;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeAddress;

@RunWith(MockitoJUnitRunner.class)
public class CollegeAddressToCourseCollegesDTOConverterTest {

	@Mock
	private College college;

	@Test
	@SuppressWarnings("deprecation")
	public void shouldConvertWithAllFields() {
		Long collegeId = 123l;
		String collegeName = "some college";
		String initials = "SC";
		
		when(college.getId()).thenReturn(collegeId);
		when(college.getName()).thenReturn(collegeName);
		when(college.getInitials()).thenReturn(initials);
		
		CollegeAddressToCourseCollegesDTOConverter subject = new CollegeAddressToCourseCollegesDTOConverter();
		
		CollegeAddress collegeAddress = new CollegeAddress();
		collegeAddress.setCollege(college);
		collegeAddress.setAddress("someAddress");
		collegeAddress.setState("SP");
		collegeAddress.setCity("Some City");
		
		CourseCollegesDTO converted = subject.convert(collegeAddress);
		
		assertEquals(collegeId, converted.getId());
		assertEquals(collegeName, converted.getName());
		assertEquals(initials, converted.getInitials());
		assertEquals(collegeAddress.getAddress(), converted.getAddress());
		assertEquals(collegeAddress.getState(), converted.getState());
		assertEquals(collegeAddress.getCity(), converted.getCity());
		
	}

}
