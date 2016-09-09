package com.zhonghao.request.filter;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;
import org.glassfish.jersey.message.internal.HeaderUtils;

/**
 * 访问日志类 AirLogFilter
 * 实现了 4 种过滤器
 * 旨在记录服务器和客户端的请求和响应
 * 
 * @author ZhongHao
 * Create on 2016年9月9日 下午3:29:18
 *
 */
@PreMatching
public class AirLogFilter
		implements ContainerRequestFilter, ClientRequestFilter, ContainerResponseFilter, ClientResponseFilter {
    private static final Logger LOGGER = Logger.getLogger(AirLogFilter.class);
    private static final String NOTIFICATION_PREFIX = "* ";
    private static final String SERVER_REQUEST = "> ";
    private static final String SERVER_RESPONSE = "< ";
    private static final String CLIENT_REQUEST = "/ ";
    private static final String CLIENT_RESPONSE = "\\ ";
    private static final AtomicLong logSequence = new AtomicLong(0);
    
    private StringBuilder prefixId(StringBuilder b, long id) {
        b.append(Long.toString(id)).append(" ");
        return b;
    }
    
    // 输出请求信息的方法
    private void printRequestLine(final String prefix, StringBuilder b, long id, String method, URI uri) {
        prefixId(b, id).append(NOTIFICATION_PREFIX).append("AirLog - Request received on thread ").append(Thread.currentThread().getName()).append("\n");
        prefixId(b, id).append(prefix).append(method).append(" ").append(uri.toASCIIString()).append("\n");
    }
	
    // 输出响应信息的方法
    private void printResponseLine(final String prefix, StringBuilder b, long id, int status) {
        prefixId(b, id).append(NOTIFICATION_PREFIX).append("AirLog - Response received on thread ").append(Thread.currentThread().getName()).append("\n");
        prefixId(b, id).append(prefix).append(Integer.toString(status)).append("\n");
    }
    
    // 输出请求头信息的
    private void printPrefixedHeaders(final String prefix, StringBuilder b, long id, MultivaluedMap<String, String> headers) {
        for (Map.Entry<String, List<String>> e : headers.entrySet()) {
            List<?> val = e.getValue();
            String header = e.getKey();

            if (val.size() == 1) {
                prefixId(b, id).append(prefix).append(header).append(": ").append(val.get(0)).append("\n");
            } else {
                StringBuilder sb = new StringBuilder();
                boolean add = false;
                for (Object s : val) {
                    if (add) {
                        sb.append(',');
                    }
                    add = true;
                    sb.append(s);
                }
                prefixId(b, id).append(prefix).append(header).append(": ").append(sb.toString()).append("\n");
            }
        }
    }
    
	// 客户端请求过滤器接口中的 filter() 方法
	// 参数为 客户端请求的上下文类 ClientRequestContext
	// 在客户端请求过滤中，输出请求资源地址信息和请求头信息
	@Override
	public void filter(ClientRequestContext context) throws IOException {
		long id = logSequence.incrementAndGet();
		StringBuilder b = new StringBuilder();
		// 获取请求方法和地址
		printRequestLine(CLIENT_REQUEST, b, id, context.getMethod(), context.getUri());
		// 获取请求头信息
		printPrefixedHeaders(CLIENT_REQUEST, b, id, HeaderUtils.asStringHeaders(context.getHeaders()));
		LOGGER.debug(b.toString());
	}
	
	// 服务器请求过滤器接口中的 filter() 方法
	// 参数为 容器请求的上下文类 ClientRequestContext
	// 容器请求过滤中，输出方法、请求资源地址信息和请求头信息
	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		long id = logSequence.incrementAndGet();
		StringBuilder b = new StringBuilder();
		// 获取容器请求方法和请求地址信息
		printRequestLine(SERVER_REQUEST, b, id, context.getMethod(), context.getUriInfo().getRequestUri());
		// 获取请求头信息
		printPrefixedHeaders(SERVER_REQUEST, b, id, context.getHeaders());
		LOGGER.debug(b.toString());
	}
	
	// 服务器响应过滤器接口中的 filter() 方法
	// 参数为：容器请求的上下文类 (ClientRequestContext)、容器响应的上下文类(ContainerResponseContext)
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		long id = logSequence.incrementAndGet();
		StringBuilder b = new StringBuilder();
		// 获取容器响应状态
		printResponseLine(SERVER_RESPONSE, b, id, responseContext.getStatus());
		// 获取容器响应头信息
		printPrefixedHeaders(SERVER_RESPONSE, b, id, HeaderUtils.asStringHeaders(responseContext.getHeaders()));
		LOGGER.debug(b.toString());
	}
	
	// 客户端响应过滤器接口中的 filter() 方法
	// 参数为：客户端请求的上下文类 (ClientRequestContext)、客户端响应的上下文类(ContainerResponseContext)
	@Override
	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
		long id = logSequence.incrementAndGet();
		StringBuilder b = new StringBuilder();
		// 获取响应状态
		printResponseLine(CLIENT_RESPONSE, b, id, responseContext.getStatus());
		// 获取响应头信息
		printPrefixedHeaders(CLIENT_RESPONSE, b, id, responseContext.getHeaders());
		LOGGER.debug(b.toString());
	}
}
