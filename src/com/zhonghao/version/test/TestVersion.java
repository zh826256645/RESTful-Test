package com.zhonghao.version.test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.version.MyResource;

public class TestVersion extends JerseyTest{
	private static final Logger LOGGER = Logger.getLogger(TestVersion.class);
	private static final String BASIC_PATH = "/version";
	
	@Override
	protected Application configure() {
        enable(org.glassfish.jersey.test.TestProperties.LOG_TRAFFIC);
        enable(org.glassfish.jersey.test.TestProperties.DUMP_ENTITY);
        ResourceConfig resourceConfig = new ResourceConfig();
		return resourceConfig.register(MyResource.class);
	}
	
	// Test v1.0
	@Test
	public void testVersion1() {
		Response response = target(BASIC_PATH).path("v1.0").request().get();
		Link link = response.getLink("currentVersion");
		Assert.assertNotNull(link);
		System.out.println(link);
	}
	
	// Test v2.0
	@Test
	public void testVersion2() {
		Response response = target(BASIC_PATH).path("v2.0").request().get();
		String result = response.readEntity(String.class);
		Assert.assertNotNull(result);
		System.out.println(result);
	}
	
	// Test head-version
	@Test
	public void testHeadVersion() {
		Response response = target(BASIC_PATH).path("head-version").request()
				.header("X-API-Version", "2").get();
		String result = response.readEntity(String.class);
		Assert.assertNotNull(result);
		System.out.println(result);
	}
}
