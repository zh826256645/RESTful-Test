package com.zhonghao.request.bing;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

/**
 * 优先级
 * 1.对于同一个扩展点的多个 Provider 的执行的先后顺序是靠优先级排序的
 * 2.优先级定义使用注解  @Priority 
 * 3.优先级的值是一个整型值，常量定义在 javax.ws.rs.Priorities 类中
 * 4.对于 ContainerRequest、PreMatchContainerRequest、ClientRequest 和读写拦截器数值采用升序策略，即数值越小优先级越高
 * 5.对于 ContainerResponse 和 ClientResponse 该数值采用降序策略，即数越大优先级越高
 * 
 * @author ZhongHao
 * Create on 2016年9月11日 下午9:20:02
 *
 */
@Provider
@AirLog
@Priority(Priorities.USER + 1)
public class AirNameBindingFilter2 implements ContainerRequestFilter, ContainerResponseFilter{
	private static final Logger LOGGER = Logger.getLogger(AirNameBindingFilter2.class);

    public AirNameBindingFilter2() {
        LOGGER.info("Air-NameBinding-Filter2 Priority+1 initialized");
    }
    
    // 在 AirNameBindingFilter 的 ContainerRequestFilter 接口方法后执行
	@Override
	public void filter(ContainerRequestContext arg0) throws IOException {
        LOGGER.debug("Air-NameBinding-ContainerRequestFilter2 Priority+1 invoked");
	}

    // 在 AirNameBindingFilter 的 ContainerResponseFilter 接口方法前执行
	@Override
	public void filter(ContainerRequestContext arg0, ContainerResponseContext arg1) throws IOException {
        LOGGER.debug("Air-NameBinding-ContainerResponseFilter2  Priority+1 invoked");
	}

}
