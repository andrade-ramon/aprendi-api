package com.qualfacul.hades.mec;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.jsoup.nodes.Document;

import com.qualfacul.hades.annotation.TaskService;
import com.qualfacul.hades.college.Address;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeGrade;
import com.qualfacul.hades.college.GradeOrigin;

@TaskService
public class MecCollegeService {

	public void setupBasicInformations(College college, Document document) {
		Address address = new Address();
		document.select("td.avalTituloCampos + td").forEach((td) -> {
			String tdText = td.text();
			
			switch (td.previousElementSibling().text()) {
			case "Nome da IES - Sigla :":				
				college.setName(tdText.substring(tdText.indexOf(")") + 2, tdText.indexOf("-")));
				if (tdText.indexOf("-") != tdText.length() - 1) { // College with Initials
					college.setInitials(tdText.substring(tdText.indexOf('-') + 2, tdText.length()));
				}
				break;
			case "Endereço:":
				address.setValue(tdText);
				break;
			case "Nº:":
				address.setNumber(tdText);
				break;
			case "CEP:":
				address.setCep(tdText);
				break;
			case "Bairro:":
				address.setNeighborhood(tdText);
				break;
			case "UF:":
				address.setState(tdText);
				break;
			case "Município":
				address.setCity(tdText);
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
		college.setAddress(address);
	}

	public void setupGrades(College college, Document document) {
		List<CollegeGrade> grades = new ArrayList<>();
		document.select("table#listar-ies-cadastro > tbody tr").forEach((tr) -> {
			@SuppressWarnings("deprecation")
			CollegeGrade collegeGrade = new CollegeGrade();
			
			String elementValue = tr.select("td").get(1).text();
			Double value = null;
			if (!equalsIgnoreCase(elementValue, "-")) {
				value = elementValue.equals("-") ? 0 : Double.parseDouble(elementValue);
			}
			
			String elementYear = tr.select("td").get(2).text();
			Calendar calendar = null;
			if (!equalsIgnoreCase(elementYear, "-")) {
				calendar = new GregorianCalendar(Integer.parseInt(elementYear), 0, 0);
			}
			
			collegeGrade.setValue(value);
			collegeGrade.setDate(calendar);
			
			if (tr.select("td").first().text().contains("CI")) {
				collegeGrade.setGradeOrigin(GradeOrigin.MEC_CI);
			} else if (tr.select("td").first().text().contains("Índice Geral de Cursos")) {
				collegeGrade.setGradeOrigin(GradeOrigin.MEC_IGC);
			} else {
				collegeGrade.setGradeOrigin(GradeOrigin.MEC_CONTINUOUS_IGC);
			}
			grades.add(collegeGrade);
			
		});
		college.setGrades(grades);
	}

}
