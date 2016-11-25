package com.qualfacul.hades.post;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.WebService;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.exceptions.CollegePostNotFoundException;
import com.qualfacul.hades.exceptions.CollegeWithoutLoginAccessException;
import com.qualfacul.hades.login.LoggedUserManager;

@WebService
public class CollegePostFacade {
	
	@Autowired
	private LoggedUserManager loggedUserManager;
	@Autowired
	private CollegePostRepository collegePostRepository;
	@Autowired
	private CollegePostSecurityAnalyzer collegePostSecurityAnalyzer;
	
	public CollegePost create(CollegePostPublishDTO postPublishDTO){
		College college = loggedUserManager.getCollege().orElseThrow(CollegeWithoutLoginAccessException::new);
		CollegePost collegePost = new CollegePost(college, postPublishDTO.getMessage());
		collegePostRepository.save(collegePost);
		return collegePost;
	}
	
	public CollegePost update(Long id, CollegePostPublishDTO postPublishDTO){
		CollegePost collegePost = collegePostRepository.findById(id).orElseThrow(CollegePostNotFoundException::new);

		collegePostSecurityAnalyzer.validate(collegePost);
		
		PostContent postContent = collegePost.getPostContent();
		postContent.setText(postPublishDTO.getMessage());
		collegePost.setUpdatedAt(Calendar.getInstance());
		collegePostRepository.save(collegePost);
		
		return collegePost;
	}
	
	public boolean delete(Long id){
		CollegePost collegePost = collegePostRepository.findById(id)
												.orElseThrow(CollegePostNotFoundException::new);
		collegePostSecurityAnalyzer.validate(collegePost);

		collegePost.markAsDeleted();
		collegePostRepository.save(collegePost);
		return true;
	}
	
	
}
