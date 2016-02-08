package com.slimpay.hapiclient.hal;

import java.io.Serializable;

/**
 * The Relation Type described in:
 * <ul>
 * <li>section 8.2 of the HAL specification</li>
 * <li>section 4 of the RFC5988 - Web Linking document</li>
 * </ul>
 * Can be:
 * <ul>
 * <li>{@link RegisteredRel Registered Relation Type}</li>
 * <li>{@link CustomRel Extension Relation Type}</li>
 * </ul>
 * @see <a href="https://tools.ietf.org/html/draft-kelly-json-hal-07#section-8.2">The HAL Specification Section 8.2</a>
 * @see <a href="https://tools.ietf.org/html/rfc5988#section-4">RFC 5988 Section 4</a>
 */
public interface Rel extends Serializable {
	
	/**
	 * @return The relation name.
	 */
	public String getName();
}
