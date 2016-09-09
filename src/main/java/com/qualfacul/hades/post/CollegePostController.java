package com.qualfacul.hades.post;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Delete;
import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.Post;
import com.qualfacul.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.converter.ListConverter;
import com.qualfacul.hades.exceptions.CollegePostNotFoundException;

@RestController
public class CollegePostController {
	
	@Autowired
	private CollegePostFacade collegePostFacade;
	
	@Autowired
	CollegePostToCollegePostDTOConverter postDTOConverter;
	
	@Autowired
	CollegePostRepository postRepository;
	
	@Autowired
	CollegeRepository collegeRepository;
	
	@PublicEndpoint
	@Get("/posts/{id}")
	public CollegePostDTO getPost(@PathVariable Long id){
		return postRepository.findById(id)
								.map(post -> postDTOConverter.convert(post))
								.orElseThrow(CollegePostNotFoundException::new);
	}

	@PublicEndpoint
	@Get("/colleges/{id}/posts")
	public List<CollegePostDTO> getPostsByCollegeId(@PathVariable Long id){
		List<CollegePost> collegeList = postRepository.findAllByCollegeId(id);
		if (collegeList.isEmpty()){
			throw new CollegePostNotFoundException();
		}
		ListConverter<CollegePost, CollegePostDTO> listConverter = new ListConverter<>(postDTOConverter);
		return listConverter.convert(collegeList);
	}
	
	@Post("/posts")
	public CollegePostDTO publishPost(@RequestBody @Valid PostPublishDTO postPublishDTO){
		return postDTOConverter.convert(collegePostFacade.create(postPublishDTO));
	}
	
	@Post("/posts/{id}/update")
	public CollegePostDTO updatePost(@PathVariable Long id, @RequestBody @Valid PostPublishDTO postPublishDTO){
		return postDTOConverter.convert(collegePostFacade.update(id, postPublishDTO));
	}
	
	@Delete("/posts/{id}")
	public void deletePost(@PathVariable Long id){
		collegePostFacade.delete(id);
	}
}
