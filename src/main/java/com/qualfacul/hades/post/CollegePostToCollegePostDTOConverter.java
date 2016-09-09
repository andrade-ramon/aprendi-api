package com.qualfacul.hades.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.college.CollegeToCollegeDTOConverter;
import com.qualfacul.hades.util.HadesDateFormat;

@WebComponent
public class CollegePostToCollegePostDTOConverter implements Converter<CollegePost, CollegePostDTO>{

	@Autowired
	private CollegeToCollegeDTOConverter collegeConverter;
	
	@Autowired
	private HadesDateFormat hadesDateFormat;
	
	@Override
	public CollegePostDTO convert(CollegePost source) {
		CollegePostDTO postDTO = new CollegePostDTO();
		postDTO.setId(source.getId());
		postDTO.setCollege(collegeConverter.convert(source.getCollege()));
		postDTO.setCreatedAt(hadesDateFormat.format(source.getCreatedAt()));
		postDTO.setUpdatedAt(hadesDateFormat.format(source.getUpdatedAt()));
		postDTO.setPostContent(source.getPostContent().getText());
		return postDTO;
	}

}
