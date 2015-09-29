package com.slimpay.hapiclient.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

abstract class AbstractRequest {
	protected final Method method;
	protected final Map<String, Object> urlVariables;
	protected final HttpEntity messageBody;
	protected final List<Header> headers;

	protected AbstractRequest(Method method, Map<String, Object> urlVariables, HttpEntity messageBody, List<Header> headers) {
		this.method = method;
		this.urlVariables = unmodifiableUrlVariables(urlVariables);
		this.messageBody = messageBody;
		this.headers = Collections.unmodifiableList(headers);
	}
	
	/**
	 * @param urlVariables
	 * @return	A Read-Only Map of Read-Only values.
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, Object> unmodifiableUrlVariables(Map<String, Object> urlVariables) {
		for (String key : urlVariables.keySet()) {
			Object value = urlVariables.get(key);
			if (value instanceof Map)
				value = Collections.unmodifiableMap((Map<Object, Object>) value);
			else if (value instanceof List)
				value = Collections.unmodifiableList((List<Object>) value);
			else if (value.getClass().isArray()) {
				if (value instanceof Object[])
					value = Collections.unmodifiableList(new ArrayList<Object>(Arrays.asList(value)));
				else
					value = unmodifiablePrimitiveArray(value);
			}
			
			urlVariables.put(key, value);
		}
		
		return Collections.unmodifiableMap(urlVariables);
	}
	
	/**
	 * Takes an array of primitives (int, double, char, float, boolean, short, long, byte),
	 * puts its content in a List and returns a Read-Only List.
	 * <p>
	 * For example, int[] will return List&lt;Integer&gt;.
	 * @param array		An array of primitives
	 * @return	A Read-Only List of the Object equivalent of the given primitives.
	 */
	private static List<Object> unmodifiablePrimitiveArray(Object array) {
		List<Object> list;
		if (array instanceof int[]) {
			list = new ArrayList<Object>(((int[]) array).length);
			for (int val : (int[]) array)
				list.add(Integer.valueOf(val));
		} else if (array instanceof double[]) {
			list = new ArrayList<Object>(((double[]) array).length);
			for (double val : (double[]) array)
				list.add(Double.valueOf(val));
		} else if (array instanceof char[]) {
			list = new ArrayList<Object>(((char[]) array).length);
			for (char val : (char[]) array)
				list.add(Character.valueOf(val));
		} else if (array instanceof float[]) {
			list = new ArrayList<Object>(((float[]) array).length);
			for (float val : (float[]) array)
				list.add(Float.valueOf(val));
		} else if (array instanceof boolean[]) {
			list = new ArrayList<Object>(((boolean[]) array).length);
			for (boolean val : (boolean[]) array)
				list.add(Boolean.valueOf(val));
		} else if (array instanceof short[]) {
			list = new ArrayList<Object>(((short[]) array).length);
			for (short val : (short[]) array)
				list.add(Short.valueOf(val));
		} else if (array instanceof long[]) {
			list = new ArrayList<Object>(((long[]) array).length);
			for (long val : (long[]) array)
				list.add(Long.valueOf(val));
		} else if (array instanceof byte[]) {
			list = new ArrayList<Object>(((byte[]) array).length);
			for (byte val : (byte[]) array)
				list.add(Byte.valueOf(val));
		} else
			throw new IllegalArgumentException("The 'array' given is not an array of primitives.");
		
		return Collections.unmodifiableList(list);
	}

	/**
	 * @return	The HTTP Method.
	 */
	public Method getMethod() {
		return method;
	}
	
	/**
	 * @return	The value of the URL variables
	 * 			contained in the URL template (Read-Only).
	 */
	public Map<String, Object> getUrlVariables() {
		return urlVariables;
	}
	
	/**
	 * @return	The message body to send.
	 */
	public HttpEntity getMessageBody() {
		return messageBody;
	}
	
	/**
	 * @return	The optional headers (Read-Only).
	 */
	public List<Header> getHeaders() {
		return headers;
	}
	
