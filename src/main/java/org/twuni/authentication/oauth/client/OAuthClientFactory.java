package org.twuni.authentication.oauth.client;

import java.net.URL;

import org.twuni.authentication.oauth.AccessToken;
import org.twuni.authentication.oauth.ConsumerToken;
import org.twuni.authentication.oauth.RequestToken;
import org.twuni.authentication.oauth.repository.TokenRepository;

public abstract class OAuthClientFactory<T extends OAuthClient> {

	private final ConsumerToken consumerToken;
	private final TokenRepository tokenRepository;

	protected OAuthClientFactory( ConsumerToken consumerToken, TokenRepository tokenRepository ) {
		this.consumerToken = consumerToken;
		this.tokenRepository = tokenRepository;
	}

	public T createInstance( String userId, URL callbackUrl ) {

		T client = createInstance( consumerToken, callbackUrl.toString() );

		AccessToken accessToken = tokenRepository.getAccessToken( userId );

		if( accessToken == null ) {

			RequestToken requestToken = client.getRequestToken();
			tokenRepository.setRequestToken( userId, requestToken );

			String authorizationUrl = client.getAuthorizationUrl( requestToken );
			throw new AuthorizationRequiredException( authorizationUrl );

		}

		client.setAccessToken( accessToken );

		return client;

	}

	public T createInstance( String userId, String verifier ) {

		T client = createInstance( consumerToken );

		RequestToken requestToken = tokenRepository.getRequestToken( userId );
		client.setRequestToken( requestToken );

		AccessToken accessToken = client.getAccessToken( verifier );
		tokenRepository.setAccessToken( userId, accessToken );

		client.setAccessToken( accessToken );

		return client;

	}

	protected abstract T createInstance( ConsumerToken consumerToken );

	protected abstract T createInstance( ConsumerToken consumerToken, String callbackUrl );

}
