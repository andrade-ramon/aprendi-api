package com.qualfacul.hades.post;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.WebService;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.exceptions.CollegePostNotFoundException;
import com.qualfacul.hades.login.LoggedUserManager;

@WebService
public class CollegePostFacade {
	
	@Autowired
	private LoggedUserManager loggedUserManager;
	@Autowired
	private CollegeRepository collegeRepository;
	@Autowired
	private CollegePostRepository postRepository;
	@Autowired
	private CollegePostSecurityAnalyzer collegePostSecurityAnalyzer;
	
	public CollegePost create(PostPublishDTO postPublishDTO){
		String cnpj = loggedUserManager.getLoginInfo().getLogin();
		College college = collegeRepository.findByCnpj(cnpj).get();
		CollegePost collegePost = new CollegePost(college, postPublishDTO.getMessage());
		postRepository.save(collegePost);
		return collegePost;
	}
	
	public CollegePost update(Long id, PostPublishDTO postPublishDTO){
		CollegePost collegePost = postRepository.findById(id).orElseThrow(CollegePostNotFoundException::new);

		collegePostSecurityAnalyzer.validate(collegePost);
		
		PostContent postContent = collegePost.getPostContent();
		postContent.setText(postPublishDTO.getMessage());
		collegePost.setUpdatedAt(Calendar.getInstance());
		postRepository.save(collegePost);
		
		return collegePost;
	}
	
	public boolean delete(Long id){
		CollegePost collegePost = postRepository.findById(id)
												.orElseThrow(CollegePostNotFoundException::new);
		collegePostSecurityAnalyzer.validate(collegePost);

		collegePost.markAsDeleted();
		postRepository.save(collegePost);
		return true;
	}
	
	
}
