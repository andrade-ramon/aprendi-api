package com.qualfacul.hades.post;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qualfacul.hades.serialization.FullDateCalendarSerializer;

public class CollegePostDTO {
	
	private Long id;
	
	@JsonSerialize(using = FullDateCalendarSerializer.class)
	private Calendar createdAt;
	
	@JsonSerialize(using = FullDateCalendarSerializer.class)
	@JsonInclude(Include.NON_NULL)
	private Calendar updatedAt;
	
	private String postContent;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Calendar getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}
	public Calendar getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
}
