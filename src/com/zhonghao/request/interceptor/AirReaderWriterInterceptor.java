package com.zhonghao.request.interceptor;

import org.apache.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.*;
import java.io.IOException;

/**
 * Interceptor 
 * 特点：
 * 1.用于拦截处理
 * 2.通常读写成对
 * 
 * @author ZhongHao
 * Create on 2016年9月10日 下午4:42:20
 *
 */
@Provider
public class AirReaderWriterInterceptor implements ReaderInterceptor, WriterInterceptor {
    private static final Logger LOGGER = Logger.getLogger(AirReaderWriterInterceptor.class);

    public AirReaderWriterInterceptor() {
        LOGGER.info("AirReaderInterceptor initialized");
    }
 
    // 参数 ReaderInterceptorContext（读拦截器的上下文接口）
    @Override
    public Object aroundReadFrom(final ReaderInterceptorContext context) throws IOException, WebApplicationException {
        Object entity = context.proceed();
        LOGGER.debug(entity);
        return entity;
    }

    // 参数 WriterInterceptorContext（写拦截器的上下文接口）
    @Override
    public void aroundWriteTo(final WriterInterceptorContext context) throws IOException, WebApplicationException {
        LOGGER.debug(context.getEntity());
        context.proceed();
    }
}
