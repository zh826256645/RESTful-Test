package com.zhonghao.request.bing.test;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.request.bing.AirAopConfig;
import com.zhonghao.request.domian.Book;
import com.zhonghao.request.domian.Books;


public class TestNamingBinding extends JerseyTest{
	private static final String BASE_URI = "books/";
	
	@Override
	protected Application configure() {
		// AirAopConfig 内部注册了 AirNameBindingFilter 和 BookResource
		return new AirAopConfig();
	}
	
	// 所请求资源对应的方法没有使用 @AirLog 注解
	// 并不会被过滤
	// 但是过滤器还是会初始化两次
	@Test
	public void testPathGetJSON() {
		final WebTarget pathTarget = target(BASE_URI).path("1");
		final Invocation.Builder invocationBuilder = pathTarget.request(MediaType.APPLICATION_JSON);
		final Book result = invocationBuilder.get().readEntity(Book.class);
		Assert.assertNotNull(result.getBookId());
	}
	
	// 所请求资源对应的方法使用了 @AirLog 注解
	// 会被过滤器 AirBindingFilter 过滤
	@Test
	public void testGetAll() {
		final Invocation.Builder invocationBuilder = target(BASE_URI).request();
		final Books books = invocationBuilder.get(Books.class);
		Assert.assertNotNull(books.getBookList());
	}
}

