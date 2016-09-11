package com.zhonghao.request.bing;

import javax.ws.rs.POST;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import com.zhonghao.request.resource.BookResource;

/**
 * ��̬��
 * 1.��̬����������ע��
 * 2.ʹ�ñ���ķ�ʽ��ʵ�ֶ�̬�����ӿ� javax.ws.rs.container.DynamicFeature
 * 3.������չ�㷽�������ơ����󷽷����͵�ƥ����Ϣ
 * 4.һ�� Provider ƥ�䵱ǰ������򷽷������������ Provider ������������
 * @author ZhongHao
 * Create on 2016��9��11�� ����8:06:30
 *
 */
public class AirDynamicFeature implements DynamicFeature {

	// ResourceInfo ��Դ�����Ϣ
	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		// ƥ������һ����ƥ�䣨�� BookResource �༰�������ƥ�䣩
		boolean classMatched = BookResource.class.isAssignableFrom(resourceInfo.getResourceClass());
		// ƥ������������������ƥ�䣨�������ư��� getBookBy ��ƥ�䣩
		boolean methodNameMatched = resourceInfo.getResourceMethod().getName().contains("getBookBy");
		// ƥ�������������󷽷�����ƥ�䣨�� POST ������ƥ�䣩
		boolean methodTypeMatched = resourceInfo.getResourceMethod().isAnnotationPresent(POST.class);
		// ��Դ������ BookResource ��������� 
		// ���������� getBookBy ���߷�������Ϊ POST �ķ�����ƥ��ɹ�
		if (classMatched && methodNameMatched || methodTypeMatched) {
			// ֻ��ƥ��ɹ�ʱ���Ż�ע�� AirDynamicBindingFilter
			context.register(AirDynamicBindingFilter.class);
		}
	}
}
