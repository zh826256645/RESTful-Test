package com.zhonghao.client;

import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.grizzly.utils.Pair;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;

/**
 * HTTP ���ӳ�
 * 
 * @author �Ӻ�
 * Create on 2016��9��17�� ����10:26:02
 *
 */
public class PoolingClient<T> extends Jaxrs2Client {
	public final static String GET = "GET";
	public final static String DELETE = "DELETE";
	public final static String POST = "POST";
	public final static String PUT = "PUT";

	public PoolingClient() {
		buildClient();
	}

	// �����ͻ���
	void buildClient() {
		final ClientConfig clientConfig = new ClientConfig();
		final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// �������ӳع���ʵ��
		// �������������
		cm.setMaxTotal(20000);
		// ������ÿ·�ɵ�Ĭ�����������
		cm.setDefaultMaxPerRoute(10000);
		// �����ӳع���ʵ������Ϊ ClientConfig ������ֵ
		clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, cm);
		// ���� Apache ������
		clientConfig.connectorProvider(new ApacheConnectorProvider());
		// �ͻ�������ʵ��Я������������Ϣ
		client = ClientBuilder.newClient(clientConfig);
		checkConfig();
	}

	// ��װ Client
	public T rest(
			// HTTP ���󷽷��� 
			String method,
			// ������Դ��ַ
			String requestUrl,
			// ����ͷ����
			Set<Pair<String, Object>> headParams,
			// �����ѯ����
			Set<Pair<String, Object>> queryParams,
			// ����ý������
			MediaType requestDataType,
			// ����ֵ����
			Class<T> returnType,
			// ����ʵ����Ϣ
			T requestData) {

		// 1.���� Client
		if (clientConfig == null) {
			clientConfig = new ClientConfig();
		}
		Client client = ClientBuilder.newClient(clientConfig);

		// 2.���� WebTarget
		WebTarget webTarget = client.target(requestUrl);
		for (Pair<String, Object> queryPair : queryParams) {
			// �����������
			// ÿ�����ò������᷵��һ���µ� WebTarget ʵ���������Ҫ���ܷ���ֵ
			webTarget = webTarget.queryParam(queryPair.getFirst(), queryPair.getSecond());
		}
		// 3.���� Invocation.Builder
		Invocation.Builder invocationBuilder = webTarget.request(requestDataType);
		for (Pair<String, Object> headParam : headParams) {
			// ��������ͷ��Ϣ
			invocationBuilder.header(headParam.getFirst(), headParam.getSecond());
		}
		// 4.��������ͽ������
		Response response;
		Entity<T> entity;
		switch (method) {
		case GET:
			response = invocationBuilder.get();
			return response.readEntity(returnType);
		case DELETE:
			response = invocationBuilder.delete();
			return response.readEntity(returnType);
		case PUT:
			entity = Entity.entity(requestData, requestDataType);
			response = invocationBuilder.put(entity);
			return response.readEntity(returnType);
		case POST:
			entity = Entity.entity(requestData, requestDataType);
			response = invocationBuilder.post(entity);
			return response.readEntity(returnType);
		default:
			return null;
		}
	}
}
