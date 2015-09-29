package com.slimpay.hapiclient.exception;

import com.slimpay.hapiclient.hal.Rel;
import com.slimpay.hapiclient.hal.Resource;

/**
 * Raised when trying to find a Link by its relation type
 * (rel) with the {@link Resource#getLinks(Rel)}
 * method but the value is a unique Link.
 */
public class LinkUniqueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LinkUniqueException() {
		super();
	}
}
