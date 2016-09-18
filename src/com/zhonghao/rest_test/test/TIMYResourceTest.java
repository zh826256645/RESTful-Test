package com.zhonghao.rest_test.test;


import java.io.IOException;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.zhonghao.request.domian.Book;
import com.zhonghao.request.domian.Books;

/**
 * ��ʹ�ò��Կ�ܵĲ���
 * 
 * @author �Ӻ�
 * Create on 2016��9��18�� ����9:21:17
 *
 */

public class TIMYResourceTest {
	// ������ URI
	private static final String BASE_URI = "http://localhost:8080/webapi/";
	private HttpServer server;
	private WebTarget target;

	// ִ�д��� @Test ���Է���֮ǰ����ִ�и÷���
	@Before
	public void setUp() throws IOException {
		final ResourceConfig config = new ResourceConfig().packages("com.zhonghao.rest_test");
		final URI uri = URI.create(TIMYResourceTest.BASE_URI);
		// ����������
		server = GrizzlyHttpServerFactory.createHttpServer(uri, config, false);
		server.start();
		// �����ͻ���
		final ClientConfig clientConfig = new ClientConfig();
		final Client client = ClientBuilder.newClient(clientConfig);
		// ��ʼ����Դ��λ�ӿ�
		target = client.target(TIMYResourceTest.BASE_URI).path("books");
	}

	// ִ������� @Test ���Է���֮���ִ�и÷���
	@After
	public void tearDown() {
		// �رշ�����
		if (server != null) {
			server.shutdown();
		}
	}

	@Test
	public void testQueryGetXml() {
		System.out.println(">> Test Query Get");
		final WebTarget queryTarget = target.path("/book").queryParam("id", Integer.valueOf(1));
		final Invocation.Builder invocationBuilder = queryTarget.request(MediaType.APPLICATION_XML_TYPE);
		final Response response = invocationBuilder.get();
		final Book result = response.readEntity(Book.class);
		System.out.println(result);
		Assert.assertNotNull(result.getBookId());
		System.out.println("<< Test Query Get");

	}

	@Test
	public void testPathGetJSON() {
		System.out.println(">>Test Path Get");
		final WebTarget pathTarget = target.path("/1");
		final Invocation.Builder invocationBuilder = pathTarget.request(MediaType.APPLICATION_JSON_TYPE);
		final Book result = invocationBuilder.get(Book.class);
		System.out.println(result);
		Assert.assertNotNull(result.getBookId());
		System.out.println("<<Test Path Get");
	}

	@Test
	public void testPostAndDelete() {
		System.out.println(">>Test Post");
		final Book newBook = new Book("Java Restful Web Serviceʵս-" + System.nanoTime());
		final Entity<Book> bookEntity = Entity.entity(newBook, MediaType.APPLICATION_JSON_TYPE);
		final Book savedBook = target.request(MediaType.APPLICATION_JSON_TYPE).post(bookEntity, Book.class);
		Assert.assertNotNull(savedBook.getBookId());
		System.out.println("<<Test Post");

		System.out.println(">>Test Delete");
		final WebTarget deleteTarget = target.path("/" + savedBook.getBookId());
		final Invocation.Builder invocationBuilder = deleteTarget.request();
		final String result = invocationBuilder.delete(String.class);
		System.out.println(result);
		Assert.assertNotNull(result);
		System.out.println("<<Test Delete");
	}

	@Test
	public void testGetAll() {
		System.out.println(">>Test Get All");
		final Invocation.Builder invocationBuilder = target.request();
		final Books result = invocationBuilder.get(Books.class);
		System.out.println(result.getBookList());
		Assert.assertNotNull(result.getBookList());
		System.out.println("<<Test Get All");
	}
}
