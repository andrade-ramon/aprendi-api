package com.qualfacul.hades.college;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CollegeToCollegeDTOConverterTest {
	
	private CollegeToCollegeDTOConverter subject;
	
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private CollegeAddress collegeAddress1;
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private CollegeAddress collegeAddress2;

	@Test
	public void shouldConvert() {
		subject = new CollegeToCollegeDTOConverter();
		
		College from = new College();
		from.setId(123L);
		from.setName("AnyCollege");
		from.setInitials("AC");
		from.setPhone("23928392");
		from.setCnpj("21-2302-202-2");
		from.setSite("www.college.com");
		
		from.setCollegeAdresses(asList(collegeAddress1, collegeAddress2));
		
		Integer coursesCount1 = 2;
		Integer coursesCount2 = 3;
		
		when(collegeAddress1.getCourseGrades().size()).thenReturn(coursesCount1);
		when(collegeAddress2.getCourseGrades().size()).thenReturn(coursesCount2);
		
		CollegeDTO converted = subject.convert(from);
		
		assertEquals(from.getId(), converted.getId());
		assertEquals(from.getName(), converted.getName());
		assertEquals(from.getInitials(), converted.getInitials());
		assertEquals(from.getPhone(), converted.getPhone());
		assertEquals(from.getCnpj(), converted.getCnpj());
		assertEquals(from.getSite(), converted.getSite());
		assertEquals(Integer.valueOf(coursesCount1 + coursesCount2), converted.getCoursesCount());
	}

}
