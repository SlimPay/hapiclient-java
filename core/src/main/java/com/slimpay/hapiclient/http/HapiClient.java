package com.slimpay.hapiclient.http;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.damnhandy.uri.template.UriTemplate;
import com.slimpay.hapiclient.exception.HttpClientErrorException;
import com.slimpay.hapiclient.exception.HttpException;
import com.slimpay.hapiclient.exception.HttpRedirectionException;
import com.slimpay.hapiclient.exception.HttpServerErrorException;
import com.slimpay.hapiclient.exception.RelNotFoundException;
import com.slimpay.hapiclient.exception.UnparsableResponseException;
import com.slimpay.hapiclient.hal.RegisteredRel;
import com.slimpay.hapiclient.hal.Resource;
import com.slimpay.hapiclient.http.auth.AuthenticationMethod;
import com.slimpay.hapiclient.util.EntityConverter;

/**
 * Example of a client instantiation and a simple GET request:
 * 
 *<pre><code>HapiClient hapiClient = new HapiClient.Builder()
 *	.setApiUrl("https://api-sandbox.slimpay.net")
 *	.setProfile("https://api.slimpay.net/alps/v1")
 *	.setAuthenticationMethod(
 *	new Oauth2BasicAuthentication.Builder()
 *		.setTokenEndPointUrl("/oauth/token")
 *		.setUserid("democreditor01")
 *		.setPassword("demosecret01")
 *		.setScope("api")
 *		.build()
 *	)
 *	.build();
 *
 *Follow follow = new Follow.Builder(new Rel("https://api.slimpay.net/alps#get-creditors"))
 *	.setMethod(Method.GET)
 *	.setUrlVariable("reference", "democreditor")
 *	.build();
 *try {
 *	Resource creditor = hapiClient.send(follow);
 *	JsonObject body = creditor.getState();
 *	String reference = body.getString("reference"); // democreditor
 *} catch (HttpException e) {
 *	// ...
 *}</code></pre>
 */
@ThreadSafe
public final class HapiClient implements Closeable {
	private final String apiUrl;
	private final String entryPointUrl;
	private final String profile;
	private final AuthenticationMethod authenticationMethod;
	
	private final CloseableHttpClient client;
	
	private Resource entryPointResource;
	
	/**
	 * @see Builder#Builder()
	 */
	private HapiClient(
			final String apiUrl,
			final String entryPointUrl,
			final String profile,
			final AuthenticationMethod authenticationMethod,
			final CloseableHttpClient client) {
		this.apiUrl = apiUrl;
		this.entryPointUrl = entryPointUrl;
		this.profile = profile;
		this.authenticationMethod = authenticationMethod;
		this.client = client;
	}
	
	/**
	 * @see Builder#setApiUrl(String)
	 */
	public String getApiUrl() {
		return apiUrl;
	}
	
	/**
	 * @see Builder#setEntryPointUrl(String)
	 */
	public String getEntryPointUrl() {
		return entryPointUrl;
	}
	
	/**
	 * @see Builder#setProfile(String)
	 */
	public String getProfile() {
		return profile;
	}
	
	/**
	 * @see Builder#setAuthenticationMethod(AuthenticationMethod)
	 */
	public AuthenticationMethod getAuthenticationMethod() {
		return authenticationMethod;
	}
	
	/**
	 * @return The Apache HTTP client used to send the requests.
	 * @see Builder#setConnectionManager(HttpClientConnectionManager)
	 */
	public CloseableHttpClient getClient() {
		return client;
	}
	
