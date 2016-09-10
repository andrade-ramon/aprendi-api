package com.qualfacul.hades.mec;

import static com.qualfacul.hades.crypt.Base64Utils.encode;
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

@TaskComponent
public class MecRequester {
	private final static Logger LOGGER = LoggerFactory.getLogger(MecRequester.class);

	private static final String MEC_ENDPOINT = "http://emec.mec.gov.br";
	private static final String ADVANCED_SEARCH_PAGE = "/emec/nova-index/listar-consulta-avancada/list/100";

	private static final String COLLEGE_HASH = "d96957f455f6405d14c6542552b0f6eb";
	private static final String COLLEGE_DETAILS_PAGE = "/emec/consulta-ies/index";

	private static final String COURSE_ADDRESS_PAGE = "/emec/consulta-curso/listar-endereco-curso";
	private static final String COURSE_HASH = "c1999930082674af6577f0c513f05a96";
	
	@Autowired
	private Client client;
	private WebTarget baseTarget;

	@PostConstruct
	public void init() {
		baseTarget = client.target(MEC_ENDPOINT);
	}
	
	public String requestCollegeList(Integer... page) {
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
		String url = COLLEGE_DETAILS_PAGE + "/" + COLLEGE_HASH + "/" + encode(mecId);

		String response = "";
		try {
			response = requestTo(url);
		} catch (Exception e) {
			LOGGER.error("Could not request college details for the college with mecId: {}", mecId, e);
		}

		return response;
	}
	
	public String requestCoursesList(Integer... page) {
		WebTarget target = null;
		if (page.length == 0) {
			target = baseTarget.path(ADVANCED_SEARCH_PAGE);
		} else {
			target = baseTarget.path(ADVANCED_SEARCH_PAGE + "/page/" + page[0]);
		}

		Builder request = target.request(TEXT_PLAIN);
		request.header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED);

		Form data = new Form();
		data.param("data[CONSULTA_AVANCADA][hid_template]", "listar-consulta-avancada-curso");
		data.param("data[CONSULTA_AVANCADA][rad_buscar_por]", "CURSO");
		data.param("data[CONSULTA_AVANCADA][hid_order]", "ies_curso.no_curso ASC");
		data.param("data[CONSULTA_AVANCADA][sel_co_situacao_funcionamento_ies]", "10035");
		data.param("data[CONSULTA_AVANCADA][sel_co_situacao_funcionamento_curso]", "10056");
		data.param("data[CONSULTA_AVANCADA][chk_tp_modalidade_gn]", "10056");

		Response response = request.post(Entity.entity(data, APPLICATION_FORM_URLENCODED_TYPE));

		return response.readEntity(String.class);
	}
	
	public String requestCourseAddress(String courseMecId){
		String url = COURSE_ADDRESS_PAGE + "/" + COURSE_HASH + "/" + encode(courseMecId) + "/list/1";
		String response = "";
		try {
			response = requestTo(url);
		} catch (Exception e) {
			LOGGER.error("Could not request address list for the course with mecId: {}", courseMecId, e);
		}
		
		return response;
	}
	
	private String requestTo(String url) throws Exception {
		WebTarget target = baseTarget.path(url);
		return target.request().get(String.class);
	}
}