	static abstract class Builder {
		protected Method method;
		protected final Map<String, Object> urlVariables;
		protected HttpEntity messageBody;
		protected final List<Header> headers;
		
		protected Builder() {
			super();
			this.method = Method.GET;
			this.urlVariables = new HashMap<String, Object>();
			this.headers = new ArrayList<Header>();
		}

		/**
		 * Overrides the default Method (GET).
		 * @param method	The HTTP Method for the request.
		 * @return	The builder.
		 */
		public Builder setMethod(Method method) {
			this.method = method;
			return this;
		}

		/**
		 * Sets the value of multiple URL variables.
		 * @param urlVariables	The URL variables to add.
		 * @return	The builder.
		 * @see	#setUrlVariable(String, Object) for more details.
		 */
		public Builder setUrlVariables(Map<String, Object> urlVariables) {
			if (urlVariables != null && !urlVariables.isEmpty())
				this.urlVariables.putAll(urlVariables);
			
			return this;
		}

		/**
		 * Sets the value of a URL variable.<br>
		 * The URL must be <a href="http://tools.ietf.org/html/rfc6570" title="RFC6570 URI Templates">templated</a>
		 * for the value to be used.
		 * 
		 * <h2>Supported Value Types</h2>
		 * <ul>
		 * <li>primitive and Object types such as:
		 * 	<ul>
		 * 		<li>int & Integer</li>
		 * 		<li>double & Double</li>
		 * 		<li>char & Character</li>
		 * 		<li>float & Float</li>
		 * 		<li>boolean & Boolean</li>
		 * 		<li>short & Short</li>
		 * 		<li>long & Long</li>
		 * 	</ul>
		 * </li>
		 * <li>
		 * 	Arrays of the above types.<br>
		 * 	Arrays will be converted to Read-Only Lists.
		 * </li>
		 * <li>java.util.List<br>Lists will be changed to be Read-Only.</li>
		 * <li>java.util.Map<br>Maps will be changed to be Read-Only.</li>
		 * <li>java.util.Date.<br>Dates will be formatted using the templates default formatter.</li>
		 * <li>
		 * 		Anything with a toString() method<br>
		 * 		Values that are not strings are rendered into the URI by calling its toString() method.
		 * </li>
		 * </ul>
		 * @see {@link https://github.com/damnhandy/Handy-URI-Templates}
		 * @param variableName	The name of the URL variable.
		 * @param value			The value of the URL variable.
		 * @return	The builder.
		 */
		public Builder setUrlVariable(String variableName, Object value) {
			this.urlVariables.put(variableName, value);
			return this;
		}

		/**
		 * Adds a message body to the request.
		 * @param messageBody	The message body to send.
		 * @return	The builder.
		 */
		public Builder setMessageBody(HttpEntity messageBody) {
			this.messageBody = messageBody;
			return this;
		}

		/**
		 * Adds a message body to the request.
		 * @param messageBody	The message body to send.
		 * @return	The builder.
		 */
		public Builder setMessageBody(MessageBody messageBody) {
			this.messageBody = messageBody.getHttpEntity();
			return this;
		}

		/**
		 * Optional headers to send with the request.
		 * Note that the following headers will implicitly
		 * being taken care of by the client:
		 * <ul>
		 * <li>Content-Type (if a MessageBody is set)</li>
		 * <li>Content-Length (if a MessageBody is set)</li>
		 * <li>Authorization (by the AuthenticationMethod)</li>
		 * </ul>
		 * @param headers	The optional headers.
		 * @return	The builder.
		 */
		public Builder addHeader(Header header) {
			headers.add(header);
			return this;
		}
		
		/**
		 * Adds multiple optional headers.
		 * @return	The builder.
		 * @see #addHeader(Header)
		 */
		public Builder addHeaders(List<Header> headers) {
			if (headers != null)
				this.headers.addAll(headers);
			
			return this;
		}
		
		public abstract AbstractRequest build();

	}
}
