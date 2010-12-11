package org.twuni.authentication.oauth;

/**
 * A consumer token is obtained from the service provider and is used to sign requests made by this application.
 */
public class ConsumerToken extends OAuthToken {

	public ConsumerToken( String key, String secret ) {
		super( key, secret );
	}

}
