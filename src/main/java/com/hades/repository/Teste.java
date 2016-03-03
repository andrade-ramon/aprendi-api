package com.hades.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="teste")
public class Teste {
	
	@Id
	@GeneratedValue
	private Long id;

	private String firstName;

	public Teste(String firstName) {
		this.firstName = firstName;
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
