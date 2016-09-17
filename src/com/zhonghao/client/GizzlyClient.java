package com.zhonghao.client;

import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.grizzly.connector.GrizzlyConnectorProvider;

/**
 * ʹ�� Gizzly �������Ŀͻ���
 * 1.�ڲ�ʹ���첽����ͻ�����Ϊ�ײ������
 * @author �Ӻ�
 * Create on 2016��9��17�� ����10:12:26
 *
 */
public class GizzlyClient extends Jaxrs2Client{
	public GizzlyClient() {
		buildClient();
	}
	
	void buildClient() {
		final ClientConfig clientConfig = new ClientConfig();
		clientConfig.property("TestKey", "TestValue");
		// ����ʹ�� Grizzly ������
		clientConfig.connectorProvider(new GrizzlyConnectorProvider());
		client = ClientBuilder.newClient();
		checkConfig();
	}
}
