package com.qualfacul.hades.conversation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessageToDTOConverterTest {

	private MessageToDTOConverter subject;
	
	@Mock(answer = RETURNS_DEEP_STUBS)
	private Message message;
	
	@Test
	public void test() {
		subject = new MessageToDTOConverter();
		
		MessageDTO dto = subject.convert(message);
		
		assertEquals(message.getId(), dto.getId());
		assertEquals(message.getSentAt(), dto.getSentAt());
		assertEquals(message.getMessageContent().getText(), dto.getContent());
		assertEquals(message.getDirection(), dto.getDirection());
		
	}

}
