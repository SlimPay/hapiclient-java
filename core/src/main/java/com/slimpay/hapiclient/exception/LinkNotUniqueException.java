package com.slimpay.hapiclient.exception;

import com.slimpay.hapiclient.hal.Rel;
import com.slimpay.hapiclient.hal.Resource;

/**
 * Raised when trying to find a Link by its relation type
 * (rel) with the {@link Resource#getLink(Rel)}
 * method but the value is an array of Links.
 */
public class LinkNotUniqueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LinkNotUniqueException() {
		super();
	}
}
