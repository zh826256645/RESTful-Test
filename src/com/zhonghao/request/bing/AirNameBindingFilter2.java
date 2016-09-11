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
 * ���ȼ�
 * 1.����ͬһ����չ��Ķ�� Provider ��ִ�е��Ⱥ�˳���ǿ����ȼ������
 * 2.���ȼ�����ʹ��ע��  @Priority 
 * 3.���ȼ���ֵ��һ������ֵ������������ javax.ws.rs.Priorities ����
 * 4.���� ContainerRequest��PreMatchContainerRequest��ClientRequest �Ͷ�д��������ֵ����������ԣ�����ֵԽС���ȼ�Խ��
 * 5.���� ContainerResponse �� ClientResponse ����ֵ���ý�����ԣ�����Խ�����ȼ�Խ��
 * 
 * @author ZhongHao
 * Create on 2016��9��11�� ����9:20:02
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
    
    // �� AirNameBindingFilter �� ContainerRequestFilter �ӿڷ�����ִ��
	@Override
	public void filter(ContainerRequestContext arg0) throws IOException {
        LOGGER.debug("Air-NameBinding-ContainerRequestFilter2 Priority+1 invoked");
	}

    // �� AirNameBindingFilter �� ContainerResponseFilter �ӿڷ���ǰִ��
	@Override
	public void filter(ContainerRequestContext arg0, ContainerResponseContext arg1) throws IOException {
        LOGGER.debug("Air-NameBinding-ContainerResponseFilter2  Priority+1 invoked");
	}

}
