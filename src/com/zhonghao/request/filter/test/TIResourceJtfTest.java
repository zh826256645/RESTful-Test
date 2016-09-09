package com.zhonghao.request.filter.test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.request.domian.Book;
import com.zhonghao.request.domian.Books;
import com.zhonghao.request.filter.AirLogFilter;
import com.zhonghao.request.resource.BookResource;

public class TIResourceJtfTest extends JerseyTest {
    private static final Logger LOGGER = Logger.getLogger(TIResourceJtfTest.class);
    private static final String BASEURI = "books/";
    
    @Override
    protected Application configure() {
        //enable(org.glassfish.jersey.test.TestProperties.LOG_TRAFFIC);
        //enable(org.glassfish.jersey.test.TestProperties.DUMP_ENTITY);
        ResourceConfig config = new ResourceConfig(BookResource.class);
        // 在服务器端注册服务类日志类 AirLogFilter
        return config.register(com.zhonghao.request.filter.AirLogFilter.class);
    }
    
    @Override
    protected void configureClient(ClientConfig config) {
    	// 在客户端注册服务日志类 AirLogFilter
        config.register(new AirLogFilter());
    }
    
    @Test
    public void testQueryGetXML() {
        TIResourceJtfTest.LOGGER.debug(">>Test Query Get");
        WebTarget queryTarget = target(TIResourceJtfTest.BASEURI + "book").queryParam("id", Integer.valueOf(1));
        Invocation.Builder invocationBuilder = queryTarget.request(MediaType.APPLICATION_XML_TYPE);
        Response response = invocationBuilder.get();
        final Book result = response.readEntity(Book.class);
        TIResourceJtfTest.LOGGER.debug(result);
        Assert.assertNotNull(result.getBookId());

        queryTarget = target(TIResourceJtfTest.BASEURI + "book").queryParam("id", Integer.valueOf(1));
        invocationBuilder = queryTarget.request(MediaType.APPLICATION_XML);
        response = invocationBuilder.get();
        final String beanXml = response.readEntity(String.class);
        TIResourceJtfTest.LOGGER.debug(beanXml);
        TIResourceJtfTest.LOGGER.debug("<<Test Query Get");
    }
    
    @Test
    public void testPathGetJSON() {
        TIResourceJtfTest.LOGGER.debug(">>Test Path Get");
        final WebTarget pathTarget = target(TIResourceJtfTest.BASEURI + "1");
        final Invocation.Builder invocationBuilder = pathTarget.request(MediaType.APPLICATION_JSON);
        final Book result = invocationBuilder.get().readEntity(Book.class);
        TIResourceJtfTest.LOGGER.debug(result);
        Assert.assertNotNull(result.getBookId());
        TIResourceJtfTest.LOGGER.debug("<<Test Path Get");
    }
    
    @Test
    public void testPost() {
        TIResourceJtfTest.LOGGER.debug(">>Test Post");
        final Book newBook = new Book("Java Restful Web Service实战-" + System.nanoTime());
        final Entity<Book> bookEntity = Entity.entity(newBook, MediaType.APPLICATION_JSON_TYPE);
        final Book savedBook = target(TIResourceJtfTest.BASEURI).request(MediaType.APPLICATION_JSON_TYPE).post(bookEntity, Book.class);
        Assert.assertNotNull(savedBook.getBookId());
        TIResourceJtfTest.LOGGER.debug("<<Test Post");
    }
    
    @Test
    public void testGetAll() {
        TIResourceJtfTest.LOGGER.debug(">>Test Get All");
        final Invocation.Builder invocationBuilder = target(TIResourceJtfTest.BASEURI).request();
        final Books result = invocationBuilder.get(Books.class);
        TIResourceJtfTest.LOGGER.debug(result.getBookList());
        Assert.assertNotNull(result.getBookList());
        TIResourceJtfTest.LOGGER.debug("<<Test Get All");
    }
}
