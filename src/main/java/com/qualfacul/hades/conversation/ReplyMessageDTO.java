package com.qualfacul.hades.conversation;

public class ReplyMessageDTO {
	private Long conversationId;
	private String message;
	
	@Deprecated
	public ReplyMessageDTO(){
	}

	public ReplyMessageDTO(Long conversationId, Long senderId, ConversationDirection direction, String message) {
		this.conversationId = conversationId;
		this.message = message;
	}

	public Long getConversationId() {
		return conversationId;
	}

	public void setConversationId(Long conversationId) {
		this.conversationId = conversationId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
