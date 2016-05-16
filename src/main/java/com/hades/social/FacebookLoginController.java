package com.hades.social;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.Get;
import com.hades.annotation.PermitEndpoint;

@RestController
public class FacebookLoginController {
	
	@Autowired
	private SocialFacade socialFacade;
	
	@Value("${social.redirectDomain}")
	private String redirectDomain;
	
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
		params.setRedirectUri(redirectDomain+"/facebook/login");
		return oauthOperations.buildAuthorizeUrl(GrantType.IMPLICIT_GRANT, params);
	}
	
	@Get("/facebook/login")
	@PermitEndpoint
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String login(@NotNull @RequestParam(value = "access_token") String accessToken){
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(facebookClientId, facebookClientSecret);
		try{
			AccessGrant accessGrant = new AccessGrant(accessToken);
			Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
			UserProfile userProfile = connection.fetchUserProfile();
			return socialFacade.socialAuthenticator(userProfile);
		}catch(ExpiredAuthorizationException e){
			return facebookLinkGenerator();
		}
	}
}
