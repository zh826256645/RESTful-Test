package com.zhonghao.asynchronized.sse.test;

import java.util.concurrent.CountDownLatch;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import com.zhonghao.asynchronized.sse.AirSsePubSubResource;

public class SsePubSubTest extends JerseyTest{
	private static final Logger log = LogManager.getLogger(SsePubSubTest.class);
	private static final String ROOT_PATH = "pubsub";
	private static final int READ_TIMEOUT = 30000;
	private static final int testCount = 10;
	private static final String messagePrefix = "pubsub-";

	@Override
	protected Application configure() {
		return new ResourceConfig(AirSsePubSubResource.class, SseFeature.class);
	}

	@Override
	protected void configureClient(ClientConfig config) {
		ClientUtil.buildApacheConfig(config);
		config.property(ClientProperties.READ_TIMEOUT, READ_TIMEOUT);
		config.register(SseFeature.class);
	}

	@Test
	public void testEventSource() throws InterruptedException {
		// ͬ����
		final CountDownLatch latch = new CountDownLatch(testCount);
		// ���� EventSource ʵ��
		// �ͻ��˴����� EventSource ʵ��
		// ��ͨ�������ʵ���������˵� GET �������Ի��  SSE �¼�����ŵ�
		final EventSource eventSource = new EventSource(target().path(ROOT_PATH)) {
			private int i;

			@Override
			public void onEvent(InboundEvent inboundEvent) {
				try {
					// �����¼�����
					String data = inboundEvent.readData(String.class);
					// ��ӡ����������ƶ��¼�������
					log.info("What the server response: {}:{}:{}",
							inboundEvent.getId(), 
							inboundEvent.getName(),
							data);
					// ʹ�ö��Բ��Է��������������Ƿ����Ԥ��
					Assert.assertEquals(messagePrefix + i++, data);
					// ����ͬ���� CountDownLatch ʵ���� countDown ����
					latch.countDown();
				} catch(ProcessingException e) {
					e.printStackTrace();
				}
			}
		};
		for (int i = 0; i < testCount; i++) {
			target().path(ROOT_PATH).request().post(Entity.text(messagePrefix + i));
		}
		try {
			latch.await();
		} finally {
			eventSource.close();
		}
	}
}
