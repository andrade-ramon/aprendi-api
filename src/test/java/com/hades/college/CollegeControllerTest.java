package com.hades.college;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.hades.college.converter.CollegeMecDTOToCollegeConverter;
import com.hades.college.converter.CollegeToCollegeMecDTOConverter;
import com.hades.course.CourseRepository;
import com.hades.course.converter.CourseMecDTOToCourseConverter;
import com.qualfacul.hermes.college.CollegeMecDTO;

@RunWith(MockitoJUnitRunner.class)
public class CollegeControllerTest {

	@Mock
	private CollegeMecDTOToCollegeConverter dtoToCollegeConverter;
	@Mock
	private CollegeToCollegeMecDTOConverter collegeToDTOConverter;
	@Mock
	private CourseMecDTOToCourseConverter dtoToCourseConverter;
	@Mock
	private CollegeRepository collegeRepository;
	@Mock
	private CourseRepository courseRepository;
	
	private CollegeApiController subject;

	private College validCollege;

	
	@Before
	public void setup() {
		subject = new CollegeApiController(dtoToCollegeConverter, collegeToDTOConverter, dtoToCourseConverter, collegeRepository, courseRepository);
		validCollege = new College.Builder()
						.withId(1L)
						.build();
	}
	
	@Test
	public void shouldUpdateOrSaveDtoWhenCollegeDoesNotExists() {
		when(dtoToCollegeConverter.convert(Mockito.any(CollegeMecDTO.class))).thenReturn(validCollege);
		when(collegeRepository.findByCnpj(validCollege.getCnpj())).thenReturn(Optional.of(validCollege));
		
		CollegeMecDTO dto = new CollegeMecDTO();
		
		assertEquals(validCollege.getId(), subject.updateOrSave(dto));
		
		verify(collegeRepository).save(validCollege);
	}

}
