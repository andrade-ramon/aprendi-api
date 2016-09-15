package com.qualfacul.hades.college;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CollegeAddressToDTOConverterTest {
	
	public CollegeAddressToDTOConverter subject;
	
	@Test
	public void shouldConvert() {
		subject = new CollegeAddressToDTOConverter();
		
		@SuppressWarnings("deprecation")
		CollegeAddress from = new CollegeAddress();
		from.setId(123L);
		from.setName("AnyCampus");
		from.setAddress("AnyAddress");
		from.setCep("012345678");
		from.setNumber("123");
		from.setCity("São Paulo");
		from.setState("SP");
		from.setNeighborhood("anyNeighborhood");
		
		CollegeAddressDTO converted = subject.convert(from);
		
		assertEquals(from.getId(), converted.getId());
		assertEquals(from.getName(), converted.getName());
		assertEquals(from.getAddress(), converted.getAddress());
		assertEquals(from.getCep(), converted.getCep());
		assertEquals(from.getNumber(), converted.getNumber());
		assertEquals(from.getCity(), converted.getCity());
		assertEquals(from.getState(), converted.getState());
		assertEquals(from.getNeighborhood(), converted.getNeighborhood());
	}

}
