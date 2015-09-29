package com.slimpay.hapiclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.util.Arrays;

import javax.json.Json;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.slimpay.hapiclient.exception.HttpClientErrorException;
import com.slimpay.hapiclient.exception.HttpException;
import com.slimpay.hapiclient.hal.CustomRel;
import com.slimpay.hapiclient.hal.Resource;
import com.slimpay.hapiclient.http.Follow;
import com.slimpay.hapiclient.http.HapiClient;
import com.slimpay.hapiclient.http.auth.Oauth2BasicAuthentication;

public class ClientTest {
	private final class Settings {
		private static final String APIURL = "https://api-sandbox.slimpay.net";
		private static final String PROFILE = "https://api.slimpay.net/alps/v1";
		private static final String APPID = "democreditor01";
		private static final String APPSECRET = "demosecret01";
		private static final String CREDITOR_REFERENCE = "democreditor";
		private static final String SCOPE = "api";
		private static final String REL_NS = "https://api.slimpay.net/alps#";
	}
	
	private static HapiClient hapiClient;
	
	@Before
	public void initClient() throws HttpException {
		hapiClient = new HapiClient.Builder()
			.setApiUrl(Settings.APIURL)
			.setProfile(Settings.PROFILE)
			.setAuthenticationMethod(
				new Oauth2BasicAuthentication.Builder()
					.setTokenEndPointUrl("/oauth/token")
					.setUserid(Settings.APPID)
					.setPassword(Settings.APPSECRET)
					.setScope(Settings.SCOPE)
					.build()
			)
			.build();
	}
	
	@After
	public void closeClient() throws IOException {
		hapiClient.close();
	}

	@Test
	public void oneFollowWithGet() throws HttpException {
		// Follow the get-creditors link
		Follow follow = new Follow.Builder(new CustomRel(Settings.REL_NS + "get-creditors"))
			.setUrlVariable("reference", Settings.CREDITOR_REFERENCE)
			.build();
		Resource creditor = hapiClient.send(follow);
		
		assertEquals(Settings.CREDITOR_REFERENCE, creditor.getState().getString("reference"));
	}

	@Test
	public void twoFollowsWithGet() throws HttpException {
		// Follow the get-creditors then get-mandates links
		Follow follow1 = new Follow.Builder(new CustomRel(Settings.REL_NS + "get-creditors"))
			.setUrlVariable("reference", Settings.CREDITOR_REFERENCE)
			.build();
		
		Follow follow2 = new Follow.Builder(new CustomRel(Settings.REL_NS + "get-mandates"))
			.setUrlVariable("rum", "1")
			.build();
		
		Resource mandate = hapiClient.send(Arrays.asList(follow1, follow2));
		
		assertEquals("1", mandate.getState().getString("rum"));
	}

	@Test
	public void oneWrongFollowWithGet() throws HttpException {
		// Follow the get-creditors link
		Follow follow = new Follow.Builder(new CustomRel(Settings.REL_NS + "get-creditors"))
			.setUrlVariable("reference", "noaccesstothiscreditor")
			.build();
		
		try {
			hapiClient.send(follow);
		} catch (HttpClientErrorException e) {
			assertEquals(403, e.getStatusCode());
			assertEquals("Forbidden", e.getReasonPhrase());
		}
	}

	@Test
	public void refreshResource() throws HttpException {
		// Follow the get-creditors link
		Follow follow = new Follow.Builder(new CustomRel(Settings.REL_NS + "get-creditors"))
			.setUrlVariable("reference", Settings.CREDITOR_REFERENCE)
			.build();
		Resource creditor = hapiClient.send(follow);
		
		// Create the same Resource but with no state
		// to simulate a "partial embedded resource".
		Resource creditorToRefresh = new Resource.Builder()
			.setState(Json.createObjectBuilder().build())
			.setLinks(creditor.getAllLinks())
			.setEmbeddedResources(creditor.getAllEmbeddedResources())
			.build();
		assertNotEquals(creditor, creditorToRefresh);
		
		// Try to "refresh" it.
		Resource refreshedCreditor = hapiClient.refresh(creditorToRefresh);
		
		// Compare the original creditor with the refreshed one
		assertEquals(creditor, refreshedCreditor);
	}
}