	/**
	 * Sends a request to an absolute or relative URL
	 * (if {@link Builder#setApiUrl(String)} is set).
	 * @param request	The {@link Request}
	 * @return	The {@link Resource} returned by the server.
	 * @throws	HttpException
	 */
	public Resource send(final Request request)
			throws HttpException {
		// Create the HTTP request
		HttpRequestBase httpRequest = createHttpRequest(request);
		
		// Execute it
		CloseableHttpResponse httpResponse = executeHttpRequest(httpRequest);
		
		// Check the status code (must be 2xx)
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		if (statusCode >= 200 && statusCode < 300)
			return Resource.fromJson(consumeResponse(httpResponse));

		// Request is not a success but we still try to get a body from the response
		String responseBody;
		try {
			responseBody = consumeResponse(httpResponse);
		} catch (UnparsableResponseException ignored) {
			responseBody = null;
		}
		
		// Exception depending on status code for 3xx, 4xx and 5xx
		if (statusCode >= 300 && statusCode < 400)
			throw new HttpRedirectionException(httpRequest, httpResponse, responseBody);
		else if (statusCode >= 400 && statusCode < 500)
			throw new HttpClientErrorException(httpRequest, httpResponse, responseBody);
		else if (statusCode >= 500 && statusCode < 600)
			throw new HttpServerErrorException(httpRequest, httpResponse, responseBody);
		else
			throw new HttpException(httpRequest, httpResponse, responseBody);
	}

	/**
	 * Consumes the HTTP response body message
	 * and returns its String representation.
	 * <p>
	 * The HTTP response is then closed and its
	 * body message may be impossible to be read again.
	 * @param httpResponse
	 * @return	The String representation of the body message.
	 * @throws UnparsableResponseException
	 * @see http://hc.apache.org/httpcomponents-core-4.4.x/tutorial/html/fundamentals.html#d5e84
	 */
	private static String consumeResponse(CloseableHttpResponse httpResponse)
			throws UnparsableResponseException {
		try {
			return EntityConverter.entityToString(httpResponse.getEntity());
		} catch (Exception e) {
			throw new UnparsableResponseException("Couldn't parse response entity.", e);
		} finally {
			closeResponseQuietly(httpResponse);
		}
	}
	
	/**
	 * Follows a link on the entry point Resource.
	 * @see #send(Follow, Resource)
	 */
	public Resource send(final Follow follow)
			throws HttpException, RelNotFoundException {
		return send(follow, getEntryPointResource());
	}

	/**
	 * Follows a link on a Resource.
	 * <p>
	 * The <a href="https://tools.ietf.org/html/draft-kelly-json-hal-07#section-8.3">Hypertext Cache Pattern</a>
	 * is implemented by this method.
	 * <blockquote>The "hypertext cache pattern" allows servers to use embedded
	 * resources to dynamically reduce the number of requests a client
	 * makes, improving the efficiency and performance of the application.</blockquote>
	 * <strong>IMPORTANT:</strong> the embedded Resource may be partial.
	 * You can try to get its full version by using {@link #refresh(Resource)}.
	 * @param follow	The Follow object containing the relation name,
	 * 					the method and eventually the data and/or headers.
	 * @param resource	The resource containing the link
	 * @return	The Resource returned by the server.
	 * @throws	HttpException
	 * @throws RelNotFoundException
	 */
	public Resource send(final Follow follow, final Resource resource)
			throws HttpException, RelNotFoundException {
		try {
			return resource.getEmbeddedResource(follow.getRel());
		} catch (RelNotFoundException ignored) { }
		
		return send(
			new Request.Builder(follow.getUrl(resource))
				.setMethod(follow.getMethod())
				.setUrlVariables(follow.getUrlVariables())
				.setMessageBody(follow.getMessageBody())
				.addHeaders(follow.getHeaders())
				.build()
		);
	}

	/**
	 * Follows one or more consecutive links, each link
	 * being in the Resource returned by the previous link
	 * starting by the entry point Resource.
	 * @see #send(List, Resource)
	 */
	public Resource send(final List<Follow> follow)
			throws HttpException, RelNotFoundException {
		return send(follow, getEntryPointResource());
	}

	/**
	 * Follows one or more consecutive links, each link
	 * being in the Resource returned by the previous link.
	 * @see #send(Follow, Resource)
	 */
	public Resource send(final List<Follow> follow, final Resource resource)
			throws HttpException, RelNotFoundException {
		if (follow.size() == 0)
			throw new IllegalArgumentException("The follows list is empty.");
		
		Resource lastResource = resource;
		for (Follow hop : follow)
			lastResource = send(hop, lastResource);
		
		return lastResource;
	}

