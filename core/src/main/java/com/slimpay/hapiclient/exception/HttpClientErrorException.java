package com.slimpay.hapiclient.exception;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * HTTP Status Code 4xx
 * @see http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4
 */
@NotThreadSafe
public class HttpClientErrorException extends HttpException {
	private static final long serialVersionUID = 1L;
	
	public HttpClientErrorException(HttpRequestBase request, CloseableHttpResponse response, String responseBody) {
		super(request, response, responseBody);
	}
}
