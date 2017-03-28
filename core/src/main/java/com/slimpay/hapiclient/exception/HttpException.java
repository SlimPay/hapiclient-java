package com.slimpay.hapiclient.exception;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import com.slimpay.hapiclient.hal.Resource;

/**
 * Raised when the HTTP status code is not equal to 2xx.
 * @see com.slimpay.hapiclient.exception.HttpRedirectionException HttpRedirectionException (3xx)
 * @see com.slimpay.hapiclient.exception.HttpClientErrorException HttpClientErrorException (4xx)
 * @see com.slimpay.hapiclient.exception.HttpServerErrorException HttpServerErrorException (5xx)
 */
public class HttpException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private final HttpRequestBase request;
	private final CloseableHttpResponse response;
	private final String responseBody;

	public HttpException(HttpRequestBase request, CloseableHttpResponse response, String responseBody) {
		super(String.valueOf(response.getStatusLine().getStatusCode()) + " " + response.getStatusLine().getReasonPhrase());
		this.request = request;
		this.response = response;
		this.responseBody = responseBody;
	}

	/**
	 * @return	The HTTP request causing the Exception.
	 */
	public HttpRequestBase getRequest() {
		return request;
	}

	/**
	 * <strong>IMPORTANT:</strong> you may not be able to consume
	 * the message body of the response since it has already been
	 * consumed once. Please use {@link HttpException#getResponseBody()}.
	 * @return The HTTP response causing the Exception.
	 * @see <a href="http://hc.apache.org/httpcomponents-core-4.4.x/tutorial/html/fundamentals.html#d5e84">HTTP entity</a>
	 */
	public CloseableHttpResponse getResponse() {
		return response;
	}
	
	/**
	 * Return the string representation of the body message
	 * returned by the HTTP response (if any).
	 * @return	The message body as a String or null if
	 * 			the message body could not be parsed as a String.
	 */
	public String getResponseBody() {
		return responseBody;
	}
	
	/**
	 * Shortcut for getResponse().getStatusLine().getStatusCode()
	 * @return The HTTP status code.
	 */
	public int getStatusCode() {
		return getResponse().getStatusLine().getStatusCode();
	}
	
	/**
	 * Shortcut for getResponse().getStatusLine().getReasonPhrase()
	 * @return The HTTP reason phrase.
	 */
	public String getReasonPhrase() {
		return getResponse().getStatusLine().getReasonPhrase();
	}
	
	/**
	 * The response message body <strong>may</strong> be a string
	 * representation of a Resource representing the error.
	 * <p>
	 * This is basically a shortcut for Resource.fromJson(getResponseBody()).
	 * @return	The Resource returned by the response or
	 * 			null if {@link #getResponseBody()} returns null.
	 */
	public Resource getResponseResource() {
		return responseBody != null ? Resource.fromJson(responseBody) : null;
	}
}
