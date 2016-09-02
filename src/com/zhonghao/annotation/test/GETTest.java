package com.zhonghao.annotation.test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.zhonghao.annotation.source.impl.EBookResourceImpl;

import org.junit.Assert;

public class GETTest extends JerseyTest{
	
	@Override
	protected Application configure() {
		// 加载的是实现类而不是接口
		return new ResourceConfig(EBookResourceImpl.class);
	}

	@Test
	public void testGet() {
		Response response = this.target("book").request().get();
		Assert.assertEquals("150M", response.readEntity(String.class));
	}
	
}
