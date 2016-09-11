package com.zhonghao.request.bing.test;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.request.bing.AirNameBindingFilter;
import com.zhonghao.request.bing.AirNameBindingFilter2;
import com.zhonghao.request.domian.Books;
import com.zhonghao.request.resource.BookResource;

/**
 * �������ȼ�
 * 
 * @author ZhongHao
 * Create on 2016��9��11�� ����9:36:25
 *
 */
public class TestPriority extends JerseyTest {
	private static final String BASE_URI = "books/";

	@Override
	protected Application configure() {
        ResourceConfig config = new ResourceConfig(BookResource.class);
        config.register(AirNameBindingFilter.class);
        config.register(AirNameBindingFilter2.class);
		return config;
	}
	
	/**
	 * ִ��˳��
	 * - Air-NameBinding-ContainerRequestFilter invoked:GET
	 * - http://localhost:9998/books/
	 * - Air-NameBinding-ContainerResponseFilter2 Priority+1 invoked
 	 * - {[1:Java Restful Web Serviceʵս:cmpbook, 2:JSF2��RichFaces4ʵս:phei]}
	 * - Air-NameBinding-ContainerResponseFilter2  Priority+1 invoked
	 * - Air-NameBinding-ContainerRequestFilter invoked:GET
	 * - status=200
	 */
    @Test
    public void testGetAll() {
        final Invocation.Builder invocationBuilder = target(BASE_URI).request();
        final Books result = invocationBuilder.get(Books.class);
        Assert.assertNotNull(result.getBookList());
    }
}
