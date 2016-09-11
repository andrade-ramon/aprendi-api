package com.qualfacul.hades.mec;

import static java.lang.Double.parseDouble;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.TaskService;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeAddress;
import com.qualfacul.hades.college.CollegeAddressRepository;
import com.qualfacul.hades.course.Course;
import com.qualfacul.hades.course.CourseDegree;
import com.qualfacul.hades.course.CourseGrade;
//import com.qualfacul.hades.course.CourseGrade;
import com.qualfacul.hades.course.CourseGradeOrigin;
import com.qualfacul.hades.course.CourseGradeRepository;
//import com.qualfacul.hades.course.CourseGradeRepository;
import com.qualfacul.hades.course.CourseModality;

@TaskService
public class MecCourseService {

	private static final List<String> INVALID_COURSES_NAME = Arrays.asList("Suspensão de Ingresso:",
			"Vedação de Aumento de Vagas", "Em Desativação/Extinção voluntária", "Medida Cautelar: Suspensão de",
			": Curso autorizado por ", "CPC 2014 não divulgado");

	@Autowired
	private CollegeAddressRepository collegeAddressRepository;
	@Autowired
	private CourseGradeRepository courseGradeRepository;
	
	@SuppressWarnings("deprecation")
	public Course setupCourseInfo(Elements tds) {
		Course course = new Course();
		String courseName = tds.get(1).text().trim();
		courseName = (courseName.substring(courseName.indexOf(")") + 1, courseName.length())).trim();
		course.setName(courseName);
		INVALID_COURSES_NAME.forEach(invalidWord -> {
			int index = course.getName().indexOf(invalidWord);
			if (index > 0) {
				course.setName(course.getName().subSequence(0, index).toString().trim());
			}
		});

		String courseDegree = tds.get(2).text().trim();
		if (equalsIgnoreCase(courseDegree, "licenciatura")) {
			course.setDegree(CourseDegree.GRADUATION);
		} else if (equalsIgnoreCase(courseDegree, "sequencial")) {
			course.setDegree(CourseDegree.SEQUENTIAL);
		} else if (equalsIgnoreCase(courseDegree, "tecnologico")) {
			course.setDegree(CourseDegree.TECHNOLOGIST);
		} else {
			course.setDegree(CourseDegree.BACHELOR);
		}

		String courseModality = tds.get(3).text().trim();
		if (equalsIgnoreCase(courseModality, "presencial")) {
			course.setModality(CourseModality.PRESENTIAL);
		} else {
			course.setModality(CourseModality.DISTANCE);
		}

		return course;
	}

	public List<CourseGrade> setupCourseGrades(Elements tds, Course course, CollegeAddress collegeAddress) {
		List<CourseGrade> courseGrades = new ArrayList<>();
		
		
		String cc = tds.get(4).text().trim();
		Double valueCC = null;
		if (!equalsIgnoreCase(cc, "-") && !equalsIgnoreCase(cc, "SC")) {
			valueCC = parseDouble(cc);
		}
		CourseGrade courseGradeCC = new CourseGrade(CourseGradeOrigin.CC, valueCC, course, collegeAddress);
		if(!courseGradeRepository.findByCollegeAddressAndCourseAndGradeOrigin(collegeAddress, course, CourseGradeOrigin.CC).isPresent()) {
			courseGrades.add(courseGradeCC);
		}
		
		
		String cpc = tds.get(5).text().trim();
		Double valueCPC = null;
		if (!equalsIgnoreCase(cpc, "-") && !equalsIgnoreCase(cpc, "SC")) {
			valueCPC = parseDouble(cpc);
		}
		CourseGrade courseGradeCPC = new CourseGrade(CourseGradeOrigin.CPC, valueCPC, course, collegeAddress);
		if(!courseGradeRepository.findByCollegeAddressAndCourseAndGradeOrigin(collegeAddress, course, CourseGradeOrigin.CPC).isPresent()) {
			courseGrades.add(courseGradeCPC);
		}
	
		
		
		String enade = tds.get(6).text().trim();
		Double valueENADE = null;
		if (!equalsIgnoreCase(enade, "-") && !equalsIgnoreCase(enade, "SC")) {
			valueENADE = parseDouble(enade);
		}
		CourseGrade courseGradeENADE = new CourseGrade(CourseGradeOrigin.ENADE, valueENADE, course, collegeAddress);
		if(!courseGradeRepository.findByCollegeAddressAndCourseAndGradeOrigin(collegeAddress, course, CourseGradeOrigin.ENADE).isPresent()) {
			courseGrades.add(courseGradeENADE);
		}
		
		return courseGrades;
	}
	
	public CollegeAddress setupCourseAddress(Element trAddress, College college, Course course) {
		Elements tdsCourse = trAddress.select("td");
		@SuppressWarnings("deprecation")
		CollegeAddress collegeAddress = new CollegeAddress();
		collegeAddress.setAddress(tdsCourse.get(0).text().trim());
		collegeAddress.setCep(tdsCourse.get(1).text().trim());
		collegeAddress.setCity(tdsCourse.get(2).text().trim());
		collegeAddress.setState(tdsCourse.get(3).text().trim());
		collegeAddress.setCollege(college);
		
		Optional<CollegeAddress> optionalAddress = collegeAddressRepository
				.findByAddressAndCep(collegeAddress.getAddress(), collegeAddress.getCep());
		
		if(optionalAddress.isPresent()) {
			collegeAddress = optionalAddress.get();
		}
			
		
		List<Course> courses = new ArrayList<>();
		courses.add(course);
		if (collegeAddress.getCourses() == null) {
			collegeAddress.setCourses(courses);
		} else if (!collegeAddress.getCourses().contains(course)) {
			collegeAddress.getCourses().add(course);
		}
		collegeAddressRepository.save(collegeAddress);
		
		return collegeAddress;
	}

}