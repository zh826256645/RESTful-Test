package com.zhonghao.annotation.response.json.test;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.zhonghao.annotation.domain.jackson.JsonBook2;
import com.zhonghao.annotation.domain.jackson.JsonBook3;
import com.zhonghao.annotation.response.json.BookResource_Jettison;
import com.zhonghao.annotation.response.json.JsonContextResolver;

public class JsonTest_Jettison extends JerseyTest {
	private static final Logger LOGGER = Logger.getLogger(JsonTest_Jettison.class);

	@Override
	protected ResourceConfig configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		ResourceConfig resourceConfig = new ResourceConfig(BookResource_Jettison.class);
		// 注册 JettisonFeature 和 JsonContextResolver
		resourceConfig.register(JettisonFeature.class);
		resourceConfig.register(JsonContextResolver.class);
		return resourceConfig;
	}

	@Override
	protected void configureClient(ClientConfig config) {
		// 注册 JettisonFeature 和 JsonContextResolver
		config.register(new JettisonFeature()).register(JsonContextResolver.class);
	}

	// 使用 JETTISON_MAPPED 处理方式
	@Test
	public void testJsonBook2() {
		JsonBook2 book = target("books-jettison").path("jsonbook2").request(MediaType.APPLICATION_JSON).get(JsonBook2.class);
		LOGGER.debug(book);
		// {"jsonBook2":{"bookId":1,"bookName":"Java Restful Web Services实战"}}
	}
	
	// 使用 BADGERFISH 处理方式
	@Test
	public void testJsonBook3() {
		JsonBook3 book = target("books-jettison").path("jsonbook3").request(MediaType.APPLICATION_JSON).get(JsonBook3.class);
		LOGGER.debug(book);
		// {"jsonBook3":{"bookId":{"$":"1"},"bookName":{"$":"Java Restful Web Services实战"}}}
	}
}
