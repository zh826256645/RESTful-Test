package com.zhonghao.request.bing; 

import org.glassfish.jersey.server.ResourceConfig;

import com.zhonghao.request.resource.BookResource;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/aop/*")
public class AirAopConfig extends ResourceConfig {

    public AirAopConfig() {
        register(BookResource.class);
        register(AirNameBindingFilter.class);
    }

    public AirAopConfig(Class<BookResource> registerClass) {
        super(registerClass);
        register(AirNameBindingFilter.class);
    }
}