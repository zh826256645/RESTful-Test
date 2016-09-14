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
 * 发布——订阅资源类
 * 1.GET 方法用于公布 SSE 通信通道
 * 2.POST 方法用于处理业务和写入 SSE 通道
 * 3.两者关系是先后调用关系
 * 4.出站事件 OutboundEvent 的数据结构包含3个主要信息：id、name 和 data
 * 
 * @author ZhongHao
 * Create on 2016年9月14日 上午9:39:49
 *
 */
@Path("pubsub")
public class AirSsePubSubResource {
	private static final Logger LOGGER = LogManager.getLogger(AirSsePubSubResource.class);
	private static EventOutput eventOutput = new EventOutput();
	
	// 提供 SSE 事件输出通道的资源方法
	@GET
	@Produces(SseFeature.SERVER_SENT_EVENTS)
	public EventOutput publishMessage() throws IOException {
		System.out.println("获得通信通道");
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
		// 执行业务逻辑
		AirSsePubSubResource.LOGGER.info("What the client post:{}", message);
		// 写入 SSE 通道的资源方法
		eventOutput.write(new OutboundEvent.Builder()
				.id(System.nanoTime() + "")
				.name("post-message")
				.data(String.class, message)
				.build());
	}
}
