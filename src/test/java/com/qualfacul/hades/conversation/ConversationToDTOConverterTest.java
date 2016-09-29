package com.qualfacul.hades.conversation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConversationToDTOConverterTest {

	@Mock(answer = RETURNS_DEEP_STUBS)
	private Conversation conversation;
	@Mock
	private MessageToDTOConverter messageConverter;
	
	private ConversationToDTOConverter subject;
	
	private Long conversationId;
	private LocalDateTime createdAt;
	private Long collegeId;
	private Long studentId;
	private String studentName;
	
	@Before
	public void setup() {
		subject = new ConversationToDTOConverter(messageConverter);
		
		conversationId = 123L;
		createdAt = LocalDateTime.now();
		collegeId = 456L;
		studentId = 678L;
		studentName = "Natalício";
		
		MessageDTO messageOne = new MessageDTO();
		messageOne.setContent("hello");
		messageOne.setDirection(ConversationDirection.STUDENT_TO_COLLEGE);
		messageOne.setSentAt(LocalDateTime.now());
		MessageDTO messageTwo = new MessageDTO();;
		messageTwo.setContent("bye");
		messageTwo.setDirection(ConversationDirection.COLLEGE_TO_STUDENT);
		messageTwo.setSentAt(LocalDateTime.now());
	}
	
	@Test
	public void shouldConvertWithoutMessages() {
		when(conversation.getId()).thenReturn(conversationId);
		when(conversation.getCreatedAt()).thenReturn(createdAt);
		when(conversation.getCollege().getId()).thenReturn(collegeId);
		when(conversation.getUser().getId()).thenReturn(studentId);
		when(conversation.getUser().getName()).thenReturn(studentName);
		
		ConversationDTO dto = subject.fromConversation(conversation).convert();
		
		assertEquals(conversationId, dto.getId());
		assertEquals(createdAt, dto.getCreatedAt());
		assertEquals(collegeId, dto.getCollegeId());
		assertEquals(studentId, dto.getStudentId());
		assertEquals(studentName, dto.getStudentName());
	}
	
}
