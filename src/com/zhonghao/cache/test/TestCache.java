package com.zhonghao.cache.test;


import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.cache.MyRsrouce;


public class TestCache extends JerseyTest{
	private static final Logger LOGGER = Logger.getLogger(TestCache.class);
	private static final String BASIC_PATH = "/cache";
	
	@Override
	protected Application configure() {
        enable(org.glassfish.jersey.test.TestProperties.LOG_TRAFFIC);
        enable(org.glassfish.jersey.test.TestProperties.DUMP_ENTITY);
        ResourceConfig resourceConfig = new ResourceConfig();
		return resourceConfig.register(MyRsrouce.class);
	}
	
	// If-Modified
	@Test
	public void TestCache1() {
		Response response = target(BASIC_PATH).path("last_modified").queryParam("userId", "zhonghao").request().get();
		String result = response.readEntity(String.class);
		Assert.assertNotNull(result);
		LOGGER.debug(result);
	}
	
	// If-None-Match
	@Test
	public void TestCache2() {
		Response response = target(BASIC_PATH).path("e_tag").queryParam("userId", "zhonghao").request().get();
		String result = response.readEntity(String.class);
		Assert.assertNotNull(result);
		LOGGER.debug(result);
	}
}
