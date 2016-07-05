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

import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.TaskComponent;
import com.qualfacul.hades.crypt.Base64Utils;

@TaskComponent
public class MecRequester {

	public static final String MEC_ENDPOINT = "http://emec.mec.gov.br";
	public static final String BASIC_PAGE = "/emec/nova-index/listar-consulta-avancada";
	public static final String DETAILS_PAGE = "/emec/consulta-ies/index";
	private static final String COLLEGE_MD5 = "d96957f455f6405d14c6542552b0f6eb";
	
	@Autowired
	private Client client;

	private WebTarget baseTarget;
	
	@PostConstruct
	public void init() {
		baseTarget = client.target(MEC_ENDPOINT);
	}

	public String requestBase(Integer...page) {
		WebTarget target = null;
		if (page.length == 0) {
			target = baseTarget.path(BASIC_PAGE);
		} else {
			target = baseTarget.path(BASIC_PAGE + "/page/" + page[0]);
		}

		Builder request = target.request(TEXT_PLAIN);
		request.header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED);

		Form data = new Form();
		data.param("data[CONSULTA_AVANCADA][hid_template]", "listar-consulta-avancada-ies");
		data.param("data[CONSULTA_AVANCADA][rad_buscar_por]", "IES");

		Response response = request.post(Entity.entity(data, APPLICATION_FORM_URLENCODED_TYPE));
		
		return response.readEntity(String.class);
	}

	public String requestDetails(String mecIdParameter) {
		String detailsUrl = DETAILS_PAGE + "/" +
						COLLEGE_MD5 + "/" +
						Base64Utils.encode(mecIdParameter);
		
		WebTarget target = baseTarget.path(detailsUrl);
		
		return target.request().get(String.class);
	}
}
