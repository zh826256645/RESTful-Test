package com.zhonghao.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * ͨ��ʵ�� ExceptionMapper �ӿ�
 * ��ʹ�� @Provider ע�⽫�䶨��Ϊһ�� Provider
 * ����ʵ��ͨ�õ��쳣���������洦��
 * 
 * @author ZhongHao
 * Create on 2016��9��6�� ����9:21:40
 *
 */
@Provider
public class EntityNotFoundMapper implements ExceptionMapper<Jaxrs2GuideNotFoundException>{

	// ���� ExceptionMapper �ӿڵ�ʵ����
	@Override
	public Response toResponse(final Jaxrs2GuideNotFoundException ex) {
		// ���ز������µ���Ӧ
		return Response.status(404)
				.entity(ex.getMessage())
				.type("text/plain").build();
	}

}
