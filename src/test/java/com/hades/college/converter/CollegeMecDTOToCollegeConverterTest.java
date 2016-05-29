package com.hades.college.converter;

import static com.qualfacul.hermes.college.CollegeGradeOrigin.MEC_IGC;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.hades.college.College;
import com.qualfacul.hermes.college.Address;
import com.qualfacul.hermes.college.CollegeMecDTO;
import com.qualfacul.hermes.college.CollegeMecGradeDTO;


public class CollegeMecDTOToCollegeConverterTest {
	
	private CollegeMecDTOToCollegeConverter subject;

	@Before
	public void setup() {
		subject = new CollegeMecDTOToCollegeConverter();
	}
	
	@Test
	public void shouldConvertAValidDTOToCollege() {
		Address address = new Address();
		
		CollegeMecDTO dto = new CollegeMecDTO();
		dto.setName("Test");
		dto.setAddress(address);
		dto.setCnpj("CNPJ");
		dto.setInitials("T");
		dto.setPhone("Phone");
		dto.setSite("www.test.com.br");
		
		CollegeMecGradeDTO gradeDTO = new CollegeMecGradeDTO();
		gradeDTO.setGradeOrigin(MEC_IGC);
		gradeDTO.setDate(Calendar.getInstance());
		gradeDTO.setValue(2.0);
		dto.addCollegeMecGradeDTO(gradeDTO);
		
		College converted = subject.convert(dto);
		
		assertEquals(dto.getName(), converted.getName());
		assertEquals(dto.getAddress(), converted.getAddress());
		assertEquals(dto.getCnpj(), converted.getCnpj());
		assertEquals(dto.getInitials(), converted.getInitials());
		assertEquals(dto.getPhone(), converted.getPhone());
		assertEquals(dto.getSite(), converted.getSite());
		assertEquals(dto.getCollegeMecGradeDTO().get(0).getValue(), converted.getGrades().get(0).getValue());
		assertEquals(dto.getCollegeMecGradeDTO().get(0).getDate(), converted.getGrades().get(0).getDate());
		assertEquals(dto.getCollegeMecGradeDTO().get(0).getGradeOrigin(), converted.getGrades().get(0).getGradeOrigin());
	}

}
