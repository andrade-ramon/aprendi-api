package com.qualfacul.hades.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;

@Configuration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebConfiguration {

}
