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
import com.hermes.college.CollegeMecDTO;

@RunWith(MockitoJUnitRunner.class)
public class CollegeControllerTest {

	@Mock
	private CollegeMecDTOToCollegeConverter converter;
	
	@Mock
	private CollegeRepository repository;
	
	private CollegeController subject;

	private College validCollege;
	
	@Before
	public void setup() {
		subject = new CollegeController(converter, repository);
		
		validCollege = new College.Builder()
						.withId(1L)
						.build();
	}
	
	@Test
	public void shouldUpdateOrSaveDtoWhenCollegeDoesNotExists() {
		when(converter.convert(Mockito.any(CollegeMecDTO.class))).thenReturn(validCollege);
		when(repository.findByCnpj(validCollege.getCnpj())).thenReturn(Optional.of(validCollege));
		
		CollegeMecDTO dto = new CollegeMecDTO();
		
		assertEquals(validCollege.getId(), subject.updateOrSave(dto));
		
		verify(repository).save(validCollege);
	}

}
