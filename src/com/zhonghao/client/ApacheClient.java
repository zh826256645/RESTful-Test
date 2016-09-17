package com.zhonghao.client;

import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

/**
 * ʹ�� Apache �������Ŀͻ���
 * 1.���Ĭ�ϵ����������ܸ���������ǿ��
 * 2.���Խ��з�������������
 * 3.���Խ��г�ʱ����
 * 
 * @author �Ӻ�
 * Create on 2016��9��17�� ����10:11:37
 *
 */
public class ApacheClient extends Jaxrs2Client{
	public ApacheClient() {
		buildClient();
	}
	
	void buildClient() {
		final ClientConfig clientConfig = new ClientConfig();
		/* �������������
		 * clientConfig.property(ClientProperties.PROXY_URI, "http://192.168.0.100");
		 * clientConfig.property(ClientProperties.PROXY_USERNAME, "ZhongHao");
		 * clientConfig.property(ClientProperties.PROXY_PASSWORD, "123456");
		 * ���ӳ�ʱ����
		 * clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 1000);
		 * clientConfig.property(ClientProperties.READ_TIMEOUT, 2000);
		 */
		
		// ���� Apache ������ 
        clientConfig.connectorProvider(new ApacheConnectorProvider());
        client = ClientBuilder.newClient(clientConfig);
        checkConfig();
	}
}
