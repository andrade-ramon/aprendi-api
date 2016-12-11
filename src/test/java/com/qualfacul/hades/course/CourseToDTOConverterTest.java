package com.qualfacul.hades.course;

import static com.qualfacul.hades.course.CourseDegree.BACHELOR;
import static com.qualfacul.hades.course.CourseModality.PRESENTIAL;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CourseToDTOConverterTest {

	@Test
	public void shouldConvertWithAllFields() {
		CourseToDTOConverter subject = new CourseToDTOConverter();
		
		Course course = new Course();
		course.setId(123l);
		course.setName("Some course");
		course.setModality(PRESENTIAL);
		course.setDegree(BACHELOR);
		
		CourseDTO converted = subject.convert(course);
		
		assertEquals(course.getId(), converted.getId());
		assertEquals(course.getDegree(), converted.getDegree());
		assertEquals(course.getModality(), converted.getModality());
		assertEquals(course.getName(), converted.getName());
	}

}
