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
	public void shouldConvertAValidCollegeDTOToCollege() {
		Address address = new Address();
		
		CollegeMecDTO collegeDTO = new CollegeMecDTO();
		collegeDTO.setName("Test");
		collegeDTO.setAddress(address);
		collegeDTO.setCnpj("CNPJ");
		collegeDTO.setInitials("T");
		collegeDTO.setPhone("Phone");
		collegeDTO.setSite("www.test.com.br");
		
		CollegeMecGradeDTO gradeDTO = new CollegeMecGradeDTO();
		gradeDTO.setGradeOrigin(MEC_IGC);
		gradeDTO.setDate(Calendar.getInstance());
		gradeDTO.setValue(2.0);
		collegeDTO.addCollegeMecGradeDTO(gradeDTO);
		
		College college = subject.convert(collegeDTO);
		
		assertEquals(collegeDTO.getName(), college.getName());
		assertEquals(collegeDTO.getAddress(), college.getAddress());
		assertEquals(collegeDTO.getCnpj(), college.getCnpj());
		assertEquals(collegeDTO.getInitials(), college.getInitials());
		assertEquals(collegeDTO.getPhone(), college.getPhone());
		assertEquals(collegeDTO.getSite(), college.getSite());
		assertEquals(collegeDTO.getCollegeMecGradeDTO().get(0).getValue(), college.getGrades().get(0).getValue());
		assertEquals(collegeDTO.getCollegeMecGradeDTO().get(0).getDate(), college.getGrades().get(0).getDate());
		assertEquals(collegeDTO.getCollegeMecGradeDTO().get(0).getGradeOrigin(), college.getGrades().get(0).getGradeOrigin());
	}

}
