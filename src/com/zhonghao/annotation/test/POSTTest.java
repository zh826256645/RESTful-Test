package com.zhonghao.annotation.test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.annotation.domain.Book;
import com.zhonghao.annotation.source.impl.EBookResourceImpl;

public class POSTTest extends JerseyTest {
    private final static Logger LOGGER = Logger.getLogger(DELETETest.class);
    
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(EBookResourceImpl.class);
    }
    
    @Test
    public void testCreate() {
    	final Book newBook = new Book("book-" + System.nanoTime());
    	MediaType contentTypeMediaType = MediaType.APPLICATION_XML_TYPE;
    	MediaType acceptMediaType = MediaType.APPLICATION_XML_TYPE;
    	final Entity<Book> bookEntity = Entity.entity(newBook, contentTypeMediaType);
    	final Book book = target("book").request(acceptMediaType).post(bookEntity , Book.class);
    	// ���� POST �����Ķ���
    	Assert.assertNotNull(book.getBookId());
    	LOGGER.debug("Server Id=" + book.getBookId());
    }
}
