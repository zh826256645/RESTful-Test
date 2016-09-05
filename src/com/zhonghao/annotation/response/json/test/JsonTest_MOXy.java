package com.zhonghao.annotation.response.json.test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.zhonghao.annotation.domain.Book2;
import com.zhonghao.annotation.domain.Books;
import com.zhonghao.annotation.response.json.BookResource_MOXy;


public class JsonTest_MOXy extends JerseyTest {
    private final static Logger LOGGER = Logger.getLogger(JsonTest_MOXy.class);
    
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(BookResource_MOXy.class);
    }
    
    @Test
    public void testGettingBooks() {
    	Books books = target("books-moxy").request(MediaType.APPLICATION_JSON_TYPE).get(Books.class);
    	for (Book2 book : books.getBookList()) {
			LOGGER.debug(book.getBookName());
		}
    }
}
