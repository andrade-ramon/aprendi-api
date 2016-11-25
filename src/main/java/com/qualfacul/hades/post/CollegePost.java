package com.qualfacul.hades.post;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import com.qualfacul.hades.college.College;

@Entity
@Where(clause = "deleted = 0")
@Table(name = "college_post")
public class CollegePost {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@ManyToOne
	private College college;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Calendar createdAt = Calendar.getInstance();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Calendar updatedAt;
	
	@NotNull
	@OneToOne(mappedBy = "collegePost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private PostContent postContent;
	
	@Column(name = "deleted")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean deleted;
	
	@Deprecated
	public CollegePost(){
	}
	
	public CollegePost(College college, String message){
		this.college = college;
		this.createdAt = Calendar.getInstance();
		this.postContent = new PostContent(this, message);
		this.deleted = false;
	}
	
	public boolean isAuthor(College college){
		if (getCollege().getId() == college.getId()){
			return true;
		}
		return false;
	}
	
	public Calendar getCurrentTime(){
		return Calendar.getInstance();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setCreatedAt(){
		this.setCreatedAt(this.getCurrentTime());
	}

	public Calendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	public PostContent getPostContent() {
		return postContent;
	}

	public void setPostContent(PostContent postContent) {
		this.postContent = postContent;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void markAsDeleted() {
		this.deleted = true;
	}
}
