package com.qualfacul.hades.configuration;

import java.io.IOException;

import org.apache.commons.compress.utils.Charsets;
import org.json.JSONObject;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StreamUtils;

public class JsonStringHttpMessageConverter extends StringHttpMessageConverter {

	@Override
	protected String readInternal(Class<? extends String> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		String jsonString = StreamUtils.copyToString(inputMessage.getBody(), Charsets.UTF_8);
		JSONObject jsonObject = new JSONObject(jsonString);
		String key = jsonObject.keys().next();
		return jsonObject.getString(key);
	}

}
