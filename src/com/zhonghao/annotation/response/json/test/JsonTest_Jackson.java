package com.zhonghao.annotation.response.json.test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.zhonghao.annotation.domain.Books;
import com.zhonghao.annotation.domain.jackson.JsonBook;
import com.zhonghao.annotation.domain.jackson.JsonHybridBook;
import com.zhonghao.annotation.domain.jackson.JsonNoJaxbBook;
import com.zhonghao.annotation.response.json.BookResource_Jackson;
import com.zhonghao.annotation.response.json.JsonContextProvider;

/**
 * ����
 * ʹ�� Jackson ���� JSON
 * 
 * @author ZhongHao
 * Create on 2016��9��5�� ����2:50:11
 *
 */
public class JsonTest_Jackson extends JerseyTest{
	private static final Logger LOGGER = Logger.getLogger(JsonTest_Jackson.class);


	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		ResourceConfig resourceConfig = new ResourceConfig(BookResource_Jackson.class);
		// ע�� JacksonFeature
		resourceConfig.register(JacksonFeature.class);
		return resourceConfig;
	}
	
	@Override
	protected void configureClient(ClientConfig config) {
		// ע�� JacksonFeature
		config.register(new JacksonFeature());
		config.register(JsonContextProvider.class);
	}
	
	// ���Գ���Ϊ JsonBook ���͵���Դ����
	@Test
	public void testEmptyArray() {
		JsonBook book = target("books-jackson").path("emptybook").request(MediaType.APPLICATION_JSON).get(JsonBook.class);
		LOGGER.debug(book);
	}
	
	// ���Գ���Ϊ JsonHybirdBook ���͵���Դ����
	@Test
	public void testHybrid() {
		JsonHybridBook book = target("books-jackson").path("hybridbook").request(MediaType.APPLICATION_JSON).get(JsonHybridBook.class);
		LOGGER.debug(book);
	}
	
	// ���Գ���Ϊ JsonNoJaxbBook ���͵���Դ����
    @Test
    public void testNoJaxb() {
        JsonNoJaxbBook book = target("books-jackson").path("nojaxbbook").request(MediaType.APPLICATION_JSON).get(JsonNoJaxbBook.class);
        LOGGER.debug(book);
    }
    
    @Test
    public void testBooks() {
    	Books books = target("books-jackson").request(MediaType.APPLICATION_JSON_TYPE).get(Books.class);
    	LOGGER.debug(books);
    }
}
