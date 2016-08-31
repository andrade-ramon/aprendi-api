package com.qualfacul.hades.conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.exceptions.CollegeNotFoundException;

@WebComponent
public class ConversationDTOToConversationConverter implements Converter<ConversationDTO, Conversation>{
	
	@Autowired
	private CollegeRepository collegeRepository;

	@Override
	public Conversation convert(ConversationDTO source) {
		Conversation conversation = new Conversation();
							
		Optional<College> optionalCollege = collegeRepository.findById(source.getCollege());
		if (!optionalCollege.isPresent()){
			throw new CollegeNotFoundException();
		}
		
		conversation.setCollege(optionalCollege.get());
		List<ConversationMessage> conversationMessage = new ArrayList<>();
		conversation.setMessages(conversationMessage);
		return conversation;
	}

}
