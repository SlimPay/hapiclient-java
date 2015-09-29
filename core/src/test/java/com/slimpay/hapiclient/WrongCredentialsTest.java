package com.slimpay.hapiclient;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.slimpay.hapiclient.exception.HttpClientErrorException;
import com.slimpay.hapiclient.exception.HttpException;
import com.slimpay.hapiclient.http.HapiClient;
import com.slimpay.hapiclient.http.auth.Oauth2BasicAuthentication;

public class WrongCredentialsTest {
	private final class Settings {
		private static final String APIURL = "https://api-sandbox.slimpay.net";
		private static final String PROFILE = "https://api.slimpay.net/alps/v1";
		private static final String APPID = "democreditor01";
		private static final String APPSECRET = "wrongsecret";
		private static final String SCOPE = "api";
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
	public void test() throws HttpException {
		try {
			hapiClient.getEntryPointResource();
			throw new RuntimeException("HttpClientErrorException was not raised while using wrong credentials.");
		} catch (HttpClientErrorException e) {
			assertEquals(401, e.getStatusCode());
			assertEquals("Unauthorized", e.getReasonPhrase());
		}
	}

}
