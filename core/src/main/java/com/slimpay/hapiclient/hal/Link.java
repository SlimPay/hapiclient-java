package com.slimpay.hapiclient.hal;

import javax.json.JsonObject;

import org.apache.http.annotation.ThreadSafe;


/**
 * The Link Object described in the
 * JSON Hypertext Application Language (draft-kelly-json-hal-07)
 * @see <a href="https://tools.ietf.org/html/draft-kelly-json-hal-07#section-5">The HAL Specification Section 5</a>
 */
@ThreadSafe
public final class Link {
	private final String href;
	private final Boolean templated;
	private final String type;
	private final String deprecation;
	private final String name;
	private final String profile;
	private final String title;
	private final String hreflang;
	
	Link(String href, Boolean templated, String type, String deprecation,
					String name, String profile, String title, String hreflang) {
		super();
		this.href = href;
		this.templated = templated;
		this.type = type;
		this.deprecation = deprecation;
		this.name = name;
		this.profile = profile;
		this.title = title;
		this.hreflang = hreflang;
		
		if (href.isEmpty())
			throw new IllegalArgumentException("The href property is mandatory.");
	}

	/**
	 * REQUIRED<br>
	 * Its value is either a URI [RFC3986] or a URI Template [RFC6570].<br>
	 * If the value is a URI Template then the Link Object SHOULD have a
	 * "templated" attribute whose value is true.
	 * @return String
	 */
	public String getHref() {
		return href;
	}

	/**
	 * OPTIONAL<br>
	 * Its value is boolean and SHOULD be true when the Link Object's "href"
	 * property is a URI Template.<br>
	 * Its value SHOULD be considered false if it is undefined or any other
	 * value than true.
	 * @return boolean
	 */
	public boolean isTemplated() {
		return templated != null ? templated.booleanValue() : false;
	}

	/**
	 * OPTIONAL<br>
	 * Its value is a string used as a hint to indicate the media type
	 * expected when dereferencing the target resource.
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * OPTIONAL<br>
	 * Its presence indicates that the link is to be deprecated (i.e.
	 * removed) at a future date.  Its value is a URL that SHOULD provide
	 * further information about the deprecation.<br>
	 * A client SHOULD provide some notification (for example, by logging a
	 * warning message) whenever it traverses over a link that has this
	 * property.  The notification SHOULD include the deprecation property's
	 * value so that a client maintainer can easily find information about
	 * the deprecation.
	 * @return String
	 */
	public String getDeprecation() {
		return deprecation;
	}

	/**
	 * OPTIONAL<br>
	 * Its value MAY be used as a secondary key for selecting Link Objects
	 * which share the same relation type.
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * OPTIONAL<br>
	 * Its value is a string which is a URI that hints about the profile (as
	 * defined by [I-D.wilde-profile-link]) of the target resource.
	 * @return String
	 */
	public String getProfile() {
		return profile;
	}

	/**
	 * OPTIONAL<br>
	 * Its value is a string and is intended for labelling the link with a
	 * human-readable identifier (as defined by [RFC5988]).
	 * @return String
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * OPTIONAL<br>
	 * Its value is a string and is intended for indicating the language of
	 * the target resource (as defined by [RFC5988]).
	 * @return String
	 */
	public String getHreflang() {
		return hreflang;
	}

	/**
	 * Constructor from a Map of properties (JsonObject).
	 * Keys that are not a valid property are ignored.
	 * @param json
	 * @return	Link
	 */
	static Link fromJson(JsonObject json) {
		String href = null;
		if (json.containsKey("href"))
			href = json.getString("href");

		Boolean templated = null;
		if (json.containsKey("templated"))
			templated = json.getBoolean("templated");

		String type = null;
		if (json.containsKey("type"))
			type = json.getString("type");

		String deprecation = null;
		if (json.containsKey("deprecation"))
			deprecation = json.getString("deprecation");

		String name = null;
		if (json.containsKey("name"))
			name = json.getString("name");

		String profile = null;
		if (json.containsKey("profile"))
			profile = json.getString("profile");

		String title = null;
		if (json.containsKey("title"))
			title = json.getString("title");

		String hreflang = null;
		if (json.containsKey("hreflang"))
			hreflang = json.getString("hreflang");
		
		return new Link(href, templated, type, deprecation, name, profile, title, hreflang);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + ((deprecation == null) ? 0 : deprecation.hashCode());
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((hreflang == null) ? 0 : hreflang.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime * result + ((templated == null) ? 0 : templated.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		
		return result;
	}

	/**
	 * Compares all the properties of the link.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Link other = (Link) obj;
		if (deprecation == null) {
			if (other.deprecation != null)
				return false;
		} else if (!deprecation.equals(other.deprecation))
			return false;
		
		if (!href.equals(other.href))
			return false;
		
		if (hreflang == null) {
			if (other.hreflang != null)
				return false;
		} else if (!hreflang.equals(other.hreflang))
			return false;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		
		if (templated == null) {
			if (other.templated != null)
				return false;
		} else if (!templated.equals(other.templated))
			return false;
		
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder().append("Link [href=").append(href);
		
		if (templated != null)		sb.append(", templated=").append(templated);
		if (type != null)			sb.append(", type=").append(type);
		if (deprecation != null)	sb.append(", deprecation").append(deprecation);
		if (name != null)			sb.append(", name=").append(name);
		if (profile != null)		sb.append(", profile=").append(profile);
		if (title != null)			sb.append(", title=").append(title);
		if (hreflang != null)		sb.append(", hreflang=").append(hreflang);
		
		return sb.append("]").toString();
	}

}
