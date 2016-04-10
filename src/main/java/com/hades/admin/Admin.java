package com.hades.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

@Entity(name = "administrator")
public class Admin {
	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	private String name;
	@NotNull
	@Column(unique=true)
	@Email
	private String email;
	
	@Deprecated 
	public Admin(){
	}
	
	public Admin(String name, String email){
		this.name = name;
		this.email = email;
	}
	
	public Admin(Long id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
