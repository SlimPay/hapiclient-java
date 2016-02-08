package com.slimpay.hapiclient.exception;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * HTTP Status Code 5xx
 * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5">10.5 Server Error 5xx</a>
 */
@NotThreadSafe
public class HttpServerErrorException extends HttpException {
	private static final long serialVersionUID = 1L;
	
	public HttpServerErrorException(HttpRequestBase request, CloseableHttpResponse response, String responseBody) {
		super(request, response, responseBody);
	}
}
