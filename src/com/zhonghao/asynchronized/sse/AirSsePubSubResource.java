package com.zhonghao.asynchronized.sse;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

/**
 * ��������������Դ��
 * 1.GET �������ڹ��� SSE ͨ��ͨ��
 * 2.POST �������ڴ���ҵ���д�� SSE ͨ��
 * 3.���߹�ϵ���Ⱥ���ù�ϵ
 * 4.��վ�¼� OutboundEvent �����ݽṹ����3����Ҫ��Ϣ��id��name �� data
 * 
 * @author ZhongHao
 * Create on 2016��9��14�� ����9:39:49
 *
 */
@Path("pubsub")
public class AirSsePubSubResource {
	private static final Logger LOGGER = LogManager.getLogger(AirSsePubSubResource.class);
	private static EventOutput eventOutput = new EventOutput();
	
	// �ṩ SSE �¼����ͨ������Դ����
	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput publishMessage() throws IOException {
		System.out.println("���ͨ��ͨ��");
		return eventOutput;
	}
	
	@GET
	@Path("slow")
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput publishLongMessage() {
		final EventOutput enventOutputLongMessage = new EventOutput();
		AirSsePubSubResource.LOGGER.info("Start to open see chanel.");
		new Thread() {
			public void run() {
				try {
					for (int i = 1; i < 5; i++) {
						enventOutputLongMessage.write(
								new OutboundEvent.Builder()
								.name("handling progress")
								.data(String.class, (i * 20) + "%")
								.build());
					}
					enventOutputLongMessage.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		return enventOutputLongMessage;
	}
	
	@POST
	public void saveMessage(String message) throws IOException {
		// ִ��ҵ���߼�
		AirSsePubSubResource.LOGGER.info("What the client post:{}", message);
		// д�� SSE ͨ������Դ����
		eventOutput.write(new OutboundEvent.Builder()
				.id(System.nanoTime() + "")
				.name("post-message")
				.data(String.class, message)
				.build());
	}
}
