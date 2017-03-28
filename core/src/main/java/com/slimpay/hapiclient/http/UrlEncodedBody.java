package com.slimpay.hapiclient.http;

import java.util.HashMap;

import org.apache.http.HttpEntity;

import com.slimpay.hapiclient.util.EntityConverter;

/**
 * A HashMap representing a URL encoded message body.
 */
public final class UrlEncodedBody extends HashMap<String, String> implements MessageBody {
	private static final long serialVersionUID = 1L;

	public HttpEntity getHttpEntity() {
		return EntityConverter.mapToUrlEncodedFormEntity(this);
	}

}
