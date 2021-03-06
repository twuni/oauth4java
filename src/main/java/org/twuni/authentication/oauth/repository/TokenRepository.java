package org.twuni.authentication.oauth.repository;

import org.twuni.authentication.oauth.AccessToken;
import org.twuni.authentication.oauth.RequestToken;

/**
 * A token repository allows an OAuthClientFactory to store and retrieve OAuth access tokens and request tokens for users of your application.
 */
public interface TokenRepository {

	public AccessToken getAccessToken( String userId );

	public RequestToken getRequestToken( String userId );

	public void setAccessToken( String userId, AccessToken accessToken );

	public void setRequestToken( String userId, RequestToken requestToken );

}
