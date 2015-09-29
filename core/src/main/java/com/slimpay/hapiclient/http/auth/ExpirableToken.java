package com.slimpay.hapiclient.http.auth;

import org.apache.http.annotation.ThreadSafe;

@ThreadSafe
public final class ExpirableToken {
	private final String value;
	private final Long expirationTime;
	
	/**
	 * @param value				The token value
	 * @param expirationTime	The token expiration timestamp
	 */
	public ExpirableToken(String value, Long expirationTime) {
		this.value = value;
		this.expirationTime = expirationTime;
	}

	/**
	 * @return	The token value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return	The token expiration timestamp
	 */
	public Long getExpirationTime() {
		return expirationTime;
	}
	
	/**
	 * Checks if the token is still valid until the given time limit.
	 * @param timeLimit		The timestamp representation of the limit
	 * @return boolean
	 */
	public boolean isValidUntil(Long timeLimit) {
		return  value != null && !value.isEmpty() &&
				expirationTime != null && expirationTime > timeLimit;
	}
}
