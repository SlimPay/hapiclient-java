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
 * @see https://tools.ietf.org/html/draft-kelly-json-hal-07#section-8.2
 * @see https://tools.ietf.org/html/rfc5988#section-4
 */
public interface Rel extends Serializable {
	
	/**
	 * @return The relation name.
	 */
	public String getName();
}