	/**
	 * Sends a request to the API entry point URL ("/" by default)
	 * and returns its {@link Resource} object.
	 * <p>
	 * The entry point Resource is only retrieved <strong>if needed</strong>
	 * and <strong>only once</strong> per HapiClient instance
	 * (and cached as an attribute).
	 * @return	The entry point Resource.
	 * @throws HttpException 
	 * @see Builder#setEntryPointUrl(String)
	 */
	public synchronized Resource getEntryPointResource()
			throws HttpException {
		if (entryPointResource == null)
			entryPointResource = send(new Request.Builder(entryPointUrl).build());
		
		return entryPointResource;
	}
	
	/**
	 * Attempts to refresh the Resource by sending a GET request
	 * to the URL referenced by the {@link RegisteredRel#SELF} relation type.
	 * If the resource doesn't have such relation type or the request fails,
	 * the same resource is returned.
	 * @param resource	The Resource to refresh.
	 * @return	The refreshed Resource or the same Resource if failed to refresh it.
	 */
	public Resource refresh(Resource resource) {
		try {
			String url = resource.getLink(RegisteredRel.SELF).getHref();
			return send(new Request.Builder(url).build());
		} catch (Exception ignored) {
			return resource;
		}
	}
	
	/**
	 * Instantiates the HttpRequest depending on the
	 * configuration from the given Request.
	 * @param request	The Request configuration.
	 * @return	The HTTP request.
	 */
	private HttpRequestBase createHttpRequest(Request request) {
		// The URL
		String url = request.getUrl().trim();
		
		// Handle URLs relative to the server URL
		if (url.startsWith("/"))
			url = apiUrl + url;
		
		// Handle templated URLs
		Map<String, Object> urlVariables = request.getUrlVariables();
		if (urlVariables != null && !urlVariables.isEmpty())
			url = UriTemplate.fromTemplate(url).set(urlVariables).expand();
		
		// Build the URI
		URI uri;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		
		HttpRequestBase httpRequest;
		switch (request.getMethod()) {
			case GET:		httpRequest = new HttpGet		(uri); break;
			case POST:		httpRequest = new HttpPost		(uri); break;
			case PUT:		httpRequest = new HttpPut		(uri); break;
			case PATCH:		httpRequest = new HttpPatch		(uri); break;
			case DELETE:	httpRequest = new HttpDelete	(uri); break;
			default: throw new UnsupportedOperationException("Request Method not supported");
		}
		
		// Message body for the compatible request methods.
		HttpEntity messageBody = request.getMessageBody();
		if (messageBody != null && httpRequest instanceof HttpEntityEnclosingRequestBase)
			((HttpEntityEnclosingRequestBase) httpRequest).setEntity(messageBody);
		
		// Accept header
		String accept;
		if (profile != null && !profile.isEmpty())
			accept = "application/hal+json; profile=\"" + profile + "\"";
		else
			accept = "application/json";

		httpRequest.addHeader("Accept", accept);
		
		// Additional headers if specified
		List<Header> headers = request.getHeaders();
		if (headers != null)
			for (Header header : headers)
				httpRequest.addHeader(header);
		
		return httpRequest;
	}
	
