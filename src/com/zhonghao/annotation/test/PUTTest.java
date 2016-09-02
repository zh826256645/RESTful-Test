package com.zhonghao.annotation.test;

import java.util.concurrent.atomic.AtomicLong;

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


public class PUTTest extends JerseyTest {

	public static AtomicLong clientBookSequence = new AtomicLong();
    private final static Logger LOGGER = Logger.getLogger(PUTTest.class);
    
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(EBookResourceImpl.class);
    }

	
	@Test
	public void testNew() {
		final Book newBook = new Book(clientBookSequence.incrementAndGet()
				, "book-" + System.nanoTime());
		MediaType contentTypeMediaType = MediaType.APPLICATION_XML_TYPE;
		MediaType acceptMediaType = MediaType.TEXT_PLAIN_TYPE;
		// Entity 类的第二参数定义该 Entity 实例的媒体类型
		final Entity<Book> bookEntity = Entity.entity(newBook, contentTypeMediaType);
		final String lastUpdate = target("book").request(acceptMediaType)
				.put(bookEntity , String.class);
		// 资源方法定义了 Product 注解和 Consumes 注解
		Assert.assertNotNull(lastUpdate);
		LOGGER.debug(lastUpdate);
		
	}
}
