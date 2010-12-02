package org.twuni.authentication.oauth.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.ParameterStyle;
import net.oauth.client.httpclient4.HttpClient4;

import org.apache.commons.io.IOUtils;
import org.twuni.authentication.oauth.AccessToken;
import org.twuni.authentication.oauth.RequestToken;

public abstract class OAuthClient {

	private OAuthAccessor accessor;
	private net.oauth.client.OAuthClient client;

	protected abstract String getRequestTokenUrl();

	protected abstract String getAccessTokenUrl();

	protected abstract String getAuthorizationUrl();

	private final OAuthServiceProvider provider = new OAuthServiceProvider( getRequestTokenUrl(), getAuthorizationUrl(), getAccessTokenUrl() );

	protected OAuthClient( String consumerKey, String consumerSecret, String callbackUrl ) {
		OAuthConsumer consumer = new OAuthConsumer( callbackUrl, consumerKey, consumerSecret, provider );
		accessor = new OAuthAccessor( consumer );
		client = new net.oauth.client.OAuthClient( new HttpClient4() );
	}

	public void setAccessToken( AccessToken accessToken ) {
		accessor.accessToken = accessToken.getKey();
		accessor.tokenSecret = accessToken.getSecret();
	}

	public RequestToken getRequestToken() throws IOException, OAuthException, URISyntaxException {
		if( accessor.requestToken == null || accessor.accessToken != null ) {
			client.getRequestToken( accessor );
		}
		return new RequestToken( accessor.requestToken, accessor.tokenSecret );
	}

	public void setRequestToken( RequestToken requestToken ) {
		accessor.requestToken = requestToken.getKey();
		accessor.tokenSecret = requestToken.getSecret();
	}

	public AccessToken getAccessToken( String verifier ) throws IOException, OAuthException, URISyntaxException {
		List<OAuth.Parameter> parameters = new ArrayList<OAuth.Parameter>();
		parameters.add( new OAuth.Parameter( OAuth.OAUTH_VERIFIER, verifier ) );
		client.getAccessToken( accessor, OAuthMessage.GET, parameters );
		return getAccessToken();
	}

	public AccessToken getAccessToken() {
		if( accessor.accessToken == null ) {
			return null;
		}
		return new AccessToken( accessor.accessToken, accessor.tokenSecret );
	}

	public String getAuthorizationUrl( RequestToken requestToken ) {
		return getAuthorizationUrl() + "?" + OAuth.OAUTH_TOKEN + "=" + requestToken.getKey();
	}

	private InputStream service( String method, String url ) throws IOException, OAuthException, URISyntaxException {
		return service( method, url, new HashMap<String, String>() );
	}

	public InputStream get( String url ) throws IOException, OAuthException, URISyntaxException {
		return service( OAuthMessage.GET, url );
	}

	public InputStream get( String url, Map<String, String> parameters ) throws IOException, OAuthException, URISyntaxException {
		return service( OAuthMessage.GET, url, parameters );
	}

	public InputStream post( String url, Map<String, String> parameters ) throws IOException, OAuthException, URISyntaxException {
		return service( OAuthMessage.POST, url, parameters );
	}

	public InputStream post( String url, String body ) throws IOException, OAuthException, URISyntaxException {
		return service( OAuthMessage.POST, url, IOUtils.toInputStream( body ) );
	}

	public InputStream put( String url, Map<String, String> parameters ) throws IOException, OAuthException, URISyntaxException {
		return service( OAuthMessage.PUT, url, parameters );
	}

	public InputStream put( String url, String body ) throws IOException, OAuthException, URISyntaxException {
		return service( OAuthMessage.PUT, url, IOUtils.toInputStream( body ) );
	}

	public InputStream delete( String url ) throws IOException, OAuthException, URISyntaxException {
		return service( OAuthMessage.DELETE, url );
	}

	public InputStream delete( String url, Map<String, String> parameters ) throws IOException, OAuthException, URISyntaxException {
		return service( OAuthMessage.DELETE, url, parameters );
	}

	private InputStream service( String method, String url, Map<String, String> parameters ) throws IOException, OAuthException, URISyntaxException {
		OAuthMessage request = accessor.newRequestMessage( method, url, parameters.entrySet() );
		return service( request );
	}

	private InputStream service( String method, String url, InputStream body ) throws IOException, OAuthException, URISyntaxException {
		return service( method, url, new HashMap<String, String>(), body );
	}

	private InputStream service( String method, String url, Map<String, String> parameters, InputStream body ) throws IOException, OAuthException, URISyntaxException {
		return service( accessor.newRequestMessage( method, url, parameters.entrySet(), body ) );
	}

	private InputStream service( OAuthMessage request ) throws IOException, OAuthException {
		OAuthMessage response = client.invoke( request, ParameterStyle.AUTHORIZATION_HEADER );
		return response.getBodyAsStream();
	}

}
