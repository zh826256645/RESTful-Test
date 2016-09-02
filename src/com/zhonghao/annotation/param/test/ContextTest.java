package com.zhonghao.annotation.param.test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.zhonghao.annotation.param.ContextResource;

public class ContextTest extends JerseyTest{

	private static final Logger LOGGER = Logger.getLogger(ContextTest.class);
	
	@Override
	protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(ContextResource.class);
	}
	
	@Test
	public void testContexts() {
		final String path = "context-resource";
		String result;
		
		/*http://localhost:9998/context-resource/ZhongHao/China/southern/GuangDong/MeiZhou*/
		final WebTarget pathTarget = target(path).path("ZhongHao").path("China").path("southern").path("GuangDong").path("MeiZhou");
		result = pathTarget.request().get().readEntity(String.class);
		ContextTest.LOGGER.debug(result);
		
		/*http://localhost:9998/context-resource/China/GuangDong/MeiZhou?station=Workers+Village&vehicle=bus*/
        final WebTarget queryTarget = target(path).path("China").path("GuangDong").path("MeiZhou").queryParam("station", "Workers Village")
                .queryParam("vehicle", "bus");
        result = queryTarget.request().get().readEntity(String.class);
        ContextTest.LOGGER.debug(result);
		
	}
}
