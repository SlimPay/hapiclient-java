package com.slimpay.hapiclient.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

public class EntityConverter {
	private static final String UTF8 = "UTF-8";
	private static final String APPLICATION_JSON = "application/json";

	/**
	 * Converts a Map to a UrlEncodedFormEntity.
	 * @param map	The Map to convert. Not {@code null}.
	 * @return UrlEncodedFormEntity
	 */
	public static UrlEncodedFormEntity mapToUrlEncodedFormEntity(Map<String, String> map) {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		for (String key : map.keySet())
			urlParameters.add(new BasicNameValuePair(key, map.get(key)));
		
		try {
			return new UrlEncodedFormEntity(urlParameters, CharsetUtils.get(UTF8));
		} catch (UnsupportedEncodingException ignored) {
			return null;
		}
	}
	
	/**
	 * Converts a JsonObject to a StringEntity with "application/json" as content type.
	 * @param jsonObject JsonObject to convert. Not {@code null}.
	 * @return StringEntity
	 */
	public static StringEntity jsonToStringEntity(JsonObject jsonObject) {
		return new StringEntity(jsonObject.toString(), ContentType.create(APPLICATION_JSON, UTF8));
	}
	
	/**
	 * Reads the content of an HttpEntity and returns it as a String.
	 * @param entity The HTTP Entity
	 * @return String or null if entity is null
	 * @throws IOException if an error occurs reading the input stream
	 * @throws ParseException if header elements cannot be parsed
	 */
	public static String entityToString(HttpEntity entity) throws ParseException, IOException {
		if (entity == null)
			return null;
		
		return EntityUtils.toString(entity, UTF8);
	}

}
