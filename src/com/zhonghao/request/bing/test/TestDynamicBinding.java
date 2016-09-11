package com.zhonghao.request.bing.test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.request.bing.AirDynamicFeature;
import com.zhonghao.request.domian.Book;
import com.zhonghao.request.resource.BookResource;

public class TestDynamicBinding extends JerseyTest {
	private static final String BASE_URI = "books/";

	@Override
	protected Application configure() {
		ResourceConfig config = new ResourceConfig(BookResource.class);
		config.register(AirDynamicFeature.class);
		return config;
	}

	// ִ�й����� AirDymanicBindingFilter 
	// ִ�е���ԴΪ BookResource
	// ������Ϊ GET
	@Test
	public void testPathGetJSON() {
		final WebTarget pathTarget = target(BASE_URI).path("1");
		final Invocation.Builder invocationBuilder = pathTarget.request(MediaType.APPLICATION_JSON_TYPE);
		final Book result = invocationBuilder.get(Book.class);
		Assert.assertNotNull(result.getBookId());
	}

	// ִ�й����� AirDymanicBindingFilter 
	// ִ�е���ԴΪ BookResource
	// ���󷽷�����Ϊ POST
	@Test
	public void testPost() {
		final Book newBook = new Book("Java Restful Web Serviceʵս-" + System.nanoTime());
		final Entity<Book> bookEntity = Entity.entity(newBook, MediaType.APPLICATION_JSON_TYPE);
		final Book savedBook = target(BASE_URI).request(MediaType.APPLICATION_JSON_TYPE).post(bookEntity, Book.class);
		Assert.assertNotNull(savedBook.getBookId());
	}
}
