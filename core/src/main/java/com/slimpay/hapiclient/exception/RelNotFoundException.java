package com.slimpay.hapiclient.exception;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.apache.http.annotation.ThreadSafe;

import com.slimpay.hapiclient.hal.Rel;

/**
 * Raised when trying to get a link or an embedded
 * resource by a non-existing relation type ({@link Rel}).
 */
@ThreadSafe
public class RelNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final Rel missingRel;
	private final Set<Rel> availableRels;

	/**
	 * @param missingRel		The missing relation type.
	 * @param availableRels		The list of available relation types.
	 */
	public RelNotFoundException(Rel missingRel, Set<Rel> availableRels) {
		super("Rel not found: " + missingRel + ". Relation types available: " + Arrays.toString(availableRels.toArray(new Rel[0])) + ".");
		this.missingRel = missingRel;
		this.availableRels = Collections.unmodifiableSet(availableRels);
	}
	
	/**
	 * @return	The missing relation type (Rel).
	 */
	public Rel getMissingRel() {
		return missingRel;
	}

	/**
	 * Returns the list of available relation types (Rel) available in the
	 * _links or _embedded property the given Rel was missing in.
	 * @return A Read-Only Set&lt;Rel&gt;
	 */
	public Set<Rel> getAvailableRels() {
		return availableRels;
	}
}
