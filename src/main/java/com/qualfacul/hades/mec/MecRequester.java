package com.qualfacul.hades.mec;

import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.TaskComponent;
import com.qualfacul.hades.crypt.Base64Utils;

@TaskComponent
public class MecRequester {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(MecRequester.class);
	
	private static final String MEC_ENDPOINT = "http://emec.mec.gov.br";
	private static final String ADVANCED_SEARCH_PAGE = "/emec/nova-index/listar-consulta-avancada";
	
	private static final String COLLEGE_MD5 = "d96957f455f6405d14c6542552b0f6eb";
	private static final String COLLEGE_DETAILS_PAGE = "/emec/consulta-ies/index";
	private static final String COLLEGE_ADDRESS_PAGE = "/emec/consulta-ies/listar-endereco";
	
	private static final String COURSES_PAGE = "/emec/consulta-ies/listar-curso-agrupado";
	private static final String COURSE_DETAILS_PAGE = "/emec/consulta-curso/listar-curso-desagrupado";
	
	
	@Autowired
	private Client client;

	private WebTarget baseTarget;
	
	@PostConstruct
	public void init() {
		baseTarget = client.target(MEC_ENDPOINT);
	}

	public String requestCollegeBasicInfo(Integer...page) {
		WebTarget target = null;
		if (page.length == 0) {
			target = baseTarget.path(ADVANCED_SEARCH_PAGE);
		} else {
			target = baseTarget.path(ADVANCED_SEARCH_PAGE + "/page/" + page[0]);
		}

		Builder request = target.request(TEXT_PLAIN);
		request.header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED);

		Form data = new Form();
		data.param("data[CONSULTA_AVANCADA][hid_template]", "listar-consulta-avancada-ies");
		data.param("data[CONSULTA_AVANCADA][rad_buscar_por]", "IES");
		data.param("data[CONSULTA_AVANCADA][hid_order]", "ies.no_ies ASC");

		Response response = request.post(Entity.entity(data, APPLICATION_FORM_URLENCODED_TYPE));
		
		return response.readEntity(String.class);
	}

	public String requestCollegeDetails(String mecId) {
		String detailsUrl = COLLEGE_DETAILS_PAGE + "/" + COLLEGE_MD5 + "/" + Base64Utils.encode(mecId);
		WebTarget target = baseTarget.path(detailsUrl);

		String response = "";
		try {
			response = target.request().get(String.class);
		} catch (Exception e) {
			LOGGER.error("Could not request college details for the college with mecId: {}", mecId, e);
		}

		return response;
	}
	
	public String requestCollegeAdresses(String mecId) {
		String addressUrl = COLLEGE_ADDRESS_PAGE + "/" + COLLEGE_MD5 + "/" + Base64Utils.encode(mecId) + "/list/10000";
		WebTarget target = baseTarget.path(addressUrl);
		String response = "";
		try {
			response = target.request().get(String.class);
		} catch (Exception e) {
			LOGGER.error("Could not request college campus for the college with mecId: {}", mecId, e);
		}
		return response;
	}
	
	public String requestCollegeCourses(Long mecIdParameter, Integer... page) {
		String coursesUrl = COURSES_PAGE + "/" + COLLEGE_MD5 + "/" + Base64Utils.encode(mecIdParameter.toString());

		if (page.length != 0) {
			coursesUrl = coursesUrl.concat("/page/" + page[0]);
		}

		WebTarget target = baseTarget.path(coursesUrl);

		String response = "";
		try {
			response = target.request().get(String.class);
		} catch (Exception e) {
			LOGGER.error("Could not request for grades on mec with mecIdParameter: {}", mecIdParameter, e);
		}

		return response;
	}

	public String requestCourse(String link) {
		String response = "";

		String[] split = link.split("/");
		try {
			String firstParameter = split[6] + "/" + split[7];
			String secondParameter = split[4] + "/" + split[5];

			String courseDetailsUrl = COURSE_DETAILS_PAGE + "/" + firstParameter + "/" + secondParameter;

			WebTarget target = baseTarget.path(courseDetailsUrl);

			response = target.request().get(String.class);
		} catch (Exception e) {
			LOGGER.error("Could not request details of course for the link {}", link);
		}

		return response;
	}
}
