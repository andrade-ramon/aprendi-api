package com.hades.social;

import static com.hades.login.LoginOrigin.FACEBOOK;
import static org.springframework.http.HttpStatus.ACCEPTED;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
public class FacebookLoginController {

	private static final String SCOPE_PERMISSION_EMAIL = "email";
	private static final String SCOPE_PERMISSION_PUBLICPROFILE = "public_profile";

	@Autowired
	private SocialService socialService;

	@Autowired
	private FacebookConnectionFactory connectionFactory;

	@Value("${social.facebook.redirectDomain}")
	private String redirectUrl;

	private static final Logger LOGGER = LoggerFactory.getLogger(FacebookLoginController.class);

	@Get("/facebook")
	@PermitEndpoint
	@ResponseStatus(ACCEPTED)
	public String facebookLinkGenerator() {
		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setScope(SCOPE_PERMISSION_PUBLICPROFILE);
		params.setScope(SCOPE_PERMISSION_EMAIL);
		params.setRedirectUri(redirectUrl);
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
			LOGGER.info("Facebook Authorization has expired for token: {}", accessToken);
			throw e;
		} catch (InvalidAuthorizationException e) {
			throw new InvalidFacebookTokenException();
		}

		UserProfile userProfile = connection.fetchUserProfile();
		if (userProfile.getEmail() == null) {
			throw new LoginFailureException();
		}

		LoginInfoDTO loginInfoDTO = socialService.authenticate(userProfile, FACEBOOK);
		return loginInfoDTO;
	}
}
