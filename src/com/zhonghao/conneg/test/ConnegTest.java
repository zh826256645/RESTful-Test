package com.zhonghao.conneg.test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import com.zhonghao.annotation.domain.Book;
import com.zhonghao.conneg.ConnegResource;

public class ConnegTest extends JerseyTest {

	private static final Logger LOGGER = Logger.getLogger(ConnegTest.class);

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(ConnegResource.class);
	}

	@Test
	public void testGetJaxb() {
		LOGGER.debug("====XML====");
		WebTarget path = target("conneg-resource").path("123");
		Builder request = path.request(MediaType.APPLICATION_XML_TYPE);
		Book book = request.get(Book.class);
		LOGGER.debug(book);
	}

	@Test
	public void testGetJson() {
		LOGGER.debug("====JSON====");
		WebTarget path = target("conneg-resource").path("123");
		Builder request = path.request();
		// 客户端明确表述格式的质量因素 JSON 高于 XML
		request.header("Accept", "application/xml;q=0.1,application/json;q=0.2");
        Book book = request.get(Book.class);
        LOGGER.debug(book);
	} 	
	

    @Test
    public void testAccept() {
        LOGGER.debug("====CONNEG JSON====");
        WebTarget path = target("conneg-resource").path("book").path("123");
        Builder request = path.request();
        // 优先用户的喜欢
        request.header("Accept", "application/xml;q=0.7,application/json;q=0.2");
        Book book = request.get(Book.class);
        LOGGER.debug(book);
    }

    @Test
    public void testContentLanguage() {
        LOGGER.debug("====CONNEG Language====");
        WebTarget path = target("conneg-resource");
        Builder request = path.request();
        // 根据用户的语言环境，返回响应
        request.header("content-language", "zh-cn");
        String s = request.get(String.class);
        LOGGER.debug(s);
    }
    
    @Test
    public void testGetEntity() {
        WebTarget path = target("conneg-resource");
        Book book = path.request().post(Entity.entity(new Book(123L), MediaType.APPLICATION_JSON)).readEntity(Book.class);
        LOGGER.debug(book);
    }
}
