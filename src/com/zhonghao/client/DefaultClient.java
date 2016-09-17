package com.zhonghao.client;

import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;

/**
 * ʹ��Ĭ���������Ŀͻ���
 * 
 * @author �Ӻ�
 * Create on 2016��9��17�� ����10:12:08
 *
 */
public class DefaultClient extends Jaxrs2Client {

	public DefaultClient() {
		buildClient();
	}
	
	// ֱ��ͨ�� ClientBuilder.newClient() �����ͻ���
	void buildClientDefault() {
		client = ClientBuilder.newClient();
	}
	
	// �� ClientConfig ʵ�����������
	// δ������������Ĭ��ʹ�� HttpUrlConnector ������
	// �� ClientConfig ʵ����Ϊ�������� ClientBuilder.newClient() ����ͻ���
	void buildClient() {
		final ClientConfig clientConfig = new ClientConfig();
		clientConfig.property("TestKey", "TestValue");
		client = ClientBuilder.newClient(clientConfig);
		client.property("TestKey2", "TestValue2");
		checkConfig();
	}
}
