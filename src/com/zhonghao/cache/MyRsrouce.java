package com.zhonghao.cache;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * 条件 GET
 * 使用 If-Modified-Since 或者 If-None-Match 头信息对 GET 请求方法进行缓存处理的一种方案
 * 
 * @author 钟浩
 * Create on 2016年9月20日 下午4:06:58
 *
 */
@Path("cache")
public class MyRsrouce {
	
	static ConcurrentHashMap<String, Date> map = new ConcurrentHashMap<>();
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            if (i % 30 == 0) {
                result.append(">").append(i);
            }
        }
        return result.toString();
    }
	
    // 使用 If-Modified 缓存处理标识
	@GET
	@Path("last_modified")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getItWithLastModified(@QueryParam("userId") String userId, @Context Request request) {
		// CacheControl 类与 HTTP 头信息 Cache-Control 对应
		CacheControl cacheControl = new CacheControl();
		// 设置最大缓存时间
		cacheControl.setMaxAge(1200);
		// 设置客户端强制不缓存
		// cacheControl.setMustRevalidate(true);
		Date lastModified = map.get(userId);
		Response.ResponseBuilder rb = null;
		if (lastModified != null) {
			// 现访问时间超过“最后访问时间  + 最大缓存时间”将会返回 null
			rb = request.evaluatePreconditions(lastModified);
		}
		// 如果 ResponseBuilder 实例不为空即代表缓存尚未失效
		// 否则重新获取数据并构造 CacheControl 和 lastModified 响应头信息
		if (rb != null) {
			return rb.cacheControl(cacheControl).build();
		} else {
			// 设置最后访问时间
			Date date = new Date();
			map.put(userId, date);
			return Response.ok(getIt()).cacheControl(cacheControl).lastModified(date).build();
		}
	}
	
	// 使用
	@GET
	@Path("e_tag")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getItWithETag(@QueryParam("userId") String userId, @Context Request request) {
		Response.ResponseBuilder rb;
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(1200);
		// EntityTag 类与 HTTP 头信息 ETag 对应
		// 使用用户 ID 的 hashcode 作为 ETage 的值
		EntityTag tag = new EntityTag(userId.hashCode() + "");
		rb = request.evaluatePreconditions(tag);
		// 如果 ResponseBuilder 实例不为空即代表缓存尚未失效
		// 否则重新获取数据并构造 CacheControl 和 ETag 响应头信息
		if (rb != null) {
			return rb.cacheControl(cacheControl).build();
		} else {
			// 完成 HTTP 头信息的设置后，可以填充给 Response 实例，作为响应头信息
			return Response.ok(getIt()).cacheControl(cacheControl).tag(tag).build();
		}
	}
}
