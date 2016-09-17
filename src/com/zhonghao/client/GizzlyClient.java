package com.zhonghao.client;

import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly.connector.GrizzlyConnectorProvider;

/**
 * 使用 Gizzly 连接器的客户端
 * 1.内部使用异步处理客户端作为底层的链接
 * @author 钟浩
 * Create on 2016年9月17日 下午10:12:26
 *
 */
public class GizzlyClient extends Jaxrs2Client{
	public GizzlyClient() {
		buildClient();
	}
	
	void buildClient() {
		final ClientConfig clientConfig = new ClientConfig();
		clientConfig.property("TestKey", "TestValue");
		// 配置使用 Grizzly 连接器
		clientConfig.connectorProvider(new GrizzlyConnectorProvider());
		client = ClientBuilder.newClient();
		checkConfig();
	}
}
