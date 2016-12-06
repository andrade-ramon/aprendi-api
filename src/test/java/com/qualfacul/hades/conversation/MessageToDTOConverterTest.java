package com.qualfacul.hades.conversation;

import static com.qualfacul.hades.conversation.ConversationDirection.COLLEGE_TO_STUDENT;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageToDTOConverterTest {

	@Mock
	private Conversation conversation;

	@Test
	public void shouldConvertWithAllFields() {
		MessageToDTOConverter subject = new MessageToDTOConverter();
		
		Message message = new Message(conversation, "some message", COLLEGE_TO_STUDENT);
		message.setId(123l);
		
		MessageDTO converted = subject.convert(message);
		
		assertEquals(message.getId(), converted.getId());
		assertEquals(message.getSentAt(), converted.getSentAt());
		assertEquals(message.getMessageContent().getText(), converted.getContent());
		assertEquals(message.getDirection(), converted.getDirection());
		
	}

}
