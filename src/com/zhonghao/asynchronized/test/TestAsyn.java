package com.zhonghao.asynchronized.test;

import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.annotation.domain.Books;
import com.zhonghao.asynchronized.resource.AsyncResource;

public class TestAsyn extends JerseyTest{
	private static final Logger LOGGER = Logger.getLogger(TestAsyn.class);

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		ResourceConfig config = new ResourceConfig(AsyncResource.class);
		return config;
	}

	@Test
	public void testAsyn() {
		Books books = target("books/").request().get().readEntity(Books.class);
		Assert.assertNotNull(books.getBookList().size());
		TestAsyn.LOGGER.debug(books);
	}
}
