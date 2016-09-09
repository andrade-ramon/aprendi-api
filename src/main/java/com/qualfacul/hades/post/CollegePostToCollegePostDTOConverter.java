package com.qualfacul.hades.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.college.CollegeToCollegeDTOConverter;

@WebComponent
public class CollegePostToCollegePostDTOConverter implements Converter<CollegePost, CollegePostDTO>{

	@Autowired
	private CollegeToCollegeDTOConverter collegeConverter;
	
	@Override
	public CollegePostDTO convert(CollegePost source) {
		CollegePostDTO postDTO = new CollegePostDTO();
		postDTO.setId(source.getId());
		postDTO.setCollege(collegeConverter.convert(source.getCollege()));
		postDTO.setCreatedAt(source.getCreatedAt());
		postDTO.setUpdatedAt(source.getUpdatedAt());
		postDTO.setPostContent(source.getPostContent().getText());
		return postDTO;
	}

}
