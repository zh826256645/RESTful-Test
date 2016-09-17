package com.zhonghao.client;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;

import com.zhonghao.request.domian.Book;

/**
 * JAX-RS2 Client API
 * 
 * @author �Ӻ�
 * Create on 2016��9��16�� ����7:42:39
 *
 */
public class Jaxrs2Client {
	protected final static Logger LOGGER = Logger.getLogger(Jaxrs2Client.class);
	public final static String BASE_URI = "http://localhost:9527/client";
	protected Client client;
	protected ClientConfig clientConfig;
	
	// ��ȡ��ǰ�ͻ���ʵ���������ļ�
	protected void checkConfig() {
		// ��ȡ client ����
		final Configuration newConfiguration = client.getConfiguration();
		// ��ȡ��������
		final Map<String, Object> properties = newConfiguration.getProperties();
		final Iterator<Entry<String, Object>> iterator = properties.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<String, Object> next = iterator.next();
			Jaxrs2Client.LOGGER.debug(next.getKey() + ":" + next.getValue());
		}
	}
	
	public void test() {
		final WebTarget webTarget = client.target(Jaxrs2Client.BASE_URI);
		final WebTarget pathTarget = webTarget.path("books");
		final WebTarget pathTarget2 = pathTarget.path("book");
		final WebTarget queryTarget = pathTarget2.queryParam("bookId", "1");
		Jaxrs2Client.LOGGER.debug(queryTarget.getUri());
		final Invocation.Builder invocationBuilder = queryTarget.request(MediaType.APPLICATION_XML);
		/* response.readEntity will close the connection*/
		final Book book = invocationBuilder.get(Book.class);
		Jaxrs2Client.LOGGER.debug(book);
	}
}
