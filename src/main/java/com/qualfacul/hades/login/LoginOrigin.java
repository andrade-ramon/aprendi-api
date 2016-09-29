package com.qualfacul.hades.login;

import com.qualfacul.hades.conversation.ConversationDirection;

public enum LoginOrigin {
	USER {
		@Override
		public ConversationDirection getConversationDirection() {
			return ConversationDirection.STUDENT_TO_COLLEGE;
		}
	}, COLLEGE {
		@Override
		public ConversationDirection getConversationDirection() {
			return ConversationDirection.COLLEGE_TO_STUDENT;
		}
	}, ADMIN {
		@Override
		public ConversationDirection getConversationDirection() {
			return ConversationDirection.STUDENT_TO_COLLEGE;
		}
	}, FACEBOOK {
		@Override
		public ConversationDirection getConversationDirection() {
			return ConversationDirection.STUDENT_TO_COLLEGE;
		}
	};
	
	public abstract ConversationDirection getConversationDirection();
}
