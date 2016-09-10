package com.zhonghao.request.interceptor;

import org.apache.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.*;
import java.io.IOException;

/**
 * Interceptor 
 * �ص㣺
 * 1.�������ش���
 * 2.ͨ����д�ɶ�
 * 
 * @author ZhongHao
 * Create on 2016��9��10�� ����4:42:20
 *
 */
@Provider
public class AirReaderWriterInterceptor implements ReaderInterceptor, WriterInterceptor {
    private static final Logger LOGGER = Logger.getLogger(AirReaderWriterInterceptor.class);

    public AirReaderWriterInterceptor() {
        LOGGER.info("AirReaderInterceptor initialized");
    }
 
    // ���� ReaderInterceptorContext�����������������Ľӿڣ�
    @Override
    public Object aroundReadFrom(final ReaderInterceptorContext context) throws IOException, WebApplicationException {
        Object entity = context.proceed();
        LOGGER.debug(entity);
        return entity;
    }

    // ���� WriterInterceptorContext��д�������������Ľӿڣ�
    @Override
    public void aroundWriteTo(final WriterInterceptorContext context) throws IOException, WebApplicationException {
        LOGGER.debug(context.getEntity());
        context.proceed();
    }
}
