package com.slimpay.hapiclient.exception;

import com.slimpay.hapiclient.hal.Rel;
import com.slimpay.hapiclient.hal.Resource;

/**
 * Raised when trying to find an embedded Resource by its relation type
 * (rel) with the {@link Resource#getEmbeddedResource(Rel)}
 * method but the value is an array of embedded Resources.
 */
public class EmbeddedResourceNotUniqueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmbeddedResourceNotUniqueException() {
		super();
	}
}
