package com.slimpay.hapiclient.hal;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.annotation.ThreadSafe;

import com.slimpay.hapiclient.exception.EmbeddedResourceNotUniqueException;
import com.slimpay.hapiclient.exception.EmbeddedResourceUniqueException;
import com.slimpay.hapiclient.exception.LinkNotUniqueException;
import com.slimpay.hapiclient.exception.LinkUniqueException;
import com.slimpay.hapiclient.exception.RelNotFoundException;

/**
 * The Resource Object described in the
 * JSON Hypertext Application Language (draft-kelly-json-hal-07)
 * @see https://tools.ietf.org/html/draft-kelly-json-hal-07#section-4
 */
@ThreadSafe
public final class Resource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final JsonObject state;
	private final Map<Rel, Object> links;
	private final Map<Rel, Object> embeddedResources;
	
	private Resource(JsonObject state, Map<Rel, Object> links, Map<Rel, Object> embeddedResources) {
		this.state = state != null ? state : Json.createObjectBuilder().build();
		this.links = Collections.unmodifiableMap(links != null ? links : new HashMap<Rel, Object>());
		this.embeddedResources = Collections.unmodifiableMap(embeddedResources != null ? embeddedResources : new HashMap<Rel, Object>());
	}
	
	/**
	 * All the properties of the resource
	 * (<strong>_links</strong> and <strong>_embedded</strong> not included).
	 * @return	JsonObject (may be empty but not null)
	 */
	public JsonObject getState() {
		return state;
	}

	/**
	 * All the links directly available in the resource.
	 * The key is the relation type ({@link Rel}) and the value
	 * is an object that can be either a Link or a Read-Only List of Links.
	 * <p>
	 * Note that there is no guarantees as to the order of the links. 
	 * @return A Read-Only Map<String, Object> (may be empty but not null)
	 */
	public Map<Rel, Object> getAllLinks() {
		return links;
	}

	/**
	 * All the embedded resources directly available in the resource.
	 * The key is the relation type ({@link Rel}) and the value
	 * is an object that can be either a Resource or A Read-Only List of Resources.
	 * <p>
	 * Note that there is no guarantees as to the order of the embedded resources. 
	 * @return A Read-Only Map<String, Object> (may be empty but not null)
	 */
	public Map<Rel, Object> getAllEmbeddedResources() {
		return embeddedResources;
	}

	/**
	 * Finds a unique link by its relation type.
	 * @param rel	The relation type ({@link Rel})
	 * @return	The Link referenced by the given rel.
	 * @throws LinkNotUniqueException
	 * @throws RelNotFoundException
	 */
	public Link getLink(Rel rel) {
		Object link = links.get(rel);
		
		if (link == null)
			throw new RelNotFoundException(rel, links.keySet());
		
		if (!(link instanceof Link))
			throw new LinkNotUniqueException();
		
		return (Link) link;
	}
	
	/**
	 * Finds an array of links by their relation type.
	 * Note that there is no guarantees as to the order of the links. 
	 * @param rel	The relation type ({@link Rel})
	 * @return	A Read-Only List of links referenced by the given rel.
	 * @throws LinkUniqueException
	 * @throws RelNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public List<Link> getLinks(Rel rel) {
		Object links = this.links.get(rel);
		
		if (links == null)
			throw new RelNotFoundException(rel, this.links.keySet());
		
		if (links instanceof Link)
			throw new LinkUniqueException();
		
		return (List<Link>) links;
	}
	
	/**
	 * Finds a unique embedded resource by its relation type.
	 * @param rel	The relation type ({@link Rel})
	 * @return	The Resource referenced by the given rel.
	 * @throws EmbeddedResourceNotUniqueException
	 * @throws RelNotFoundException
	 */
	public Resource getEmbeddedResource(Rel rel) {
		Object resource = embeddedResources.get(rel);
		
		if (resource == null)
			throw new RelNotFoundException(rel, embeddedResources.keySet());
		
		if (!(resource instanceof Resource))
			throw new EmbeddedResourceNotUniqueException();
		
		return (Resource) resource;
	}
	
	/**
	 * Finds an array of embedded resources by their relation type.
	 * Note that there is no guarantees as to the order of the resources. 
	 * @param rel	The relation type ({@link Rel})
	 * @return	The array of embedded resources referenced by the given rel.
	 * @throws EmbeddedResourceUniqueException
	 * @throws RelNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public List<Resource> getEmbeddedResources(Rel rel) {
		Object resources = embeddedResources.get(rel);
		
		if (resources == null)
			throw new RelNotFoundException(rel, embeddedResources.keySet());
		
		if (resources instanceof Resource)
			throw new EmbeddedResourceUniqueException();
		
		return (List<Resource>) resources;
	}
	
	@NotThreadSafe
	public static class Builder {
		private JsonObject state;
		private Map<Rel, Object> links;
		private Map<Rel, Object> embeddedResources;
		
		/**
		 * Start building from an empty Resource then use
		 * {@link #setState(JsonObject)}, {@link #setLinks(Map)}
		 * and {@link #setEmbeddedResources(Map)}
		 */
		public Builder() {
			
		}

		/**
		 * @see Resource#getState()
		 */
		public Builder setState(JsonObject state) {
			this.state = state;
			return this;
		}

		/**
		 * @see Resource#getAllLinks()
		 */
		public Builder setLinks(Map<Rel, Object> links) {
			this.links = links;
			return this;
		}

		/**
		 * @see Resource#getAllEmbeddedResources()
		 */
		public Builder setEmbeddedResources(Map<Rel, Object> embeddedResources) {
			this.embeddedResources = embeddedResources;
			return this;
		}

		public Resource build() {
			return new Resource(state, links, embeddedResources);
		}
	}
	
	/**
	 * Builds a Resource from its JSON representation.
	 * @param json	A JsonObject representing the resource.
	 */
	public static Resource fromJson(JsonObject json) {
		return new Builder()
			.setState(extractState(json))
			.setLinks(extractLinks(json))
			.setEmbeddedResources(extractEmbeddedResources(json))
			.build();
	}
	
	/**
	 * Builds a Resource from its JSON representation.
	 * <p>
	 * Note: a null or empty String will be converted to "{}".
	 * @param json	A JSON String representing the resource.
	 * @throws JsonException
	 */
	public static Resource fromJson(String json) throws JsonException {
		if (json == null || json.trim().isEmpty())
			json = "{}";
		
		JsonReader jsonReader = Json.createReader(new StringReader(json));
		JsonObject jsonObject = jsonReader.readObject();
	    jsonReader.close();
	    
	    return fromJson(jsonObject);
	}

	/**
	 * @see Resource#getState()
	 */
	private static JsonObject extractState(JsonObject json) {
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		for (String key : json.keySet())
			if (!"_links".equals(key) && !"_embedded".equals(key))
				objectBuilder.add(key, json.get(key));
		
		return objectBuilder.build();
	}

	/**
	 * @see Resource#getAllLinks()
	 */
	private static Map<Rel, Object> extractLinks(JsonObject json) {
		Map<Rel, Object> links = new HashMap<Rel, Object>();
		
		if (!json.containsKey("_links"))
			return links;
		
		// Iterate through links
		JsonObject _links = json.getJsonObject("_links");
		for (String name : _links.keySet()) {
		    // The relation type (Rel)
			Rel rel;
			if ((rel = RegisteredRel.getByName(name)) == null)
				rel = new CustomRel(name);
			
			try { // Array of Links
				JsonArray value = _links.getJsonArray(name);
		    	List<Link> arrayOfLinks = new ArrayList<Link>(value.size());
		    	for (int i = 0, j = value.size(); i < j; i++)
		    		arrayOfLinks.add(Link.fromJson(value.getJsonObject(i)));
		    	
		    	links.put(rel, Collections.unmodifiableList(arrayOfLinks));
			} catch (ClassCastException ignored) { // Unique Link
		    	links.put(rel, Link.fromJson(_links.getJsonObject(name)));
			}
		}
		
		return links;
	}

	/**
	 * @see Resource#getAllEmbeddedResources()
	 */
	private static Map<Rel, Object> extractEmbeddedResources(JsonObject json) {
		Map<Rel, Object> embeddedResources = new HashMap<Rel, Object>();
		
		if (!json.containsKey("_embedded"))
			return embeddedResources;
		
		// Iterate through embedded Resources
		JsonObject _embedded = json.getJsonObject("_embedded");
		for (String name : _embedded.keySet()) {
		    // The relation type (Rel)
			Rel rel;
			if ((rel = RegisteredRel.getByName(name)) == null)
				rel = new CustomRel(name);
			
			try { // Array of Resources
				JsonArray value = _embedded.getJsonArray(name);
		    	List<Resource> arrayOfEmbeddedResources = new ArrayList<Resource>(value.size());
		    	for (int i = 0, j = value.size(); i < j; i++)
		    		arrayOfEmbeddedResources.add(Resource.fromJson(value.getJsonObject(i)));
		    	
		    	embeddedResources.put(rel, Collections.unmodifiableList(arrayOfEmbeddedResources));
			} catch (ClassCastException ignored) { // Unique embedded Resource
				embeddedResources.put(rel, Resource.fromJson(_embedded.getJsonObject(name)));
			}
		}
		
		return embeddedResources;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((embeddedResources == null) ? 0 : embeddedResources.hashCode());
		result = prime * result + ((links == null) ? 0 : links.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	/**
	 * Compares two resources by comparing their state, links and embedded resources.
	 * Since all of the above implement Map, see {@link Map#equals(Object)}.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null || getClass() != obj.getClass())
			return false;
		
		Resource other = (Resource) obj;
		
		return  state.equals(other.state) && links.equals(other.links) &&
				embeddedResources.equals(other.embeddedResources);
	}

	/**
	 * Returns the state, links and embedded of the Resource
	 * as a String. The output is <strong>not</strong> a
	 * JSON representation of the Resource.
	 */
	@Override
	public String toString() {
		return new StringBuilder()
			.append("Resource [")
			.append("state=").append(state).append(", ")
			.append("links=").append(links).append(", ")
			.append("embeddedResources=").append(embeddedResources).append("]")
			.toString();
	}

}
