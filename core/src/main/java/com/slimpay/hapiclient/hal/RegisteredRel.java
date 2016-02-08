package com.slimpay.hapiclient.hal;

/**
 * The link relation types registered by the IANA Registry.
 * <p>
 * <strong>Last updated:</strong> 2015-01-21
 * @see <a href="http://www.iana.org/assignments/link-relations/link-relations.xhtml">IANA Link Relation Types</a>
 */
public enum RegisteredRel implements Rel {
	/**
	 * Not part of the IANA Registry but a reserved
	 * relation type in the HAL Specification for
	 * the CURIE syntax.
	 */
	CURIES("curies"),
	
	// To update from the Link Relation Types CSV file available in the IANA Registry:
	// 0) check that the return line format is UNIX (\n)
	// 1) remove the first line
	// 2) find all: (([^,-]+)(?:-([^,-]+))?(?:-([^,-]+))?(?:-([^,-]+))?),(?:"((?:""|[^"])+)"|([^,]+))?,(?:"((?:""|[^"])+)"|([^,]+))?,(?:"((?:""|[^"])+)"|([^,]+))?\n
	// 3) replace by: /**\n * <ul>\n * <li>Relation Name: <strong>$1</strong></li>\n * <li>Description: $6$7</li>\n * <li>Reference: $8$9</li>\n * <li>Notes: $10$11</li>\n * </ul>\n */\n\U$2$3$4$5\E\("$1"\),\n\n
	// The masks:
	// - 1:			The whole relation name
	// - 2 to 5:	Potentially the 4 parts of the relation name without the dashes
	// - 6 or 7:	The Description
	// - 8 or 9:	The Reference
	// - 10 or 11:	The Notes
	// Then replace the last comma (,) by a semicolon (;).
	// Tested in Notepad++.
	
