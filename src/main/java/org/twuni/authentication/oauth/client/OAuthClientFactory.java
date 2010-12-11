package org.twuni.authentication.oauth.client;

import java.net.URL;

import org.twuni.authentication.oauth.AccessToken;
import org.twuni.authentication.oauth.ConsumerToken;
import org.twuni.authentication.oauth.RequestToken;
import org.twuni.authentication.oauth.repository.TokenRepository;

/**
 * This class constructs OAuth clients, taking care of most of the authorization workflow.
 * 
 * @param <T> The type of OAuthClient that this factory will create.
 */
public abstract class OAuthClientFactory<T extends OAuthClient> {

	private final ConsumerToken consumerToken;
	private final TokenRepository tokenRepository;

	protected OAuthClientFactory( ConsumerToken consumerToken, TokenRepository tokenRepository ) {
		this.consumerToken = consumerToken;
		this.tokenRepository = tokenRepository;
	}

	/**
	 * @return a fully authorized client if an access token already exists in the repository for the specified user.
	 * @throws AuthorizationRequiredException if an access token does not yet exist for the specified user.
	 */
	public T createInstance( String userId, URL authorizationCallbackUrl ) {

		T client = createInstance( consumerToken, authorizationCallbackUrl.toString() );

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

	/**
	 * @return a fully authorized client.
	 */
	public T createInstance( String userId, String verifier ) {

		T client = createInstance( consumerToken );

		RequestToken requestToken = tokenRepository.getRequestToken( userId );
		client.setRequestToken( requestToken );

		AccessToken accessToken = client.getAccessToken( verifier );
		tokenRepository.setAccessToken( userId, accessToken );

		client.setAccessToken( accessToken );

		return client;

	}

	/**
	 * @return a freshly constructed client.
	 */
	protected abstract T createInstance( ConsumerToken consumerToken );

	/**
	 * @return a freshly constructed client.
	 */
	protected abstract T createInstance( ConsumerToken consumerToken, String callbackUrl );

}
