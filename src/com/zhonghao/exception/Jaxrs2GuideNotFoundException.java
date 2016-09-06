package com.zhonghao.exception;

import javax.ws.rs.WebApplicationException;

/**
 * �Զ�����ص�ҵ���쳣��
 * 
 * @author ZhongHao
 * Create on 2016��9��6�� ����9:11:38
 *
 */
// ���� WebApplicationException �ӿ�ʵ����
public class Jaxrs2GuideNotFoundException extends WebApplicationException {
    private static final long serialVersionUID = 1L;

    public Jaxrs2GuideNotFoundException() {
    	// ���� HTTP ״̬
    	// ״̬�� 404
        super(javax.ws.rs.core.Response.Status.NOT_FOUND);
    }

    public Jaxrs2GuideNotFoundException(String message) {
        super(message);
    }
}
