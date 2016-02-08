package com.slimpay.hapiclient.http;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.annotation.ThreadSafe;

import com.slimpay.hapiclient.util.EntityConverter;

@ThreadSafe
public final class JsonBody implements MessageBody {
	private final JsonObject jsonObject;
	
	/**
	 * A JsonObject is immutable. So is a JsonBody.
	 * You can build a JsonObject by using a builder:
	 * <p>
	 * <code>Json.createObjectBuilder()</code>
	 * @param jsonObject The JsonObject as Message Body.
	 */
	public JsonBody(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	/**
	 * JsonBody using a JsonObjectBuilder. The JsonObject
	 * is built right when calling this constructor.
	 * Any modification to the JsonObjectBuilder won't
	 * affect the JsonBody.
	 * @param jsonObjectBuilder The Builder from which the JsonObject
	 * 							will be extracted.
	 */
	public JsonBody(JsonObjectBuilder jsonObjectBuilder) {
		this.jsonObject = jsonObjectBuilder.build();
	}
	
	/**
	 * @return	JsonObject
	 */
	public JsonObject getJsonObject() {
		return jsonObject;
	}

	public HttpEntity getHttpEntity() {
		return EntityConverter.jsonToStringEntity(jsonObject);
	}

}
