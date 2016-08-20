package com.qualfacul.hades.college;

public class CollegeDTO {
	private Long id;

	private long mecId;

	private String name;

	private String initials;

	private String phone;

	private String cnpj;

	private String site;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getMecId() {
		return mecId;
	}

	public void setMecId(long mecId) {
		this.mecId = mecId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

}
