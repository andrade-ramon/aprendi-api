package com.hades.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping(method = DELETE)
@Target(METHOD)
@Retention(RUNTIME)
@Documented
@ResponseStatus(OK)
public @interface Delete {

	@AliasFor(annotation = RequestMapping.class, attribute = "path")
	String[] value();
	
	@AliasFor(annotation = ResponseStatus.class, attribute = "value")
	HttpStatus responseStatus() default OK;
}
