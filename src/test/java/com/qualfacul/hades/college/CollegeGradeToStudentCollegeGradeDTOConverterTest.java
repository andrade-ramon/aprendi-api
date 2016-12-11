package com.qualfacul.hades.college;

import static com.qualfacul.hades.college.CollegeGradeOrigin.STUDENT_PRICE;
import static com.qualfacul.hades.login.LoginOrigin.USER;
import static com.qualfacul.hades.util.LocalDateUtils.from;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.qualfacul.hades.login.LoginInfo;
import com.qualfacul.hades.user.User;

public class CollegeGradeToStudentCollegeGradeDTOConverterTest {

	@Test
	public void shouldConvertWithAllFields() {
		CollegeGradeToStudentCollegeGradeDTOConverter converter = new CollegeGradeToStudentCollegeGradeDTOConverter();
		
		LoginInfo loginInfo = new LoginInfo("test@test.com", USER);
		User user = new User("Test", "test@test.com", loginInfo);
		user.setId(123l);
		College college = new College();
		college.setId(456l);
		CollegeGrade grade = new CollegeGrade().builder()
						.from(user)
						.withOrigin(STUDENT_PRICE)
						.withValue(Double.valueOf(2.3))
						.to(college)
						.build();
		
		StudentCollegeGradeDTO converted = converter.convert(grade);
		
		assertEquals(college.getId(), converted.getCollegeId());
		assertEquals(user.getId(), converted.getStudentId());
		assertEquals(user.getName(), converted.getStudentName());
		assertEquals(from(grade.getDate()), converted.getDate());
		assertEquals(grade.getGradeOrigin(), converted.getOrigin());
		assertEquals(grade.getValue(), converted.getValue());
	}

}
