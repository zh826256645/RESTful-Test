package com.zhonghao.request.bing;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

import org.apache.log4j.Logger;

// 动态绑定了该过滤器
public class AirDynamicBindingFilter implements ContainerRequestFilter {
	private static final Logger LOGGER = Logger.getLogger(AirDynamicBindingFilter.class);
	
    public AirDynamicBindingFilter() {
        LOGGER.info("Air-Dynamic-Binding-Filter initialized");
    }
    
	@Override
	public void filter(ContainerRequestContext arg0) throws IOException {
		AirDynamicBindingFilter.LOGGER.debug("Air-Dynamic-Binding-Filter invoked");
	}

}
