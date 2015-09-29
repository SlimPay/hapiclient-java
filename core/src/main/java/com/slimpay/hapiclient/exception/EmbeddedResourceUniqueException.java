package com.slimpay.hapiclient.exception;

import com.slimpay.hapiclient.hal.Rel;
import com.slimpay.hapiclient.hal.Resource;

/**
 * Raised when trying to find an embedded Resource by its relation type
 * (rel) with the {@link Resource#getEmbeddedResources(Rel)}
 * method but the value is a unique embedded Resource.
 */
public class EmbeddedResourceUniqueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmbeddedResourceUniqueException() {
		super();
	}
}
