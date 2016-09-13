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
 * 异步资源类
 * 
 * @author ZhongHao
 * Create on 2016年9月12日 上午10:29:08
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
	
	// 用于定义回调
	public void configResponse(final AsyncResponse asyncResponse) {
		// CompletionCallback 是 JAX-RS2 定义的用于处理异步完成的接口
		// 当请求处理完成时，CompletionCallback 实例的 onComplete() 方法会被调用
		// 实现 onComplete 方法，可以监听请求处理完成时间并实现相关的业务流程
		asyncResponse.register((CompletionCallback) throwable -> {
			if (throwable == null) {
				AsyncResource.LOGGER.info("CompletionCallback-onComplete: OK");
			} else {
				AsyncResource.LOGGER.info("CompletionCallback-onComplete: ERROR: " + throwable.getMessage());
			}
		});
		
		// ConnectionCallback 是 JAX-RS2 定义的连接断开的接口
		// 当请求——响应模型的连接断开时，ConnectionCallback 实力的 onDisconnect() 方法会被调回
		// 实现 onDisconnection 方法可以监听连接断开事件并实现相关业务
		asyncResponse.register((ConnectionCallback) disconnected -> {
			AsyncResource.LOGGER.info("ConnectionCallback-onDisconnect");
			// Status.GONE=410
			disconnected.resume(Response.status(Response.Status.GONE).entity("disconnect!").build());
		});
		
		// TimeoutHandler 是 JAX-RS2 定义的超时处理器接口，用于处理一部响应类超时事件
		// 当预期的超时时间到达后，TimeoutHandler 实例的 handleTimeout() 方法会被调用
		// 实现 handleTimeout 方法可以监听超时时间并处理相关业务
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
				// 执行查询
				Books books = doBatch();
				// 异步响应实例的 resume() 方法被调用
				// 请求处理线程被唤醒
				// 返回值将作为 resume() 方法的参数响应给客户端
				response.resume(books);
			} catch (InterruptedException e) {
				AsyncResource.LOGGER.error(e);
			}
			
		}
		
		// 模拟业务耗时查询
		private Books doBatch() throws InterruptedException {
			Books books = new Books();
			for (int i = 0; i < 10; i++) {
				// 线程休眠
				Thread.sleep(500);
				Book book = new Book(i + 10000l, "Java RESTful Web Services", "华章");
				AsyncResource.LOGGER.debug(book);
				books.getBookList().add(book);
			}
			return books;
		}
	}
}
