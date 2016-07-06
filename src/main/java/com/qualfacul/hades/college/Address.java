package com.qualfacul.hades.college;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class Address {

	@NotBlank(message = "college.mec.invalid.address.value")
	@Column(name = "value")
	private String value;
	
	@Column(name = "number")
	private String number;
	
	@Column(name = "cep")
	private String cep;
	
	@Column(name = "neighborhood")
	private String neighborhood;
	
	@NotBlank(message = "college.mec.invalid.address.city")
	@Column(name = "city")
	private String city;
	
	@NotBlank(message = "college.mec.invalid.address.state")
	@Column(name = "state")
	private String state;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
