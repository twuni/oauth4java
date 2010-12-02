package org.twuni.authentication.oauth;

abstract class OAuthToken {

	protected final String key;
	protected final String secret;

	protected OAuthToken( String key, String secret ) {
		this.key = key;
		this.secret = secret;
	}

	public String getKey() {
		return key;
	}

	public String getSecret() {
		return secret;
	}

}
