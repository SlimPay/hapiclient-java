package com.slimpay.hapiclient.exception;

/**
 * Raised when the HTTP response message body can't be parsed.
 */
public class UnparsableResponseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UnparsableResponseException(String message) {
		super(message);
	}

	public UnparsableResponseException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
