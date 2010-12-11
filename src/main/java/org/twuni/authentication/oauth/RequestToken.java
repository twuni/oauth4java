package org.twuni.authentication.oauth;

/**
 * A request token is issued by the service provider to this application to request authorization for a user.
 */
public class RequestToken extends OAuthToken {

	public RequestToken( String key, String secret ) {
		super( key, secret );
	}

}
