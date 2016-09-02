package com.zhonghao.annotation.param.test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.annotation.param.BeanParamResource;


public class BeanParamTest extends JerseyTest {

	private static final Logger LOGGER = Logger.getLogger(JerseyTest.class);
	
	@Override
	protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(BeanParamResource.class);
	}
	
	/*http://localhost:9998/bean-resource/China/southern/GuangDong/MeiZhou?station=Workers+Village&vehicle=bus*/
	@Test
	public void testBeanParam() {
		final String path = "bean-resource";
		String result;
		
		final WebTarget queryTarget = target(path).path("China").path("southern").path("GuangDong")
				.path("MeiZhou").queryParam("station", "Workers Village").queryParam("vehicle", "bus");
		result = queryTarget.request().get().readEntity(String.class);
		LOGGER.debug(result);
		Assert.assertEquals("China/southern:MeiZhou:Workers Village:bus", result);
	}
}
