package com.slimpay.hapiclient.http;

import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * The configuration for a request to the given 
 * <strong>URL</strong> with optional values
 * for URL variables, optional message body
 * and optional headers.
 */
public final class Request extends AbstractRequest {
	private final String url;

	/**
	 * @see Builder#Builder(String)
	 */
	private Request(String url, Method method, Map<String, Object> urlVariables, HttpEntity messageBody, List<Header> headers) {
		super(method, urlVariables, messageBody, headers);
		this.url = url;
	}
	
	/**
	 * @return	The URL.
	 */
	public String getUrl() {
		return url;
	}
	
	public static class Builder extends AbstractRequest.Builder {
		private final String url;
		
		/**
		 * @param url	The URL.
		 */
		public Builder(String url) {
			super();
			
			url = url.trim();
			if (url.isEmpty())
				throw new IllegalArgumentException("URL is mandatory.");
			
			this.url = url;
		}
		
		@Override
		public Builder setMethod(Method method) {
			super.setMethod(method);
			return this;
		}

		@Override
		public Builder setUrlVariables(Map<String, Object> urlVariables) {
			super.setUrlVariables(urlVariables);
			return this;
		}

		@Override
		public Builder setUrlVariable(String variableName, Object value) {
			super.setUrlVariable(variableName, value);
			return this;
		}

		@Override
		public Builder setMessageBody(HttpEntity messageBody) {
			super.setMessageBody(messageBody);
			return this;
		}

		@Override
		public Builder setMessageBody(MessageBody messageBody) {
			super.setMessageBody(messageBody);
			return this;
		}

		@Override
		public Builder addHeader(Header header) {
			super.addHeader(header);
			return this;
		}

		@Override
		public Builder addHeaders(List<Header> headers) {
			super.addHeaders(headers);
			return this;
		}
		
		/**
		 * Instantiates the Request object.
		 * @return	The instantiated Request.
		 */
		@Override
		public Request build() {
			return new Request(url, method, urlVariables, messageBody, headers);
		}

	}

}
