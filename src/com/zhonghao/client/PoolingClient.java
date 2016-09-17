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
 * HTTP 连接池
 * 
 * @author 钟浩
 * Create on 2016年9月17日 下午10:26:02
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

	// 构建客户端
	void buildClient() {
		final ClientConfig clientConfig = new ClientConfig();
		final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// 配置连接池管理实例
		// 设置最大连接数
		cm.setMaxTotal(20000);
		// 设置了每路由的默认最大连接数
		cm.setDefaultMaxPerRoute(10000);
		// 将连接池管理实例配置为 ClientConfig 的属性值
		clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, cm);
		// 配置 Apache 连接器
		clientConfig.connectorProvider(new ApacheConnectorProvider());
		// 客户端配置实例携带了连接器信息
		client = ClientBuilder.newClient(clientConfig);
		checkConfig();
	}

	// 封装 Client
	public T rest(
			// HTTP 请求方法名 
			String method,
			// 请求资源地址
			String requestUrl,
			// 请求头参数
			Set<Pair<String, Object>> headParams,
			// 请求查询参数
			Set<Pair<String, Object>> queryParams,
			// 请求媒体类型
			MediaType requestDataType,
			// 返回值类型
			Class<T> returnType,
			// 请求实体信息
			T requestData) {

		// 1.构造 Client
		if (clientConfig == null) {
			clientConfig = new ClientConfig();
		}
		Client client = ClientBuilder.newClient(clientConfig);

		// 2.构造 WebTarget
		WebTarget webTarget = client.target(requestUrl);
		for (Pair<String, Object> queryPair : queryParams) {
			// 设置请求参数
			// 每次设置参数都会返回一个新的 WebTarget 实例，因此需要接受返回值
			webTarget = webTarget.queryParam(queryPair.getFirst(), queryPair.getSecond());
		}
		// 3.构造 Invocation.Builder
		Invocation.Builder invocationBuilder = webTarget.request(requestDataType);
		for (Pair<String, Object> headParam : headParams) {
			// 设置请求头信息
			invocationBuilder.header(headParam.getFirst(), headParam.getSecond());
		}
		// 4.发起请求和结果处理
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
