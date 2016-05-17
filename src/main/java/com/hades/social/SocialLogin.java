package com.hades.social;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.hades.user.User;

@Entity
@Table(name = "social_login")
public class SocialLogin {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private String socialId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SocialType socialType;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;
	
	@Transient
	private String token;

	@Deprecated
	public SocialLogin(){
	}
	
	public SocialLogin(String socialId, SocialType socialType, User user) {
		this.socialId = socialId;
		this.socialType = socialType;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	public SocialType getSocialType() {
		return socialType;
	}

	public void setSocialType(SocialType socialType) {
		this.socialType = socialType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
