package com.qualfacul.hades.post;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.Parameter;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "post_content")
public class PostContent {
	@Id
	@GeneratedValue(generator = "SharedPrimaryKeyGenerator")
	@GenericGenerator(
		name="SharedPrimaryKeyGenerator",
		strategy="foreign",
		parameters = @Parameter(name="property", value="collegePost")
	)
	private Long id;
	
	@PrimaryKeyJoinColumn
	@OneToOne(cascade = CascadeType.ALL)
	private CollegePost collegePost;

	@NotNull
	private String text;

	@Deprecated
	public PostContent(){
	}
	
	public PostContent(CollegePost collegePost, String text) {
		this.collegePost = collegePost;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CollegePost getCollegePost() {
		return collegePost;
	}

	public void setCollegePost(CollegePost collegePost) {
		this.collegePost = collegePost;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
