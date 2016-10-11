package com.qualfacul.hades.college;

import static com.qualfacul.hades.college.CollegeGradeOrigin.MEC_CI;
import static com.qualfacul.hades.college.CollegeGradeOrigin.STUDENT_INFRA;
import static com.qualfacul.hades.college.CollegeGradeOrigin.STUDENT_PRICE;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.user.User;
import com.qualfacul.hades.user.address.UserCollegeAddress;
import com.qualfacul.hades.user.address.UserCollegeAddressId;

@RunWith(MockitoJUnitRunner.class)
public class CollegeToCollegeDTOConverterTest {
	
	private CollegeToCollegeDTOConverter subject;
	
	@Mock(answer = RETURNS_DEEP_STUBS)
	private CollegeAddress collegeAddress1;
	@Mock(answer = RETURNS_DEEP_STUBS)
	private CollegeAddress collegeAddress2;
	@Mock(answer = RETURNS_DEEP_STUBS)
	private CollegeGrade grade1;
	@Mock(answer = RETURNS_DEEP_STUBS)
	private CollegeGrade grade2;
	@Mock(answer = RETURNS_DEEP_STUBS)
	private CollegeGrade grade3;
	@Mock(answer = RETURNS_DEEP_STUBS)
	private CollegeGrade grade4;
	@Mock(answer = RETURNS_DEEP_STUBS)
	private User someUser;
	@Mock(answer = RETURNS_DEEP_STUBS)
	private User otherUser;
	@Mock
	private LoggedUserManager loggedUserManager;

	@Test
	public void shouldConvert() {
		subject = new CollegeToCollegeDTOConverter(loggedUserManager);
		
		College from = new College();
		from.setId(123L);
		from.setName("AnyCollege");
		from.setInitials("AC");
		from.setPhone("23928392");
		from.setCnpj("21-2302-202-2");
		from.setSite("www.college.com");
		
		UserCollegeAddressId anyId = new UserCollegeAddressId(collegeAddress1, someUser);
		
		UserCollegeAddress someUserCollegeAddress = new UserCollegeAddress(anyId, "");
		
		from.setAdresses(asList(collegeAddress1, collegeAddress2));
		from.setGrades(asList(grade1, grade2, grade3, grade4));
		
		Integer coursesCount1 = 2;
		Integer coursesCount2 = 3;
		Integer studentsCount = 3;
		Integer studentsRatingsCount = 2;
		
		when(collegeAddress1.getCourses().size()).thenReturn(coursesCount1);
		when(collegeAddress2.getCourses().size()).thenReturn(coursesCount2);
		when(collegeAddress1.getUserCollegeAddress()).thenReturn(asList(someUserCollegeAddress, someUserCollegeAddress));
		when(collegeAddress2.getUserCollegeAddress()).thenReturn(asList(someUserCollegeAddress));
		when(grade1.getGradeOrigin()).thenReturn(STUDENT_INFRA);
		when(grade1.getUser()).thenReturn(someUser);
		when(grade2.getGradeOrigin()).thenReturn(STUDENT_PRICE);
		when(grade2.getUser()).thenReturn(someUser);
		when(grade3.getGradeOrigin()).thenReturn(STUDENT_INFRA);
		when(grade3.getUser()).thenReturn(otherUser);
		when(grade4.getGradeOrigin()).thenReturn(MEC_CI);
		when(loggedUserManager.getStudent()).thenReturn(Optional.of(someUser));
		
		CollegeDTO converted = subject.convert(from);
		
		assertEquals(from.getId(), converted.getId());
		assertEquals(from.getName(), converted.getName());
		assertEquals(from.getInitials(), converted.getInitials());
		assertEquals(from.getPhone(), converted.getPhone());
		assertEquals(from.getCnpj(), converted.getCnpj());
		assertEquals(from.getSite(), converted.getSite());
		assertEquals(Integer.valueOf(coursesCount1 + coursesCount2), converted.getCoursesCount());
		assertEquals(Integer.valueOf(studentsCount), converted.getStudentsCount());
		assertEquals(studentsRatingsCount, converted.getRatingsCount());
		assertTrue(converted.isAlreadyRated());
	}

}
