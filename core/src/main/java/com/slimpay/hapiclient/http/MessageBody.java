package com.slimpay.hapiclient.http;

import org.apache.http.HttpEntity;

public interface MessageBody {
	/**
	 * @return	The HttpEntity object containing both the content
	 * 			and the content type of the request data.
	 */
	public HttpEntity getHttpEntity();
}
