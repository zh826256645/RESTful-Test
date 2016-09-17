package com.zhonghao.client.test;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;

import com.zhonghao.client.Jaxrs2Client;

/**
 * 配置测试服务器
 * 
 * @author 钟浩
 * Create on 2016年9月17日 下午1:13:40
 *
 */
public class BasicTest {
	HttpServer server;
	
	@Before
	public void begin() {
		final ResourceConfig resourceConfig = new ResourceConfig();
		final ResourceConfig rc = resourceConfig.packages("com.zhonghao.client");
		final URI uri = UriBuilder.fromUri(Jaxrs2Client.BASE_URI).port(9527).build();
		server = GrizzlyHttpServerFactory.createHttpServer(uri, rc);
	}
	
	@After
	public void end() {
		if (server != null) {
			server.shutdown();
		}
	}
}
