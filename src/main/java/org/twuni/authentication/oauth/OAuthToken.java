package org.twuni.authentication.oauth;

abstract class OAuthToken {

	private String key;
	private String secret;

	public OAuthToken( String key, String secret ) {
		this.key = key;
		this.secret = secret;
	}

	public String getKey() {
		return key;
	}

	public String getSecret() {
		return secret;
	}

	public void setKey( String key ) {
		this.key = key;
	}

	public void setSecret( String secret ) {
		this.secret = secret;
	}

}
