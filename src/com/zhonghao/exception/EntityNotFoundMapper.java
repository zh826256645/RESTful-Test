package com.zhonghao.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * 通过实现 ExceptionMapper 接口
 * 并使用 @Provider 注解将其定义为一个 Provider
 * 可以实现通用的异常的面向切面处理
 * 
 * @author ZhongHao
 * Create on 2016年9月6日 下午9:21:40
 *
 */
@Provider
public class EntityNotFoundMapper implements ExceptionMapper<Jaxrs2GuideNotFoundException>{

	// 定义 ExceptionMapper 接口的实现类
	@Override
	public Response toResponse(final Jaxrs2GuideNotFoundException ex) {
		// 拦截并返回新的响应
		return Response.status(404)
				.entity(ex.getMessage())
				.type("text/plain").build();
	}

}
