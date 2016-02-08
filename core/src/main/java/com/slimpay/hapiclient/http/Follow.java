package com.slimpay.hapiclient.http;

import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.annotation.ThreadSafe;

import com.slimpay.hapiclient.exception.LinkNotUniqueException;
import com.slimpay.hapiclient.exception.RelNotFoundException;
import com.slimpay.hapiclient.hal.Rel;
import com.slimpay.hapiclient.hal.Resource;

/**
 * The configuration for a request to the given 
 * <strong>relation type</strong> (rel) with optional
 * values for URL variables, optional message body
 * and optional headers.
 */
@ThreadSafe
public final class Follow extends AbstractRequest {
	private final Rel rel;
	
	/**
	 * @see Builder#Builder(Rel)
	 */
	private Follow(Rel rel, Method method, Map<String, Object> urlVariables, HttpEntity messageBody, List<Header> headers) {
		super(method, urlVariables, messageBody, headers);
		this.rel = rel;
	}
	
	/**
	 * Looks for a unique Link referenced by the set
	 * relation type ({@link Rel}) and returns its href property.
	 * @param resource	The Resource containing a Link referenced
	 * 					by the set relation type (rel).
	 * @return	The URL in the <code>href</code> property of the Link.
	 * @throws LinkNotUniqueException if the Rel given points to an array of Links.
	 * @throws RelNotFoundException if the Rel is inexistant in the Resource.
	 */
	public String getUrl(Resource resource) {
		return resource.getLink(rel).getHref();
	}
	
	/**
	 * @return	The relation type (Rel).
	 */
	public Rel getRel() {
		return rel;
	}
	
	@NotThreadSafe
	public static class Builder extends AbstractRequest.Builder {
		private final Rel rel;
		
		/**
		 * @param rel	The relation type (rel).
		 */
		public Builder(Rel rel) {
			super();
			this.rel = rel;

			if (this.rel == null)
				throw new IllegalArgumentException("Rel is mandatory.");
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
		 * Instantiates the Follow object.
		 * @return	The instantiated Follow.
		 */
		@Override
		public Follow build() {
			return new Follow(rel, method, urlVariables, messageBody, headers);
		}

	}

}
