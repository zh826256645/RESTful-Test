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
 * ������־�� AirLogFilter
 * ʵ���� 4 �ֹ�����
 * ּ�ڼ�¼�������Ϳͻ��˵��������Ӧ
 * 
 * @author ZhongHao
 * Create on 2016��9��9�� ����3:29:18
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
    
    // ���������Ϣ�ķ���
    private void printRequestLine(final String prefix, StringBuilder b, long id, String method, URI uri) {
        prefixId(b, id).append(NOTIFICATION_PREFIX).append("AirLog - Request received on thread ").append(Thread.currentThread().getName()).append("\n");
        prefixId(b, id).append(prefix).append(method).append(" ").append(uri.toASCIIString()).append("\n");
    }
	
    // �����Ӧ��Ϣ�ķ���
    private void printResponseLine(final String prefix, StringBuilder b, long id, int status) {
        prefixId(b, id).append(NOTIFICATION_PREFIX).append("AirLog - Response received on thread ").append(Thread.currentThread().getName()).append("\n");
        prefixId(b, id).append(prefix).append(Integer.toString(status)).append("\n");
    }
    
    // �������ͷ��Ϣ��
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
    
	// �ͻ�������������ӿ��е� filter() ����
	// ����Ϊ �ͻ���������������� ClientRequestContext
	// �ڿͻ�����������У����������Դ��ַ��Ϣ������ͷ��Ϣ
	@Override
	public void filter(ClientRequestContext context) throws IOException {
		long id = logSequence.incrementAndGet();
		StringBuilder b = new StringBuilder();
		// ��ȡ���󷽷��͵�ַ
		printRequestLine(CLIENT_REQUEST, b, id, context.getMethod(), context.getUri());
		// ��ȡ����ͷ��Ϣ
		printPrefixedHeaders(CLIENT_REQUEST, b, id, HeaderUtils.asStringHeaders(context.getHeaders()));
		LOGGER.debug(b.toString());
	}
	
	// ����������������ӿ��е� filter() ����
	// ����Ϊ ����������������� ClientRequestContext
	// ������������У����������������Դ��ַ��Ϣ������ͷ��Ϣ
	@Override
	public void filter(ContainerRequestContext context) throws IOException {
		long id = logSequence.incrementAndGet();
		StringBuilder b = new StringBuilder();
		// ��ȡ�������󷽷��������ַ��Ϣ
		printRequestLine(SERVER_REQUEST, b, id, context.getMethod(), context.getUriInfo().getRequestUri());
		// ��ȡ����ͷ��Ϣ
		printPrefixedHeaders(SERVER_REQUEST, b, id, context.getHeaders());
		LOGGER.debug(b.toString());
	}
	
	// ��������Ӧ�������ӿ��е� filter() ����
	// ����Ϊ������������������� (ClientRequestContext)��������Ӧ����������(ContainerResponseContext)
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		long id = logSequence.incrementAndGet();
		StringBuilder b = new StringBuilder();
		// ��ȡ������Ӧ״̬
		printResponseLine(SERVER_RESPONSE, b, id, responseContext.getStatus());
		// ��ȡ������Ӧͷ��Ϣ
		printPrefixedHeaders(SERVER_RESPONSE, b, id, HeaderUtils.asStringHeaders(responseContext.getHeaders()));
		LOGGER.debug(b.toString());
	}
	
	// �ͻ�����Ӧ�������ӿ��е� filter() ����
	// ����Ϊ���ͻ���������������� (ClientRequestContext)���ͻ�����Ӧ����������(ContainerResponseContext)
	@Override
	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
		long id = logSequence.incrementAndGet();
		StringBuilder b = new StringBuilder();
		// ��ȡ��Ӧ״̬
		printResponseLine(CLIENT_RESPONSE, b, id, responseContext.getStatus());
		// ��ȡ��Ӧͷ��Ϣ
		printPrefixedHeaders(CLIENT_RESPONSE, b, id, responseContext.getHeaders());
		LOGGER.debug(b.toString());
	}
}