	/**
	 * <ul>
	 * <li>Relation Name: <strong>about</strong></li>
	 * <li>Description: Refers to a resource that is the subject of the link's context.</li>
	 * <li>Reference: [RFC6903], section 2</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	ABOUT("about"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>alternate</strong></li>
	 * <li>Description: Refers to a substitute for this context</li>
	 * <li>Reference: [http://www.w3.org/TR/html5/links.html#link-type-alternate]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	ALTERNATE("alternate"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>appendix</strong></li>
	 * <li>Description: Refers to an appendix.</li>
	 * <li>Reference: [http://www.w3.org/TR/1999/REC-html401-19991224]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	APPENDIX("appendix"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>archives</strong></li>
	 * <li>Description: Refers to a collection of records, documents, or other
	      materials of historical interest.</li>
	 * <li>Reference: [http://www.w3.org/TR/2011/WD-html5-20110113/links.html#rel-archives]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	ARCHIVES("archives"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>author</strong></li>
	 * <li>Description: Refers to the context's author.</li>
	 * <li>Reference: [http://www.w3.org/TR/html5/links.html#link-type-author]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	AUTHOR("author"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>bookmark</strong></li>
	 * <li>Description: Gives a permanent link to use for bookmarking purposes.</li>
	 * <li>Reference: [http://www.w3.org/TR/html5/links.html#link-type-bookmark]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	BOOKMARK("bookmark"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>canonical</strong></li>
	 * <li>Description: Designates the preferred version of a resource (the IRI and its contents).</li>
	 * <li>Reference: [RFC6596]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	CANONICAL("canonical"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>chapter</strong></li>
	 * <li>Description: Refers to a chapter in a collection of resources.</li>
	 * <li>Reference: [http://www.w3.org/TR/1999/REC-html401-19991224]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	CHAPTER("chapter"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>collection</strong></li>
	 * <li>Description: The target IRI points to a resource which represents the collection resource for the context IRI.</li>
	 * <li>Reference: [RFC6573]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	COLLECTION("collection"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>contents</strong></li>
	 * <li>Description: Refers to a table of contents.</li>
	 * <li>Reference: [http://www.w3.org/TR/1999/REC-html401-19991224]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	CONTENTS("contents"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>copyright</strong></li>
	 * <li>Description: Refers to a copyright statement that applies to the
	    link's context.</li>
	 * <li>Reference: [http://www.w3.org/TR/1999/REC-html401-19991224]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	COPYRIGHT("copyright"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>create-form</strong></li>
	 * <li>Description: The target IRI points to a resource where a submission form can be obtained.</li>
	 * <li>Reference: [RFC6861]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	CREATEFORM("create-form"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>current</strong></li>
	 * <li>Description: Refers to a resource containing the most recent
	      item(s) in a collection of resources.</li>
	 * <li>Reference: [RFC5005]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	CURRENT("current"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>derivedfrom</strong></li>
	 * <li>Description: The target IRI points to a resource from which this material was derived.</li>
	 * <li>Reference: [draft-hoffman-xml2rfc]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	DERIVEDFROM("derivedfrom"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>describedby</strong></li>
	 * <li>Description: Refers to a resource providing information about the
	      link's context.</li>
	 * <li>Reference: [http://www.w3.org/TR/powder-dr/#assoc-linking]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	DESCRIBEDBY("describedby"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>describes</strong></li>
	 * <li>Description: The relationship A 'describes' B asserts that
	      resource A provides a description of resource B. There are no
	      constraints on the format or representation of either A or B,
	      neither are there any further constraints on either resource.</li>
	 * <li>Reference: [RFC6892]</li>
	 * <li>Notes: This link relation type is the inverse of the 'describedby'
	        relation type.  While 'describedby' establishes a relation from
	        the described resource back to the resource that describes it,
	        'describes' established a relation from the describing resource to
	        the resource it describes.  If B is 'describedby' A, then A
	        'describes' B.</li>
	 * </ul>
	 */
	DESCRIBES("describes"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>disclosure</strong></li>
	 * <li>Description: Refers to a list of patent disclosures made with respect to material for which 'disclosure' relation is specified.</li>
	 * <li>Reference: [RFC6579]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	DISCLOSURE("disclosure"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>duplicate</strong></li>
	 * <li>Description: Refers to a resource whose available representations
	      are byte-for-byte identical with the corresponding representations of
	      the context IRI.</li>
	 * <li>Reference: [RFC6249]</li>
	 * <li>Notes: This relation is for static resources.  That is, an HTTP GET
	      request on any duplicate will return the same representation.  It
	      does not make sense for dynamic or POSTable resources and should not
	      be used for them.</li>
	 * </ul>
	 */
	DUPLICATE("duplicate"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>edit</strong></li>
	 * <li>Description: Refers to a resource that can be used to edit the
	      link's context.</li>
	 * <li>Reference: [RFC5023]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	EDIT("edit"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>edit-form</strong></li>
	 * <li>Description: The target IRI points to a resource where a submission form for
	      editing associated resource can be obtained.</li>
	 * <li>Reference: [RFC6861]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	EDITFORM("edit-form"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>edit-media</strong></li>
	 * <li>Description: Refers to a resource that can be used to edit media
	      associated with the link's context.</li>
	 * <li>Reference: [RFC5023]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	EDITMEDIA("edit-media"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>enclosure</strong></li>
	 * <li>Description: Identifies a related resource that is potentially
	      large and might require special handling.</li>
	 * <li>Reference: [RFC4287]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	ENCLOSURE("enclosure"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>first</strong></li>
	 * <li>Description: An IRI that refers to the furthest preceding resource
	    in a series of resources.</li>
	 * <li>Reference: [RFC5988]</li>
	 * <li>Notes: This relation type registration did not indicate a
	      reference.  Originally requested by Mark Nottingham in December
	      2004.</li>
	 * </ul>
	 */
	FIRST("first"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>glossary</strong></li>
	 * <li>Description: Refers to a glossary of terms.</li>
	 * <li>Reference: [http://www.w3.org/TR/1999/REC-html401-19991224]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	GLOSSARY("glossary"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>help</strong></li>
	 * <li>Description: Refers to context-sensitive help.</li>
	 * <li>Reference: [http://www.w3.org/TR/html5/links.html#link-type-help]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	HELP("help"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>hosts</strong></li>
	 * <li>Description: Refers to a resource hosted by the server indicated by
	      the link context.</li>
	 * <li>Reference: [RFC6690]</li>
	 * <li>Notes: This relation is used in CoRE where links are retrieved as a
	      ""/.well-known/core"" resource representation, and is the default
	      relation type in the CoRE Link Format.</li>
	 * </ul>
	 */
	HOSTS("hosts"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>hub</strong></li>
	 * <li>Description: Refers to a hub that enables registration for
	    notification of updates to the context.</li>
	 * <li>Reference: [http://pubsubhubbub.googlecode.com]</li>
	 * <li>Notes: This relation type was requested by Brett Slatkin.</li>
	 * </ul>
	 */
	HUB("hub"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>icon</strong></li>
	 * <li>Description: Refers to an icon representing the link's context.</li>
	 * <li>Reference: [http://www.w3.org/TR/html5/links.html#link-type-icon]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	ICON("icon"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>index</strong></li>
	 * <li>Description: Refers to an index.</li>
	 * <li>Reference: [http://www.w3.org/TR/1999/REC-html401-19991224]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	INDEX("index"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>item</strong></li>
	 * <li>Description: The target IRI points to a resource that is a member of the collection represented by the context IRI.</li>
	 * <li>Reference: [RFC6573]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	ITEM("item"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>last</strong></li>
	 * <li>Description: An IRI that refers to the furthest following resource
	      in a series of resources.</li>
	 * <li>Reference: [RFC5988]</li>
	 * <li>Notes: This relation type registration did not indicate a
	      reference. Originally requested by Mark Nottingham in December
	      2004.</li>
	 * </ul>
	 */
	LAST("last"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>latest-version</strong></li>
	 * <li>Description: Points to a resource containing the latest (e.g.,
	      current) version of the context.</li>
	 * <li>Reference: [RFC5829]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	LATESTVERSION("latest-version"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>license</strong></li>
	 * <li>Description: Refers to a license associated with this context.</li>
	 * <li>Reference: [RFC4946]</li>
	 * <li>Notes: For implications of use in HTML, see: 
	      http://www.w3.org/TR/html5/links.html#link-type-license</li>
	 * </ul>
	 */
	LICENSE("license"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>lrdd</strong></li>
	 * <li>Description: Refers to further information about the link's context,
	      expressed as a LRDD (""Link-based Resource Descriptor Document"")
	      resource.  See [RFC6415] for information about
	      processing this relation type in host-meta documents. When used
	      elsewhere, it refers to additional links and other metadata.
	      Multiple instances indicate additional LRDD resources. LRDD
	      resources MUST have an ""application/xrd+xml"" representation, and
	      MAY have others.</li>
	 * <li>Reference: [RFC6415]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	LRDD("lrdd"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>memento</strong></li>
	 * <li>Description: The Target IRI points to a Memento, a fixed resource that will not change state anymore.</li>
	 * <li>Reference: [RFC7089]</li>
	 * <li>Notes: A Memento for an Original Resource is a resource that
	      encapsulates a prior state of the Original Resource.</li>
	 * </ul>
	 */
	MEMENTO("memento"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>monitor</strong></li>
	 * <li>Description: Refers to a resource that can be used to monitor changes in an HTTP resource.</li>
	 * <li>Reference: [RFC5989]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	MONITOR("monitor"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>monitor-group</strong></li>
	 * <li>Description: Refers to a resource that can be used to monitor changes in a specified group of HTTP resources.</li>
	 * <li>Reference: [RFC5989]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	MONITORGROUP("monitor-group"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>next</strong></li>
	 * <li>Description: Indicates that the link's context is a part of a series, and
	      that the next in the series is the link target.</li>
	 * <li>Reference: [http://www.w3.org/TR/html5/links.html#link-type-next]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	NEXT("next"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>next-archive</strong></li>
	 * <li>Description: Refers to the immediately following archive resource.</li>
	 * <li>Reference: [RFC5005]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	NEXTARCHIVE("next-archive"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>nofollow</strong></li>
	 * <li>Description: Indicates that the context’s original author or publisher does not endorse the link target.</li>
	 * <li>Reference: [http://www.w3.org/TR/html5/links.html#link-type-nofollow]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	NOFOLLOW("nofollow"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>noreferrer</strong></li>
	 * <li>Description: Indicates that no referrer information is to be leaked when following the link.</li>
	 * <li>Reference: [http://www.w3.org/TR/html5/links.html#link-type-noreferrer]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	NOREFERRER("noreferrer"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>original</strong></li>
	 * <li>Description: The Target IRI points to an Original Resource.</li>
	 * <li>Reference: [RFC7089]</li>
	 * <li>Notes: An Original Resource is a resource that exists or used to
	      exist, and for which access to one of its prior states may be
	      required.</li>
	 * </ul>
	 */
	ORIGINAL("original"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>payment</strong></li>
	 * <li>Description: Indicates a resource where payment is accepted.</li>
	 * <li>Reference: [RFC5988]</li>
	 * <li>Notes: This relation type registration did not indicate a
	      reference.  Requested by Joshua Kinberg and Robert Sayre.  It is
	      meant as a general way to facilitate acts of payment, and thus
	      this specification makes no assumptions on the type of payment or
	      transaction protocol.  Examples may include a web page where
	      donations are accepted or where goods and services are available
	      for purchase. rel=""payment"" is not intended to initiate an
	      automated transaction.  In Atom documents, a link element with a
	      rel=""payment"" attribute may exist at the feed/channel level and/or
	      the entry/item level.  For example, a rel=""payment"" link at the
	      feed/channel level may point to a ""tip jar"" URI, whereas an entry/
	      item containing a book review may include a rel=""payment"" link
	      that points to the location where the book may be purchased
	      through an online retailer.</li>
	 * </ul>
	 */
	PAYMENT("payment"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>predecessor-version</strong></li>
	 * <li>Description: Points to a resource containing the predecessor
	      version in the version history.</li>
	 * <li>Reference: [RFC5829]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	PREDECESSORVERSION("predecessor-version"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>prefetch</strong></li>
	 * <li>Description: Indicates that the link target should be preemptively cached.</li>
	 * <li>Reference: [http://www.w3.org/TR/html5/links.html#link-type-prefetch]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	PREFETCH("prefetch"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>prev</strong></li>
	 * <li>Description: Indicates that the link's context is a part of a series, and
	      that the previous in the series is the link target.</li>
	 * <li>Reference: [http://www.w3.org/TR/html5/links.html#link-type-prev]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	PREV("prev"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>preview</strong></li>
	 * <li>Description: Refers to a resource that provides a preview of the link's context.</li>
	 * <li>Reference: [RFC6903], section 3</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	PREVIEW("preview"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>previous</strong></li>
	 * <li>Description: Refers to the previous resource in an ordered series
	      of resources.  Synonym for ""prev"".</li>
	 * <li>Reference: [http://www.w3.org/TR/1999/REC-html401-19991224]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	PREVIOUS("previous"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>prev-archive</strong></li>
	 * <li>Description: Refers to the immediately preceding archive resource.</li>
	 * <li>Reference: [RFC5005]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	PREVARCHIVE("prev-archive"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>privacy-policy</strong></li>
	 * <li>Description: Refers to a privacy policy associated with the link's context.</li>
	 * <li>Reference: [RFC6903], section 4</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	PRIVACYPOLICY("privacy-policy"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>profile</strong></li>
	 * <li>Description: Identifying that a resource representation conforms
	to a certain profile, without affecting the non-profile semantics
	of the resource representation.</li>
	 * <li>Reference: [RFC6906]</li>
	 * <li>Notes: Profile URIs are primarily intended to be used as
	identifiers, and thus clients SHOULD NOT indiscriminately access
	profile URIs.</li>
	 * </ul>
	 */
	PROFILE("profile"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>related</strong></li>
	 * <li>Description: Identifies a related resource.</li>
	 * <li>Reference: [RFC4287]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	RELATED("related"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>replies</strong></li>
	 * <li>Description: Identifies a resource that is a reply to the context
	      of the link.</li>
	 * <li>Reference: [RFC4685]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	REPLIES("replies"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>search</strong></li>
	 * <li>Description: Refers to a resource that can be used to search through
	      the link's context and related resources.</li>
	 * <li>Reference: [http://www.opensearch.org/Specifications/OpenSearch/1.1]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	SEARCH("search"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>section</strong></li>
	 * <li>Description: Refers to a section in a collection of resources.</li>
	 * <li>Reference: [http://www.w3.org/TR/1999/REC-html401-19991224]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	SECTION("section"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>self</strong></li>
	 * <li>Description: Conveys an identifier for the link's context.</li>
	 * <li>Reference: [RFC4287]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	SELF("self"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>service</strong></li>
	 * <li>Description: Indicates a URI that can be used to retrieve a
	      service document.</li>
	 * <li>Reference: [RFC5023]</li>
	 * <li>Notes: When used in an Atom document, this relation type specifies
	      Atom Publishing Protocol service documents by default.  Requested
	      by James Snell.</li>
	 * </ul>
	 */
	SERVICE("service"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>start</strong></li>
	 * <li>Description: Refers to the first resource in a collection of
	      resources.</li>
	 * <li>Reference: [http://www.w3.org/TR/1999/REC-html401-19991224]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	START("start"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>stylesheet</strong></li>
	 * <li>Description: Refers to a stylesheet.</li>
	 * <li>Reference: [http://www.w3.org/TR/html5/links.html#link-type-stylesheet]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	STYLESHEET("stylesheet"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>subsection</strong></li>
	 * <li>Description: Refers to a resource serving as a subsection in a
	      collection of resources.</li>
	 * <li>Reference: [http://www.w3.org/TR/1999/REC-html401-19991224]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	SUBSECTION("subsection"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>successor-version</strong></li>
	 * <li>Description: Points to a resource containing the successor version
	      in the version history.</li>
	 * <li>Reference: [RFC5829]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	SUCCESSORVERSION("successor-version"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>tag</strong></li>
	 * <li>Description: Gives a tag (identified by the given address) that applies to
	      the current document.</li>
	 * <li>Reference: [http://www.w3.org/TR/html5/links.html#link-type-tag]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	TAG("tag"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>terms-of-service</strong></li>
	 * <li>Description: Refers to the terms of service associated with the link's context.</li>
	 * <li>Reference: [RFC6903], section 5</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	TERMSOFSERVICE("terms-of-service"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>timegate</strong></li>
	 * <li>Description: The Target IRI points to a TimeGate for an Original Resource.</li>
	 * <li>Reference: [RFC7089]</li>
	 * <li>Notes: A TimeGate for an Original Resource is a resource that is
	      capable of datetime negotiation to support access to prior states
	      of the Original Resource.</li>
	 * </ul>
	 */
	TIMEGATE("timegate"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>timemap</strong></li>
	 * <li>Description: The Target IRI points to a TimeMap for an Original Resource.</li>
	 * <li>Reference: [RFC7089]</li>
	 * <li>Notes: A TimeMap for an Original Resource is a resource from which
	      a list of URIs of Mementos of the Original Resource is available.</li>
	 * </ul>
	 */
	TIMEMAP("timemap"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>type</strong></li>
	 * <li>Description: Refers to a resource identifying the abstract semantic type of which the link's context is considered to be an instance.</li>
	 * <li>Reference: [RFC6903], section 6</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	TYPE("type"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>up</strong></li>
	 * <li>Description: Refers to a parent document in a hierarchy of
	      documents.</li>
	 * <li>Reference: [RFC5988]</li>
	 * <li>Notes: This relation type registration did not indicate a
	      reference.  Requested by Noah Slater.</li>
	 * </ul>
	 */
	UP("up"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>version-history</strong></li>
	 * <li>Description: Points to a resource containing the version history
	      for the context.</li>
	 * <li>Reference: [RFC5829]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	VERSIONHISTORY("version-history"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>via</strong></li>
	 * <li>Description: Identifies a resource that is the source of the
	      information in the link's context.</li>
	 * <li>Reference: [RFC4287]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	VIA("via"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>working-copy</strong></li>
	 * <li>Description: Points to a working copy for this resource.</li>
	 * <li>Reference: [RFC5829]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	WORKINGCOPY("working-copy"),

	/**
	 * <ul>
	 * <li>Relation Name: <strong>working-copy-of</strong></li>
	 * <li>Description: Points to the versioned resource from which this
	      working copy was obtained.</li>
	 * <li>Reference: [RFC5829]</li>
	 * <li>Notes: </li>
	 * </ul>
	 */
	WORKINGCOPYOF("working-copy-of");
	
	private final String name;
	
	private RegisteredRel(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("RegisteredRel [name=").append(name).append("]").toString();
	}
	
	/**
	 * @param name	The Registered relation name.
	 * @return	The RegisteredRel or null if not found.
	 */
	public static RegisteredRel getByName(String name) {
		for (RegisteredRel rel : RegisteredRel.values())
			if (rel.name.equals(name))
				return rel;
		
		return null;
	}
}
