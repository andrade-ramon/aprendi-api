package com.hades.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.Get;
import com.hades.annotation.PermitEndpoint;

@Component
@RestController
public class SocialController {
	
	@Autowired
	private SocialFacade socialFacade;
	
	@Value("${facebook.clientId}")
	private String facebookClientId;
	
	@Value("${facebook.clientSecret}")
	private String facebookClientSecret;
	
	@Get("/facebook")
	@PermitEndpoint
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String facebookLinkGenerator(){
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookClientId, facebookClientSecret);
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setScope("email");
		params.setRedirectUri("https://dev.qualfacul.com:6660/facebook/login");
		return oauthOperations.buildAuthorizeUrl(GrantType.IMPLICIT_GRANT, params);
	}
	
	@Get("/facebook/login")
	@PermitEndpoint
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String login(@RequestParam(value = "access_token") String accessToken){
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookClientId, facebookClientSecret);
		AccessGrant accessGrant = new AccessGrant(accessToken);
		Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
		UserProfile userProfile = connection.fetchUserProfile();
		return socialFacade.socialAuthenticator(userProfile);
	}
}
