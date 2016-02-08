package com.slimpay.hapiclient.hal;

import java.net.URI;
import java.util.Locale;

import org.apache.http.annotation.ThreadSafe;

/**
 * The Extension Relation Type described in:
 * <ul>
 * <li>section 8.2 of the HAL specification (as custom link relation types)</li>
 * <li>section 4 of the RFC5988 - Web Linking document</li>
 * </ul>
 * @see <a href="https://tools.ietf.org/html/draft-kelly-json-hal-07#section-8.2">The HAL Specification Section 8.2</a>
 * @see <a href="https://tools.ietf.org/html/rfc5988#section-4">RFC 5988 Section 4</a>
 */
@ThreadSafe
public final class CustomRel implements Rel {
	private static final long serialVersionUID = 1L;
	
	private final String name;
	
	/**
	 * An Extension Relation Type.
	 * @param uri The Relation Name
	 * @see <a href="https://tools.ietf.org/html/rfc5988#section-4.2">RFC 5988 Section 4.2</a>
	 */
	public CustomRel(URI uri) {
		String name = uri.toString().trim();
		if (name.isEmpty())
			throw new IllegalArgumentException("The uri is mandatory and can't be empty");
			
		this.name = name;
	}
	
	/**
	 * An Extension Relation Type SHOULD be an URI.
	 * <em>SHOULD</em>... so we allow a regular string.
	 * @param name The Relation Name
	 * @see <a href="https://tools.ietf.org/html/rfc5988#section-4.2">RFC 5988 Section 4.2</a>
	 */
	public CustomRel(String name) {
		if (name.trim().isEmpty())
			throw new IllegalArgumentException("The name is mandatory and can't be empty.");
		
		this.name = name.trim();
	}
	
	/**
	 * An Extension Relation Type using the CURIE syntax.
	 * <p>
	 * <strong>Note:</strong> the Resource's links SHOULD contain a
	 * curies property with a <strong>name</strong> equal to the
	 * <strong>prefix</strong> used as specified in the
	 * <a href="https://tools.ietf.org/html/draft-kelly-json-hal-07#section-8.2">section 8.2</a>
	 * of the HAL Specification.
	 * @param prefix		The prefix of the CURIE.
	 * @param reference		The reference of the CURIE.
	 * @see <a href="http://www.w3.org/TR/2009/CR-curie-20090116/">W3C CURIE Syntax 1.0</a>
	 */
	public CustomRel(String prefix, String reference) {
		if (prefix.trim().isEmpty())
			throw new IllegalArgumentException("The prefix is mandatory and can't be empty.");
		
		if (reference.trim().isEmpty())
			throw new IllegalArgumentException("The reference is mandatory and can't be empty.");
		
		this.name = prefix.trim() + ":" + reference.trim();
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.toLowerCase(Locale.ENGLISH).hashCode();
		return result;
	}

	/**
	 * <blockquote>When extension relation types are compared, they MUST be compared as
	 * strings [...] in a case-insensitive fashion.</blockquote>
	 * @see <a href="https://tools.ietf.org/html/rfc5988#section-4.2">RFC 5988 Section 4.2</a>
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		return name.toLowerCase(Locale.ENGLISH).equals(((CustomRel) obj).name.toLowerCase(Locale.ENGLISH));
	}

	@Override
	public String toString() {
		return new StringBuilder().append("CustomRel [name=").append(name).append("]").toString();
	}
}
