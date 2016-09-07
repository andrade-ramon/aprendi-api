package com.qualfacul.hades.mec;

import static com.qualfacul.hades.college.CollegeGradeOrigin.MEC_CI;
import static com.qualfacul.hades.college.CollegeGradeOrigin.MEC_CONTINUOUS_IGC;
import static com.qualfacul.hades.college.CollegeGradeOrigin.MEC_IGC;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.abbreviate;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.StringUtils;
import com.qualfacul.hades.annotation.TaskService;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeGrade;

@TaskService
public class MecCollegeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MecCollegeService.class);
	
	public College setupBasicCollegeInformations(Document document) {
		College college = new College();

		document.select("td.avalTituloCampos + td").forEach(td -> {
			String tdText = td.text();

			switch (td.previousElementSibling().text()) {
			case "Nome da IES - Sigla :":
				String name = tdText.substring(tdText.indexOf(")") + 2, tdText.length());
				String initials = null;

				if (tdText.indexOf("-") == tdText.lastIndexOf("-") && tdText.lastIndexOf("-") < tdText.length() - 1) {
					name = tdText.substring(tdText.indexOf(")") + 2, tdText.indexOf("-"));
					initials = tdText.substring(tdText.lastIndexOf('-') + 2, tdText.length());
				}
				
				if(StringUtils.isNullOrEmpty(name)){
					LOGGER.info("EMPTY COLLEGE NAME FOUND: {}", tdText);
				}

				college.setName(abbreviate(name, 255));
				college.setInitials(abbreviate(initials, 30));
				break;
			case "Telefone:":
				college.setPhone(tdText);
				break;
			case "CNPJ :":
				college.setCnpj(tdText);
				break;
			case "Sítio:":
				college.setSite(tdText);
				break;
			}
		});
		
		setupCollegeGrades(document, college);
		return college;
	}
	
	private void setupCollegeGrades(Document document, College college) {
		List<CollegeGrade> grades = new ArrayList<>();
		
		document.select("table#listar-ies-cadastro > tbody tr").forEach((tr) -> {
			@SuppressWarnings("deprecation")
			CollegeGrade collegeGrade = new CollegeGrade();

			String elementValue = tr.select("td").get(1).text();
			Double value = null;
			if (!equalsIgnoreCase(elementValue, "-")) {
				value = elementValue.equals("-") ? 0 : parseDouble(elementValue);
			}

			String elementYear = tr.select("td").get(2).text();
			Calendar calendar = null;
			if (!equalsIgnoreCase(elementYear, "-")) {
				calendar = new GregorianCalendar(parseInt(elementYear) + 1, 0, 0);
			}

			collegeGrade.setValue(value);
			collegeGrade.setDate(calendar);
			collegeGrade.setCollege(college);
			
			if (tr.select("td").first().text().contains("CI")) {
				collegeGrade.setGradeOrigin(MEC_CI);
			} else if (tr.select("td").first().text().contains("Índice Geral de Cursos")) {
				collegeGrade.setGradeOrigin(MEC_IGC);
			} else {
				collegeGrade.setGradeOrigin(MEC_CONTINUOUS_IGC);
			}
			
			grades.add(collegeGrade);
		});
		college.setGrades(grades);
	}
}