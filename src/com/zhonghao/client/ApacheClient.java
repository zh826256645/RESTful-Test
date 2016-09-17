package com.zhonghao.client;

import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

/**
 * 使用 Apache 连接器的客户端
 * 1.相比默认的连接器功能更完整、更强大
 * 2.可以进行服务器代理设置
 * 3.可以进行超时设置
 * 
 * @author 钟浩
 * Create on 2016年9月17日 下午10:11:37
 *
 */
public class ApacheClient extends Jaxrs2Client{
	public ApacheClient() {
		buildClient();
	}
	
	void buildClient() {
		final ClientConfig clientConfig = new ClientConfig();
		/* 代理服务器配置
		 * clientConfig.property(ClientProperties.PROXY_URI, "http://192.168.0.100");
		 * clientConfig.property(ClientProperties.PROXY_USERNAME, "ZhongHao");
		 * clientConfig.property(ClientProperties.PROXY_PASSWORD, "123456");
		 * 连接超时设置
		 * clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 1000);
		 * clientConfig.property(ClientProperties.READ_TIMEOUT, 2000);
		 */
		
		// 配置 Apache 连接器 
        clientConfig.connectorProvider(new ApacheConnectorProvider());
        client = ClientBuilder.newClient(clientConfig);
        checkConfig();
	}
}
