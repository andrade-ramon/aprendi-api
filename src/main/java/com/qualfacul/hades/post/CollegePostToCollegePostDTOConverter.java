package com.qualfacul.hades.post;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class CollegePostToCollegePostDTOConverter implements Converter<CollegePost, CollegePostDTO>{

	@Override
	public CollegePostDTO convert(CollegePost source) {
		CollegePostDTO postDTO = new CollegePostDTO();
		postDTO.setId(source.getId());
		postDTO.setCreatedAt(source.getCreatedAt());
		postDTO.setUpdatedAt(source.getUpdatedAt());
		postDTO.setContent(source.getPostContent().getText());
		return postDTO;
	}

}
