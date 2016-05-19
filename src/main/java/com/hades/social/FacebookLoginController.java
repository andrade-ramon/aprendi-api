package com.hades.social;

import static org.springframework.http.HttpStatus.ACCEPTED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.InvalidAuthorizationException;
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
import com.hades.exceptions.InvalidFacebookTokenException;
import com.hades.exceptions.LoginFailureException;
import com.hades.login.LoginInfoDTO;
import com.hades.login.LoginOrigin;

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
	public String facebookLinkGenerator() {
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setScope(SCOPE_PERMISSION_PUBLICPROFILE);
		params.setScope(SCOPE_PERMISSION_EMAIL);
		params.setRedirectUri(redirectDomain + FACEBOOK_LOGIN_URL);
		return oauthOperations.buildAuthorizeUrl(GrantType.IMPLICIT_GRANT, params);
	}

	@Get("/facebook/login")
	@PermitEndpoint
	@ResponseStatus(ACCEPTED)
	public LoginInfoDTO login(@RequestParam(value = "access_token") String accessToken) {
		Connection<Facebook> connection;
		try {
			AccessGrant accessGrant = new AccessGrant(accessToken);
			connection = connectionFactory.createConnection(accessGrant);
		} catch (ExpiredAuthorizationException e) {
			throw e;
		} catch (InvalidAuthorizationException e) {
			throw new InvalidFacebookTokenException();
		}

		UserProfile userProfile = connection.fetchUserProfile();
		if (userProfile.getEmail() == null) {
			throw new LoginFailureException();
		}

		LoginInfoDTO loginInfoDTO = socialService.authenticate(userProfile, LoginOrigin.FACEBOOK);
		return loginInfoDTO;
	}
}
