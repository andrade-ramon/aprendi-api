package com.hades.social;

import org.springframework.social.connect.UserProfile;

interface SocialLoginCallback {

	String onSucess(UserProfile userProfile);
	
}
