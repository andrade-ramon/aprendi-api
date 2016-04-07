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
	@NotNull
	private String password;
	
	@Deprecated 
	public Admin(){
	}
	
	public Admin(String name, String email, String password){
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public Admin(Long id, String name, String email, String password){
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public Long getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public String getEmail(){
		return this.email;
	}
	public String getPassword(){
		return this.password;
	}
	public void setId(Long id){
		this.id = id;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public void setPassword(String password){
		this.password = password;
	}
}
