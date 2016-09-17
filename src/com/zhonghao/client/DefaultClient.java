package com.zhonghao.client;

import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;

/**
 * 使用默认连接器的客户端
 * 
 * @author 钟浩
 * Create on 2016年9月17日 下午10:12:08
 *
 */
public class DefaultClient extends Jaxrs2Client {

	public DefaultClient() {
		buildClient();
	}
	
	// 直接通过 ClientBuilder.newClient() 构建客户端
	void buildClientDefault() {
		client = ClientBuilder.newClient();
	}
	
	// 在 ClientConfig 实例中完成配置
	// 未配置连接器，默认使用 HttpUrlConnector 连接器
	// 将 ClientConfig 实例作为参数传进 ClientBuilder.newClient() 构造客户端
	void buildClient() {
		final ClientConfig clientConfig = new ClientConfig();
		clientConfig.property("TestKey", "TestValue");
		client = ClientBuilder.newClient(clientConfig);
		client.property("TestKey2", "TestValue2");
		checkConfig();
	}
}
