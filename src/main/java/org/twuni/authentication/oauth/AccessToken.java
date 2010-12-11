package org.twuni.authentication.oauth;

/**
 * An access token provides the application with access to interact with the OAuth service provider on a user's behalf.
 */
public class AccessToken extends OAuthToken {

	public AccessToken( String key, String secret ) {
		super( key, secret );
	}

}
