package com.qualfacul.hades.passwordrecovery;

public class PasswordChangeDTO {
	private String email;
	
	@Deprecated
	public PasswordChangeDTO(){
	}
	
	public PasswordChangeDTO(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
