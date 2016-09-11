package com.zhonghao.request.bing;

import javax.ws.rs.POST;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

import com.zhonghao.request.resource.BookResource;

/**
 * 动态绑定
 * 1.动态绑定无需新增注解
 * 2.使用编码的方式，实现动态特征接口 javax.ws.rs.container.DynamicFeature
 * 3.定义扩展点方法的名称、请求方法类型等匹配信息
 * 4.一旦 Provider 匹配当前处理类或方法，面向切面的 Provider 方法即被触发
 * @author ZhongHao
 * Create on 2016年9月11日 下午8:06:30
 *
 */
public class AirDynamicFeature implements DynamicFeature {

	// ResourceInfo 资源类的信息
	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		// 匹配条件一：类匹配（对 BookResource 类及其子类的匹配）
		boolean classMatched = BookResource.class.isAssignableFrom(resourceInfo.getResourceClass());
		// 匹配条件二：方法名称匹配（方法名称包含 getBookBy 的匹配）
		boolean methodNameMatched = resourceInfo.getResourceMethod().getName().contains("getBookBy");
		// 匹配条件三：请求方法类型匹配（与 POST 方法的匹配）
		boolean methodTypeMatched = resourceInfo.getResourceMethod().isAnnotationPresent(POST.class);
		// 资源必须是 BookResource 类或其子类 
		// 方法名包含 getBookBy 或者方法类型为 POST 的方法将匹配成功
		if (classMatched && methodNameMatched || methodTypeMatched) {
			// 只有匹配成功时，才会注册 AirDynamicBindingFilter
			context.register(AirDynamicBindingFilter.class);
		}
	}
}
