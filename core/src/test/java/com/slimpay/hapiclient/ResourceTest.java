package com.slimpay.hapiclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.json.JsonObject;

import org.junit.Test;

import com.slimpay.hapiclient.hal.CustomRel;
import com.slimpay.hapiclient.hal.RegisteredRel;
import com.slimpay.hapiclient.hal.Resource;

public class ResourceTest {
	private static final String JSON_REPRESENTATION = new StringBuilder()
		.append("{")
		.append("  \"_links\": {")
		.append("    \"self\": { \"href\": \"/orders\" },")
		.append("    \"curies\": [{")
		.append("      \"name\": \"acme\",")
		.append("      \"href\": \"http://docs.acme.com/relations/{rel}\",")
		.append("      \"templated\": true")
		.append("    }],")
		.append("    \"next\": { \"href\": \"/orders?page=2\" },")
		.append("    \"find\": { \"href\": \"/orders{?id}\", \"templated\": true }")
		.append("  },")
		.append("  \"_embedded\": {")
		.append("    \"acme:orders\": [{")
		.append("        \"_links\": {")
		.append("          \"self\": { \"href\": \"/orders/123\" },")
		.append("          \"acme:basket\": { \"href\": \"/baskets/98712\" },")
		.append("          \"acme:customer\": { \"href\": \"/customers/7809\" }")
		.append("        },")
		.append("        \"total\": 30.00,")
		.append("        \"currency\": \"USD\",")
		.append("        \"status\": \"shipped\"")
		.append("      },{")
		.append("        \"_links\": {")
		.append("          \"self\": { \"href\": \"/orders/124\" },")
		.append("          \"acme:basket\": { \"href\": \"/baskets/97213\" },")
		.append("          \"acme:customer\": { \"href\": \"/customers/12369\" }")
		.append("        },")
		.append("        \"total\": 20.00,")
		.append("        \"currency\": \"USD\",")
		.append("        \"status\": \"processing\"")
		.append("    }]")
		.append("  },")
		.append("  \"currentlyProcessing\": 14,")
		.append("  \"shippedToday\": 20")
		.append("}").toString();

	/**
	 * Test the parsing of the JSON representation of a resource:
	 * - properties
	 * - links
	 * - embedded resources
	 * - links in embedded resources
	 */
	@Test
	public void parseJsonRepresentation() {
		Resource resource = Resource.fromJson(JSON_REPRESENTATION);
		
		// Check the properties
		assertEquals(14, resource.getState().getInt("currentlyProcessing"));
		assertEquals(20, resource.getState().getInt("shippedToday"));
		
		// We got 4 links
		assertEquals(4, resource.getAllLinks().size());
		
		// Are links templated
		assertFalse(resource.getLink(RegisteredRel.NEXT).isTemplated());
		assertTrue(resource.getLink(new CustomRel("Find")).isTemplated()); // Capitalized on purpose
		
		// We got 1 embedded array of resources
		assertEquals(1, resource.getAllEmbeddedResources().size());
		
		// We got 2 embedded orders
		List<Resource> orders = resource.getEmbeddedResources(new CustomRel("acme", "orders"));
		assertEquals(2, orders.size());
		Resource order1 = orders.get(0), order2 = orders.get(1);
		JsonObject order1State = order1.getState(), order2State = order2.getState();
		
		// Check the totals
		double	total1 = order1State.getJsonNumber("total").doubleValue(),
				total2 = order2State.getJsonNumber("total").doubleValue();
		assertTrue(total1 == 20.00 && total2 == 30.00 || total2 == 20.00 && total1 == 30.00);
		
		// Check the links of the embedded orders
		assertEquals(3, order1.getAllLinks().size());
		
		// Check the the embedded resources of the embedded orders
		assertEquals(0, order1.getAllEmbeddedResources().size());
	}
	
	@Test
	public void equalResources() {
		Resource resource1 = Resource.fromJson(JSON_REPRESENTATION);
		Resource resource2 = Resource.fromJson(JSON_REPRESENTATION);
		
		assertTrue("States are not equal.",
				resource1.getState().equals(resource2.getState()));
		
		assertTrue("Links are not equal.",
				resource1.getAllLinks().equals(resource2.getAllLinks()));
		
		assertTrue("Embedded resources are not equal.",
				resource1.getAllEmbeddedResources().equals(resource2.getAllEmbeddedResources()));
		
		assertTrue("Resources are not equal.",
				resource1.equals(resource2));
	}

}
