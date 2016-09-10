package com.zhonghao.request.bing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

/**
 * ���ư�ע��
 * ʹ�� @NameBinding ע����Զ���һ������ʱ���Զ���ע��
 * ��ע�����ڶ����༶�����ƺ���ķ�������
 * 
 * @author ZhongHao
 * Create on 2016��9��10�� ����5:00:34
 *
 */
@NameBinding
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AirLog {
}
