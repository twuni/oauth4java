package org.twuni.authentication.oauth.client;

/**
 * This exception should be thrown whenever an OAuth access token does not yet exist for a user.
 */
public class AuthorizationRequiredException extends RuntimeException {

	private String authorizationUrl;

	/**
	 * @param authorizationUrl
	 *            The URL that must be visited by the user to authorize this application.
	 */
	public AuthorizationRequiredException( String authorizationUrl ) {
		this.authorizationUrl = authorizationUrl;
	}

	public String getAuthorizationUrl() {
		return authorizationUrl;
	}

}
