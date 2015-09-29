package com.slimpay.hapiclient.http.auth;

import org.apache.http.client.methods.HttpUriRequest;

import com.slimpay.hapiclient.exception.HttpException;
import com.slimpay.hapiclient.http.HapiClient;

public interface AuthenticationMethod {
	/**
	 * This is called right before sending the HTTP request.
	 * @param hapiClient	The client used to send the request.
	 * @param httpRequest	The HTTP request before it is sent.
	 * @throws HttpException
	 */
	public void authorizeRequest(final HapiClient hapiClient, final HttpUriRequest httpRequest) throws HttpException;
}
