package com.slimpay.hapiclient.http.auth;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.json.JsonObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;

import com.slimpay.hapiclient.exception.HttpException;
import com.slimpay.hapiclient.http.HapiClient;
import com.slimpay.hapiclient.http.Method;
import com.slimpay.hapiclient.http.Request;
import com.slimpay.hapiclient.http.UrlEncodedBody;

/**
 * The <a href="https://tools.ietf.org/html/rfc6749">Oauth2 authentication</a> using a
 * <a href="https://tools.ietf.org/html/rfc2617#section-2">Basic authentication</a>
 * to get the access token.
 */
@ThreadSafe
public final class Oauth2BasicAuthentication implements AuthenticationMethod {
	private final String tokenEndPointUrl;
	private final String userid;
	private final String password;
	private final String grantType;
	private final String scope;
	
	private ExpirableToken token;
	
	/**
	 * @see Builder#Builder()
	 */
	private Oauth2BasicAuthentication(String tokenEndPointUrl, String userid, String password, String grantType, String scope, ExpirableToken token) {
		this.tokenEndPointUrl = tokenEndPointUrl;
		this.userid = userid;
		this.password = password;
		this.grantType = grantType;
		this.scope = scope;
	}

	/**
	 * @return	The API server authentication end point.
	 */
	public String getTokenEndPointUrl() {
		return tokenEndPointUrl;
	}

	/**
	 * @return	The first part of the oauth2 authentication.
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @return	The second part of the oauth2 authentication.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * @return	The grant_type parameter.
	 */
	public String getGrantType() {
		return grantType;
	}

	/**
	 * @return	The scope parameter.
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @return	The last token used.
	 */
	public synchronized ExpirableToken getToken() {
		return token;
	}

	/**
	 * Adds the authorization header to the request with a valid token.
	 * If we do not have a valid token yet, we send a request for one.
	 * @param hapiClient		The client used to send the request.
	 * @param httpRequest	The HTTP request before it is sent.
	 * @throws HttpException 
	 */
	public synchronized void authorizeRequest(final HapiClient hapiClient, final HttpUriRequest httpRequest) throws HttpException {
		if (isRequestAuthorized(httpRequest))
			return;
		
		// Request a new access token if needed
		if (!isTokenStillValid())
			getAccessToken(hapiClient);
		
		// We have a valid token (make sure to clean the Authorization header)
		httpRequest.removeHeaders("Authorization");
		httpRequest.addHeader("Authorization", "Bearer " + token.getValue());
	}
	
	/**
	 * Checks if the request contains a Basic or Bearer Authorization.
	 * @param httpRequest	The HTTP request before it is sent.
	 * @return false if the request needs to be authorized
	 */
	private boolean isRequestAuthorized(final HttpUriRequest httpRequest) {
		Header authorization = httpRequest.getFirstHeader("Authorization");
		if (authorization == null)
			return false;
		else
			return authorization.getValue().startsWith("Basic") || authorization.getValue().startsWith("Bearer");
	}

	/**
	 * Sends a request for an access token.
	 * @param hapiClient	The client used to send the request.
	 */
	private synchronized void getAccessToken(final HapiClient hapiClient) throws HttpException {
		String basic = new String(Base64.encodeBase64((userid + ":" + password).getBytes()));
		Header authorizationHeader = new BasicHeader("Authorization", "Basic " + basic);
		
		UrlEncodedBody requestData = new UrlEncodedBody();
		requestData.put("grant_type", grantType);
		requestData.put("scope", scope);
		
		Request request = new Request.Builder(tokenEndPointUrl)
			.setMethod(Method.POST)
			.setMessageBody(requestData)
			.addHeader(authorizationHeader)
			.build();
		
		// Send the request
		JsonObject state = hapiClient.send(request).getState();
		
		// Check the response
		if (state == null || !state.containsKey("access_token") || !state.containsKey("expires_in"))
			throw new RuntimeException("The authentication was a success but the response did not contain the token or its validity limit.");
		
		// We update the token
		token = new ExpirableToken(state.getString("access_token"), getTime() + Long.valueOf(state.getInt("expires_in")));
	}
	
	/**
	 * Checks if the token is still valid at the time
	 * this method is called.
	 * @return boolean
	 */
	private synchronized boolean isTokenStillValid() {
		return token != null && token.isValidUntil(getTime());
	}
	
	/**
	 * Note: It is important to use the same method when setting
	 * the expiration time and checking if it is still valid.
	 * @return	The current time in seconds.
	 */
	private Long getTime() {
		return TimeUnit.MILLISECONDS.toSeconds(new Date().getTime());
	}
	
	/**
	 * The {@link Oauth2BasicAuthentication} builder
	 */
	@NotThreadSafe
	public static class Builder {
		private String tokenEndPointUrl;
		private String userid;
		private String password;
		private String grantType;
		private String scope;
		private ExpirableToken token;
		
		/**
		 * The Oauth2 authentication method using the
		 * Basic authorization header composed of a
		 * userid and a password.
		 * <p>
		 * The default grant_type parameter is "client_credentials"
		 * and the default scope is "api".
		 */
		public Builder() {
			grantType = "client_credentials";
			scope = "api";
		}
		
		/**
		 * @param tokenEndPointUrl	The server access token end point URL
		 * 							(absolute or relative to the client API URL)
		 */
		public Builder setTokenEndPointUrl(String tokenEndPointUrl) {
			this.tokenEndPointUrl = tokenEndPointUrl;
			return this;
		}

		/**
		 * @param userid	The first part of the Basic authentication
		 */
		public Builder setUserid(String userid) {
			this.userid = userid;
			return this;
		}

		/**
		 * @param password	The second part of the Basic authentication
		 */
		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}

		/**
		 * @param grantType		The grant_type parameter
		 */
		public Builder setGrantType(String grantType) {
			this.grantType = grantType;
			return this;
		}

		/**
		 * @param scope		The scope parameter
		 */
		public Builder setScope(String scope) {
			this.scope = scope;
			return this;
		}

		/**
		 * You may want to initialize this AuthenticationMethod
		 * with a valid token. If the token is not valid anymore,
		 * it will just be ignored.
		 * @param token		A valid token
		 */
		public void setToken(ExpirableToken token) {
			this.token = token;
		}

		public Oauth2BasicAuthentication build() {
			return new Oauth2BasicAuthentication(tokenEndPointUrl, userid, password, grantType, scope, token);
		}
	}

}