	/**
	 * Sends the HTTP request and rethrows any IOException as a RuntimeException.
	 * @param httpRequest	The HTTP request to send.
	 * @return	The HTTP response.
	 * @throws HttpException	May be raised by the authentication method.
	 */
	private CloseableHttpResponse executeHttpRequest(HttpUriRequest httpRequest)
			throws HttpException {
		try {
			// Authorization
			if (authenticationMethod != null)
				authenticationMethod.authorizeRequest(this, httpRequest);
			
			// Execution
			CloseableHttpResponse httpResponse = client.execute(httpRequest);
			
			// If Unauthorized, maybe the authorization just timed out.
			// Try it again to be sure.
			if (httpResponse.getStatusLine().getStatusCode() == 401 &&
				authenticationMethod != null) {
				// Close the old response
				closeResponseQuietly(httpResponse);
				
				// Authorize again
				authenticationMethod.authorizeRequest(this, httpRequest);

				// Execute again
				httpResponse = client.execute(httpRequest);
			}
			
			return httpResponse;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Closes the HTTP response quietly (no IOException thrown).
	 */
	private static void closeResponseQuietly(CloseableHttpResponse httpResponse) {
		try {
			httpResponse.close();
		} catch (IOException ignored) { }
	}

	/**
	 * Closes the HTTP client.
	 */
	public void close() throws IOException {
		client.close();
	}

	/**
	 * Closes the HTTP client quietly (no IOException thrown)
	 * If you want to catch the exception, please use {@link HapiClient#close()}.
	 */
	public void closeQuietly() {
		try {
			client.close();
		} catch (IOException ignored) { }
	}
	
	@NotThreadSafe
	public static class Builder {
		private String apiUrl;
		private String entryPointUrl;
		private String profile;
		private AuthenticationMethod authenticationMethod;
		private HttpClientConnectionManager connectionManager;
		
		/**
		 * In order to use a REST HAPI, you need
		 * to set all of the {@link #setApiUrl(String) API URL},
		 * the {@link #setProfile(String) profile URL} and the
		 * {@link #setAuthenticationMethod(AuthenticationMethod) authentication method}.
		 * You might want to use this client to send HTTP requests
		 * to a regular REST API. In that case, all the parameters
		 * are optional and you will only be able to send Request
		 * objects (no Follow).
		 */
		public Builder() {
			
		}
		
		/**
		 * Optional since you may pass an absolute URL
		 * to the {@link HapiClient#send(Request)} method.
		 * Mandatory if you want to use URLs relative to the API server. 
		 * @param apiUrl	The URL pointing to the API server.
		 * @return 	The builder.
		 */
		public Builder setApiUrl(String apiUrl) {
			this.apiUrl = apiUrl;
			return this;
		}

		/**
		 * Overrides the default entry point ("/").
		 * @param entryPointUrl	The URL to the entry point Resource.
		 * @return	The Builder.
		 */
		public Builder setEntryPointUrl(String entryPointUrl) {
			this.entryPointUrl = entryPointUrl;
			return this;
		}

		/**
		 * Optional.
		 * Mandatory if you set the API URL to a Hypermedia API server.
		 * @param profile		The HAL profile containing the resources
		 *						and their descriptors.
		 *						If specified, the client will send an
		 *						Accept header with application/hal+json
		 *						and a <code>profile</code> attribute
		 *						containing the value set here.
		 * @return 	The builder.
		 */
		public Builder setProfile(String profile) {
			this.profile = profile;
			return this;
		}
		
		/**
		 * Optional (may be mandatory depending on the server).
		 * @param authenticationMethod	The authentication method used by the server.
		 * @return 	The builder.
		 */
		public Builder setAuthenticationMethod(AuthenticationMethod authenticationMethod) {
			this.authenticationMethod = authenticationMethod;
			return this;
		}
		
		/**
		 * Optional.
		 * Overrides the default {@link PoolingHttpClientConnectionManager} that has
		 * a maximum total connections of 20 and a maximum connection per route of 5.
		 * @param connectionManager	The connection manager used by the client.
		 * @return 	The builder.
		 */
		public Builder setConnectionManager(HttpClientConnectionManager connectionManager) {
			this.connectionManager = connectionManager;
			return this;
		}
		
		/**
		 * Instantiates the HapiClient.
		 * @return	The instantiated HapiClient.
		 */
		public HapiClient build() {
			if (entryPointUrl == null || entryPointUrl.trim().isEmpty())
				this.entryPointUrl = "/";
			
			if (connectionManager == null) {
				// Default pooling connection manager
				connectionManager = new PoolingHttpClientConnectionManager();
				((PoolingHttpClientConnectionManager) connectionManager).setMaxTotal(20);
				((PoolingHttpClientConnectionManager) connectionManager).setDefaultMaxPerRoute(5);
			}
			
			CloseableHttpClient client = HttpClients.custom().setConnectionManager(connectionManager).build();
			
			return new HapiClient(apiUrl, entryPointUrl, profile, authenticationMethod, client);
		}
		
	}
}
