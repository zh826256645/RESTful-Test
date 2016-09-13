package com.zhonghao.asynchronized.resource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.ConnectionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.zhonghao.request.domian.Book;
import com.zhonghao.request.domian.Books;





/**
 * �첽��Դ��
 * 
 * @author ZhongHao
 * Create on 2016��9��12�� ����10:29:08
 *
 */
@Path("books")
@Produces({"application/json;charset=UTF-8", "application/javascript;charset=UTF-8", "text/javascript;charset=UTF-8"})
public class AsyncResource {
	private static final Logger LOGGER = LogManager.getLogger(AsyncResource.class);
	public static final long TIMEOUT = 120;
	final ExecutorService threadPool = Executors.newFixedThreadPool(10);
	
	@GET
	public void getAll(@Suspended final AsyncResponse asyncResponse) {
		configResponse(asyncResponse);
		final BatchRunner batchTask = new BatchRunner(asyncResponse);
		threadPool.submit(batchTask);
	}
	
	// ���ڶ���ص�
	public void configResponse(final AsyncResponse asyncResponse) {
		// CompletionCallback �� JAX-RS2 ��������ڴ����첽��ɵĽӿ�
		// �����������ʱ��CompletionCallback ʵ���� onComplete() �����ᱻ����
		// ʵ�� onComplete ���������Լ������������ʱ�䲢ʵ����ص�ҵ������
		asyncResponse.register((CompletionCallback) throwable -> {
			if (throwable == null) {
				AsyncResource.LOGGER.info("CompletionCallback-onComplete: OK");
			} else {
				AsyncResource.LOGGER.info("CompletionCallback-onComplete: ERROR: " + throwable.getMessage());
			}
		});
		
		// ConnectionCallback �� JAX-RS2 ��������ӶϿ��Ľӿ�
		// �����󡪡���Ӧģ�͵����ӶϿ�ʱ��ConnectionCallback ʵ���� onDisconnect() �����ᱻ����
		// ʵ�� onDisconnection �������Լ������ӶϿ��¼���ʵ�����ҵ��
		asyncResponse.register((ConnectionCallback) disconnected -> {
			AsyncResource.LOGGER.info("ConnectionCallback-onDisconnect");
			// Status.GONE=410
			disconnected.resume(Response.status(Response.Status.GONE).entity("disconnect!").build());
		});
		
		// TimeoutHandler �� JAX-RS2 ����ĳ�ʱ�������ӿڣ����ڴ���һ����Ӧ�೬ʱ�¼�
		// ��Ԥ�ڵĳ�ʱʱ�䵽���TimeoutHandler ʵ���� handleTimeout() �����ᱻ����
		// ʵ�� handleTimeout �������Լ�����ʱʱ�䲢�������ҵ��
		asyncResponse.setTimeoutHandler(asyncResponse0 -> {
			AsyncResource.LOGGER.info("TIMEOUT");
			// Status.SERVICE_UNAVAILABLE=503
			asyncResponse0.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Operation time out.").build());
		});
		asyncResponse.setTimeout(TIMEOUT, TimeUnit.SECONDS);
		
	}
	
	class BatchRunner implements Runnable {
		private final AsyncResponse response;
		
		public BatchRunner(AsyncResponse asyncResponse) {
			this.response = asyncResponse;
		}
		
		@Override
		public void run() {
			try{
				// ִ�в�ѯ
				Books books = doBatch();
				// �첽��Ӧʵ���� resume() ����������
				// �������̱߳�����
				// ����ֵ����Ϊ resume() �����Ĳ�����Ӧ���ͻ���
				response.resume(books);
			} catch (InterruptedException e) {
				AsyncResource.LOGGER.error(e);
			}
			
		}
		
		// ģ��ҵ���ʱ��ѯ
		private Books doBatch() throws InterruptedException {
			Books books = new Books();
			for (int i = 0; i < 10; i++) {
				// �߳�����
				Thread.sleep(500);
				Book book = new Book(i + 10000l, "Java RESTful Web Services", "����");
				AsyncResource.LOGGER.debug(book);
				books.getBookList().add(book);
			}
			return books;
		}
	}
}
