package com.zhonghao.request.bing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

/**
 * 名称绑定注解
 * 使用 @NameBinding 注解可以定义一个运行时的自定义注解
 * 该注解用于定义类级别名称和类的方法名称
 * 
 * @author ZhongHao
 * Create on 2016年9月10日 下午5:00:34
 *
 */
@NameBinding
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AirLog {
}
