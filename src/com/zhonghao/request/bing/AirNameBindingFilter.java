package com.zhonghao.request.bing;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.apache.log4j.Logger;

// ʹ���Զ���ע�� @AirLog
// ʵ�������ư�
// ֻ��ʹ���� @AirLog ע�����Դ�������Դ��ķ���
// ������ʱ���Ż�ִ�иù�����
@AirLog
@Priority(Priorities.USER)
public class AirNameBindingFilter implements ContainerRequestFilter, ContainerResponseFilter {
	private static final Logger LOGGER = Logger.getLogger(AirNameBindingFilter.class);
	
	public AirNameBindingFilter() {
		LOGGER.info("Air-NameBinding-Filter initialized");
	}
	
	// �������������
	// filter ʵ�ַ�����־
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		LOGGER.debug("Air-NameBinding-ContainerRequestFilter invoked:" + 
				requestContext.getMethod());
		LOGGER.debug(requestContext.getUriInfo().getRequestUri());
	}

	// ��������Ӧ����
	// filter ʵ�ַ�����־
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		LOGGER.debug("Air-NameBinding-ContainerResponseFilter invoked:" +
				requestContext.getMethod());
		LOGGER.debug("status=" + responseContext.getStatus());
	}
	
	
}
