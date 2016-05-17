package com.hades.social;

import static org.springframework.http.HttpStatus.ACCEPTED;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	private static final String SCOPE_PERMISSION_EMAIL = "email";
	private static final String SCOPE_PERMISSION_PUBLICPROFILE = "public_profile";
	private static final String FACEBOOK_LOGIN_URL = "/facebook/login";

	@Autowired
	private SocialService socialService;
	
	@Autowired
	private FacebookConnectionFactory connectionFactory;
	
	@Value("${social.redirectDomain}")
	private String redirectDomain;

	
	@Get("/facebook")
	@PermitEndpoint
	@ResponseStatus(ACCEPTED)
	public String facebookLinkGenerator(){
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setScope(SCOPE_PERMISSION_PUBLICPROFILE);
		params.setScope(SCOPE_PERMISSION_EMAIL);
		params.setRedirectUri(redirectDomain + FACEBOOK_LOGIN_URL);
		return oauthOperations.buildAuthorizeUrl(GrantType.IMPLICIT_GRANT, params);
	}
	
	@Get(FACEBOOK_LOGIN_URL)
	@PermitEndpoint
	@ResponseStatus(ACCEPTED)
	public String login(@NotNull @RequestParam(value = "access_token") String accessToken){
		AccessGrant accessGrant = new AccessGrant(accessToken);
		Connection<Facebook> connection;
		try{
			connection = connectionFactory.createConnection(accessGrant);
		}catch(ExpiredAuthorizationException e){
			return facebookLinkGenerator();
		}
		UserProfile userProfile = connection.fetchUserProfile();
		return socialService.authenticate(userProfile, SocialType.FACEBOOK);
	}
}
