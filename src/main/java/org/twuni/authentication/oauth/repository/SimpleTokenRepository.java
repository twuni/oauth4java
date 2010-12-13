package org.twuni.authentication.oauth.repository;

import java.util.HashMap;
import java.util.Map;

import org.twuni.authentication.oauth.AccessToken;
import org.twuni.authentication.oauth.RequestToken;
import org.twuni.authentication.oauth.repository.TokenRepository;

public class SimpleTokenRepository implements TokenRepository {

	private final Map<String, RequestToken> requestTokens = new HashMap<String, RequestToken>();
	private final Map<String, AccessToken> accessTokens = new HashMap<String, AccessToken>();

	public AccessToken getAccessToken( String key ) {
		return accessTokens.get( key );
	}

	public RequestToken getRequestToken( String key ) {
		return requestTokens.get( key );
	}

	public void setAccessToken( String key, AccessToken value ) {
		accessTokens.put( key, value );
	}

	public void setRequestToken( String key, RequestToken value ) {
		requestTokens.put( key, value );
	}

}
