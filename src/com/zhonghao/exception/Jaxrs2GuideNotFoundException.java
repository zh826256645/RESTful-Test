package com.zhonghao.exception;

import javax.ws.rs.WebApplicationException;

/**
 * 自定义相关的业务异常类
 * 
 * @author ZhongHao
 * Create on 2016年9月6日 下午9:11:38
 *
 */
// 定义 WebApplicationException 接口实现类
public class Jaxrs2GuideNotFoundException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public Jaxrs2GuideNotFoundException() {
    	// 定义 HTTP 状态
    	// 状态码 404
        super(javax.ws.rs.core.Response.Status.NOT_FOUND);
    }

    public Jaxrs2GuideNotFoundException(String message) {
        super(message);
    }
}
