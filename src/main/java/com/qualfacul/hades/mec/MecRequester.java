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
	
	public static final String MEC_ENDPOINT = "http://emec.mec.gov.br";
	public static final String ADVANCED_SEARCH_PAGE = "/emec/nova-index/listar-consulta-avancada";
	public static final String COLLEGE_DETAILS_PAGE = "/emec/consulta-ies/index";
	public static final String COURSES_PAGE = "/emec/consulta-ies/listar-curso-agrupado";
	public static final String COURSE_DETAILS_PAGE = "/emec/consulta-curso/listar-curso-desagrupado";
	private static final String COLLEGE_MD5 = "d96957f455f6405d14c6542552b0f6eb";
	
	@Autowired
	private Client client;

	private WebTarget baseTarget;
	
	@PostConstruct
	public void init() {
		baseTarget = client.target(MEC_ENDPOINT);
	}

	public String requestCollegeBasic(Integer...page) {
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

	public String requestCollegeDetails(String mecIdParameter) {
		String detailsUrl = COLLEGE_DETAILS_PAGE + "/" + COLLEGE_MD5 + "/" + Base64Utils.encode(mecIdParameter);

		WebTarget target = baseTarget.path(detailsUrl);

		String response = "";
		try {
			response = target.request().get(String.class);
		} catch (Exception e) {
			LOGGER.error("Could not request on mec for the mecIdParameter: {}", mecIdParameter, e);
		}

		return response;
	}
}
