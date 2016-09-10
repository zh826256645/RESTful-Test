package com.zhonghao.request.bing;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.apache.log4j.Logger;

// 使用自定义注解 @AirLog
// 实现了名称绑定
// 只有使用了 @AirLog 注解的资源类或者资源类的方法
// 被请求时，才会执行该过滤器
@AirLog
@Priority(Priorities.USER)
public class AirNameBindingFilter implements ContainerRequestFilter, ContainerResponseFilter {
	private static final Logger LOGGER = Logger.getLogger(AirNameBindingFilter.class);
	
	public AirNameBindingFilter() {
		LOGGER.info("Air-NameBinding-Filter initialized");
	}
	
	// 服务器请求过滤
	// filter 实现访问日志
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		LOGGER.debug("Air-NameBinding-ContainerRequestFilter invoked:" + 
				requestContext.getMethod());
		LOGGER.debug(requestContext.getUriInfo().getRequestUri());
	}

	// 服务器响应过滤
	// filter 实现访问日志
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		LOGGER.debug("Air-NameBinding-ContainerResponseFilter invoked:" +
				requestContext.getMethod());
		LOGGER.debug("status=" + responseContext.getStatus());
	}
	
	
}
