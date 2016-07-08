package com.qualfacul.hades.mec;

import static java.lang.Double.parseDouble;
import static org.springframework.util.StringUtils.trimTrailingWhitespace;

import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class MecCourseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MecCourseService.class);
	
//	@SuppressWarnings("deprecation")
//	public Optional<CourseMecDTO> setupCoursesInformations(Document document, CollegeMecDTO college) {
//		CourseMecDTO course = new CourseMecDTO();
//		
//		Elements tds = document.select("tbody tr td");
//		try {
//			course.setModality(trimTrailingWhitespace(tds.get(1).ownText()));
//			course.setType(trimTrailingWhitespace(tds.get(2).ownText()));
//			course.setName(trimTrailingWhitespace(tds.get(3).ownText()));
//			
//			
//			tds.select("span").stream()
//							  .filter(span -> !span.text().equals("-"))
//							  .collect(Collectors.toList())
//							  .forEach(span -> setupCourseFor(course, span, college.getId()));
//		} catch (IndexOutOfBoundsException e) {
//			LOGGER.error("Fail to retrieve course info for college with id {}", college.getId());
//			return Optional.empty();
//		}
//		return Optional.ofNullable(course);
//	}
//
//	private void setupCourseFor(CourseMecDTO course, Element span, Long collegeId) {
//		String title = span.attr("title");
//		
//		CourseMecGradeDTO gradeDTO = null;
//		
//		if (title.contains("CC")) {
//			gradeDTO = new CourseMecGradeDTO(CC, parseDouble(span.text()), collegeId);
//		} else if (title.contains("CPC")) {
//			gradeDTO = new CourseMecGradeDTO(CPC, parseDouble(span.text()), collegeId);
//		} else if (title.contains("ENADE")) {
//			gradeDTO = new CourseMecGradeDTO(ENADE, parseDouble(span.text()),collegeId);
//		}
//		course.addCourseMecGradeDTOs(gradeDTO);
//	}
}