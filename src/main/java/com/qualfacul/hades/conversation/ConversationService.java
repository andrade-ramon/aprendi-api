package com.qualfacul.hades.conversation;

import com.qualfacul.hades.annotation.WebService;
import com.qualfacul.hades.login.LoginInfo;

@WebService
public class ConversationService {
	public ConversationDirection getUserDirection(LoginInfo loginInfo){
		if (loginInfo.isFromUser()){
			return ConversationDirection.STUDENT_TO_COLLEGE;
		}
		return ConversationDirection.COLLEGE_TO_STUDENT;
	}
}
