package com.slimpay.hapiclient.exception;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * HTTP Status Code 3xx
 * <p>
 * Note: Since the Apache HTTP client do not handle 3xx status code,
 * we may do it at the HAPI Client level later on.
 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.3
 */
@NotThreadSafe
public class HttpRedirectionException extends HttpException {
	private static final long serialVersionUID = 1L;
	
	public HttpRedirectionException(HttpRequestBase request, CloseableHttpResponse response, String responseBody) {
		super(request, response, responseBody);
	}
}
