package com.qualfacul.hades.post;

import com.qualfacul.hades.college.CollegeDTO;

public class CollegePostDTO {
	private Long id;
	private CollegeDTO college;
	private String createdAt;
	private String updatedAt;
	private String postContent;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public CollegeDTO getCollege() {
		return college;
	}
	public void setCollege(CollegeDTO college) {
		this.college = college;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
}
